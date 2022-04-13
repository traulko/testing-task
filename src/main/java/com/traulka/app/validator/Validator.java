package com.traulka.app.validator;

public interface Validator<T> {
    void validate(T t);
}
