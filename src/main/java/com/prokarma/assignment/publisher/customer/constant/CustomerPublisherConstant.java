package com.prokarma.assignment.publisher.customer.constant;

public enum CustomerPublisherConstant {
    BIRTHDATE_REGEX("[^-\\n](?=.*-[^-]*$)"), CUSTOMER_NUMBER_REGEX(
            "\\d(?=(?:\\D*\\d){0,3}\\D*$)"), EMAIL_REGEX(
                    "[\\w\\W]{4}"), REPLACEMENT_CHARACTER("*"), EMAIL_REPLACEMENT_CHARACTER("****");

    private String value;

    private CustomerPublisherConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
