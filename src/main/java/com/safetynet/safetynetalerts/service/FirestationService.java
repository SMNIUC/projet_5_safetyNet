package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FirestationService {

    private final JsonReaderUtil jsonReaderUtil = new JsonReaderUtil();

    //TEST - list all Firestation content
    public List<Firestation> getAllFirestations() {
        return jsonReaderUtil.getFirestationList();
    }

    //Add a new firestation
    public List<Firestation> addNewFirestation(Firestation newFirestation) {

        List<Firestation> firestationList = jsonReaderUtil.getFirestationList();
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

        List<Firestation> firestationList = jsonReaderUtil.getFirestationList();

        if(station != null && address != null) {
            Firestation firestationToDelete = jsonReaderUtil.getFirestationByStationAndAddress(station, address);
            firestationList.remove(firestationToDelete);
        }
    }
 }
