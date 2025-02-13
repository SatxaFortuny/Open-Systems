package model.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class ArticleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private LocalDate publishDate;
    private int views;
    private String featuredImageUrl;
    private boolean isPublic;
    private String resume;
    private String author; 
    private String authorPhoto;
    
    public ArticleDTO(){
        
    }
    
    // Constructor ajustado
    public ArticleDTO(Long id, String title, LocalDate publishDate, int views, String featuredImageUrl, boolean isPublic, String resume, String author, String authorPhoto) {
        this.id=id;
        this.title = title;
        this.publishDate = publishDate;
        this.views = views;
        this.featuredImageUrl = featuredImageUrl;
        this.isPublic = isPublic;
        this.resume = resume;
        this.author = author;
        this.authorPhoto = authorPhoto;
    }

    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id=id;
    }
    // Getters y setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }
    
    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate){
        this.publishDate=publishDate;
    }
    
    public int getViews() {
        return views;
    }

    public void setViews(int views){
        this.views=views;
    }
    
    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl){
        this.featuredImageUrl=featuredImageUrl;
    }
    
    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic){
        this.isPublic=isPublic;
    }
    
    public String getResume() {
        return resume;
    }

    public void setResume(String resume){
        this.resume=resume;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author){
        this.author=author;
    }

    public String getAuthorPhoto() {
        return authorPhoto;
    }
    
    public void setAuthorPhoto(String authorPhoto){
        this.authorPhoto=authorPhoto;
    }
    
    @Override
    public String toString(){
        return "id: "+id+"\ntitle:"+title+"\npublishDate:"+publishDate+"\nviews:"+views+"\nimage:"+featuredImageUrl+"\npublic:"+isPublic+"\nresume:"+resume+"\nauthor:"+author+"\nauthorPhoto:"+authorPhoto;
    }

}