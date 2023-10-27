package com.safetynet.safetynetalerts.service;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JsonReaderUtil {

    void readJSON() throws IOException {
        // Accessing a resource from the classpath using Spring
        Resource resource = new ClassPathResource("classpath:data.json");

        // Loading the file from the folder source
        File file = resource.getFile();

        // Reading file and storing all info, regardless of type, in 'content' array variable
        byte[] content = Files.readAllBytes(file.toPath());

        // Converting array content into an iterator object/creating iterator from byte array
        JsonIterator iter = JsonIterator.parse(content);

        // Binding iterator's next value into any-type object
        Any any = iter.readAny();

    }

}
