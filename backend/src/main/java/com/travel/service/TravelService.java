package com.travel.service;
import com.travel.entity.*;
import com.travel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TravelService {
    @Autowired private TravelCardRepository cardRepo;
    @Autowired private VocabularyItemRepository vocabRepo;

    public List<TravelCard> getAllCards() { return cardRepo.findAll(); }
    public List<TravelCard> getCardsByCategory(String category) { return cardRepo.findByCategory(category); }
    @Transactional public TravelCard toggleCardCompleted(Long id) {
        TravelCard c = cardRepo.findById(id).orElseThrow(); c.setCompleted(!c.getCompleted()); return cardRepo.save(c);
    }
    public Map<String,Object> getProgress() {
        Map<String,Object> m=new HashMap<>(); m.put("total",cardRepo.count()); m.put("completed",cardRepo.findByCompletedTrue().size()); return m;
    }
    public List<VocabularyItem> getAllVocab() { return vocabRepo.findAll(); }
    public List<VocabularyItem> getVocabByCategory(String category) { return vocabRepo.findByCategory(category); }
    @Transactional public VocabularyItem toggleVocabLearned(Long id) {
        VocabularyItem v = vocabRepo.findById(id).orElseThrow(); v.setLearned(!v.getLearned()); return vocabRepo.save(v);
    }
    public List<Map<String,Object>> getCategories() {
        return cardRepo.findAll().stream().map(c->c.getCategory()).distinct().map(cat->{
            Map<String,Object> m=new HashMap<>(); m.put("id",cat); m.put("name",capitalize(cat)); m.put("color",catColor(cat));
            return m;
        }).collect(Collectors.toList());
    }
    private String capitalize(String s){
        switch(s){case"entry":return"入境";case"entertainment":return"娱乐";case"accommodation":return"住宿";case"attractions":return"景点";case"return":return"回程";default:return s;}
    }
    private String catColor(String s){
        switch(s){case"entry":return"#FFD54F";case"entertainment":return"#FF8A65";case"accommodation":return"#FFAB40";case"attractions":return"#EF5350";case"return":return"#FFB74D";default:return"#FFD54F";}
    }
}
