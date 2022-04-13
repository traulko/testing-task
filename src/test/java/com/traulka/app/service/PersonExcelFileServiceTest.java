package com.traulka.app.service;

import com.google.gson.Gson;
import com.traulka.app.PersonsTestJsonData;
import com.traulka.app.TestResourceLoader;
import com.traulka.app.dto.PersonDto;
import com.traulka.app.entity.Person;
import com.traulka.app.service.impl.PersonExcelFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonExcelFileServiceTest {

    private TestResourceLoader resourceLoader;

    @InjectMocks
    private PersonExcelFileService fileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resourceLoader = new TestResourceLoader();
    }

    @Test
    void uploadPersonDtoListFromFileTest() throws IOException {
        List<Person> expectedPersonList =
                resourceLoader.loadResource(PersonsTestJsonData.class, "sorted-by-city-filtered-by-gender-data.json").getPersons();
        byte[] data = this.getClass().getClassLoader().getResourceAsStream("test-data.xlsx").readAllBytes();
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("file", "test-data.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", data);
        List<PersonDto> actualPersonList = fileService.getData(mockMultipartFile);
        String actual = new Gson().toJson(actualPersonList);
        String expected = new Gson().toJson(expectedPersonList);
        assertEquals(actual, expected);
    }
}
