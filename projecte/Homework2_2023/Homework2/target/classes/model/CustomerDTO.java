package deim.urv.cat.homework2.model;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.List;

public class CustomerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String featuredImageUrl;
    private List<ArticleDTO> articles;
    private boolean includeArticles;
    @Transient
    private Link links;

    // Constructor para convertir la entidad Customer en DTO
    public CustomerDTO(Customer customer, boolean includeArticles) {
        if (customer != null) {
            this.firstName = customer.getFirstName();
            this.lastName = customer.getLastName();
            this.featuredImageUrl = customer.getFeaturedImageUrl();
            this.includeArticles = includeArticles;

        }
    }

    // Getters y setters para los campos

    public String getFirstName() {
        return fixNull(this.firstName);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return fixNull(this.lastName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFeaturedImageUrl() {
        return fixNull(this.featuredImageUrl);
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    public boolean isIncludeArticles() {
        return includeArticles;
    }

    public void setIncludeArticles(boolean includeArticles) {
        this.includeArticles = includeArticles;
    }

    @JsonbProperty
    public List<ArticleDTO> getArticles() {
        if (includeArticles) {
            return articles;  // Devuelve los artículos si se debe incluir
        }
        return null;  // Si no se deben incluir, devuelve null
    }

    public void setArticles(List<ArticleDTO> articles) {
        this.articles = articles;
    }

    @Transient
    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }
    
    private String fixNull(String in) {
        return (in == null) ? "" : in;
    }
}
