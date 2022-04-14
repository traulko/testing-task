package com.traulka.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traulka.app.PersonsTestJsonData;
import com.traulka.app.TestResourceLoader;
import com.traulka.app.dto.PersonDto;
import com.traulka.app.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class PersonFacadeTest {

    @Mock
    private PersonService personService;

    @Mock
    private FileService<PersonDto> personDtoFileService;

    @Mock
    private NormalizationService normalizationService;

    @InjectMocks
    private PersonFacade personFacade;

    private TestResourceLoader resourceLoader;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resourceLoader = new TestResourceLoader();
        objectMapper = new ObjectMapper();
    }

    @Test
    void uploadPersonsFromFileTest() throws IOException {
        List<PersonDto> expectedPersonDtoList =
                resourceLoader.loadResource(PersonsTestJsonData.class, "sorted-by-city-filtered-by-gender-data.json").getPersons();

        byte[] data = this.getClass().getClassLoader().getResourceAsStream("test-data.xlsx").readAllBytes();
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("file", "test-data.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", data);

        given(personDtoFileService.getData(mockMultipartFile)).willReturn(expectedPersonDtoList);
        given(personService.saveAll(expectedPersonDtoList)).willReturn(expectedPersonDtoList);

        List<PersonDto> actualPersonDtos = personFacade.uploadPersonsFromFile(mockMultipartFile);
        assertEquals(actualPersonDtos, expectedPersonDtoList);
    }

    @Test
    void prepareNormalizedPersons() throws IOException {
        List<PersonDto> sortedByCityPersonList = objectMapper
                .readValue(Paths.get("src/test/resources/sorted-by-city-person-data.json").toFile(), new TypeReference<>(){});
        List<PersonDto> normalizedSortedByCityPersonList = objectMapper
                .readValue(Paths.get("src/test/resources/normalized-sorted-by-city-person-data.json").toFile(), new TypeReference<>(){});
        given(personService.findAll()).willReturn(sortedByCityPersonList);
        given(normalizationService.normalizePersonList(sortedByCityPersonList)).willReturn(normalizedSortedByCityPersonList);

        List<PersonDto> actualPersonDtos = personFacade.prepareNormalizedPersons();
        assertEquals(actualPersonDtos, normalizedSortedByCityPersonList);
    }
}