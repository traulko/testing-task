package com.traulka.app.validator.impl;

import com.traulka.app.exception.ValidationException;
import com.traulka.app.validator.Validator;
import org.springframework.stereotype.Component;

@Component
public class FileExtensionValidatorImpl implements Validator<String> {

    private static final String XLSX_FILE_EXTENSION = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Override
    public void validate(String contentType) {
        if (!XLSX_FILE_EXTENSION.equals(contentType)) {
            throw new ValidationException(String.format("Content-Type of the file %s is incorrect", contentType));
        }
    }
}
