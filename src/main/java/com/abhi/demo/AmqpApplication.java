package com.abhi.demo;

import java.nio.channels.Channel;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.abhi.demo.config.ExchangeConfiguration;

@SpringBootApplication
public class AmqpApplication {

	private static String ROUTING_KEY_USER_IMPORTANT_WARN = "user.important.warn";
	private static String ROUTING_KEY_USER_IMPORTANT_ERROR = "user.important.error";

	public static void main(String[] args) {
		SpringApplication.run(AmqpApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(RabbitTemplate rabbitTemplate) {

		String message = " payload is broadcast";

		return args -> {
			rabbitTemplate.convertAndSend(ExchangeConfiguration.FANOUT_EXCHANGE_NAME, "", "fanout" + message);
			rabbitTemplate.convertAndSend(ExchangeConfiguration.TOPIC_EXCHANGE_NAME, ROUTING_KEY_USER_IMPORTANT_WARN,
					"topic important warn" + message);
			rabbitTemplate.convertAndSend(ExchangeConfiguration.TOPIC_EXCHANGE_NAME, ROUTING_KEY_USER_IMPORTANT_ERROR,
					"topic important error" + message);
		};
	}

	@RabbitListener(queues = { ExchangeConfiguration.FANOUT_QUEUE_1_NAME })
	public void receiveMessageFromFanout1( String message) throws Exception {
		//throw new Exception("went workng");
		System.out.println("Received fanout 1 message: " + message);
	}

	@RabbitListener(queues = { ExchangeConfiguration.FANOUT_QUEUE_2_NAME })
	public void receiveMessageFromFanout2(String message) {
		System.out.println("Received fanout 2 message: " + message);
	}

	@RabbitListener(queues = { ExchangeConfiguration.TOPIC_QUEUE_1_NAME })
	public void receiveMessageFromTopic1(String message) {
		System.out.println("Received topic 1 (" + ExchangeConfiguration.BINDING_PATTERN_IMPORTANT + ") message: " + message);
	}

	@RabbitListener(queues = { ExchangeConfiguration.TOPIC_QUEUE_2_NAME })
	public void receiveMessageFromTopic2(String message) {
		System.out.println("Received topic 2 (" + ExchangeConfiguration.BINDING_PATTERN_ERROR + ") message: " + message);
	}

}
