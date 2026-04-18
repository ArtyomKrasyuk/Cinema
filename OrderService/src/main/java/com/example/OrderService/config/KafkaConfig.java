package com.example.OrderService.config;

import com.example.OrderService.events.*;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
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
    @Value("${app.kafka.process.payment.topic}")
    private String processPaymentTopic;
    @Value("${app.kafka.payment.succeeded.topic}")
    private String paymentSucceededTopic;
    @Value("${app.kafka.payment.failed.topic}")
    private String paymentFailedTopic;
    @Value("${app.kafka.refund.event.topic}")
    private String refundTopic;

    @Bean
    public NewTopic createProcessPaymentTopic(){
        return TopicBuilder.name(processPaymentTopic).partitions(1).build();
    }

    @Bean
    public NewTopic createPaymentSucceededTopic(){
        return TopicBuilder.name(paymentSucceededTopic).partitions(1).build();
    }

    @Bean
    public NewTopic createPaymentFailedTopic(){
        return TopicBuilder.name(paymentFailedTopic).partitions(1).build();
    }

    @Bean
    public NewTopic createRefundEventTopic(){
        return TopicBuilder.name(refundTopic).partitions(1).build();
    }

    private Map<String, Object> getProducerConfig(){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return config;
    }

    @Bean
    public ProducerFactory<String, ProcessPaymentEvent> producerFactoryForProcessPayment(){
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, ProcessPaymentEvent> kafkaTemplateForProcessPayment(
            ProducerFactory<String, ProcessPaymentEvent> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, RefundEvent> producerFactoryForRefund(){
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, RefundEvent> kafkaTemplateForRefund(
            ProducerFactory<String, RefundEvent> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, PaymentSucceededEvent> consumerFactoryForPaymentSucceeded() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        var jsonDeserializer = new JsonDeserializer<>(PaymentSucceededEvent.class);
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
    public ConcurrentKafkaListenerContainerFactory<String, PaymentSucceededEvent> containerFactoryForPaymentSucceeded(
            ConsumerFactory<String, PaymentSucceededEvent> consumerFactory
    ){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, PaymentSucceededEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PaymentFailedEvent> consumerFactoryForPaymentFailed() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        var jsonDeserializer = new JsonDeserializer<>(PaymentFailedEvent.class);
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
    public ConcurrentKafkaListenerContainerFactory<String, PaymentFailedEvent> containerFactoryForPaymentFailed(
            ConsumerFactory<String, PaymentFailedEvent> consumerFactory
    ){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, PaymentFailedEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, RefundSucceededEvent> consumerFactoryForRefundSucceeded() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        var jsonDeserializer = new JsonDeserializer<>(RefundSucceededEvent.class);
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
    public ConcurrentKafkaListenerContainerFactory<String, RefundSucceededEvent> containerFactoryForRefundSucceeded(
            ConsumerFactory<String, RefundSucceededEvent> consumerFactory
    ){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, RefundSucceededEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, RefundFailedEvent> consumerFactoryForRefundFailed() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        var jsonDeserializer = new JsonDeserializer<>(RefundFailedEvent.class);
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
    public ConcurrentKafkaListenerContainerFactory<String, RefundFailedEvent> containerFactoryForRefundFailed(
            ConsumerFactory<String, RefundFailedEvent> consumerFactory
    ){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, RefundFailedEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
