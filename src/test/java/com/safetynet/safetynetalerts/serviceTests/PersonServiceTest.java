package com.safetynet.safetynetalerts.serviceTests;

import com.safetynet.safetynetalerts.model.Dtos.*;
import com.safetynet.safetynetalerts.model.Household;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(PersonService.class)
@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    private static final JsonReaderUtil jsonReaderUtil = new JsonReaderUtil();
    private static final List<Person> personList = jsonReaderUtil.getPersonList();

    @InjectMocks
    private PersonService service;

    private static Person personTest;

    @BeforeAll
    static void setUp() {
        personTest = new Person();
        personTest.setFirstName("Mark");
        personTest.setLastName("Twain");
        personTest.setAddress("69 Main St");
        personTest.setCity("NYC");
        personTest.setZip("08648");
        personTest.setPhone("032-145-6987");
        personTest.setEmail("youpi@email.com");
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Mark");
        medicalRecord.setLastName("Twain");
        List<String> medications = new ArrayList<>();
        medications.add("omeprazol");
        medicalRecord.setMedications(medications);
        List<String> allergies = new ArrayList<>();
        allergies.add("peanuts");
        medicalRecord.setAllergies(allergies);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthday = LocalDate.parse("05/06/2010", formatter);
        medicalRecord.setBirthdate(birthday);
        personTest.setMedicalRecord(medicalRecord);
    }
    @Test
    void addNewPersonTest() {

        List<Person> personList = service.addNewPerson(personTest);
        assertThat(personList).contains(personTest);
    }

    @Test
    void updatePersonInfoTest() {

        Person newPersonInfo = new Person();
        newPersonInfo.setFirstName("John");
        newPersonInfo.setLastName("Boyd");
        newPersonInfo.setAddress("65 Avenue de la Tour");
        newPersonInfo.setCity("Los Angeles");
        newPersonInfo.setZip("12345");
        newPersonInfo.setPhone("789.456.1230");
        newPersonInfo.setEmail("bliblablou@blo.com");

        Person personToTest = service.updatePersonInfo("John", "Boyd", newPersonInfo);

        assertThat(personToTest.getAddress()).isEqualTo("65 Avenue de la Tour");
        assertThat(personToTest.getCity()).isEqualTo("Los Angeles");
        assertThat(personToTest.getZip()).isEqualTo("12345");
        assertThat(personToTest.getPhone()).isEqualTo("789.456.1230");
        assertThat(personToTest.getEmail()).isEqualTo("bliblablou@blo.com");

    }

    @Test
    void deletePersonTest() {

        Person personToDelete = new Person();
        personToDelete.setFirstName("Sophia");
        personToDelete.setLastName("Zemicks");
        personToDelete.setAddress("4892 Downing Ct");
        personToDelete.setCity("Culver");
        personToDelete.setZip("97451");
        personToDelete.setPhone("841-874-7878");
        personToDelete.setEmail("soph@email.com");

        service.deletePerson("Sophia", "Zemicks");

        assertThat(personList).isNotEmpty().doesNotContain(personToDelete);

    }

    @Test
    void isChildTest() {
        Boolean isChildTest = service.isChild(personTest);
        assertThat(isChildTest).isTrue();
    }

    @Test
    void getPersonPerFirestationTest() {

        PersonPerFirestationDto personPerFirestationDtoTest = new PersonPerFirestationDto();
        personPerFirestationDtoTest.setFirstName("Tessa");
        personPerFirestationDtoTest.setLastName("Carman");
        personPerFirestationDtoTest.setAddress("834 Binoc Ave");
        personPerFirestationDtoTest.setCity("Culver");
        personPerFirestationDtoTest.setZip("97451");
        personPerFirestationDtoTest.setPhone("841-874-6512");
        personPerFirestationDtoTest.setNumberOfAdults(3);
        personPerFirestationDtoTest.setNumberOfChildren(3);

        List<PersonPerFirestationDto> personPerFirestationDTO = service.getPersonPerFirestation("3");

        assertThat(personPerFirestationDTO).contains(personPerFirestationDtoTest);

    }

    @Test
    void getChildrenPerHouseholdTest() {

        List<PersonInfoDto> personInfo = service.getPersonInfo("Tessa", "Carman");
        PersonInfoDto getAgePerson = personInfo.get(0);
        int age = getAgePerson.getAge();
        ChildrenPerHouseholdDto childrenPerHouseholdDtoTest = new ChildrenPerHouseholdDto();
        childrenPerHouseholdDtoTest.setFirstName("Tessa");
        childrenPerHouseholdDtoTest.setLastName("Carman");
        childrenPerHouseholdDtoTest.setAge(age);
        childrenPerHouseholdDtoTest.setHouseholdMembers(new ArrayList<>());

        List<ChildrenPerHouseholdDto> childrenPerHouseholdList = service.getChildrenPerHousehold("834 Binoc Ave");

        assertThat(childrenPerHouseholdList).contains(childrenPerHouseholdDtoTest);

    }

    @Test
    void getPhoneNumbersPerFirestationTest() {

        Person personInfo = personList.get(22);
        String phoneNumber = personInfo.getPhone();

        List<String> getPhoneNumbersPerFirestation = service.getPhoneNumbersPerFirestation("2");

        assertThat(getPhoneNumbersPerFirestation).contains(phoneNumber);
    }

    @Test
    void getFireAlertTest() {

        List<PersonInfoDto> personInfo = service.getPersonInfo("Lily", "Cooper");
        PersonInfoDto getAgePerson = personInfo.get(0);
        int age = getAgePerson.getAge();
        PersonForFireAlertDto personForFireAlertDtoTest = new PersonForFireAlertDto();
        personForFireAlertDtoTest.setFirstName("Lily");
        personForFireAlertDtoTest.setLastName("Cooper");
        personForFireAlertDtoTest.setAge(age);
        personForFireAlertDtoTest.setPhoneNumber("841-874-9845");
        personForFireAlertDtoTest.setFirestation("4");
        personForFireAlertDtoTest.setAllergies(new ArrayList<>());
        personForFireAlertDtoTest.setMedicines(new ArrayList<>());

        List<PersonForFireAlertDto> personForFireAlertDtoListTest = service.getFireAlert("489 Manchester St");

        assertThat(personForFireAlertDtoListTest).contains(personForFireAlertDtoTest);
    }

    @Test
    void getPersonInfoTest() {

        List<PersonInfoDto> personInfo = service.getPersonInfo("Lily", "Cooper");
        PersonInfoDto getAgePerson = personInfo.get(0);
        int age = getAgePerson.getAge();
        PersonInfoDto personInfoDtoTest = new PersonInfoDto();
        personInfoDtoTest.setFirstName("Lily");
        personInfoDtoTest.setLastName("Cooper");
        personInfoDtoTest.setAddress("489 Manchester St");
        personInfoDtoTest.setAge(age);
        personInfoDtoTest.setEmail("lily@email.com");
        personInfoDtoTest.setAllergies(new ArrayList<>());
        personInfoDtoTest.setMedication(new ArrayList<>());

        List<PersonInfoDto> personInfoDtoListTest = service.getPersonInfo("Lily", "Cooper");

        assertThat(personInfoDtoListTest).contains(personInfoDtoTest);
    }

    @Test
    void getFloodAlertTest() {

        List<PersonInfoDto> personInfo = service.getPersonInfo("Lily", "Cooper");
        PersonInfoDto getAgePerson = personInfo.get(0);
        int age = getAgePerson.getAge();
        PersonForFloodAlertDto personForFloodAlertDtoTest = new PersonForFloodAlertDto();
        personForFloodAlertDtoTest.setFirstName("Lily");
        personForFloodAlertDtoTest.setLastName("Cooper");
        personForFloodAlertDtoTest.setAge(age);
        personForFloodAlertDtoTest.setPhoneNumber("841-874-9845");
        personForFloodAlertDtoTest.setAllergies(new ArrayList<>());
        personForFloodAlertDtoTest.setMedicines(new ArrayList<>());
        List<PersonForFloodAlertDto> personForFloodAlertDtoListTest = new ArrayList<>();
        personForFloodAlertDtoListTest.add(personForFloodAlertDtoTest);
        Household householdTest = new Household();
        householdTest.setPersonsInThisHousehold(personForFloodAlertDtoListTest);

        List<String> stationsList = List.of("4");
        List<Household> householdListTest = service.getFloodAlert(stationsList);

        assertThat(householdListTest).contains(householdTest);
    }

    @Test
    void getEmailsTest() {

        List<String> emailsListTest = service.getEmails("Culver");
        assertThat(emailsListTest).contains("lily@email.com");
    }
}
