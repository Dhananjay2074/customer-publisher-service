package com.prokarma.assignment.publisher.customer.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prokarma.assignment.publisher.customer.converter.DefaultCustomerPublisherRequestConverter;
import com.prokarma.assignment.publisher.customer.domain.CustomerRequest;
import com.prokarma.assignment.publisher.customer.domain.CustomerResponse;
import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;
import com.prokarma.assignment.publisher.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerPublisherController {

    Logger logger = LoggerFactory.getLogger(CustomerPublisherController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DefaultCustomerPublisherRequestConverter defaultCustomerRequestConverter;

    @PostMapping("/save")
    public ResponseEntity<CustomerResponse> saveCustomerData(
            @RequestHeader(value = "Authorization", required = true) String authorization,
            @RequestHeader(value = "Activity-Id", required = true) String activityId,
            @RequestHeader(value = "Application-Id", required = true) String applicationId,
            @Valid @RequestBody CustomerRequest customerRequest) {

        KafkaCustomerRequest kafkaCustomerRequest =
                defaultCustomerRequestConverter.convert(customerRequest);

        KafkaCustomerRequest maskedKafkaCustomerRequest =
                defaultCustomerRequestConverter.maskKafkaCustomerRequest(kafkaCustomerRequest);

        logger.info("Request Body : {}", maskedKafkaCustomerRequest);

        CustomerResponse response = customerService.invokeCustomerResponse(kafkaCustomerRequest);

        logger.info("Response Body : {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
