package com.traulka.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(setterPrefix = "set")
public class PersonDto {
    private String id;
    private String firstName;
    private String surname;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postCode;
    private String countryCode;
    private String gender;
    private String dateOfBirth;
}
