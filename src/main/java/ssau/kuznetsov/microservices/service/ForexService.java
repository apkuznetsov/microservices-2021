package ssau.kuznetsov.microservices.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ssau.kuznetsov.microservices.model.ExchangeRate;
import ssau.kuznetsov.microservices.model.ForexResponse;
import ssau.kuznetsov.microservices.repository.RateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class ForexService implements InitializingBean {

    private final static long DELAY = 14_400_000;
    private static final Logger log = Logger.getLogger(ForexService.class.getName());
    private final String FOREX_URL;
    private final String ACCESS_KEY;
    @Autowired
    private RateRepository rateRepo;

    @Autowired
    public ForexService(
            @Value("${forex.url}") String forexUrl,
            @Value("${forex.access-key}") String accessKey) {
        FOREX_URL = forexUrl;
        ACCESS_KEY = accessKey;
    }

    private List<ExchangeRate> toExchangeRate(ForexResponse response) {
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

        ForexResponse forexResponse = restTemplate.getForObject(builder.toUriString(), ForexResponse.class);
        if (forexResponse == null) {
            return;
        }

        List<ExchangeRate> rates = toExchangeRate(forexResponse);

        try {
            rateRepo.saveAll(rates);
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        log.info("СОХРАНЕНО В БД");
    }

    @Override
    public void afterPropertiesSet() {
        updateRateRepo();
    }
}
