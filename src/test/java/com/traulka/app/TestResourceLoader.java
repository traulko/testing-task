package com.traulka.app;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

public class TestResourceLoader {
    public <ResourceT> ResourceT loadResource(Class<ResourceT> resourceClass, String resourceName) {
        try {
            URL resource = getClass().getClassLoader().getResource(resourceName);
            return new Gson()
                    .fromJson(
                            new JsonReader(new FileReader(resource.getFile())),
                            resourceClass);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
