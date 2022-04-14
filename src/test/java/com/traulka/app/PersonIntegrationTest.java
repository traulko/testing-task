package com.traulka.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traulka.app.config.WebConfig;
import com.traulka.app.controller.PersonController;
import com.traulka.app.dto.PersonDto;
import com.traulka.app.service.PersonFacade;
import com.traulka.app.service.PersonService;
import org.apache.commons.codec.CharEncoding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Paths;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonController.class)
@ContextConfiguration(classes = WebConfig.class)
class PersonIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonFacade personFacade;

    private TestResourceLoader resourceLoader;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        resourceLoader = new TestResourceLoader();
        objectMapper = new ObjectMapper();
    }

    @Test
    void itShouldUploadFileSuccessfully() throws Exception {
        byte[] data = this.getClass().getClassLoader().getResourceAsStream("test-data.xlsx").readAllBytes();
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("file", "test-data.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", data);
        List<PersonDto> expectedPersonDtoList =
                resourceLoader.loadResource(PersonsTestJsonData.class, "sorted-by-city-filtered-by-gender-data.json").getPersons();
        given(personFacade.uploadPersonsFromFile(mockMultipartFile)).willReturn(expectedPersonDtoList);
        mockMvc.perform(multipart("/api/v1/person/uploading-file")
                        .file(mockMultipartFile)
                        .characterEncoding(CharEncoding.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPersonDtoList)));
    }

    @Test
    void itShouldReturnSortedByCityPersonAndFilteredByGenderListSuccessfully() throws Exception {
        List<PersonDto> expectedPersonDtoList =
                resourceLoader.loadResource(PersonsTestJsonData.class, "sorted-by-city-filtered-by-gender-data.json").getPersons();
        given(personService.findAll("i", "city")).willReturn(expectedPersonDtoList);
        mockMvc.perform(get("/api/v1/person")
                        .param("surname", "i")
                        .param("sortBy", "city"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPersonDtoList)));
    }

    @Test
    void itShouldReturnNormalizedPersonListSuccessfully() throws Exception {
        List<PersonDto> expectedPersonDtoList = objectMapper
                .readValue(Paths.get("src/test/resources/normalized-sorted-by-city-person-data.json").toFile(), new TypeReference<>(){});
        given(personFacade.prepareNormalizedPersons()).willReturn(expectedPersonDtoList);
        mockMvc.perform(get("/api/v1/person/normalization"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPersonDtoList)));
    }
}
