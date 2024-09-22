package org.example;

import org.example.service.Ewelink;
import org.example.service.SendCurlToGroup;
import org.example.service.Utils;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

import static org.example.config.Resources.*;

public class SvitHomeBot extends TelegramLongPollingBot {
    private static boolean light ;
    private static boolean light2 ;
    private static boolean svit ;
    private static boolean svit2 ;
    private static TimerTask task;
    static String msgLight = "світло є" + " \uD83D\uDCA1 ";
    static String msgNoLight = "світла нема " + " \uD83D\uDD6F ";
    static String vvod1 = "1️⃣ ввод - " ;
    static String vvod2 = "2️⃣ ввод - " ;
    public static final String greeting = """
                Привіт, я вмію показувати актуальні дані світла в домі (поки що тільки 1й ввод).\n Обговорення та автоматична розсилка повідомлень про стан світла є у цій групі - https://t.me/svithomeVirmenska6""";
    public static final String groupLink = "Автоматична розсилка повідомлень про стан світла є у цій групі \uD83D\uDC49 https://t.me/svithomeVirmenska6";
    public static final String ask = "Спитати можно тут \uD83D\uDCDD https://t.me/Tryamkin1";


    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new SvitHomeBot());

    }

    @Override
    public String getBotUsername() {
        return BotUsername;
    }
    @Override
    public String getBotToken() {
        return BotToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        if (update.hasMessage() && update.getMessage().getText().equals("/status")) {
            light(chatId);
            light2(chatId);
            Utils.showConsoleLogs(update);
       }

        if (update.hasMessage() && update.getMessage().getText().equals("/token")) {
            Ewelink.login();
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/auto")) {
            autoLight(chatIdGroup , update);
            autoLight2(update);
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/stop")) {
            stopAutoLight();
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            new Utils().sendMsg(greeting, chatId);
            Utils.showConsoleLogs(update);
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/ask")) {
            new Utils().sendMsg(ask, chatId);
            Utils.showConsoleLogs(update);
        }

        if (update.hasMessage() && update.getMessage().getText().equals("/free")) {
            try {
                SendCurlToGroup.sendMsg();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }

        }


    }

    private void autoLight(Long chatId, Update update ){
        java.util.Timer timer = new java.util.Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("svit " + svit);
                System.out.println("Ewelink.Status - " + Ewelink.isOnline());
                if (svit != Ewelink.Status()) {
                    svit = Ewelink.Status();
                    light(chatIdGroup);
                }
                Utils.showConsoleLogs(update);
                System.out.println("autolight working  " + Utils.getTime());
            }
        };
        long delay = 0;
        long period = 3 * 60 * 1000;
        timer.scheduleAtFixedRate(task, delay, period);
    }

    private void autoLight2(Update update ){
        java.util.Timer timer = new java.util.Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("svit2 " + svit2);
                System.out.println("Ewelink.Status2  - " + Ewelink.Status2());
                if (svit2 != Ewelink.Status2()){
                    svit2 = Ewelink.Status2();
                    light2(chatIdGroup);
                }
                Utils.showConsoleLogs(update);
                System.out.println("autolight2 working  " + Utils.getTime());
            }
        };
        long delay = 0;
        long period = 3 * 60 * 1000;
        timer.scheduleAtFixedRate(task, delay, period);
    }

    public void stopAutoLight() {
        task.cancel();
    }


    private void light(Long chatId)  {
        light = Ewelink.Status();
        SendMessage sendMessage3 = new SendMessage();
        sendMessage3.setChatId(chatId);
        if (Ewelink.getRepairStatus()){
            repeir(chatId);
        }
        if (light) {
           sendMessage3.setText(vvod1 + msgLight + Utils.getTime());
            try {
                execute(sendMessage3);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            sendMessage3.setText(vvod1 + msgNoLight + Utils.getTime());
            try {
                executeAsync(sendMessage3);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void light2(Long chatId)  {
        light2 = Ewelink.Status2();
        SendMessage sendMessage3 = new SendMessage();
        sendMessage3.setChatId(chatId);
        if (Ewelink.getRepairStatus()){
            repeir(chatId);
        }
        if (light2) {
            sendMessage3.setText(vvod2 +  " не встановлен" );// + msgLight+ Utils.getTime() +
            sendMessage3.setParseMode("markdown");
            try {
                executeAsync(sendMessage3);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            sendMessage3.setText(vvod2 + " не встановлен"); //+ msgNoLight + Utils.getTime()
            sendMessage3.setParseMode("markdown");
            try {
                executeAsync(sendMessage3);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void repeir(Long chatId)  {
        light = Ewelink.Status();
        SendMessage sendMessage3 = new SendMessage();
        sendMessage3.setChatId(chatId);
        sendMessage3.setText("Оновіть токен командою /token");
        try {
            executeAsync(sendMessage3);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}