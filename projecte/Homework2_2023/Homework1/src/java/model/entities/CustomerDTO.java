package model.entities;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Transient;
import jakarta.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 
DTO for Customer entity*/
public class CustomerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String featuredImageUrl;
    private List<ArticleDTO> articles;
    private boolean includeArticles;
    @Transient
    private Link links;

    // Constructor para convertir entidad a DTO
    public CustomerDTO(Customer customer, boolean includeArticles) {
        if (customer != null) {
            this.firstName = customer.getFirstName();
            this.lastName = customer.getLastName();
            this.featuredImageUrl = customer.getFeaturedImageUrl();
            this.includeArticles=includeArticles;
            
            // Mapea la lista de entidades Article a una lista de ArticleDTO
            if ((customer.getArticles() != null) && (includeArticles)) {
                this.articles = customer.getArticles().stream()
                    .map(article -> new ArticleDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getPublishDate(),
                    article.getViews(),
                    article.getFeaturedImageUrl(),
                    article.getIsPublic(),
                    article.getResume(),
                    article.getAuthorName(),
                    article.getAuthor().getFeaturedImageUrl()
                ))
                    .collect(Collectors.toList());
            }
        }
    }

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    @JsonbProperty
    public List<ArticleDTO> getArticles() {
        if (includeArticles) {
            return articles;  // Devuelve los artículos si la bandera está activada
        }
        return null;  // O devuelve null si no se deben incluir
    }

    public void setArticles(List<ArticleDTO> articles) {
        this.articles = articles;
    }

    @XmlElement
    public Link getLinks() {
        if(links != null) return links;
        else return null;
    }

    public void setLinks(Link links) {
        this.links = links;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
}