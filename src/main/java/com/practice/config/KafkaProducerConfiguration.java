package com.practice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.practice.model.Stock;
import com.practice.model.StockProfile;

import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

@Configuration
public class KafkaProducerConfiguration {
	
	@Bean
	public ProducerFactory<String, Stock> stockProducerFactory() {
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return new DefaultKafkaProducerFactory<String, Stock>(config);
	}
	
	@Bean
	public ProducerFactory<String, StockProfile> stockProfileProducerFactory() {
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return new DefaultKafkaProducerFactory<String, StockProfile>(config);
	}
	
	@Bean
	public KafkaTemplate<String, Stock> stockKafkaTemplate() {
		return new KafkaTemplate<String, Stock>(stockProducerFactory());
	}
	
	@Bean
	public KafkaTemplate<String, StockProfile> stockProfileKafkaTemplate() {
		return new KafkaTemplate<String, StockProfile>(stockProfileProducerFactory());
	}
	
}
