package com.practice.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.services.JetStreamService;

@RestController
@RequestMapping("/js/publish")
public class JetStreamPublishController {

	@Autowired
	JetStreamService jetStreamService;
	
	@GetMapping("/stocks")
	public void sendMessageToKafkaTopic() throws ParseException {
		jetStreamService.publishMessage();
	}
}
