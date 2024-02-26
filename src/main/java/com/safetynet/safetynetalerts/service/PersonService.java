package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.dto.*;
import com.safetynet.safetynetalerts.model.*;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService
{

    private static final Logger log = LogManager.getLogger();
    private final JsonReaderUtil jsonReaderUtil;


    /**
     * Adds a new person information to the persons list
     *
     * @param newPerson - New person object
     * @return          - The new added person object with all their information
     */
    public Person addNewPerson( Person newPerson )
    {
        log.debug( "addNewPerson()" );
        jsonReaderUtil.getPersonList( ).add( newPerson );
        log.info("New person {} added to the list.", newPerson );

        return newPerson;
    }


    /**
     * Updates a person's information based on that person's first and family name
     *
     * @param firstName - First name of the person to update
     * @param lastName  - Last name of the person to update
     * @param person    - The new information to update on the person
     * @return          - The person with their updated information
     *
     * @throws ResourceNotFoundException
     */
    public Person updatePersonInfo( String firstName, String lastName, Person person ) throws ResourceNotFoundException
    {
        log.debug( "updatePersonInfo()" );

        Person personToUpdate = jsonReaderUtil.getPersonByName( firstName, lastName );

        if ( personToUpdate != null )
        {

            String address = person.getAddress( );
            if ( address != null )
            {
                personToUpdate.setAddress( address );
            }

            String city = person.getCity( );
            if ( city != null )
            {
                personToUpdate.setCity( city );
            }

            String zip = person.getZip( );
            if ( zip != null )
            {
                personToUpdate.setZip( zip );
            }

            String phone = person.getPhone( );
            if ( phone != null )
            {
                personToUpdate.setPhone( phone );
            }

            String email = person.getEmail( );
            if ( email != null )
            {
                personToUpdate.setEmail( email );
            }

            log.info( "Person with first name {} and last name {} was updated with the following information: {}.", firstName, lastName, person );

            return personToUpdate;

        }
        else
        {
            throw new ResourceNotFoundException( "Person to update not found" );
        }
    }


    /**
     * Deletes an entire person and their information based on their first and last name
     *
     * @param firstName - First name of the person to delete
     * @param lastName  - Last name of the person to delete
     */
    public void deletePerson( String firstName, String lastName )
    {
        log.debug( "deletePerson()" );
        Person personToDelete = jsonReaderUtil.getPersonByName( firstName, lastName );
        jsonReaderUtil.getPersonList( ).remove( personToDelete );
        log.info( "The following person was removed from the list: {} {}", firstName, lastName );
    }


    /**
     * Determines whether a person is a child or an adult
     * Is a child any person under 18 years old
     *
     * @param person - The person to define as a child or an adult
     * @return       - Boolean true is person is a child, false is person is an adult
     */
    public boolean isChild( Person person )
    {
        log.debug( "isChild()" );
        int age = Period.between( person.getMedicalRecord( ).getBirthdate( ), LocalDate.now( ) ).getYears( );
        log.info( "Returning whether the individual is a child." );

        return age < 18;
    }


    /**
     * Returns a list of the number of children and adults covered by a certain firestation number
     *
     * @param station - Number of the firestation
     * @return        - List of persons per firestation + number of children/adults
     */
    public List<PersonPerFirestationDto> getPersonPerFirestation( String station )
    {
        log.debug( "getPersonPerFirestation()" );
        List<PersonPerFirestationDto> personPerFireStationListDTO = new ArrayList<>( );

        int adults = 0;
        int children = 0;

        for ( Firestation firestation : jsonReaderUtil.getFirestationList( ) )
        {
            if ( firestation.getStation( ).equals( station ) )
            {
                String address = firestation.getAddress( );
                for ( Person person : jsonReaderUtil.getPersonList( ) )
                {
                    if ( person.getAddress( ).equals( address ) )
                    {

                        PersonPerFirestationDto personPerFireStationDTO = new PersonPerFirestationDto( );

                        personPerFireStationDTO.setFirstName( person.getFirstName( ) );
                        personPerFireStationDTO.setLastName( person.getLastName( ) );
                        personPerFireStationDTO.setAddress( person.getAddress( ) );
                        personPerFireStationDTO.setCity( person.getCity( ) );
                        personPerFireStationDTO.setZip( person.getZip( ) );
                        personPerFireStationDTO.setPhone( person.getPhone( ) );
                        if ( isChild( person ) )
                        {
                            children++;
                            log.info( "Counting children." );
                        }
                        else
                        {
                            adults++;
                            log.info( "Counting adults." );
                        }

                        personPerFireStationDTO.setNumberOfChildren( children );
                        personPerFireStationDTO.setNumberOfAdults( adults );

                        personPerFireStationListDTO.add( personPerFireStationDTO );
                    }
                }
            }
        }
        log.info( "Returning list of persons covered by firestation {}.", station );

        return personPerFireStationListDTO;
    }


    /**
     * Returns a list of the children living in a specified household
     *
     * @param address - Address of the household
     * @return        - List of the children living in this household
     */
    public List<ChildrenPerHouseholdDto> getChildrenPerHousehold( String address)
    {
        log.debug( "getChildrenPerHousehold()" );
        List<Person> householdMembers = jsonReaderUtil.getPersonList( );

        List<ChildrenPerHouseholdDto> childrenPerHouseholdDtoList = new ArrayList<>( );

        for ( Person person : jsonReaderUtil.getPersonList( ) )
        {
            if ( person.getAddress( ).equals( address ) && ( isChild( person ) ) )
            {

                ChildrenPerHouseholdDto childrenPerHouseholdDTO = new ChildrenPerHouseholdDto( );

                childrenPerHouseholdDTO.setFirstName( person.getFirstName( ) );
                childrenPerHouseholdDTO.setLastName( person.getLastName( ) );
                childrenPerHouseholdDTO.setAge( Period.between( person.getMedicalRecord( ).getBirthdate( ), LocalDate.now( ) ).getYears( ) );

                List<ChildHouseholdMemberDto> childHouseholdMemberDtoList = getChildHouseholdMemberDTOS( address, householdMembers, childrenPerHouseholdDTO );
                childrenPerHouseholdDTO.setHouseholdMembers( childHouseholdMemberDtoList );

                childrenPerHouseholdDtoList.add( childrenPerHouseholdDTO );
            }
        }
        log.info( "Returning list of children per household." );

        return childrenPerHouseholdDtoList;
    }


    /**
     * Returns a list of a child's household members
     *
     * @param address                  - Address of the household in which the child lives
     * @param householdMembers         - List of the household members at a specific address
     * @param childrenPerHouseholdDTO  - List of the children living at a specific address
     * @return                         - List of a child's household members
     */
    public static List<ChildHouseholdMemberDto> getChildHouseholdMemberDTOS( String address, List<Person> householdMembers, ChildrenPerHouseholdDto childrenPerHouseholdDTO )
    {
        log.debug( "getChildHouseholdMemberDTOS()" );
        List<ChildHouseholdMemberDto> childHouseholdMemberDtoList = new ArrayList<>( );

        for ( Person householdMember : householdMembers )
        {
            if ( householdMember.getAddress( ).equals( address ) && !householdMember.getFirstName( ).equals( childrenPerHouseholdDTO.getFirstName( ) ) )
            {
                ChildHouseholdMemberDto childHouseholdMemberDTO = new ChildHouseholdMemberDto( );
                childHouseholdMemberDTO.setFirstName( householdMember.getFirstName( ) );
                childHouseholdMemberDTO.setLastName( householdMember.getLastName( ) );
                childHouseholdMemberDtoList.add( childHouseholdMemberDTO );
            }
        }
        log.info( "Returning a list of the household members of each child living at {}.", address );

        return childHouseholdMemberDtoList;
    }


    /**
     * Gets all the phone numbers of the people covered by a specific firestation
     *
     * @param station - Number of the queried station
     * @return        - List of phone numbers
     */
    public List<String> getPhoneNumbersPerFirestation( String station )
    {
        log.debug( "getPhoneNumbersPerFirestation()" );
        List<String> phoneNumbersPerFirestationList = new ArrayList<>( );

        for ( Firestation firestation : jsonReaderUtil.getFirestationList( ) )
        {
            if ( firestation.getStation( ).equals( station ) )
            {
                for ( Person person : jsonReaderUtil.getPersonList( ) )
                {
                    if ( person.getAddress( ).equals( firestation.getAddress( ) ) )
                    {

                        phoneNumbersPerFirestationList.add( person.getPhone( ) );

                    }
                }
            }
        }
        log.info( "Returning a list of the phone numbers of the people covered by station {}.", station );

        return phoneNumbersPerFirestationList;
    }


    /**
     * Gets all the persons living at a specific address, as well as the number of the firestation
     * that covers this address
     *
     * @param address - Household address
     * @return        - List of the persons living at this address and the number of the firestation that covers it
     */
    public List<PersonForFireAlertDto> getFireAlert( String address )
    {
        log.debug( "getFireAlert()" );
        List<PersonForFireAlertDto> personForFireAlertDtoList = new ArrayList<>( );

        for ( Person person : jsonReaderUtil.getPersonList( ) )
        {
            if ( person.getAddress( ).equals( address ) )
            {

                PersonForFireAlertDto personForFireAlertDTO = new PersonForFireAlertDto( );

                personForFireAlertDTO.setFirstName( person.getFirstName( ) );
                personForFireAlertDTO.setLastName( person.getLastName( ) );
                personForFireAlertDTO.setPhoneNumber( person.getPhone( ) );
                personForFireAlertDTO.setAge( Period.between( person.getMedicalRecord( ).getBirthdate( ), LocalDate.now( ) ).getYears( ) );
                personForFireAlertDTO.setMedicines( person.getMedicalRecord( ).getMedications( ) );
                personForFireAlertDTO.setAllergies( person.getMedicalRecord( ).getAllergies( ) );

                for ( Firestation firestation : jsonReaderUtil.getFirestationList( ) )
                {
                    if ( firestation.getAddress( ).equals( address ) )
                    {
                        personForFireAlertDTO.setFirestation( firestation.getStation( ) );
                    }
                }

                personForFireAlertDtoList.add( personForFireAlertDTO );
            }
        }
        log.info( "Returning a list of the inhabitants living at {}, as well as the number of the firestation covering them.", address );

        return personForFireAlertDtoList;
    }


    /**
     * Returns all info for a specific person
     *
     * @param firstName - Person's first name
     * @param lastName  - Person's last name
     * @return          - List of that person's information
     */
    public List<PersonInfoDto> getPersonInfo( String firstName, String lastName )
    {
        log.debug( "getPersonInfo()" );
        List<PersonInfoDto> personInfoDtoList = new ArrayList<>( );

        for ( Person person : jsonReaderUtil.getPersonList( ) )
        {
            if ( person.getFirstName( ).equals( firstName ) && person.getLastName( ).equals( lastName ) )
            {

                PersonInfoDto personInfoDTO = new PersonInfoDto( );

                personInfoDTO.setFirstName( person.getFirstName( ) );
                personInfoDTO.setLastName( person.getLastName( ) );
                personInfoDTO.setAddress( person.getAddress( ) );
                personInfoDTO.setEmail( person.getEmail( ) );
                personInfoDTO.setAge( Period.between( person.getMedicalRecord( ).getBirthdate( ), LocalDate.now( ) ).getYears( ) );
                personInfoDTO.setMedication( person.getMedicalRecord( ).getMedications( ) );
                personInfoDTO.setAllergies( person.getMedicalRecord( ).getAllergies( ) );

                personInfoDtoList.add( personInfoDTO );
            }
        }
        log.info( "Returning all {} {}'s personal information.", firstName, lastName );

        return personInfoDtoList;
    }


    /**
     * Returns a list of all the persons covered by specific firestation(s).
     * Persons are grouped by household.
     *
     * @param stationsList - One or several firestation numbers
     * @return             - List of all the persons covered by these firestations, with their information, sorted by household
     */
    public List<Household> getFloodAlert( List<String> stationsList )
    {
        log.debug( "getFloodAlert()" );
        List<Household> householdList = new ArrayList<>( );

        for ( String station : stationsList )
        {
            for ( Firestation firestation : jsonReaderUtil.getFirestationList( ) )
            {
                if ( station.equals( firestation.getStation( ) ) )
                {

                    Household householdPerStation = new Household( );

                    List<PersonForFloodAlertDto> personForFloodAlertDtoList = new ArrayList<>( );
                    for ( Person person : jsonReaderUtil.getPersonList( ) )
                    {
                        if ( person.getAddress( ).equals( firestation.getAddress( ) ) )
                        {

                            PersonForFloodAlertDto personForFloodAlertDTO = new PersonForFloodAlertDto( );

                            personForFloodAlertDTO.setFirstName( person.getFirstName( ) );
                            personForFloodAlertDTO.setLastName( person.getLastName( ) );
                            personForFloodAlertDTO.setPhoneNumber( person.getPhone( ) );
                            personForFloodAlertDTO.setAge( Period.between( person.getMedicalRecord( ).getBirthdate( ), LocalDate.now( ) ).getYears( ) );
                            personForFloodAlertDTO.setMedicines( person.getMedicalRecord( ).getMedications( ) );
                            personForFloodAlertDTO.setAllergies( person.getMedicalRecord( ).getAllergies( ) );

                            personForFloodAlertDtoList.add( personForFloodAlertDTO );
                        }

                        householdPerStation.setPersonsInThisHousehold( personForFloodAlertDtoList );
                    }

                    householdList.add( householdPerStation );
                }
            }
        }
        log.info( "Returning a flood alert household list for firestation(s) {}.", stationsList );

        return householdList;
    }


    /**
     * Gets the emails of everyone living in a specific city
     *
     * @param city - Name of the city
     * @return     - List of all the emails
     */
    public List<String> getEmails( String city )
    {
        log.debug( "getEmails()" );
        List<String> emailList = new ArrayList<>( );

        for ( Person person : jsonReaderUtil.getPersonList( ) )
        {
            if ( person.getCity( ).equals( city ) )
            {
                emailList.add( person.getEmail( ) );
            }
        }
        log.info( "Returning the list of all email contacts in the city." );

        return emailList;
    }


    /**
     * Returns a list of all the persons data available
     *
     * @return - List of Person objects
     */
    public List<Person> getAllPersons( )
    {
        log.debug( "getAllPersons()" );
        log.info( "Returning all the person information available." );

        return jsonReaderUtil.getPersonList( );
    }
}