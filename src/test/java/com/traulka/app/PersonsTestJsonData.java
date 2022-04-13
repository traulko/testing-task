package com.traulka.app;

import java.util.List;

public class PersonsTestJsonData<T> {
    private List<T> persons;

    public List<T> getPersons() {
        return persons;
    }

    public void setPersons(List<T> persons) {
        this.persons = persons;
    }
}
