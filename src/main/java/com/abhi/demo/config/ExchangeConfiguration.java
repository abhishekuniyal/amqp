package com.abhi.demo.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfiguration {

	private static final boolean NON_DURABLE = false;
	public final static String TOPIC_QUEUE_1_NAME = "com.abhishek.spring-amqp-simple.topic.queue1";
	public final static String TOPIC_QUEUE_2_NAME = "com.abhishek.spring-amqp-simple.topic.queue2";
	public final static String TOPIC_EXCHANGE_NAME = "com.abhishek.spring-amqp-simple.topic.exchange";
	public static final String BINDING_PATTERN_IMPORTANT = "*.important.*";
	public static final String BINDING_PATTERN_ERROR = "#.error";

	public final static String FANOUT_QUEUE_1_NAME = "com.abhishek.spring-amqp-simple.fanout.queue1";
	public final static String FANOUT_QUEUE_2_NAME = "com.abhishek.spring-amqp-simple.fanout.queue2";
	public final static String FANOUT_EXCHANGE_NAME = "com.abhishek.spring-amqp-simple.fanout.exchange";

	@Bean
	public Declarables topicBinding() {

		Queue topicQueue1 = new Queue(TOPIC_QUEUE_1_NAME, NON_DURABLE);
		Queue topicQueue2 = new Queue(TOPIC_QUEUE_2_NAME, NON_DURABLE);

		TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME, NON_DURABLE, false);

		return new Declarables(topicQueue1, topicQueue2, topicExchange,
				BindingBuilder.bind(topicQueue1).to(topicExchange).with(BINDING_PATTERN_IMPORTANT),
				BindingBuilder.bind(topicQueue2).to(topicExchange).with(BINDING_PATTERN_ERROR));

	}

	@Bean
	public Declarables fanoutBinding() {

		Queue fanQueue1 = new Queue(FANOUT_QUEUE_1_NAME, NON_DURABLE);
		Queue fanQueue2 = new Queue(FANOUT_QUEUE_2_NAME, NON_DURABLE);

		FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE_NAME, NON_DURABLE, false);

		return new Declarables(fanQueue1, fanQueue2, fanoutExchange, 
				BindingBuilder.bind(fanQueue1).to(fanoutExchange),
				BindingBuilder.bind(fanQueue2).to(fanoutExchange));

	}
}
