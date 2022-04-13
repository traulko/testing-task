package com.traulka.app.mapper;

import com.traulka.app.dto.PersonDto;
import com.traulka.app.entity.Person;
import com.traulka.app.util.UtilMapper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UtilMapper.class})
public interface PersonMapper {

    @Named("toEntity")
    @Mapping(source = "gender", target = "gender", qualifiedByName = "genderFromString")
    Person toEntity(PersonDto dto);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Person> toEntities(List<PersonDto> dtos);

    @Named("toDto")
    PersonDto toDto(Person person);

    @IterableMapping(qualifiedByName = "toDto")
    List<PersonDto> toDtos(List<Person> entities);
}
