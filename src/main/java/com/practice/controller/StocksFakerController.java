package com.practice.controller;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;
import com.practice.model.Stock;
import com.practice.model.StockProfile;

@RestController
@RequestMapping("/random")
public class StocksFakerController {
	
	@GetMapping("/stocks")
	public List<Stock> getRandomStocks() throws ParseException {
		
		Faker faker = new Faker();
		List<Stock> stockCollection = new ArrayList<Stock>();
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));   // This line converts the given date into UTC time zone
		final Date dateObjFrom = sdf.parse("2001-01-01T01:37:56");
		final Date dateObjTo = sdf.parse("2020-03-31T01:37:56");
		
		for (int i = 0; i < 2000; i++) {
			Stock stock = new Stock(faker.date().between(dateObjFrom, dateObjTo).toString(),
					faker.company().name(),
					Float.parseFloat(faker.commerce().price(20, 1000)),
					Float.parseFloat(faker.commerce().price(500, 1000)),
					Float.parseFloat(faker.commerce().price(800, 1000)),
					Float.parseFloat(faker.commerce().price(5, 200)),
					faker.random().nextInt(100, 1000000));
			stockCollection.add(stock);
		}
		
		return stockCollection;		
		
	}
	
	@GetMapping("/stock-profiles")
	public List<StockProfile> getRandomStockProfiles() throws ParseException {
		
		Faker faker = new Faker();
		List<StockProfile> stockProfileCollection = new ArrayList<StockProfile>();
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));   // This line converts the given date into UTC time zone
		final Date dateObjFrom = sdf.parse("2000-01-01T01:37:56");
		final Date dateObjTo = sdf.parse("2000-12-31T01:37:56");
		
		for (int i = 0; i < 1000; i++) {
			StockProfile stockProfile = new StockProfile(faker.company().name(),
					faker.company().profession(),
					faker.company().industry(),
					faker.address().fullAddress(),
					faker.date().between(dateObjFrom, dateObjTo).toString());
			stockProfileCollection.add(stockProfile);		
		}
		
		return stockProfileCollection;		
		
	}
	
}
