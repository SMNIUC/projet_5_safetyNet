package com.safetynet.safetynetalerts.service;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Utils {

    void readJSON() throws IOException {
        Resource resource = new ClassPathResource("classpath:data.json");
        File file = resource.getFile();
        byte[] content = Files.readAllBytes(file.toPath());
        JsonIterator iter = JsonIterator.parse(content);
        Any any = JsonIterator.readAny();

    }

}
