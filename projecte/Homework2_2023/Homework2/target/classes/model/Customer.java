package deim.urv.cat.homework2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;

/**
 * Customer model for Homework2
 */
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private ViewCredentials credentials; 
    private String firstName;
    private String lastName;
    private String email;
    private String featuredImageUrl;
    private List<Article> articles; 
    private Link links;               

    public Customer() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEmail() {
        return fixNull(this.email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeaturedImageUrl() {
        return fixNull(this.featuredImageUrl);
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    // Métodos para manejar las credenciales (username y password)
    @JsonIgnore
    public String getUsername() {
        return credentials != null ? credentials.getUsername() : "";
    }

    public void setUsername(String username) {
        if (credentials != null) {
            credentials.setUsername(username);
        }
    }

    @JsonIgnore
    public String getPassword() {
        return credentials != null ? credentials.getPassword() : "";
    }

    public void setPassword(String password) {
        if (credentials != null) {
            credentials.setPassword(password);
        }
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Link getLinks() {
        return links != null ? links : null;
    }

    public void setLinks(Link links) {
        this.links = links;
    }

    public ViewCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(ViewCredentials credentials) {
        this.credentials = credentials;
    }

    private String fixNull(String in) {
        return (in == null) ? "" : in;
    }
}