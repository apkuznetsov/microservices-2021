package ssau.kuznetsov.microservices.dtos;

import java.io.Serializable;
import java.sql.Date;

public class FromTo implements Serializable {

    private Date date;
    private String from;
    private String to;
    private double rate;

    public FromTo(
            Date date,
            String from, String to,
            double rate
    ) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
