package com.safetynet.safetynetalerts.controllerTests;

import com.jsoniter.output.JsonStream;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
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

    @BeforeEach
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    //Test for Person POST
    @Test
    void addNewPerson() throws Exception {
        Person person = new Person();
        person.setFirstName("Mark");
        person.setLastName("Twain");
        String requestJson = JsonStream.serialize(person);

        mvc.perform(MockMvcRequestBuilders
                        .post("/person")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Mark"));
    }

    //Test for Person PUT
    @Test
    void updatePersonInfo() throws Exception {
        Person person = new Person();
        person.setCity("NYC");
        String requestJson = JsonStream.serialize(person);

        mvc.perform(MockMvcRequestBuilders
                        .put("/person")
                        .param("firstName", "John")
                        .param("lastName", "Boyd")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("NYC"));
    }

    //Test for Person DELETE
    @Test
    void deletePerson() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .delete("/person")
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
        mvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                        .param("station", "3")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(13)))
                .andExpect(jsonPath("$[0]").value("841-874-6512"));
    }

    //Test for Person GET - returns a list of personal info for each inhabitant
    @Test
    void getPersonInfo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/personInfo")
                        .param("firstName", "John")
                        .param("lastName", "Boyd")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].address").value("1509 Culver St"));
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
                .andExpect(jsonPath("$", hasSize(23)))
                .andExpect(jsonPath("$[0]").value("jaboyd@email.com"));
    }
}
