package com.SpringKafka.KafkaT;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import com.SpringKafka.KafkaT.config.KafkaConfigProps;
import com.SpringKafka.KafkaT.domain.CustomerVisitEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class KafkaTApplication {
	
	@Autowired
	private ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(KafkaTApplication.class, args);
	}
	
	
	@Bean
	public ApplicationRunner runner(final KafkaTemplate<String, String> kafkaTemplate,final KafkaConfigProps kafkaConfigProps) throws JsonProcessingException {
	
		
		CustomerVisitEvent event =CustomerVisitEvent.builder()
		.CustName("shubham")
		.customerId(UUID.randomUUID().toString())
		.dateTime(LocalDateTime.now())
		.build();
		
	String payload=	objectMapper.writeValueAsString(event);
	
	return args ->{
		kafkaTemplate.send(kafkaConfigProps.getTopic(),payload);
	};
		
	}
	
	@KafkaListener(topics ="customer.visit")
	public String listens(final String in)
	{
		System.out.println(in);
		return in;
	}

}
