package com.example.tejas.smartcityapp.Items;

/**
 * Created by Tejas on 24-03-2018.
 */

public class AlertItem {

    public String alert_id,date,time,title,area,type;

    public AlertItem(String alert_id,String date,String time,String title,String area,String type)
    {
        this.alert_id=alert_id;
        this.date=date;
        this.time=time;
        this.title=title;
        this.area=area;
        this.type=type;
    }

}
