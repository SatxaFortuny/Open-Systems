package deim.urv.cat.homework2.model;

import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author satxa
 */

public class Article {
    private Long id;

    private String title;
    private String content;
    private LocalDate publishDate;
    private int views;
    private String featuredImageUrl;
    private boolean isPublic = true;
    private List<Topic> topics;
    private String resume;
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
        this.authorName = author.getFirstName()+' '+author.getLastName();
    }
    
    public void setAuthorName(String authorName){
        this.authorName = authorName;
    }
    
    public List<Topic> getTopic() {
        return topics;
    }

    public void setTopic(List<Topic> topic) {
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
    
    public String getAuthorName(){
        return authorName;
    }
    
    public void setAuthorPhoto(String authorPhoto){
        this.authorPhoto=authorPhoto;
    }
    
    public String getAuthorPhoto(){
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
    
}
