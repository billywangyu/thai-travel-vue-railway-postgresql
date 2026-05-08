package com.travel.entity;
import javax.persistence.*;
@Entity @Table(name = "travel_cards")
public class TravelCard {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardId, category, title, subtitle, icon, image;
    @Column(length = 1000) private String frontContent;
    @Column(columnDefinition = "TEXT") private String backContent;
    @Column(columnDefinition = "TEXT") private String keyPoints;
    private Boolean completed = false;
    // getters/setters
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getCardId() { return cardId; } public void setCardId(String cardId) { this.cardId = cardId; }
    public String getCategory() { return category; } public void setCategory(String category) { this.category = category; }
    public String getTitle() { return title; } public void setTitle(String title) { this.title = title; }
    public String getSubtitle() { return subtitle; } public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
    public String getIcon() { return icon; } public void setIcon(String icon) { this.icon = icon; }
    public String getImage() { return image; } public void setImage(String image) { this.image = image; }
    public String getFrontContent() { return frontContent; } public void setFrontContent(String frontContent) { this.frontContent = frontContent; }
    public String getBackContent() { return backContent; } public void setBackContent(String backContent) { this.backContent = backContent; }
    public String getKeyPoints() { return keyPoints; } public void setKeyPoints(String keyPoints) { this.keyPoints = keyPoints; }
    public Boolean getCompleted() { return completed; } public void setCompleted(Boolean completed) { this.completed = completed; }
}
