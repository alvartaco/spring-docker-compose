package com.programmingwithmati.springdockercompose;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.programmingwithmati.springdockercompose.model.Transaction;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransactionProducer {

    public static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static void main(String[] args) {
        try (KafkaProducer<String, String> transactionProducer = new KafkaProducer<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094",
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        ))) {


            List<Transaction> data1 = List.of(
                    Transaction.builder()
                            .id(UUID.randomUUID().toString())
                            .accountNumber("123456789")
                            .transactionDate(LocalDateTime.now())
                            .type("DEPOSIT")
                            .currency("GBP")
                            .status("SETTLED")
                            .amount(new BigDecimal(10))
                            .build()
            );

            data1.stream()
                    .map(t -> new ProducerRecord<>("transactions", t.getAccountNumber(), toJson(t)))
                    .forEach(record -> send(transactionProducer, record));
        }

    }

    @SneakyThrows
    private static void send(KafkaProducer<String, String> transactionProducer, ProducerRecord<String, String> record) {
        transactionProducer.send(record).get();
    }

    @SneakyThrows
    private static String toJson(Transaction t) {
        return OBJECT_MAPPER.writeValueAsString(t);
    }
}
