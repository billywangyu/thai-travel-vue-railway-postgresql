package com.travel.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.entity.*;
import com.travel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private TravelCardRepository cardRepo;
    @Autowired private VocabularyItemRepository vocabRepo;
    @Autowired private EnglishCategoryRepository englishCatRepo;
    @Autowired private EnglishSentenceRepository englishSentRepo;

    @PostMapping("/reload")
    @Transactional
    public String reloadAllData() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // 清空所有表
            englishSentRepo.deleteAll();
            englishCatRepo.deleteAll();
            vocabRepo.deleteAll();
            cardRepo.deleteAll();

            // 重新加载卡片
            try (InputStream is = new ClassPathResource("cards.json").getInputStream()) {
                List<TravelCard> cards = mapper.readValue(is, new TypeReference<List<TravelCard>>() {});
                cardRepo.saveAll(cards);
            }

            // 重新加载词汇
            try (InputStream is = new ClassPathResource("vocabularies.json").getInputStream()) {
                List<VocabularyItem> vocabItems = mapper.readValue(is, new TypeReference<List<VocabularyItem>>() {});
                vocabRepo.saveAll(vocabItems);
            }

            // 重新加载英语分类
            try (InputStream is = new ClassPathResource("english_categories.json").getInputStream()) {
                List<EnglishCategory> categories = mapper.readValue(is, new TypeReference<List<EnglishCategory>>() {});
                for (EnglishCategory cat : categories) {
                    if (cat.getSentences() != null) {
                        for (EnglishSentence s : cat.getSentences()) {
                            s.setCategory(cat);
                        }
                    }
                }
                englishCatRepo.saveAll(categories);
            }

            return "✅ 所有数据已重新加载";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ 重载失败：" + e.getMessage();
        }
    }
}