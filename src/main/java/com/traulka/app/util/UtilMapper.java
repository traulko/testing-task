package com.traulka.app.util;

import com.traulka.app.entity.type.GenderEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UtilMapper {

    @Named("genderFromString")
    default GenderEnum genderFromString(String gender) {
        return gender != null ? GenderEnum.valueOf(gender) : null;
    }
}
