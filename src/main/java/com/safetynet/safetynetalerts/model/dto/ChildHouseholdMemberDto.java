package com.safetynet.safetynetalerts.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ChildHouseholdMemberDto {

    private String firstName;
    private String lastName;
}
