package com.safetynet.safetynetalerts.serviceTests;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FirestationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FirestationServiceTest {

    @Autowired
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
        Firestation firestation = service.addNewFirestation(firestationTest);
        assertThat(firestation).isEqualTo(firestationTest);
    }

    @Test
    void updateFirestationInfoTest() {

        Firestation firestationToUpdate = new Firestation();
        firestationToUpdate.setAddress("1509 Culver St");
        firestationToUpdate.setStation("2");
        Firestation firestationTest = service.updateFirestationInfo("1509 Culver St", firestationToUpdate);

        assertThat(firestationTest).isEqualTo(firestationToUpdate);
    }

    @Test
    void deleteFirestationTest() {


        service.deleteFirestation("1509 Culver St", null );

        assertThat(service.getAllFirestations()).isNotEmpty().doesNotContain(firestationTest);

    }

}
