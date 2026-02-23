package com.example.OrderService.config;

import com.example.OrderService.events.ProcessPaymentEvent;
import com.example.OrderService.events.RefundEvent;
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

    @Value("spring.kafka.bootstrap-servers")
    private String bootstrapServers;
    @Value("spring.kafka.consumer.group-id")
    private String consumerGroupId;
    @Value("spring.kafka.consumer.properties.spring.json.trusted.packages")
    private String trustedPackages;
    @Value("app.kafka.process.payment.topic")
    private String processPaymentTopic;
    @Value("app.kafka.payment.succeeded.topic")
    private String paymentSucceededTopic;
    @Value("app.kafka.payment.failed.topic")
    private String paymentFailedTopic;
    @Value("app.kafka.refund.event.topic")
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
    public ConsumerFactory<String, Object> consumerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory(
            ConsumerFactory<String, Object> consumerFactory
    ){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
