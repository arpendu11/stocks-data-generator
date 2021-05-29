package com.practice.services;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.practice.controller.StocksFakerController;
import com.practice.model.Stock;
import com.practice.model.StockProfile;

import io.nats.client.Connection;
import io.nats.client.JetStream;
import io.nats.client.JetStreamManagement;
import io.nats.client.Nats;
import io.nats.client.api.PublishAck;
import io.nats.client.api.StorageType;
import io.nats.client.api.StreamConfiguration;
import io.nats.client.api.StreamInfo;
import io.nats.client.support.JsonUtils;

@Service
public class JetStreamService {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	private static final String STOCKS_SUBJECT = "stocks";
	private static final String STOCKS_PROFILES_SUBJECT = "stocks_profiles";

	@Autowired
	StocksFakerController stocksFakerController;

	@Transactional
	public void publishMessage() throws ParseException {
		List<Stock> stocks = stocksFakerController.getRandomStocks();
		List<StockProfile> stocksProfiles = stocksFakerController.getRandomStockProfiles();
		try (Connection nc = Nats.connect("nats://vlab045701.dom045700.lab:4222")) {
			JetStreamManagement jsm = nc.jetStreamManagement();

			// Build the configuration
			StreamConfiguration streamConfig = StreamConfiguration.builder()
					.name("StockFaker")
					.subjects(STOCKS_SUBJECT, STOCKS_PROFILES_SUBJECT)
					.storageType(StorageType.Memory)
					.build();

			// Create the stream
			StreamInfo streamInfo = jsm.addStream(streamConfig);
			JsonUtils.printFormatted(streamInfo);
			JetStream js = nc.jetStream();

			for (Stock stock : stocks) {
				// use Gson to encode the object to JSON
	            GsonBuilder builder = new GsonBuilder();
	            Gson gson = builder.create();
	            String json = gson.toJson(stock);
				logger.info(String.format("$$ -> Publishing stocks message --> %s", json));
				PublishAck ack = js.publish(STOCKS_SUBJECT, json.getBytes(StandardCharsets.UTF_8));
	            JsonUtils.printFormatted(ack);
			}
			for (StockProfile stockProfile : stocksProfiles) {
				// use Gson to encode the object to JSON
	            GsonBuilder builder = new GsonBuilder();
	            Gson gson = builder.create();
	            String json = gson.toJson(stockProfile);
				logger.info(String.format("$$ -> Publishing stocks profile message --> %s", json));
				PublishAck ack = js.publish(STOCKS_PROFILES_SUBJECT, json.getBytes(StandardCharsets.UTF_8));
	            JsonUtils.printFormatted(ack);
			}
			
			// Make sure the message goes through before we close
            nc.flush(Duration.ZERO);
            nc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
