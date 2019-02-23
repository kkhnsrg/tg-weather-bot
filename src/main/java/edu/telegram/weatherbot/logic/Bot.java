package edu.telegram.weatherbot.logic;

import edu.telegram.weatherbot.entity.WeatherInfo;
import edu.telegram.weatherbot.utility.BotConstant;
import edu.telegram.weatherbot.utility.BotPrivateInfo;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {

    public void sendReply(Message message, String text) {
        SendMessage sendMessage = new SendMessage(message.getChatId(), text);
        sendMessage.enableMarkdown(true);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendReply(message, BotConstant.GREETING);
                    break;
                case "/help":
                    sendReply(message, BotConstant.HELP_INFO);
                    break;
                case "/setting":
                    sendReply(message, BotConstant.SETTING);
                    break;
                default:
                    try {
                        WeatherInfo info = new WeatherInfo();
                        sendReply(message, WeatherLogic.getWeather(message.getText(), info));
                    } catch (IOException e) {
                        sendReply(message, "I don't know this city!");
                    }
            }
        }
    }

    public String getBotUsername() {
        return BotPrivateInfo.BOT_USERNAME;
    }

    public String getBotToken() {
        return BotPrivateInfo.BOT_TOKEN;
    }
}
