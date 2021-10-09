package ssau.kuznetsov.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssau.kuznetsov.microservices.model.ExchangeRate;

import java.sql.Date;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    List<ExchangeRate> findAllByDate(Date date);
}
