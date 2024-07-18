package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils extends SvitHomeBot{
   static String time ;
    public static void showTime() {
        Date now = new Date();
        // Создание объекта SimpleDateFormat для форматирования даты и времени
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // Форматирование текущей даты и времени
        String formattedDate = dateFormat.format(now);
        // Вывод формата даты и времени в консоль
        System.out.println("Current time: " + formattedDate);
        time = formattedDate;
    }

    public static String getTime(){
        showTime();
        return time;
    }


}
