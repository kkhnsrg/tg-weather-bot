package edu.telegram.weatherbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfo {
    private String name;
    private Double temperature;
    private Double humidity;
    private String main;
}
