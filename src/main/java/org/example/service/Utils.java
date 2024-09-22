package org.example.service;

import org.example.SvitHomeBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.example.config.Resources.chatIdGroup;

public class Utils extends SvitHomeBot {
   static String time ;
    public static void showTime() {
        Date now = new Date();
        // Создание объекта SimpleDateFormat для форматирования даты и времени
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // Форматирование текущей даты и времени
        String formattedDate = dateFormat.format(now);
        // Вывод формата даты и времени в консоль
        // System.out.println("Current time: " + formattedDate);
        time = formattedDate;
    }

    public static String getTime(){
        showTime();
        return time;
    }
    public static SendMessage createMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");
        message.setChatId(chatId);
        return message;
    }

    public void showMsgInGroup(String message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatIdGroup);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMsg(String message, Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public static void showConsoleLogs(Update update){
        System.out.println("Firstname - " + update.getMessage().getChat().getFirstName());
        System.out.println("ChatId - " + update.getMessage().getChatId().toString());
    }
}
