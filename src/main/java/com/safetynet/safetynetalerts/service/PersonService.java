package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.*;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final JsonReaderUtil jsonReaderUtil;
    private final LocalDate today = LocalDate.now();

    //TEST - list all Person content
    public List<Person> getAllPersons() {
        return jsonReaderUtil.getPersonList();
    }

    //Add a new person
    public Person addNewPerson(Person newPerson) {

        List<Person> personList = jsonReaderUtil.getPersonList();
        personList.add(newPerson);

        return newPerson;
    }

    //Update a person's details
    public Person updatePersonInfo(String firstName, String lastName, Person person) {

        Person personToUpdate = jsonReaderUtil.getPersonByName(firstName, lastName);

        if(personToUpdate.getFirstName() != null && personToUpdate.getLastName() != null) {

            String address = person.getAddress();
            if(address != null) {
                personToUpdate.setAddress(address);
            }

            String city = person.getCity();
            if(city != null) {
                personToUpdate.setCity(city);
            }

            String zip = person.getZip();
            if(zip != null) {
                personToUpdate.setZip(zip);
            }

            String phone = person.getPhone();
            if(phone != null) {
                personToUpdate.setPhone(phone);
            }

            String email = person.getEmail();
            if(email != null) {
                personToUpdate.setEmail(email);
            }

            return personToUpdate;

        } else {
            return null;
        }
    }

    //Delete a person
    public void deletePerson(String firstName, String lastName) {

        List<Person> personList = jsonReaderUtil.getPersonList();
        Person personToDelete = jsonReaderUtil.getPersonByName(firstName, lastName);

        personList.remove(personToDelete);
    }

    //Returns list of counted children and adults covered by a certain firestation
    public List<PersonPerFirestation> getPersonPerFirestation(String station) {

        List<Person> personList = jsonReaderUtil.getPersonList();
        List<Firestation> firestationList = jsonReaderUtil.getFirestationList();
        List<MedicalRecord> medicalRecordList = jsonReaderUtil.getMedicalRecordsList();

        List<PersonPerFirestation> personPerFireStationList = new ArrayList<>();

        int adults = 0;
        int children = 0;

        for(Firestation firestation : firestationList) {
            if(firestation.getStation().equals(station)) {
                String address = firestation.getAddress();
                for (Person person : personList) {
                    if (person.getAddress().equals(address)) {

                        PersonPerFirestation personPerFireStation = new PersonPerFirestation();

                        personPerFireStation.setFirstName(person.getFirstName());
                        personPerFireStation.setLastName(person.getLastName());
                        personPerFireStation.setAddress(person.getAddress());
                        personPerFireStation.setCity(person.getCity());
                        personPerFireStation.setZip(person.getZip());
                        personPerFireStation.setPhone(person.getPhone());

                        for(MedicalRecord medicalRecord : medicalRecordList) {
                            if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {

                                if (medicalRecord.getBirthdate().isBefore(today.minusYears(18))) {
                                    adults++;
                                } else {
                                    children++;
                                }
                            }
                        }
                        personPerFireStation.setNumberOfChildren(children);
                        personPerFireStation.setNumberOfAdults(adults);

                        personPerFireStationList.add(personPerFireStation);
                    }
                }
            }
        }
        return personPerFireStationList;
    }

    public List<ChildrenPerHousehold> getChildrenPerHousehold(String address) {

        List<Person> personList = jsonReaderUtil.getPersonList();
        List<MedicalRecord> medicalRecordList = jsonReaderUtil.getMedicalRecordsList();

        List<ChildrenPerHousehold> childrenPerHouseholdList = new ArrayList<>();

        for(Person person : personList) {
            if(person.getAddress().equals(address)) {
                for(MedicalRecord medicalRecord : medicalRecordList) {
                    if(medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName()) && medicalRecord.getBirthdate().isAfter(today.minusYears(18))) {

                        ChildrenPerHousehold childrenPerHousehold = new ChildrenPerHousehold();
                        List<Person> householdMemberList = new ArrayList<>();

                        childrenPerHousehold.setFirstName(medicalRecord.getFirstName());
                        childrenPerHousehold.setLastName(medicalRecord.getLastName());
                        childrenPerHousehold.setAge(Period.between(medicalRecord.getBirthdate(), today).getYears());

                        for(Person otherPerson : personList) {
                            if(otherPerson.getAddress().equals(address) && otherPerson.getFirstName().equals(childrenPerHousehold.getFirstName()) && otherPerson.getLastName().equals(childrenPerHousehold.getLastName())) {
                                householdMemberList.add(otherPerson);
                            }
                        }

                        childrenPerHousehold.setHouseholdMembers(householdMemberList);

                        childrenPerHouseholdList.add(childrenPerHousehold);
                    }
                }
            }
        }
        return childrenPerHouseholdList;
    }

    public List<Person> getHouseholdMembers(String address, String firstName, String lastName) {

        List<Person> personList = jsonReaderUtil.getPersonList();
        List<Person> householdMembersList = new ArrayList<>();

        for(Person person : personList) {
            if(person.getAddress().equals(address) && !person.getFirstName().equals(firstName) && !person.getLastName().equals(lastName)) {
                householdMembersList.add(person);
            }
        }
        return householdMembersList;
    }
}