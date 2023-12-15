package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.PersonPerFirestationDTO;
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

    private static final JsonReaderUtil jsonReaderUtil = new JsonReaderUtil();
    private static final List<Person> personList = jsonReaderUtil.getPersonList();
    private static final List<Firestation> firestationList = jsonReaderUtil.getFirestationList();

    //TEST - list all Person content
    public List<Person> getAllPersons() {
        return jsonReaderUtil.getPersonList();
    }

    //Add a new person
    public Person addNewPerson(Person newPerson) {

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

        Person personToDelete = jsonReaderUtil.getPersonByName(firstName, lastName);

        personList.remove(personToDelete);
    }

    //Returns list of counted children and adults covered by a certain firestation
    public List<PersonPerFirestationDTO> getPersonPerFirestation(String station) {

        List<PersonPerFirestationDTO> personPerFireStationListDTO = new ArrayList<>();

        int adults = 0;
        int children = 0;

        for(Firestation firestation : firestationList) {
            if(firestation.getStation().equals(station)) {
                String address = firestation.getAddress();
                for (Person person : personList) {
                    if (person.getAddress().equals(address)) {

                        PersonPerFirestationDTO personPerFireStationDTO = new PersonPerFirestationDTO();

                        personPerFireStationDTO.setFirstName(person.getFirstName());
                        personPerFireStationDTO.setLastName(person.getLastName());
                        personPerFireStationDTO.setAddress(person.getAddress());
                        personPerFireStationDTO.setCity(person.getCity());
                        personPerFireStationDTO.setZip(person.getZip());
                        personPerFireStationDTO.setPhone(person.getPhone());
                        if(person.isChild()) {
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

    public List<ChildrenPerHousehold> getChildrenPerHousehold(String address) {

        LocalDate today = LocalDate.now();
        List<Person> householdMembers = jsonReaderUtil.getPersonList();

        List<ChildrenPerHousehold> childrenPerHouseholdList = new ArrayList<>();

        for(Person person : personList) {
            if(person.getAddress().equals(address) && (person.isChild())) {

                ChildrenPerHousehold childrenPerHousehold = new ChildrenPerHousehold();

                childrenPerHousehold.setFirstName(person.getFirstName());
                childrenPerHousehold.setLastName(person.getLastName());
                childrenPerHousehold.setAge(Period.between(person.getMedicalRecord().getBirthdate(), today).getYears());

                List<ChildHouseholdMemberDTO> childHouseholdMemberDTOList = getChildHouseholdMemberDTOS(address, householdMembers, childrenPerHousehold);
                childrenPerHousehold.setHouseholdMembers(childHouseholdMemberDTOList);

                childrenPerHouseholdList.add(childrenPerHousehold);
            }
        }
        return childrenPerHouseholdList;
    }

    private static List<ChildHouseholdMemberDTO> getChildHouseholdMemberDTOS(String address, List<Person> householdMembers, ChildrenPerHousehold childrenPerHousehold) {
        List<ChildHouseholdMemberDTO> childHouseholdMemberDTOList = new ArrayList<>();

        for(Person householdMember : householdMembers){
            if(householdMember.getAddress().equals(address) && !householdMember.getFirstName().equals(childrenPerHousehold.getFirstName())) {
                ChildHouseholdMemberDTO childHouseholdMemberDTO = new ChildHouseholdMemberDTO();
                childHouseholdMemberDTO.setFirstName(householdMember.getFirstName());
                childHouseholdMemberDTO.setLastName(householdMember.getLastName());
                childHouseholdMemberDTOList.add(childHouseholdMemberDTO);
            }
        }
        return childHouseholdMemberDTOList;
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

    public List<PersonForFireAlertDTO> getFireAlert(String address) {

        LocalDate today = LocalDate.now();
        List<PersonForFireAlertDTO> personForFireAlertDTOList = new ArrayList<>();

        for(Person person : personList) {
            if(person.getAddress().equals(address)) {

                PersonForFireAlertDTO personForFireAlertDTO = new PersonForFireAlertDTO();

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

                personForFireAlertDTOList.add(personForFireAlertDTO);
            }
        }
        return personForFireAlertDTOList;
    }

    public List<PersonInfoDTO> getPersonInfo(String firstName, String lastName) {

        LocalDate today = LocalDate.now();
        List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();

        for(Person person : personList) {
            if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {

                PersonInfoDTO personInfoDTO = new PersonInfoDTO();

                personInfoDTO.setFirstName(person.getFirstName());
                personInfoDTO.setLastName(person.getLastName());
                personInfoDTO.setAddress(person.getAddress());
                personInfoDTO.setEmail(person.getEmail());
                personInfoDTO.setAge(Period.between(person.getMedicalRecord().getBirthdate(), today).getYears());
                personInfoDTO.setMedication(person.getMedicalRecord().getMedications());
                personInfoDTO.setAllergies(person.getMedicalRecord().getAllergies());

                personInfoDTOList.add(personInfoDTO);
            }
        }
        return personInfoDTOList;
    }

//    public List<Household> getFloodAlert(List<String> stationsList) {
//
//        List<Firestation> firestationList = jsonReaderUtil.getFirestationList();
//        List<Person> personList = jsonReaderUtil.getPersonList();
//        List<MedicalRecord> medicalRecordList = jsonReaderUtil.getMedicalRecordsList();
//
//        List<Household> householdAddressStationList = new ArrayList<>();
//
//        for(String station : stationsList) {
//            for(Firestation firestation : firestationList) {
//                if(station.equals(firestation.getStation())) {
//                    for(Person person : personList) {
//                        if(person.getAddress().equals(firestation.getAddress())) {
//
//                            PersonForFireAlertDTO personForFireAlertDTO = new PersonForFireAlertDTO();
//                            Household householdAddressStation = new Household();
//
//                            personForFireAlertDTO.setFirstName(person.getFirstName());
//                            personForFireAlertDTO.setLastName(person.getLastName());
//                            personForFireAlertDTO.setPhoneNumber(person.getPhone());
//
//                            for(MedicalRecord medicalRecord : medicalRecordList) {
//                                if(person.getFirstName().equals(medicalRecord.getFirstName()) && person.getLastName().equals(medicalRecord.getLastName())) {
//
//                                    personForFireAlertDTO.setAge(Period.between(medicalRecord.getBirthdate(), today).getYears());
//                                    personForFireAlertDTO.setMedicines(medicalRecord.getMedications());
//                                    personForFireAlertDTO.setAllergies(medicalRecord.getAllergies());
//                                }
//                            }
//
//                            householdAddressStation.setPersonForFireAlertDTOS(personForFireAlertDTO);
//                            householdAddressStationList.add(householdAddressStation);
//
//                        }
//                    }
//                }
//            }
//        }
//        return householdAddressStationList;
//    }

    public List<String> getEmails(String city) {

        List<String> emailList = new ArrayList<>();

        for(Person person : personList) {
            if(person.getCity().equals(city)) {
                emailList.add(person.getEmail());
            }
        }
        return emailList;
    }
}