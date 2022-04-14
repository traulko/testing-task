package com.traulka.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traulka.app.PersonsTestJsonData;
import com.traulka.app.TestResourceLoader;
import com.traulka.app.dto.PersonDto;
import com.traulka.app.entity.Person;
import com.traulka.app.mapper.PersonMapper;
import com.traulka.app.repository.PersonRepository;
import com.traulka.app.service.impl.PersonServiceImpl;
import com.traulka.app.repository.specification.PersonSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @Mock
    private PersonSpecification filterSpecification;

    @InjectMocks
    private PersonServiceImpl service;

    private TestResourceLoader resourceLoader;

    private ObjectMapper objectMapper;

    @Mock
    private PersonMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resourceLoader = new TestResourceLoader();
        objectMapper = new ObjectMapper();
    }

    @Test
    void uploadPersonFromFileTest() {
        List<PersonDto> expectedPersonDtoList =
                resourceLoader.loadResource(PersonsTestJsonData.class, "sorted-by-city-filtered-by-gender-data.json").getPersons();
        List<Person> personList =
                resourceLoader.loadResource(PersonsTestJsonData.class, "sorted-by-city-filtered-by-gender-data.json").getPersons();

        given(mapper.toEntities(expectedPersonDtoList)).willReturn(personList);
        given(repository.saveAll(personList)).willReturn(personList);
        given(mapper.toDtos(personList)).willReturn(expectedPersonDtoList);

        List<PersonDto> actualPersonList = service.saveAll(expectedPersonDtoList);

        assertEquals(actualPersonList, expectedPersonDtoList);
    }

    @Test
    void findAllSortedTest() throws IOException {
        List<Person> sortedByCityPersonList = objectMapper
                .readValue(Paths.get("src/test/resources/sorted-by-city-person-data.json").toFile(), new TypeReference<>(){});

        List<PersonDto> sortedByCityPersonDtoList =
                resourceLoader.loadResource(PersonsTestJsonData.class, "sorted-by-city-filtered-by-gender-data.json").getPersons();

        given(repository.findAll(any(Specification.class), eq(Sort.by("city")))).willReturn(sortedByCityPersonList);
        given(mapper.toDtos(sortedByCityPersonList)).willReturn(sortedByCityPersonDtoList);

        List<PersonDto> actualSortedPersonList = service.findAll("F", "city");
        assertEquals(actualSortedPersonList, sortedByCityPersonDtoList);
    }

    @Test
    void findAll() throws IOException {
        List<Person> sortedByCityPersonList = objectMapper
                .readValue(Paths.get("src/test/resources/sorted-by-city-person-data.json").toFile(), new TypeReference<>(){});

        List<PersonDto> sortedByCityPersonDtoList =
                resourceLoader.loadResource(PersonsTestJsonData.class, "sorted-by-city-filtered-by-gender-data.json").getPersons();

        given(repository.findAll()).willReturn(sortedByCityPersonList);
        given(mapper.toDtos(sortedByCityPersonList)).willReturn(sortedByCityPersonDtoList);

        List<PersonDto> actualSortedPersonList = service.findAll();
        assertEquals(actualSortedPersonList, sortedByCityPersonDtoList);
    }
}
