package ssau.kuznetsov.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssau.kuznetsov.microservices.model.ExchangeRate;

@Repository
public interface RateRepository extends JpaRepository<ExchangeRate, Integer> {
}
