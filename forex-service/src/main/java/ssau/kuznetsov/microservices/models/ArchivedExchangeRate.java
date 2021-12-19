package ssau.kuznetsov.microservices.models;

import javax.persistence.*;
import java.sql.Date;

@Table(name = "archived_exchange_rate")
@Entity
public class ArchivedExchangeRate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "letter_code")
    private String letterCode;
    @Column(name = "rate")
    private double exchangeRate;
    @Column(name = "date")
    private Date date;

    public ArchivedExchangeRate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLetterCode() {
        return letterCode;
    }

    public void setLetterCode(String letterCode) {
        this.letterCode = letterCode;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
