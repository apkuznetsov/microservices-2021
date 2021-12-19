package ssau.kuznetsov.microservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssau.kuznetsov.microservices.dtos.DateRates;
import ssau.kuznetsov.microservices.dtos.FromAmountTo;
import ssau.kuznetsov.microservices.dtos.FromTo;
import ssau.kuznetsov.microservices.models.ExchangeRate;
import ssau.kuznetsov.microservices.repos.ExchangeRateRepo;
import ssau.kuznetsov.microservices.service.ForexService;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ForexController {

    private static final Logger log = Logger.getLogger(ForexService.class.getName());
    @Autowired
    private ExchangeRateRepo rateRepo;

    @GetMapping(path = "/today")
    public ResponseEntity today() {
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        Date today = new Date(System.currentTimeMillis());
        List<ExchangeRate> todayRates = rateRepo.findAllByDate(today);

        if (!todayRates.isEmpty()) {
            Map<String, Double> rates = todayRates.stream().collect(
                    Collectors.toMap(ExchangeRate::getLetterCode, ExchangeRate::getRate
                    ));
            DateRates response = new DateRates(today, rates);
            responseEntity = new ResponseEntity(response, HttpStatus.OK);
        }

        return responseEntity;
    }

    @GetMapping(path = "/{from}/{to}")
    public ResponseEntity fromTo(
            @PathVariable("from") String from,
            @PathVariable("to") String to) {

        ExchangeRate currFrom = rateRepo.findByLetterCode(from.toUpperCase());
        ExchangeRate currTo = rateRepo.findByLetterCode(to.toUpperCase());

        double rate = currTo.getRate() / currFrom.getRate();
        FromTo response = new FromTo(currFrom.getDate(), currFrom.getLetterCode(), currTo.getLetterCode(), rate);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{from}/{amount}/{to}")
    public ResponseEntity fromAmountTo(
            @PathVariable("from") String from,
            @PathVariable("amount") int amount,
            @PathVariable("to") String to) {

        ExchangeRate currFrom = rateRepo.findByLetterCode(from.toUpperCase());
        ExchangeRate currTo = rateRepo.findByLetterCode(to.toUpperCase());

        double rate = (double) amount * currTo.getRate() / currFrom.getRate();
        FromAmountTo response = new FromAmountTo(currFrom.getDate(),
                currFrom.getLetterCode(), amount,
                currTo.getLetterCode(), rate);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
