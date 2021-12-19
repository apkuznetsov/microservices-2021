package ssau.kuznetsov.microservices.controllers;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ssau.kuznetsov.microservices.models.ExchangeRate;
import ssau.kuznetsov.microservices.dtos.ThirdPartyResponse;
import ssau.kuznetsov.microservices.repos.ExchangeRateRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class ThirdPartyController implements InitializingBean {

    private final static long DELAY = 14_400_000;
    private static final Logger log = Logger.getLogger(ThirdPartyController.class.getName());
    private final String FOREX_URL;
    private final String ACCESS_KEY;
    @Autowired
    private ExchangeRateRepo rateRepo;

    @Autowired
    public ThirdPartyController(
            @Value("${forex.url}") String forexUrl,
            @Value("${forex.access-key}") String accessKey) {
        FOREX_URL = forexUrl;
        ACCESS_KEY = accessKey;
    }

    private List<ExchangeRate> toExchangeRate(ThirdPartyResponse response) {
        int quantity = response.getBase().length();
        List<ExchangeRate> result = new ArrayList<>(quantity);

        for (Map.Entry<String, Double> entry : response.getRates().entrySet()) {
            result.add(new ExchangeRate(entry.getKey(), entry.getValue(), response.getDate()));
        }

        return result;
    }

    @Scheduled(fixedDelay = DELAY)
    public void updateRateRepo() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FOREX_URL)
                .queryParam("access_key", ACCESS_KEY);
        RestTemplate restTemplate = new RestTemplate();

        ThirdPartyResponse thirdPartyResponse = restTemplate.getForObject(builder.toUriString(), ThirdPartyResponse.class);
        if (thirdPartyResponse == null) {
            return;
        }

        List<ExchangeRate> rates = toExchangeRate(thirdPartyResponse);

        try {
            rateRepo.saveAll(rates);
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        log.info("СОХРАНЕНО В БД");
    }

    @Override
    public void afterPropertiesSet() {
        //updateRateRepo();
    }
}
