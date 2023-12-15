package com.safetynet.safetynetalerts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ChildHouseholdMemberDTO {

    private String firstName;
    private String lastName;
}
