package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FirestationService {

    private JsonReaderUtil jsonReaderUtil;
    private List<Firestation> firestationList;

    @Autowired
    public FirestationService(JsonReaderUtil jsonReaderUtil) {
        this.jsonReaderUtil = jsonReaderUtil;
        firestationList = jsonReaderUtil.getFirestationList();
    }

    //TEST - list all Firestation content
    public List<Firestation> getAllFirestations() {
        return firestationList;
    }

    //Add a new firestation
    public List<Firestation> addNewFirestation(Firestation newFirestation) {

        firestationList.add(newFirestation);
        return firestationList;
    }

    //Update a firestation details by address
    public Firestation updateFirestationInfo(String address, Firestation firestation) {

        Firestation firestationToUpdate = jsonReaderUtil.getFirestationByAddress(address);

        if(firestationToUpdate.getAddress() != null) {

            String station = firestation.getStation();
            if(station != null) {
                firestationToUpdate.setStation(station);
            }

            return firestationToUpdate;

        } else {
            return null;
        }
    }

    //Delete a firestation
    public void deleteFirestation(String address, String station) {

        if(station != null) {
            Firestation firestationToDelete = jsonReaderUtil.getFirestationByStation(station);
            firestationList.remove(firestationToDelete);
        }

        if(address != null) {
            Firestation firestationToDelete = jsonReaderUtil.getFirestationByAddress(address);
            firestationList.remove(firestationToDelete);
        }
    }
 }
