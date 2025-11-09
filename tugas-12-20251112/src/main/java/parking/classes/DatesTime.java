package parking.classes;

import java.time.LocalDateTime;

public class DatesTime {
    Dates date;
    Time time;

    public DatesTime(int year, int month, int day, int hour, int minute, int second) {
        this.date = new Dates(year, month, day);
        this.time = new Time(hour, minute, second);
    }

    public Dates getDate() {
        return date;
    }

    public void setDate(Dates date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(
                this.date.getYear(),
                this.date.getMonth(),
                this.date.getDay(),
                this.time.getHour(),
                this.time.getMinute(),
                this.time.getSecond()
        );
    }
}
