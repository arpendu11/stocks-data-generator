package com.practice.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.services.Producer;

@RestController
@RequestMapping("/publish")
public class KafkaPublishController {
	
	@Autowired
	Producer producer;
	
	@PostMapping("/stocks")
	public void sendMessageToKafkaTopic() throws ParseException {
		producer.sendMessage();
	}
}
