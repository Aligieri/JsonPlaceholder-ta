package com.epam.ta.utils;

import io.restassured.internal.support.FileReader;
import java.io.File;


public class Utils {
    public static String readJsonToString(String filepath){
        File file = new File(filepath);
        return FileReader.readToString(file, "UTF-8");
    }
}
