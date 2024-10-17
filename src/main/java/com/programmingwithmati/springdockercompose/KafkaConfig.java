package com.programmingwithmati.springdockercompose;

import com.programmingwithmati.springdockercompose.model.Transaction;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Transaction> kafkaListenerContainerFactory(
          ConsumerFactory<String, Transaction> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, Transaction> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    return factory;
  }
  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "transaction-group");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    return props;
  }
  @Bean
  public ConsumerFactory<String, Transaction> consumerFactory() {
    JsonDeserializer<Transaction> deserializer = new JsonDeserializer<>(Transaction.class);
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(true);

    return new DefaultKafkaConsumerFactory<>(
            consumerConfigs(),
            new StringDeserializer(),
            deserializer
    );
  }


}
