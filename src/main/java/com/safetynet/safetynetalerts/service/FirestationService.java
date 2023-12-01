package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.utils.JsonReaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FirestationService {

    private final JsonReaderUtil jsonReaderUtil;

    public List<Firestation> getAllFirestations() {
        return jsonReaderUtil.getFirestationList();
    }
 }
