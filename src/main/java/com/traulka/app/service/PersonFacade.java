package com.traulka.app.service;

import com.traulka.app.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonFacade {

    private final PersonService personService;
    private final FileService<PersonDto> personDtoFileService;
    private final NormalizationService normalizationService;

    public List<PersonDto> uploadPersonsFromFile(MultipartFile file) {
        List<PersonDto> data = personDtoFileService.getData(file);
        return personService.saveAll(data);
    }

    public List<PersonDto> prepareNormalizedPersons() {
        List<PersonDto> all = personService.findAll();
        return normalizationService.normalizePersonList(all);
    }
}
