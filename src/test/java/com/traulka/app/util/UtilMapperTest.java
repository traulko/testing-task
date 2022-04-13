package com.traulka.app.util;

import com.traulka.app.entity.type.GenderEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UtilMapperTest {

    private UtilMapper utilMapper;

    @BeforeEach
    void setUp() {
        utilMapper = new UtilMapperImpl();
    }

    @Test
    void genderFromStringTest() {
        GenderEnum gender = utilMapper.genderFromString("F");
        assertThat(gender).isEqualTo(GenderEnum.F);
    }
}
