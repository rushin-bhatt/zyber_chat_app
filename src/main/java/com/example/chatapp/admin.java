package com.example.chatapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class admin {
    public static void main(String[] args) {
        String filePath = "D:/PROJECTS/ZyberChat/ChatApp/ChatApp/src/main/java/com/example/chatapp/logs/serverlog.txt"; //user1 log or data to be sent to user2
        String filePath3 = "D:/PROJECTS/ZyberChat/ChatApp/ChatApp/src/main/java/com/example/chatapp/logs/clientlog.txt";//user2 log or data to be sent to user1
        String filePath4 = "D:/PROJECTS/ZyberChat/ChatApp/ChatApp/src/main/java/com/example/chatapp/logs/serverlocker.txt";//locker which can only be accessed by user1 & admin ( to add data)
        String filePath2 = "D:/PROJECTS/ZyberChat/ChatApp/ChatApp/src/main/java/com/example/chatapp/logs/clientlocker.txt"; //locker which can only be accessed by user2 & admin ( to add data)
        try {
            while (true) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
                if (bufferedReader.ready()) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                        try (FileWriter fileWriter2 = new FileWriter(filePath2,true)) {
                            fileWriter2.write(line + System.lineSeparator());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    FileWriter fileWriter = new FileWriter(filePath);
                    fileWriter.write("");
                    bufferedReader.close();
                    fileWriter.close();
                }
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(filePath3));
                if (bufferedReader2.ready()) {
                    String line;
                    while ((line = bufferedReader2.readLine()) != null) {
                        System.out.println(line);
                        try (FileWriter fileWriter2 = new FileWriter(filePath4,true)) {
                            fileWriter2.write(line + System.lineSeparator());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    FileWriter fileWriter = new FileWriter(filePath3);
                    fileWriter.write("");
                    bufferedReader.close();
                    fileWriter.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
