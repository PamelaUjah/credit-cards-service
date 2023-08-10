package com.clearscore.testcreditcards;

import com.clearscore.creditcards.Customer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class TestCustomer {

    public static final String VALID_FULL_NAME = "Jasmine Lincoln";
    public static final int VALID_CREDIT_SCORE = 200;
    public static final int VALID_SALARY = 31000;
    public static final String INVALID_FULL_NAME = " ";
    public static final int INVALID_CREDIT_SCORE = 750;
    public static final int INVALID_SALARY = -1;

    private Customer customer;
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
    }
    @Test
    @DisplayName("Given valid customer details, when customer details are set, then there are no violations ")
    void testCustomerDetailsAreValid() {
        givenCustomerDetails();

        whenCustomerDetailsAreSet(VALID_FULL_NAME, VALID_CREDIT_SCORE, VALID_SALARY);

        thenNoViolations();
    }

    @Test
    @DisplayName("Given invalid customer details, when customer details are set, then there are violations")
    void testInvalidCustomerDetails() {
        givenCustomerDetails();

        whenCustomerDetailsAreSet(INVALID_FULL_NAME, INVALID_CREDIT_SCORE, INVALID_SALARY);

        thenViolationsAreLogged();
    }

    void givenCustomerDetails() {

    }

    void whenCustomerDetailsAreSet(String fullName, Integer creditScore, Integer salary) {
        customer = new Customer(fullName, creditScore, salary);
    }

    private void thenNoViolations() {
        assertThat(customer.getName()).isEqualTo(VALID_FULL_NAME)
                .isNotBlank()
                .isNotNull();

        assertThat(customer.getSalary())
                .isEqualTo(VALID_SALARY)
                .isNotNull();

        assertThat(customer.getCreditScore())
                .isEqualTo(VALID_CREDIT_SCORE)
                .isNotNull();
    }

    private void thenViolationsAreLogged() {
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations.size()).isEqualTo(3);
    }
}
