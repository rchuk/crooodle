package org.ukma.spring.crooodle.usersvc.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserJmsConfig {

	@Bean(name = "userJmsMessageConverter")
	public MessageConverter jacksonJmsMessageConverter(ObjectMapper om) {

		om.registerModule(new JavaTimeModule());

		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		converter.setObjectMapper(om);
		return converter;
	}

	@Bean(name = "userJmsTemplate")
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, @Qualifier("userJmsMessageConverter") MessageConverter messageConverter) {
		JmsTemplate template = new JmsTemplate(connectionFactory);
		template.setMessageConverter(messageConverter);
		return template;
	}
}
