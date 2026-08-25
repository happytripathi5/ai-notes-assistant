package com.AI.firstAi.dto;

public class KeyPointResponse {
    private String title;
    private String firstKeyPoint;
    private String secondKeyPoint;

    public String getTitle(){
        return this.title;

    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getFirstKeyPoint(){
        return this.firstKeyPoint;
    }
    public void setFirstKeyPoint(String firstKeyPoint){
        this.firstKeyPoint=firstKeyPoint;
    }

    public String getSecondKeyPoint(){
        return this.secondKeyPoint;
    }
    public void setSecondKeyPoint(String secondKeyPoint){
        this.secondKeyPoint=secondKeyPoint;
    }
}
