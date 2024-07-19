package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.TimerTask;


public class SvitHomeBot extends TelegramLongPollingBot {
    private static boolean light ;
    private static boolean svit ;
    private static TimerTask task;
    private static String chatIdstr;
    private static Long chatIdGroup = -4242637154L ;

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
        chatIdstr = update.getMessage().getChatId().toString();
        autoLight(chatIdGroup);
        if (update.hasMessage() && update.getMessage().getText().equals("/status")) {
            light(chatId);
            System.out.println("Firstname - " + update.getMessage().getChat().getFirstName());
            System.out.println("Nickname - " + update.getMessage().getChat().getUserName());
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/token")) {
            Ewelink.login();
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/auto")) {
            // showMessage( "Запуск автоматичного сповіщення зміни статусу світла, наразі:");
            // light(chatId);
            autoLight(chatId);
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/stop")) {
            stopAutoLight();
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/test")) {
           SendCurlToGroup.testSendMsg();
        }
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("Привіт, я вмію показувати актуальні дані світла в домі (поки що тільки 1й ввод).\n" +
                    "Автоматична розсилка повідомлень про стан світла є у цій групі - https://t.me/svitlobot_virmenska_6\n" );
            //  "Ідея полягає в тому, щоб показати стан світла при частковому відключенню будинка. "
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
                System.out.println("svit - " + svit);
                System.out.println("Ewelink.Status - " + Ewelink.Status());
                if (svit != Ewelink.Status())
                light(-4242637154L);
                System.out.println("autolight working  " + Utils.getTime());
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


    private  void light(Long chatId)  {
        light = Ewelink.Status();
        SendMessage sendMessage3 = new SendMessage();
        sendMessage3.setChatId(chatId);
        if (Ewelink.getRepairStatus()){
            repeir(chatId);
        }

        if (light) {
            svit = true;
            String msgLight = "Світло є" + " \uD83D\uDCA1 "+ Utils.getTime();
            showMessage(msgLight);

        } else {
            svit = false;
            String msgNoLight = "Світла нема " + " \uD83D\uDD6F " + Utils.getTime();
            showMessage(msgNoLight);
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

    public void showMessage(String message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatIdstr); //"-1001863421062" - tryam group
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}