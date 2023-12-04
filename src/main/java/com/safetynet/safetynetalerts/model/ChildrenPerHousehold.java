package com.safetynet.safetynetalerts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class ChildrenPerHousehold {

    private String firstName;
    private String lastName;
    private int age;
    List<Person> householdMembers;
}
