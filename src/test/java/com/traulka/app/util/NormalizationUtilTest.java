package com.traulka.app.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NormalizationUtilTest {

    @ParameterizedTest
    @CsvSource({"5 Watling Ave, 5 Watling Avenue",
            "8 Wrong Rd, 8 Wrong Road",
            "4 Clements Ln, 4 Clements Lane"})
    void normalizeAddressTest(String actual, String expected) {
        assertEquals(NormalizationUtil.normalizeAddress(actual), expected);
    }

    @ParameterizedTest
    @CsvSource({"W Yorks, West Yorkshire"})
    void normalizeStateTest(String actual, String expected) {
        assertEquals(NormalizationUtil.normalizeState(actual), expected);
    }

    @Test
    void normalizePostCodeTest() {
        String expectedPostCode = "EC14AA";
        String actualPostCode = "EC1 4AA";
        assertEquals(NormalizationUtil.normalizePostCode(actualPostCode), expectedPostCode);
    }
}
