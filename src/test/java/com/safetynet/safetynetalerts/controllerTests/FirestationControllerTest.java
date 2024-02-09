package com.safetynet.safetynetalerts.controllerTests;

import com.jsoniter.output.JsonStream;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class FirestationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webContext;

    @InjectMocks
    private FirestationService firestationService;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    //Test for Firestation POST
    @Test
    void addNewFirestation() throws Exception {
        Firestation firestation = new Firestation();
        firestation.setStation("1");
        firestation.setAddress("29 15th St");
        String requestJson = JsonStream.serialize(firestation);

        mvc.perform(MockMvcRequestBuilders
                        .post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station").value("1"));
    }

    //Test for Firestation PUT
    @Test
    void updateFirestationInfo() throws Exception {
        Firestation firestation = new Firestation();
        firestation.setStation("1");
        firestation.setAddress("644 Gershwin Cir");
        String requestJson = JsonStream.serialize(firestation);

        mvc.perform(MockMvcRequestBuilders
                        .put("/firestation")
                        .param("address", "644 Gershwin Cir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station").value("1"));
    }

    //Test for Firestation DELETE
    @Test
    void deleteFirestation() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .delete("/firestation")
                        .param("address", "1509 Culver St")
                        .param("station", "3"))
                .andExpect(status().isNoContent());
    }

    //Test for Firestation GET - returns list of persons covered by a certain firestation
    @Test
    void getPersonPerFirestation() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/firestation")
                        .param("station", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(13)))
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    //Test for Firestation GET - returns a list of inhabitants at a specific address and the associated firestation
    @Test
    void getFireAlert() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fire")
                        .param("address", "1509 Culver St")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    //Test for Firestation GET - returns a list of all households for a specific firestation in case of a flood
    @Test
    void getFloodAlert() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                        .param("stationsList", "2,4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(5)));
    }
}
