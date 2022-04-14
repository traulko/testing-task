package com.traulka.app.service;

import com.traulka.app.dto.PersonDto;

import java.util.List;

public interface PersonService {
    List<PersonDto> findAll(String gender, String sortBy);

    List<PersonDto> findAll();

    List<PersonDto> saveAll(List<PersonDto> dtos);
}
