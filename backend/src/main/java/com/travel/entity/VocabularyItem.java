package com.travel.entity;
import javax.persistence.*;
@Entity @Table(name = "vocabulary_items")
public class VocabularyItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word, pronunciation, partOfSpeech, definition, chineseTranslation, example, category, difficulty;
    private Boolean learned = false;
    // getters/setters
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getWord() { return word; } public void setWord(String word) { this.word = word; }
    public String getPronunciation() { return pronunciation; } public void setPronunciation(String pronunciation) { this.pronunciation = pronunciation; }
    public String getPartOfSpeech() { return partOfSpeech; } public void setPartOfSpeech(String partOfSpeech) { this.partOfSpeech = partOfSpeech; }
    public String getDefinition() { return definition; } public void setDefinition(String definition) { this.definition = definition; }
    public String getChineseTranslation() { return chineseTranslation; } public void setChineseTranslation(String chineseTranslation) { this.chineseTranslation = chineseTranslation; }
    public String getExample() { return example; } public void setExample(String example) { this.example = example; }
    public String getCategory() { return category; } public void setCategory(String category) { this.category = category; }
    public String getDifficulty() { return difficulty; } public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public Boolean getLearned() { return learned; } public void setLearned(Boolean learned) { this.learned = learned; }
}
