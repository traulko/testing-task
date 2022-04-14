package com.traulka.app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traulka.app.TestResourceLoader;
import com.traulka.app.dto.PersonDto;
import com.traulka.app.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NormalizationServiceTest {

    @InjectMocks
    private NormalizationService service;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void normalizePersonListTest() throws IOException {
        List<PersonDto> sortedByCityPersonList = objectMapper
                .readValue(Paths.get("src/test/resources/sorted-by-city-person-data.json").toFile(), new TypeReference<>(){});

        List<PersonDto> normalizedSortedByCityPersonList = objectMapper
                .readValue(Paths.get("src/test/resources/normalized-sorted-by-city-person-data.json").toFile(), new TypeReference<>(){});

        service.normalizePersonList(sortedByCityPersonList);

        assertEquals(sortedByCityPersonList, normalizedSortedByCityPersonList);
    }
}
