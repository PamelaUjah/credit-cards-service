package com.clearscore.creditcards;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreditCardSearchTest {
    public static final String VALID_FULL_NAME = "Jasmine Lincoln";
    public static final int VALID_CREDIT_SCORE = 200;
    public static final int VALID_SALARY = 31000;
    public static final String INVALID_FULL_NAME = " ";
    public static final int INVALID_CREDIT_SCORE = 750;
    public static final int INVALID_SALARY = -1;

    private CreditCardSearch creditCardSearch;

    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
    }

    @Test
    @DisplayName("Given valid user details, when user details are set, then there are no violations ")
    void testUserDetailsAreValid() {
        givenUserDetails();

        whenUserDetailsAreSet(VALID_FULL_NAME, VALID_CREDIT_SCORE, VALID_SALARY);

        thenNoViolations();
    }

    @Test
    @DisplayName("Given invalid user details, when user details are set, then there are violations")
    void testInvalidUserDetails() {
        givenUserDetails();

        whenUserDetailsAreSet(INVALID_FULL_NAME, INVALID_CREDIT_SCORE, INVALID_SALARY);

        thenViolationsAreLogged();
    }

    void givenUserDetails() {

    }

    void whenUserDetailsAreSet(String fullName, Integer creditScore, Integer salary) {
        creditCardSearch = new CreditCardSearch(fullName, creditScore, salary);
    }

    private void thenNoViolations() {
        assertThat(creditCardSearch.getName()).isEqualTo(VALID_FULL_NAME)
                .isNotBlank()
                .isNotNull();

        assertThat(creditCardSearch.getSalary())
                .isEqualTo(VALID_SALARY)
                .isNotNull();

        assertThat(creditCardSearch.getCreditScore())
                .isEqualTo(VALID_CREDIT_SCORE)
                .isNotNull();
    }

    private void thenViolationsAreLogged() {
        Set<ConstraintViolation<CreditCardSearch>> violations = validator.validate(creditCardSearch);
        assertThat(violations.size()).isEqualTo(3);
    }
}