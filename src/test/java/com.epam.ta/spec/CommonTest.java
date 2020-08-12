package com.epam.ta.spec;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CommonTest {
    static Path currentDir = Paths.get("./src/test/java/com.epam.ta/expected");
    public static final String EXPECTED_FILES_PATH = currentDir.toAbsolutePath().toString() + "\\";
    public static final String API_URI = "https://jsonplaceholder.typicode.com/";

    ObjectMapper mapper = new ObjectMapper();
}
