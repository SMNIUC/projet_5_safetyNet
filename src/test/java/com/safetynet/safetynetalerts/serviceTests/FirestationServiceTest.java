package com.safetynet.safetynetalerts.serviceTests;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(FirestationService.class)
@ExtendWith(SpringExtension.class)
class FirestationServiceTest {

    private static final JsonReaderUtil jsonReaderUtil = new JsonReaderUtil();
    private static final List<Firestation> firestationList = jsonReaderUtil.getFirestationList();

    @InjectMocks
    private FirestationService service;

    private static Firestation firestationTest;

    @BeforeAll
    static void setUp() {
        firestationTest = new Firestation();
        firestationTest.setStation("3");
        firestationTest.setAddress("1509 Culver St");
    }

    @Test
    void addNewFirestationTest() {
        List<Firestation> firestationListTest = service.addNewFirestation(firestationTest);
        assertThat(firestationListTest).contains(firestationTest);
    }

    @Test
    void updateFirestationInfoTest() {

        Firestation firestationToUpdate = new Firestation();
        firestationToUpdate.setAddress("1509 Culver St");
        firestationToUpdate.setStation("2");

        Firestation firestationTest = service.updateFirestationInfo("1509 Culver St", firestationToUpdate);

        assertThat(firestationTest).isEqualTo(firestationToUpdate);
    }

//    @Test
//    void deleteFirestationTest() {
//
//        Firestation firestationTest = jsonReaderUtil.getFirestationByAddress("951 LoneTree Rd");
//
//        service.deleteFirestation("951 LoneTree Rd", "2");
//
//        assertThat(firestationList).isNotEmpty().doesNotContain(firestationTest);
//
//    }

}
