package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Dtos.*;
import com.safetynet.safetynetalerts.model.*;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    JsonReaderUtil jsonReaderUtil;

    @Autowired
    public PersonService(JsonReaderUtil jsonReaderUtil) {
        this.jsonReaderUtil = jsonReaderUtil;
    }

    private List<Person> personList = jsonReaderUtil.getPersonList();
    private List<Firestation> firestationList = jsonReaderUtil.getFirestationList();
    private final LocalDate today = LocalDate.now();

    //Add a new person
    public List<Person> addNewPerson(Person newPerson) {

        personList.add(newPerson);

        return personList;
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

        Person personToDelete = jsonReaderUtil.getPersonByName(firstName, lastName);

        personList.remove(personToDelete);
    }

    public boolean isChild(Person person) {
        int age = Period.between(person.getMedicalRecord().getBirthdate(), today).getYears();
        return age < 18;
    }

    //Returns list of counted children and adults covered by a certain firestation
    public List<PersonPerFirestationDto> getPersonPerFirestation(String station) {

        List<PersonPerFirestationDto> personPerFireStationListDTO = new ArrayList<>();

        int adults = 0;
        int children = 0;

        for(Firestation firestation : firestationList) {
            if(firestation.getStation().equals(station)) {
                String address = firestation.getAddress();
                for (Person person : personList) {
                    if (person.getAddress().equals(address)) {

                        PersonPerFirestationDto personPerFireStationDTO = new PersonPerFirestationDto();

                        personPerFireStationDTO.setFirstName(person.getFirstName());
                        personPerFireStationDTO.setLastName(person.getLastName());
                        personPerFireStationDTO.setAddress(person.getAddress());
                        personPerFireStationDTO.setCity(person.getCity());
                        personPerFireStationDTO.setZip(person.getZip());
                        personPerFireStationDTO.setPhone(person.getPhone());
                        if(isChild(person)) {
                            children++;
                        } else {
                            adults++;
                        }

                        personPerFireStationDTO.setNumberOfChildren(children);
                        personPerFireStationDTO.setNumberOfAdults(adults);

                        personPerFireStationListDTO.add(personPerFireStationDTO);
                    }
                }
            }
        }
        return personPerFireStationListDTO;
    }

    public List<ChildrenPerHouseholdDto> getChildrenPerHousehold(String address) {

        List<Person> householdMembers = jsonReaderUtil.getPersonList();

        List<ChildrenPerHouseholdDto> childrenPerHouseholdDtoList = new ArrayList<>();

        for(Person person : personList) {
            if(person.getAddress().equals(address) && (isChild(person))) {

                ChildrenPerHouseholdDto childrenPerHouseholdDTO = new ChildrenPerHouseholdDto();

                childrenPerHouseholdDTO.setFirstName(person.getFirstName());
                childrenPerHouseholdDTO.setLastName(person.getLastName());
                childrenPerHouseholdDTO.setAge(Period.between(person.getMedicalRecord().getBirthdate(), today).getYears());

                List<ChildHouseholdMemberDto> childHouseholdMemberDtoList = getChildHouseholdMemberDTOS(address, householdMembers, childrenPerHouseholdDTO);
                childrenPerHouseholdDTO.setHouseholdMembers(childHouseholdMemberDtoList);

                childrenPerHouseholdDtoList.add(childrenPerHouseholdDTO);
            }
        }
        return childrenPerHouseholdDtoList;
    }

    public static List<ChildHouseholdMemberDto> getChildHouseholdMemberDTOS(String address, List<Person> householdMembers, ChildrenPerHouseholdDto childrenPerHouseholdDTO) {
        List<ChildHouseholdMemberDto> childHouseholdMemberDtoList = new ArrayList<>();

        for(Person householdMember : householdMembers){
            if(householdMember.getAddress().equals(address) && !householdMember.getFirstName().equals(childrenPerHouseholdDTO.getFirstName())) {
                ChildHouseholdMemberDto childHouseholdMemberDTO = new ChildHouseholdMemberDto();
                childHouseholdMemberDTO.setFirstName(householdMember.getFirstName());
                childHouseholdMemberDTO.setLastName(householdMember.getLastName());
                childHouseholdMemberDtoList.add(childHouseholdMemberDTO);
            }
        }
        return childHouseholdMemberDtoList;
    }

    public List<String> getPhoneNumbersPerFirestation(String station) {

        List<String> phoneNumbersPerFirestationList = new ArrayList<>();

        for(Firestation firestation : firestationList) {
            if(firestation.getStation().equals(station)) {
                for(Person person : personList) {
                    if (person.getAddress().equals(firestation.getAddress())) {

                        phoneNumbersPerFirestationList.add(person.getPhone());

                    }
                }
            }
        }
        return phoneNumbersPerFirestationList;
    }

    public List<PersonForFireAlertDto> getFireAlert(String address) {

        List<PersonForFireAlertDto> personForFireAlertDtoList = new ArrayList<>();

        for(Person person : personList) {
            if(person.getAddress().equals(address)) {

                PersonForFireAlertDto personForFireAlertDTO = new PersonForFireAlertDto();

                personForFireAlertDTO.setFirstName(person.getFirstName());
                personForFireAlertDTO.setLastName(person.getLastName());
                personForFireAlertDTO.setPhoneNumber(person.getPhone());
                personForFireAlertDTO.setAge(Period.between(person.getMedicalRecord().getBirthdate(), today).getYears());
                personForFireAlertDTO.setMedicines(person.getMedicalRecord().getMedications());
                personForFireAlertDTO.setAllergies(person.getMedicalRecord().getAllergies());

                for(Firestation firestation : firestationList) {
                    if(firestation.getAddress().equals(address)) {
                        personForFireAlertDTO.setFirestation(firestation.getStation());
                    }
                }

                personForFireAlertDtoList.add(personForFireAlertDTO);
            }
        }
        return personForFireAlertDtoList;
    }

    public List<PersonInfoDto> getPersonInfo(String firstName, String lastName) {

        List<PersonInfoDto> personInfoDtoList = new ArrayList<>();

        for(Person person : personList) {
            if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {

                PersonInfoDto personInfoDTO = new PersonInfoDto();

                personInfoDTO.setFirstName(person.getFirstName());
                personInfoDTO.setLastName(person.getLastName());
                personInfoDTO.setAddress(person.getAddress());
                personInfoDTO.setEmail(person.getEmail());
                personInfoDTO.setAge(Period.between(person.getMedicalRecord().getBirthdate(), today).getYears());
                personInfoDTO.setMedication(person.getMedicalRecord().getMedications());
                personInfoDTO.setAllergies(person.getMedicalRecord().getAllergies());

                personInfoDtoList.add(personInfoDTO);
            }
        }
        return personInfoDtoList;
    }

    public List<Household> getFloodAlert(List<String> stationsList) {

        List<Household> householdList = new ArrayList<>();

        for(String station : stationsList) {
            for(Firestation firestation : firestationList) {
                if(station.equals(firestation.getStation())) {

                    Household householdPerStation = new Household();

                    List<PersonForFloodAlertDto> personForFloodAlertDtoList = new ArrayList<>();
                    for(Person person : personList) {
                        if(person.getAddress().equals(firestation.getAddress())) {

                            PersonForFloodAlertDto personForFloodAlertDTO = new PersonForFloodAlertDto();

                            personForFloodAlertDTO.setFirstName(person.getFirstName());
                            personForFloodAlertDTO.setLastName(person.getLastName());
                            personForFloodAlertDTO.setPhoneNumber(person.getPhone());
                            personForFloodAlertDTO.setAge(Period.between(person.getMedicalRecord().getBirthdate(), today).getYears());
                            personForFloodAlertDTO.setMedicines(person.getMedicalRecord().getMedications());
                            personForFloodAlertDTO.setAllergies(person.getMedicalRecord().getAllergies());

                            personForFloodAlertDtoList.add(personForFloodAlertDTO);
                        }
                        householdPerStation.setPersonsInThisHousehold(personForFloodAlertDtoList);
                    }
                    householdList.add(householdPerStation);
                }
            }
        }
        return householdList;
    }

    public List<String> getEmails(String city) {

        List<String> emailList = new ArrayList<>();

        for(Person person : personList) {
            if(person.getCity().equals(city)) {
                emailList.add(person.getEmail());
            }
        }
        return emailList;
    }

    //TESTING
    public List<Person> getAllPersons() {
        return jsonReaderUtil.getPersonList();
    }
}