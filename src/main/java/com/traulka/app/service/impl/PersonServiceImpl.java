package com.traulka.app.service.impl;

import com.traulka.app.dto.PersonDto;
import com.traulka.app.entity.Person;
import com.traulka.app.mapper.PersonMapper;
import com.traulka.app.repository.PersonRepository;
import com.traulka.app.service.PersonService;
import com.traulka.app.repository.specification.PersonSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;
    private final PersonSpecification filterSpecification;

    @Override
    public List<PersonDto> findAll(String gender, String sortBy) {
        List<Person> all = repository.findAll(buildSpecification(gender), Sort.by(sortBy));
        return mapper.toDtos(all);
    }

    @Override
    public List<PersonDto> findAll() {
        return mapper.toDtos(repository.findAll());
    }

    @Override
    public List<PersonDto> saveAll(List<PersonDto> dtos) {
        List<Person> persons = mapper.toEntities(dtos);
        return mapper.toDtos(repository.saveAll(persons));
    }

    private Specification<Person> buildSpecification(String gender) {
        return Specification.where(filterSpecification.findByGender(gender));
    }
}
