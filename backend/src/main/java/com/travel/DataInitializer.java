package com.travel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.entity.*;
import com.travel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private TravelCardRepository cardRepo;
    @Autowired private VocabularyItemRepository vocabRepo;
    @Autowired private EnglishCategoryRepository englishCatRepo;

    @Override
    public void run(String... args) {
        ObjectMapper mapper = new ObjectMapper();

        // ========== 1. 旅行卡片 ==========
        if (cardRepo.count() == 0) {
            try (InputStream is = new ClassPathResource("cards.json").getInputStream()) {
                List<TravelCard> cards = mapper.readValue(is, new TypeReference<List<TravelCard>>() {});
                cardRepo.saveAll(cards);
                System.out.println("✅ 旅行卡片初始化完成，共 " + cards.size() + " 张");
            } catch (Exception e) {
                System.err.println("❌ 卡片数据初始化失败：");
                e.printStackTrace();
            }
        } else {
            System.out.println("⏭️ 卡片数据已存在，跳过初始化");
        }

        // ========== 2. 词汇数据（从 JSON 加载） ==========
        if (vocabRepo.count() == 0) {
            try (InputStream is = new ClassPathResource("vocabularies.json").getInputStream()) {
                List<VocabularyItem> vocabItems = mapper.readValue(is, new TypeReference<List<VocabularyItem>>() {});
                vocabRepo.saveAll(vocabItems);
                System.out.println("✅ 词汇数据初始化完成，共 " + vocabItems.size() + " 个");
            } catch (Exception e) {
                System.err.println("❌ 词汇数据初始化失败：");
                e.printStackTrace();
            }
        } else {
            System.out.println("⏭️ 词汇数据已存在，跳过初始化");
        }

        // ========== 3. 英语分类和句子（从 JSON 加载） ==========
        if (englishCatRepo.count() == 0) {
            try (InputStream is = new ClassPathResource("english_categories.json").getInputStream()) {
                List<EnglishCategory> categories = mapper.readValue(is, new TypeReference<List<EnglishCategory>>() {});

                // 手动建立双向关联，确保 sentences 持久化
                for (EnglishCategory cat : categories) {
                    if (cat.getSentences() != null) {
                        for (EnglishSentence s : cat.getSentences()) {
                            s.setCategory(cat);
                        }
                    }
                }

                englishCatRepo.saveAll(categories);
                System.out.println("✅ 英语分类初始化完成，共 " + categories.size() + " 个分类");
            } catch (Exception e) {
                System.err.println("❌ 英语分类数据初始化失败：");
                e.printStackTrace();
            }
        } else {
            System.out.println("⏭️ 英语分类数据已存在，跳过初始化");
        }
    }
}