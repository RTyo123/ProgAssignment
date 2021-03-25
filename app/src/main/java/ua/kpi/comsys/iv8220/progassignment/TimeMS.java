package ua.kpi.comsys.iv8220.progassignment;

import android.annotation.SuppressLint;

import java.util.Date;

public class TimeMS {
    private int hour;
    private int minute;
    private int seconds;

    public TimeMS() {
        this.hour = 0;
        this.minute = 0;
        this.seconds = 0;
    }

    public TimeMS(int hour, int minute, int seconds) {
        if (hour >= 0 && hour <= 23)
            this.hour = hour;
        else
            this.hour = 0;

        if (minute >= 0 && minute <= 59)
            this.minute = minute;
        else
            this.minute = 0;

        if (seconds >= 0 && seconds <= 59)
            this.seconds = seconds;
        else
            this.seconds = 0;

        /*if (this.hour == 0){
            this.hour = 12;
        }*/
    }

    public TimeMS(Date date) {
        hour = date.getHours();
        minute = date.getMinutes();
        seconds = date.getSeconds();
    }

    @SuppressLint("DefaultLocale")
    public String getTime() {
        return String.format("%02d:%02d:%02d %sM", hour>12? hour-12: hour == 0? 12: hour, minute, seconds, hour<12? "A": "P");
    }

    private int getTimeInSec(){
        return hour*3600 + minute*60 + seconds;
    }

    private static int[] getTimeFromSec(int secs) {
        int newHour = secs/3600;
        secs %= 3600;
        int newMin = secs/60;
        secs %= 60;
        return new int[]{newHour, newMin, secs};
    }

    public TimeMS getTimeSum(TimeMS a, TimeMS b) {
        int[] sum = getTimeFromSec(a.getTimeInSec() + b.getTimeInSec());
        sum[0] %= 24;
        return new TimeMS(sum[0], sum[1], sum[2]);
    }

    public TimeMS getTimeSum(TimeMS b) {
        return getTimeSum(this, b);
    }

    public static TimeMS getTimeSub(TimeMS a, TimeMS b) {
        int[] sum = getTimeFromSec((24*3600)+a.getTimeInSec() - b.getTimeInSec());
        //System.out.println(sum[0] + " " + sum[1] + " " + sum[2]);
        sum[0] %= 24;
        return new TimeMS(sum[0], sum[1], sum[2]);
    }

    public TimeMS getTimeSub(TimeMS b) {
        return getTimeSub(this, b);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSeconds() {
        return seconds;
    }
}
