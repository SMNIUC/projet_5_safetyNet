package com.safetynet.safetynetalerts.model;

import com.safetynet.safetynetalerts.model.dto.PersonForFloodAlertDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Household {

    private List<PersonForFloodAlertDto> personsInThisHousehold;

}
