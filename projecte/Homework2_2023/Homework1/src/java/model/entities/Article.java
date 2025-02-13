/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author satxa
 */

@Entity
@XmlRootElement
@NamedQuery(
    name = "Article.findLatestArticleIdByCustomerId",
    query = "SELECT a " +
            "FROM Article a " +
            "WHERE a.author.id = :customerId " +
            "AND a.publishDate = (SELECT MAX(a2.publishDate) FROM Article a2 WHERE a2.author.id = :customerId)"
)
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="Article_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Article_Gen")
    private Long id;

    private String title;
    @Column(length = 1000)
    private String content;
    private LocalDate publishDate;
    private int views;
    private String featuredImageUrl;
    private boolean isPublic;
    @ElementCollection(targetClass = Topic.class)
    @Enumerated(EnumType.STRING)
    private List<Topic> topics;
    private String resume;
    @ManyToOne
    @JsonbTransient
    private Customer author;
    private String authorName;
    private String authorPhoto;
    
    public Article() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }

    public List<Topic> getTopic() {
        return topics;
    }

    public void setTopics(List<Topic> topic) {
        if (topic.size()<3) topics=topic;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void setAuthorName(String authorName){
        this.authorName=author.getFirstName()+' '+author.getLastName();
    }
    
    public String getAuthorName(){
        authorName=author.getFirstName()+' '+author.getLastName();
        return authorName;
    }
    
    public void setAuthorPhoto(String authorPhoto){
        this.authorPhoto=authorPhoto;
    }
    
    public String getAuthorPhoto(){
        authorPhoto=author.getFeaturedImageUrl();
        return authorPhoto;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Article[ Content=" + content + " ]";
    }
}
