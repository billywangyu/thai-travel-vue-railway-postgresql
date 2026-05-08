package com.travel.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
@Entity @Table(name = "english_sentences")
public class EnglishSentence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "category_id") @JsonIgnore
    private EnglishCategory category;
    @Column(length = 500) private String en;
    private String cn;
    // getters/setters
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public EnglishCategory getCategory() { return category; } public void setCategory(EnglishCategory category) { this.category = category; }
    public String getEn() { return en; } public void setEn(String en) { this.en = en; }
    public String getCn() { return cn; } public void setCn(String cn) { this.cn = cn; }
}
