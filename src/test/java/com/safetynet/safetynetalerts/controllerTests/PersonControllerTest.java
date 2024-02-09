package com.safetynet.safetynetalerts.controllerTests;

import com.jsoniter.output.JsonStream;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webContext;

    @InjectMocks
    private PersonService personService;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private Person person;
    private final String firstName = "Mark";
    private final String lastName = "Twain";
    private final String address = "69 Main St";
    private final String city = "NYC";
    private final String zip = "38218";
    private final String phone = "456-857-8463";
    private final String email = "mark.twain@toocool.com";
    private MedicalRecord medicalRecord;
    private final List<String> medications = new ArrayList<>();
    private final List<String> allergies = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final LocalDate birthday = LocalDate.parse("05/06/2010", formatter);

    @BeforeEach
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(webContext).build();
        person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(address);
        person.setCity(city);
        person.setZip(zip);
        person.setPhone(phone);
        person.setEmail(email);
        medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName(firstName);
        medicalRecord.setLastName(lastName);
        medications.add("omeprazol");
        medicalRecord.setMedications(medications);
        allergies.add("peanuts");
        medicalRecord.setAllergies(allergies);
        medicalRecord.setBirthdate(birthday);
    }

    //Test for Person POST
    @Test
    void addNewPerson() throws Exception {
        String requestJson = JsonStream.serialize(person);

        mvc.perform(MockMvcRequestBuilders.post("/person")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Mark"));
    }

    //Test for Person PUT
    @Test
    void updatePersonInfo() throws Exception {
        String requestJson = JsonStream.serialize(person);
        mvc.perform(MockMvcRequestBuilders.put("/person")
                        .param("firstName", "Mark")
                        .param("lastName", "Twain")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("NYC"));
    }

    //Test for Person DELETE
    @Test
    void deletePerson() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Boyd"))
                .andExpect(status().isNoContent());
    }

    //Test for Person GET - returns a list of children living at a specific address
    @Test
    void getChildrenPerHousehold() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/childAlert")
                        .contentType(APPLICATION_JSON)
                        .param("address", "1509 Culver St"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("Tenley"));
    }

    //Test for Person GET - returns a list of phone numbers per firestation
    @Test
    void getPhoneNumbersPerFirestation() throws Exception {
        String requestJson = JsonStream.serialize(person);

        mvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(APPLICATION_JSON)
                .content(requestJson));

        mvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                        .param("station", "3")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0]").value("841-874-6512"));
    }

    //Test for Person GET - returns a list of personal info for each inhabitant
    @Test
    void getPersonInfo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/personInfo")
                        .param("firstName", "Zach")
                        .param("lastName", "Zemicks")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].address").value("892 Downing Ct"));
    }

    //Test for Person GET - returns a list of all the city inhabitants' email
    @Test
    void getEmails() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                        .param("city", "Culver")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(22)))
                .andExpect(jsonPath("$[0]").value("drk@email.com"));
    }
}
