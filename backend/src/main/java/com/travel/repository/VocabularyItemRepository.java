package com.travel.repository;
import com.travel.entity.VocabularyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VocabularyItemRepository extends JpaRepository<VocabularyItem, Long> {
    List<VocabularyItem> findByCategory(String category);
    List<VocabularyItem> findByLearnedTrue();
}
