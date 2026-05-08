package com.travel.controller;
import com.travel.entity.*;
import com.travel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/english")
public class EnglishController {
    @Autowired private EnglishCategoryRepository catRepo;
    @GetMapping("/categories") public List<EnglishCategory> getCategories() { return catRepo.findAll(); }
    @PutMapping("/categories/{id}/progress") public EnglishCategory updateProgress(@PathVariable Long id, @RequestBody Map<String,Integer> body) {
        EnglishCategory cat = catRepo.findById(id).orElseThrow();
        cat.setMasteredCount(body.get("masteredCount")); return catRepo.save(cat);
    }
}
