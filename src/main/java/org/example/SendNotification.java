package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendNotification {
    public static void main(String[] args) {
        //  MyBot bot = new MyBot();
        String chatId = "YOUR_CHAT_ID"; // Укажите нужный chat_id
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

    public static String sendNotification (){
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
        return lst.toString();
    }
}
