package com.traulka.app.util;

import com.traulka.app.exception.ValidationException;
import com.traulka.app.validator.impl.FileExtensionValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileExtensionValidatorTest {

    private FileExtensionValidatorImpl fileExtensionValidatorImpl;

    @BeforeEach
    void setUp() {
        fileExtensionValidatorImpl = new FileExtensionValidatorImpl();
    }

    @Test
    void shouldThrownFileExtensionValidationException() {
        String contentType = "application/json";
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> fileExtensionValidatorImpl.validate(contentType));
        assertEquals(String.format("Content-Type of the file %s is incorrect", contentType),
                validationException.getMessage());
    }

    @Test
    void shouldNotThrownFileExtensionValidationException() {
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        assertDoesNotThrow(() -> fileExtensionValidatorImpl.validate(contentType),
                String.format("Content-Type of the file %s is incorrect", contentType));
    }
}
