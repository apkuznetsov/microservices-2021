package ssau.kuznetsov.microservices.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;

public class ForexResponse implements Serializable {

    private Date date;
    private Map<String, Double> rates;

    public ForexResponse(Date date, Map<String, Double> rates) {
        this.date = date;
        this.rates = rates;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
