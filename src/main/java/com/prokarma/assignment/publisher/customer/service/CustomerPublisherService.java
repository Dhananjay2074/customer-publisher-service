package com.prokarma.assignment.publisher.customer.service;

import com.prokarma.assignment.publisher.customer.domain.CustomerResponse;
import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;

public interface CustomerPublisherService {

    public CustomerResponse invokeCustomerResponse(KafkaCustomerRequest kafkaCustomerRequest);

}