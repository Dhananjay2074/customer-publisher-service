package com.prokarma.assignment.publisher.customer.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.prokarma.assignment.publisher.customer.constant.CustomerPublisherConstant;
import com.prokarma.assignment.publisher.customer.domain.CustomerRequest;
import com.prokarma.assignment.publisher.customer.kafka.domain.KafkaCustomerRequest;

@Component
public class DefaultCustomerPublisherRequestConverter
        implements CustomerPublisherRequestConverter<CustomerRequest, KafkaCustomerRequest> {

    @Override
    public KafkaCustomerRequest convert(CustomerRequest customerRequest) {

        KafkaCustomerRequest kafkaCustomerRequest = new KafkaCustomerRequest();
        kafkaCustomerRequest.setCustomerNumber(customerRequest.getCustomerNumber());
        kafkaCustomerRequest.setFirstName(customerRequest.getFirstName());
        kafkaCustomerRequest.setLastName(customerRequest.getLastName());
        kafkaCustomerRequest.setBirthdate(customerRequest.getBirthdate());
        kafkaCustomerRequest.setCountry(customerRequest.getCountry());
        kafkaCustomerRequest.setCountryCode(customerRequest.getCountryCode());
        kafkaCustomerRequest.setMobileNumber(customerRequest.getMobileNumber());
        kafkaCustomerRequest.setEmail(customerRequest.getEmail());
        kafkaCustomerRequest.setAddress(customerRequest.getAddress().toString());
        kafkaCustomerRequest
                .setCustomerStatus(String.valueOf(customerRequest.getCustomerStatus().name()));
        return kafkaCustomerRequest;
    }

    public KafkaCustomerRequest maskKafkaCustomerRequest(
            KafkaCustomerRequest kafkaCustomerRequest) {

        KafkaCustomerRequest maskedKafkaCustomerRequest = new KafkaCustomerRequest();

        BeanUtils.copyProperties(kafkaCustomerRequest, maskedKafkaCustomerRequest);

        maskedKafkaCustomerRequest.setCustomerNumber(kafkaCustomerRequest.getCustomerNumber()
                .replaceAll(CustomerPublisherConstant.CUSTOMER_NUMBER_REGEX.getValue(),
                        CustomerPublisherConstant.REPLACEMENT_CHARACTER.getValue()));

        maskedKafkaCustomerRequest.setEmail(kafkaCustomerRequest.getEmail().replaceFirst(
                CustomerPublisherConstant.EMAIL_REGEX.getValue(),
                CustomerPublisherConstant.EMAIL_REPLACEMENT_CHARACTER.getValue()));

        maskedKafkaCustomerRequest.setBirthdate(kafkaCustomerRequest.getBirthdate().replaceAll(
                CustomerPublisherConstant.BIRTHDATE_REGEX.getValue(),
                CustomerPublisherConstant.REPLACEMENT_CHARACTER.getValue()));

        return maskedKafkaCustomerRequest;
    }

}
