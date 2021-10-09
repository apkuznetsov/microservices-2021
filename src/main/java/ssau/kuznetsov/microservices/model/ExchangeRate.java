package ssau.kuznetsov.microservices.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Table(name = "exchange_rate")
@Entity
public class ExchangeRate implements Serializable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "letter_code")
    private String letterCode;
    @Column(name = "rate")
    private double rate;
    @Column(name = "date")
    private Date date;

    public ExchangeRate() {
    }

    public ExchangeRate(String letterCode, Double rate, Date date) {
        this.letterCode = letterCode;
        this.rate = rate;
        this.date = date;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double exchangeRate) {
        this.rate = exchangeRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
