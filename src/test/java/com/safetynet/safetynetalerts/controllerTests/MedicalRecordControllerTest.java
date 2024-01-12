package com.safetynet.safetynetalerts.controllerTests;

import com.jsoniter.output.JsonStream;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webContext;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @BeforeEach
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    //Test for MedicalRecord POST
    @Test
    void addNewMedicalRecord() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Mark");
        medicalRecord.setLastName("Twain");
        String requestJson = JsonStream.serialize(medicalRecord);

        mvc.perform(MockMvcRequestBuilders
                        .post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(jsonPath("$.firstName").value("Mark"));
    }

//    //Test for MedicalRecord PUT
    @Test
    void updateMedicalRecord() throws Exception {

        List<String> meds = List.of("xilliathal");
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(meds);
        String requestJson = JsonStream.serialize(medicalRecord);

        mvc.perform(MockMvcRequestBuilders
                        .put("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Boyd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medications").value("xilliathal"));
    }

    //Test for MedicalRecord DELETE
    @Test
    void deleteMedicalRecord() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Boyd"))
                .andExpect(status().isNoContent());
    }
}
