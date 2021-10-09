package ssau.kuznetsov.microservices.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssau.kuznetsov.microservices.dto.DateRates;
import ssau.kuznetsov.microservices.model.ExchangeRate;
import ssau.kuznetsov.microservices.repository.ExchangeRateRepository;
import ssau.kuznetsov.microservices.service.ForexService;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ForexRestcontroller {

    private static final Logger log = Logger.getLogger(ForexService.class.getName());
    @Autowired
    private ExchangeRateRepository rateRepo;

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
}
