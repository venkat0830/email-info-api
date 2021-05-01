//package com.email.api;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//
//@EnableKafka
//@Configuration
//public class KafkaConfiguration {
//	@Bean
//	public ConsumerFactory<String, String> consumerFactory() {
//		Map<String, Object> props = new HashMap<>();
//		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		props.put(ConsumerConfig.GROUP_ID_CONFIG, "recon-group");
//		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		return new DefaultKafkaConsumerFactory<>(props);
//	}
//
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(consumerFactory());
//		return factory;
//	}
//}

// @Bean
// public ConsumerFactory<String, Record> recordConsumerFactory() {
// Map<String, Object> props = new HashMap<>();
// props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
// props.put(ConsumerConfig.GROUP_ID_CONFIG, "recon-group");
// props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
// StringDeserializer.class);
// props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
// StringDeserializer.class);
//// props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//// props.put("max.poll.records", "100");
// //props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.kafka.consumer.Record");
// return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new
// JsonDeserializer<>(Record.class));
// }
//
// @Bean
// public ConcurrentKafkaListenerContainerFactory<String, Record>
// kafkaListenerContainerFactory() {
// ConcurrentKafkaListenerContainerFactory<String, Record> factory = new
// ConcurrentKafkaListenerContainerFactory<>();
// factory.setConsumerFactory(recordConsumerFactory());
//
// return factory;
// }
