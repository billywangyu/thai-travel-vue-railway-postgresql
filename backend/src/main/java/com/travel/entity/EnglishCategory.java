package com.travel.entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "english_categories")
public class EnglishCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, description;
    @Column(columnDefinition = "TEXT") private String content;
    private Integer total;
    private Integer masteredCount = 0;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<EnglishSentence> sentences = new ArrayList<>();
    // getters/setters
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
    public String getContent() { return content; } public void setContent(String content) { this.content = content; }
    public Integer getTotal() { return total; } public void setTotal(Integer total) { this.total = total; }
    public Integer getMasteredCount() { return masteredCount; } public void setMasteredCount(Integer masteredCount) { this.masteredCount = masteredCount; }
    public List<EnglishSentence> getSentences() { return sentences; } public void setSentences(List<EnglishSentence> sentences) { this.sentences = sentences; }
}
