package com.traulka.app.controller;

import com.traulka.app.dto.PersonDto;
import com.traulka.app.service.PersonFacade;
import com.traulka.app.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController {

    private final PersonService personService;
    private final PersonFacade personFacade;

    @PostMapping(value = "/uploading-file")
    public List<PersonDto> uploadFile(@RequestPart("file") MultipartFile file) {
        return personFacade.uploadPersonsFromFile(file);
    }

    @GetMapping
    public List<PersonDto> findAll(
            @RequestParam(required = false, defaultValue = "city") final String sortBy,
            @RequestParam(required = false) final String gender) {
        return personService.findAll(gender, sortBy);
    }

    @GetMapping("/normalization")
    public List<PersonDto> findAllNormalized() {
        return personFacade.prepareNormalizedPersons();
    }
}
