package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Timer;
import java.util.TimerTask;


public class SvitHomeBot extends TelegramLongPollingBot {
    private boolean light ;
    private boolean svit ;
    private TimerTask task;

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new SvitHomeBot());
    }

    @Override
    public String getBotUsername() {
        return
                "SvitHomeBot";
                //"TryamkinsBot";
    }

    @Override
    public String getBotToken() {
        return
                "7116590369:AAHTmFYS9Bgg1LiDF7CmOC7uIWKL7_XBx8s";
         //"5355288386:AAFEoSF-H7A592K1xziUay1J6DUfMXeoIlE";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = getChatId(update);
        if (update.hasMessage() && update.getMessage().getText().equals("/status")) {
            light(chatId);
            System.out.println("Firstname - " + update.getMessage().getChat().getFirstName());
            System.out.println("Nickname - " + update.getMessage().getChat().getUserName());
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/token")) {
            Ewelink.login();
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/auto")) {
            showMessage(chatId, "Запуск автоматичного сповіщення зміни статусу світла, наразі:");
            light(chatId);
            autoLight(chatId);
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/stop")) {
            stopAutoLight();
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("Привіт, я вмію показувати актуальні дані світла в домі (поки що тільки 1й ввод)" +
                    " працюю за прнципом опитування смарт пристрою кожні 5ть хвилин.");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void autoLight(Long chatId){
        java.util.Timer timer = new java.util.Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (svit != Ewelink.Status())
                    light(chatId);
                System.out.println("autolight working" + Utils.getTime());
                //   showMessage(chatId, "working");
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
            svit = true;
            sendMessage3.setText("Світло є" + " \uD83D\uDCA1 "+ Utils.getTime());
            try {
                executeAsync(sendMessage3);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else {
            svit = false;
            sendMessage3.setText("Світла нема " + " \uD83D\uDD6F " + Utils.getTime());
            try {
                execute(sendMessage3);
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


    public static Long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
    }

    public void showMessage(Long chatId,String message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}