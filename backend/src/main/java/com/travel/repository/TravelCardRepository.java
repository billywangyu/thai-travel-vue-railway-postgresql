package com.travel.repository;
import com.travel.entity.TravelCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TravelCardRepository extends JpaRepository<TravelCard, Long> {
    List<TravelCard> findByCategory(String category);
    List<TravelCard> findByCompletedTrue();
}
