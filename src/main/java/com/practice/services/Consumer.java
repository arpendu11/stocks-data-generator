package com.practice.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	
//	@KafkaListener(topics = "stocks", groupId = "group_json", containerFactory = "kafkaListenerContainerFactory")
	public void consumeStockJson(String stock) {
		System.out.println("Consumed Stock JSON message: " + stock);
	}
	
//	@KafkaListener(topics = "stocks_profiles", groupId = "group_json", containerFactory = "kafkaListenerContainerFactory")
	public void consumeStockProfileJson(String stockProfile) {
		System.out.println("Consumed Stock Profile JSON message: " + stockProfile);
	}
}
