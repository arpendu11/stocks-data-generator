package com.practice;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.practice.model.Stock;
import com.practice.model.StockProfile;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;


@SpringBootApplication
@EnableAutoConfiguration
public class StocksDataGeneratorApplication {

	public static void main(String[] args) {
		
		final Logger logger = LoggerFactory.getLogger(StocksDataGeneratorApplication.class);
		final String STOCKS_SUBJECT = "stocks";
		final String STOCKS_PROFILES_SUBJECT = "stocks_profiles";
		
		SpringApplication.run(StocksDataGeneratorApplication.class, args);
		
		try {
            Connection nc = Nats.connect("nats://vlab045701.dom045700.lab:4222");

            // Use a latch to wait for 10 messages to arrive
            CountDownLatch latch = new CountDownLatch(100);

            // Create a dispatcher and inline message handler
            Dispatcher d = nc.createDispatcher((msg) -> {
                Gson gson = new Gson();
                String json = new String(msg.getData(), StandardCharsets.UTF_8);
                Stock stock = gson.fromJson(json, Stock.class);
                
                // Use the object
                logger.info(String.format("$$ -> Subscribing stocks message --> %s", json));

                latch.countDown();
            });
            Dispatcher d1 = nc.createDispatcher((msg) -> {
                Gson gson = new Gson();
                String json = new String(msg.getData(), StandardCharsets.UTF_8);
                StockProfile stockProfile = gson.fromJson(json, StockProfile.class);
                
                // Use the object
                logger.info(String.format("$$ -> Subscribing stockProfile message --> %s", json));

                latch.countDown();
            });

            // Subscribe
            d.subscribe(STOCKS_SUBJECT);
            d1.subscribe(STOCKS_PROFILES_SUBJECT);

            // Wait for a message to come in
            latch.await(); 

            // Close the connection
            nc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

}
