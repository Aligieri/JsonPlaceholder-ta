package com.epam.ta.spec;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonTest {
    public static final String EXPECTED_FILES_PATH = "C:\\Users\\Stanislav_Ursakii\\Documents\\repo\\" +
            "JsonPlaceholder-ta\\src\\test\\java\\com.epam.ta\\expected\\";
    public static final String API_URI = "https://jsonplaceholder.typicode.com/";

    ObjectMapper mapper = new ObjectMapper();
}
