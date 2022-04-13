package com.traulka.app.repository.specification;

import com.traulka.app.entity.Person;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static com.traulka.app.util.SpecificationUtils.fieldLike;
import static java.util.Optional.ofNullable;

@Component
public class PersonSpecification {

    public Specification<Person> findByGender(String searchText) {
        return (root, query, criteriaBuilder) ->
                ofNullable(searchText)
                        .filter(StringUtils::isNotBlank)
                        .map(value -> fieldLike(criteriaBuilder, root.get("gender"), value))
                        .orElseGet(criteriaBuilder::conjunction); //to ignore this clause
    }

}
