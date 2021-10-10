package ssau.kuznetsov.microservices.dto;

import java.io.Serializable;
import java.sql.Date;

public class FromAmountTo implements Serializable {

    private Date date;
    private String from;
    private int amount;
    private String to;
    private double rate;

    public FromAmountTo(
            Date date,
            String from, int amount,
            String to, double rate
    ) {
        this.date = date;

        this.from = from;
        this.amount = amount;

        this.to = to;
        this.rate = rate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
