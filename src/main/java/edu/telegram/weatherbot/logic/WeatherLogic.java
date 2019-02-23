package edu.telegram.weatherbot.logic;

import edu.telegram.weatherbot.entity.WeatherInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class WeatherLogic {
    private static final String NAME_KEY = "name";
    private static final String MAIN_KEY = "main";
    private static final String TEMPERATURE_KEY = "temp";
    private static final String HUMIDITY_KEY = "humidity";
    private static final String WEATHER_KEY = "weather";

    private static final String WEATHER_API_BEGIN = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String WEATHER_API_END = "&units=metric&appid=6fff53a641b9b9a799cfd6b079f5cd4e";

    private WeatherLogic() {
    }

    public static String getWeather(String message, WeatherInfo info) throws IOException {
        URL url = new URL( WEATHER_API_BEGIN + message + WEATHER_API_END);

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        info.setName(object.getString(NAME_KEY));

        JSONObject main = object.getJSONObject(MAIN_KEY);
        info.setTemperature(main.getDouble(TEMPERATURE_KEY));
        info.setHumidity(main.getDouble(HUMIDITY_KEY));

        JSONArray getArray = object.getJSONArray(WEATHER_KEY);
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            info.setMain((String) obj.get(MAIN_KEY));
        }

        return "City: " + info.getName() + "\n" +
                "Temperature: " + info.getTemperature() + "C" + "\n" +
                "Humidity:" + info.getHumidity() + "%" + "\n" +
                "Main: " + info.getMain();
    }

}
