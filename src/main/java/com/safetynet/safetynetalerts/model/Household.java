package com.safetynet.safetynetalerts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Household {

    private List<PersonForFloodAlertDTO> personsInThisHousehold;

}
