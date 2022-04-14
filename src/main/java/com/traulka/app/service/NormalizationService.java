package com.traulka.app.service;

import com.traulka.app.dto.PersonDto;
import com.traulka.app.entity.Person;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NormalizationService {

    private static final String CITY_KEY = "city";
    private static final String COUNTRY_CODE_KEY = "countryCode";

    public List<PersonDto> normalizePersonList(List<PersonDto> personList) {
        Map<String, Map<String, String>> valuesMap = uploadCachePersonInfo(personList);

        personList.stream()
                .filter(person -> person.getState() == null)
                .forEach(p -> p.setState(valuesMap.get(CITY_KEY).get(p.getCity())));

        personList.stream()
                .filter(person -> person.getCountryCode() == null)
                .forEach(p -> p.setCountryCode(valuesMap.get(COUNTRY_CODE_KEY).get(p.getCity())));
        return personList;
    }

    private Map<String, Map<String, String>> uploadCachePersonInfo(List<PersonDto> personDtos) {
        Map<String, Map<String, String>> valuesMap = new HashMap<>();
        valuesMap.put(CITY_KEY, new HashMap<>());
        valuesMap.put(COUNTRY_CODE_KEY, new HashMap<>());
        personDtos.forEach(person -> {
            valuesMap.get(CITY_KEY).put(person.getCity(), person.getState());
            valuesMap.get(COUNTRY_CODE_KEY).put(person.getCity(), person.getCountryCode());
        });
        return valuesMap;
    }
}
