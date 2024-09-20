package org.example.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendNotification {
    public static void main(String[] args) {
        StringBuilder str = new StringBuilder();
        List lst = new ArrayList() ;


        try (BufferedReader br = new BufferedReader(new FileReader("messages.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                //  bot.sendMessage(chatId, line);
                lst.add(line);
                str.append(line+" ");
            }
        } catch (IOException e) {
            System.out.println("Обломс");
        }

        System.out.println(str);
    }

    public static String sendNotification() {

        File currentDir = new File("");
        String rootPath = currentDir.getAbsolutePath();
        System.out.println("Root path of the project: " + rootPath);
        StringBuilder str = new StringBuilder();
        List<String> lst = new ArrayList<>();
        String currentDir1 = System.getProperty("user.dir");
        System.out.println("Current directory: " + currentDir1);
        try (BufferedReader br = new BufferedReader(new FileReader("./messages.txt"))) {
            // Чтение текущего рабочего каталога


            String line;
            while ((line = br.readLine()) != null) {
                // Добавление строки в список и StringBuilder
                lst.add(line);
                str.append(line).append(" ");
            }
        } catch (IOException e) {
            System.out.println("Обломс");
            e.printStackTrace();
        }

        return str.toString();
    }
}
