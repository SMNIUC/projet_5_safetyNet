package com.safetynet.safetynetalerts.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class ChildrenPerHouseholdDto {

    private String firstName;
    private String lastName;
    private int age;
    List<ChildHouseholdMemberDto> householdMembers;
}
