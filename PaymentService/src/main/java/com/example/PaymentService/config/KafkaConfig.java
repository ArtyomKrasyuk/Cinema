package com.example.PaymentService.config;

import com.example.PaymentService.events.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    private Map<String, Object> getProducerConfig(){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return config;
    }

    @Bean
    public ProducerFactory<String, PaymentSucceededEvent> producerFactoryForSucceededPayment(){
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    @Bean
    public ProducerFactory<String, PaymentFailedEvent> producerFactoryForFailedPayment(){
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, PaymentSucceededEvent> kafkaTemplateForSucceededPayment(
            ProducerFactory<String, PaymentSucceededEvent> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaTemplate<String, PaymentFailedEvent> kafkaTemplateForFailedPayment(
            ProducerFactory<String, PaymentFailedEvent> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaTemplate<String, RefundSucceededEvent> kafkaTemplateForSucceededRefund(
            ProducerFactory<String, RefundSucceededEvent> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaTemplate<String, RefundFailedEvent> kafkaTemplateForFailedRefund(
            ProducerFactory<String, RefundFailedEvent> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, ProcessPaymentEvent> consumerFactoryForProcessPayment() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        var jsonDeserializer = new JsonDeserializer<>(ProcessPaymentEvent.class);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setUseTypeHeaders(false);
        var errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(jsonDeserializer);
        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                errorHandlingDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProcessPaymentEvent> containerFactoryForProcessPayment(
            ConsumerFactory<String, ProcessPaymentEvent> consumerFactory
    ){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, ProcessPaymentEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, RefundEvent> consumerFactoryForRefund() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        var jsonDeserializer = new JsonDeserializer<>(RefundEvent.class);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setUseTypeHeaders(false);
        var errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(jsonDeserializer);
        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                errorHandlingDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RefundEvent> containerFactoryForRefund(
            ConsumerFactory<String, RefundEvent> consumerFactory
    ){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, RefundEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
