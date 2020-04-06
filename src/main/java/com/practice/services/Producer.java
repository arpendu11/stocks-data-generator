package com.practice.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.controller.StocksFakerController;
import com.practice.model.Stock;
import com.practice.model.StockProfile;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;

@Service
public class Producer {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	private static final String STOCKS_TOPIC = "stocks";
	private static final String STOCKS_PROFILES_TOPIC = "stocks_profiles";

	@Autowired
	StocksFakerController stocksFakerController;

	@Autowired
	private KafkaTemplate<String, Stock> kafkaStockTemplate;
	
	@Autowired
	private KafkaTemplate<String, StockProfile> kafkaStockProfileTemplate;

	@Transactional
	public void sendMessage() throws ParseException {
		List<Stock> stocks = stocksFakerController.getRandomStocks();
		List<StockProfile> stocksProfiles = stocksFakerController.getRandomStockProfiles();
		for (Stock stock: stocks) {
			logger.debug(String.format("$$ -> Producing stocks message --> %s", stock));
			this.kafkaStockTemplate.send(STOCKS_TOPIC, stock);
		}
		for (StockProfile stockProfile: stocksProfiles) {
			logger.debug(String.format("$$ -> Producing stocks profile message --> %s", stockProfile));
			this.kafkaStockProfileTemplate.send(STOCKS_PROFILES_TOPIC, stockProfile);
		}
	}
}
