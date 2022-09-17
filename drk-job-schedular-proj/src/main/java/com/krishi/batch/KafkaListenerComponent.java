package com.krishi.batch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishi.model.KafkaResponseModel;
import com.krishi.service.KafkaRightsService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;

@Component
public class KafkaListenerComponent {
	private static final Logger LOGGER = LogManager.getLogger(KafkaListenerComponent.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private KafkaRightsService kafkaRightsService;

	/*listen to kafka topic, update the records in the database and trigger sms*/
/*	@KafkaListener(topics = "#{@getTopicName}")
	public void listen(@Payload String message, Acknowledgment acknowledgment) {
		try {
			System.out.println("Message from kafka topic --> " + message);
			LOGGER.info("Message from kafka topic --> {}" + message);
			KafkaResponseModel model = objectMapper.readValue(message, KafkaResponseModel.class);
			kafkaRightsService.updateRights(model.getData());
			acknowledgment.acknowledge();
		} catch (Exception e) {
			System.out.println("\n\nInvalid data:- " + message + "\n");
			System.out.print(e.getMessage() + "\n");
			e.printStackTrace();
		}
	}*/
}
