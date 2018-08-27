package com.airosoft.task.model.local;

public class WeatherModel {
    private long dateRequest;
    private ConditionModel condition;
    private WindModel wind;
    private MainDataModel mainData;

    public long getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(long dateRequest) {
        this.dateRequest = dateRequest;
    }

    public ConditionModel getCondition() {
        return condition;
    }

    public void setCondition(ConditionModel condition) {
        this.condition = condition;
    }

    public WindModel getWind() {
        return wind;
    }

    public void setWind(WindModel wind) {
        this.wind = wind;
    }

    public MainDataModel getMainData() {
        return mainData;
    }

    public void setMainData(MainDataModel mainData) {
        this.mainData = mainData;
    }
}
