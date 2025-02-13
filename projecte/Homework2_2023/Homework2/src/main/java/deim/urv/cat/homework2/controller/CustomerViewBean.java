package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.model.Link;
import deim.urv.cat.homework2.model.ViewCredentials;
import jakarta.inject.Named;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import java.util.List;

@Named("customerView")
public class CustomerViewBean {
    @NotBlank
    @Size(min=2, max=30, message = "First name must be between 2 and 30 characters")
    @FormParam("firstName")
    private String firstName;
    
    @NotBlank
    @Size(min=2, max=30, message = "Last name must be between 2 and 30 characters")
    @FormParam("lastName")
    private String lastName;

    @NotBlank
    @Email(message = "Email should be valid")
    @FormParam("email")
    private String email;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @FormParam("username")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    @FormParam("password")
    private String password;

    @NotBlank
    @FormParam("featuredImageUrl")
    private String featuredImageUrl;

    private ViewCredentials credentials = new ViewCredentials();
    private List<Article> articles;
    private Link links;

    // Getters and Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    public ViewCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(ViewCredentials credentials) {
        this.credentials = credentials;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }
}

