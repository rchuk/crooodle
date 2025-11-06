package org.ukma.spring.crooodle.reservationsvc.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ResJmsConfig {

	@Bean(name = "resJmsMessageConverter")
	public MessageConverter jacksonJmsMessageConverter(ObjectMapper om) {

		om.registerModule(new JavaTimeModule());

		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		converter.setObjectMapper(om);
		return converter;
	}

	@Bean(name = "p2pResJmsTemplate")
	public JmsTemplate p2pJmsTemplate(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
																		@Qualifier("resJmsMessageConverter")MessageConverter messageConverter) {
		JmsTemplate template = new JmsTemplate(connectionFactory);
		template.setMessageConverter(messageConverter);
		return template;
	}

	@Bean(name = "pubSubResJmsTemplate")
	public JmsTemplate pubSubJmsTemplate(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
																			 @Qualifier("resJmsMessageConverter")MessageConverter messageConverter) {
		JmsTemplate template = new JmsTemplate(connectionFactory);
		template.setMessageConverter(messageConverter);
		template.setPubSubDomain(true);
		return template;
	}

	@Bean(name = "resQueueListenerFactory")
	public DefaultJmsListenerContainerFactory queueListenerFactory(
		@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
		@Qualifier("resJmsMessageConverter") MessageConverter messageConverter) {

		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(messageConverter);
		factory.setPubSubDomain(false);
		return factory;
	}

	@Bean(name = "resTopicListenerFactory")
	public DefaultJmsListenerContainerFactory topicListenerFactory(@Qualifier("jmsConnectionFactory")ConnectionFactory connectionFactory,
																																 @Qualifier("resJmsMessageConverter")MessageConverter messageConverter) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(messageConverter);
		factory.setPubSubDomain(true);
		return factory;
	}
}
