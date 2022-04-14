package com.traulka.app.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class NormalizationUtil {

    public static String normalizePostCode(String postCode) {
        return StringUtils.isNotBlank(postCode) ? postCode.replace(" ", "")
                : postCode;
    }

    public String normalizeAddress(String address) {
        return StringUtils.isNotBlank(address)
                ? address.replace("Ln", "Lane")
                .replace("Ave", "Avenue")
                .replace("Rd", "Road")
                : address;
    }

    public String normalizeState(String state) {
        return StringUtils.isNotBlank(state) ? state.replace("W Yorks", "West Yorkshire")
                : state;
    }

}
