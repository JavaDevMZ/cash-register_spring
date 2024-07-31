package com.javadevMZ.service;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;

@org.springframework.stereotype.Service
public class Manager {

    public void writeToFile(String text, File file){
        try(FileWriter writer = new FileWriter(file, Charset.forName("UTF8"), true)){
            writer.write("\n" + text);
            writer.flush();
        }catch(Exception exception){

        }
    }
}
