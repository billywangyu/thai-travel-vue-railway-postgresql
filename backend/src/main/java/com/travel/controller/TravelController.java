package com.travel.controller;
import com.travel.entity.*;
import com.travel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api")
public class TravelController {
    @Autowired private TravelService travelService;
    @GetMapping("/cards") public List<TravelCard> getCards(@RequestParam(required=false) String category) {
        return category!=null ? travelService.getCardsByCategory(category) : travelService.getAllCards();
    }
    @PutMapping("/cards/{id}/toggle") public TravelCard toggleCard(@PathVariable Long id) { return travelService.toggleCardCompleted(id); }
    @GetMapping("/progress") public Map<String,Object> getProgress() { return travelService.getProgress(); }
    @GetMapping("/vocab") public List<VocabularyItem> getVocab(@RequestParam(required=false) String category) {
        return category!=null ? travelService.getVocabByCategory(category) : travelService.getAllVocab();
    }
    @PutMapping("/vocab/{id}/toggle") public VocabularyItem toggleVocab(@PathVariable Long id) { return travelService.toggleVocabLearned(id); }
    @GetMapping("/categories") public List<Map<String,Object>> getCategories() { return travelService.getCategories(); }
}
