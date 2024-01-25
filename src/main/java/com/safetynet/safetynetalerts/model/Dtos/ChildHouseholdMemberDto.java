package com.safetynet.safetynetalerts.model.Dtos;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ChildHouseholdMemberDto {

    private String firstName;
    private String lastName;
}
