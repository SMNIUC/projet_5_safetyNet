package com.safetynet.safetynetalerts.utils;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Data
@Component
public class JsonReaderUtil {

    private static final Logger log = LogManager.getLogger();

    private List<Person> personList = new ArrayList<>();
    private List<Firestation> firestationList = new ArrayList<>();
    private List<MedicalRecord> medicalRecordsList = new ArrayList<>();

    public JsonReaderUtil() {
        try {
            this.readJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readJSON() throws IOException {
        // Accessing a resource from the classpath using Spring
        Resource resource = new ClassPathResource("static/data.json");

        // Loading the file from the folder source
        File file = resource.getFile();

        // Reading file and storing all info, regardless of type, in 'content' array variable
        byte[] content = Files.readAllBytes(file.toPath());

        // Converting array content into an iterator object/creating iterator from byte array
        JsonIterator iter = JsonIterator.parse(content);

        // Binding iterator's next value into any-type global object
        Any any = iter.readAny();


        //Persons
        for(Any persons:any.get("persons")) {

            String firstName = persons.get("firstName").toString();
            String lastName = persons.get("lastName").toString();
            String address = persons.get("address").toString();
            String city = persons.get("city").toString();
            String zip = persons.get("zip").toString();
            String phone = persons.get("phone").toString();
            String email = persons.get("email").toString();

            Person person = new Person();

            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setAddress(address);
            person.setCity(city);
            person.setZip(zip);
            person.setPhone(phone);
            person.setEmail(email);

            personList.add(person);
        }

        //Firestations
        for(Any firestations:any.get("firestations")) {

            String address = firestations.get("address").toString();
            String station = firestations.get("station").toString();

            Firestation firestation = new Firestation();

            firestation.setAddress(address);
            firestation.setStation(station);

            firestationList.add(firestation);

        }

        //Medical Records
        for(Any medicalRecords:any.get("medicalrecords")) {

            String firstName = medicalRecords.get("firstName").toString();
            String lastName = medicalRecords.get("lastName").toString();

            String birthdate = medicalRecords.get("birthdate").toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthday = LocalDate.parse(birthdate, formatter);

            List<String> medicationList = medicalRecords.get("medications").asList().stream().map(Any::toString).toList();

            List<String> allergyList = medicalRecords.get("allergies").asList().stream().map(Any::toString).toList();

            MedicalRecord medicalRecord = new MedicalRecord();

            medicalRecord.setFirstName(firstName);
            medicalRecord.setLastName(lastName);
            medicalRecord.setBirthdate(birthday);
            medicalRecord.setMedications(medicationList);
            medicalRecord.setAllergies(allergyList);

            Person person = this.getPersonByName(firstName, lastName);

            person.setMedicalRecord(medicalRecord);

            medicalRecordsList.add(medicalRecord);
        }
    }

    public Person getPersonByName(String firstName, String lastName) {

        Person queriedPerson = null;

        for(Person person : personList) {
            if(person.getFirstName().equals(firstName) && (person.getLastName().equals(lastName))) {
                queriedPerson = person;

            }
        }
        return queriedPerson;
    }

    public Firestation getFirestationByAddress(String address) {

        Firestation queriedStation = null;

        for(Firestation firestation : firestationList) {
            if(firestation.getAddress().equals(address)) {
                queriedStation = firestation;

            }
        }
        return queriedStation;
    }

    public Firestation getFirestationByStationAndAddress(String station, String address) {

        Firestation queriedStation = null;

        for(Firestation firestation : firestationList) {
            if(firestation.getStation().equals(station) && firestation.getAddress().equals(address)) {
                queriedStation = firestation;

            }
        }
        return queriedStation;
    }

    public MedicalRecord getMedicalRecordByName(String firstName, String lastName) {

        MedicalRecord queriedRecord = null;

        for(MedicalRecord medicalRecord : medicalRecordsList) {
            if(medicalRecord.getFirstName().equals(firstName) && (medicalRecord.getLastName().equals(lastName))) {
                queriedRecord = medicalRecord;

            }
        }
        return queriedRecord;
    }
}
