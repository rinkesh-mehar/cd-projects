package com.krishi.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishi.entity.StaticData;
import com.krishi.repository.StaticDataRepository;

/*Kafka configuration*/
@EnableKafka
@Configuration
public class KafkaConfig {

	@Autowired
	private StaticDataRepository repo;

	/*Json scrilizer and descrilizer configuration*/
	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}

	/*Kafka topic configuration*/
	@Bean
	public String getTopicName() {
		StaticData data = repo.findByKey("kafkaRightTopicName");
		if (data != null) {
			return data.getValue();
		} else {
			return "";
		}
	}

	/*kafka consumer factory configuration*/
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		StaticData data = repo.findByKey("kafkaBrokerURL");
		Map<String, Object> props = new HashMap<>();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, data.getValue());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "drkrishi-group");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		return factory;
	}

}
