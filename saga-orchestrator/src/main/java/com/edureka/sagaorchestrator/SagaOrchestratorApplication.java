package com.edureka.sagaorchestrator;

import com.edureka.sagaorchestrator.domain.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SagaOrchestratorApplication {

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
		typeIdMappings.put("JMS_TYPE", Order.class);

		converter.setTypeIdMappings(typeIdMappings);
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	public static void main(String[] args) {
		SpringApplication.run(SagaOrchestratorApplication.class, args);
	}

}
