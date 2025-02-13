/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import authn.Credentials;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.util.List;
/**
 *
 * @author miriam
 */
@Entity
@XmlRootElement
@NamedQuery(
    name = "Customer.findAll",
    query = "SELECT c FROM Customer c"
)
@NamedQuery(
    name = "Customer.findById",
    query = "SELECT c FROM Customer c WHERE c.credentials.id = :id"
)
@NamedQuery(
    name = "Customer.findAllWithLatestArticle",
    query = "SELECT c, a " +
            "FROM Customer c " +
            "LEFT JOIN FETCH Article a ON a.author.id = c.id " +  // Asegúrate de que 'author' sea la relación con Customer
            "WHERE a.publishDate = (SELECT MAX(a2.publishDate) FROM Article a2 WHERE a2.author.id = c.id)"
)
@NamedQuery(name = "Customer.findByName", query = "SELECT c.credentials.id FROM Customer c "
        + "WHERE c.credentials.username = :name")
@NamedQuery(name = "Customer.findEmail", query = "SELECT c.email FROM Customer c "
        + "WHERE c.email = :email")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Customer_Gen", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Customer_Gen")
    private Long id;
    @OneToOne
    private Credentials credentials;
    private String firstName;
    private String lastName;
    private String email;
    private String featuredImageUrl;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Article> articles;
    @Transient
    private Link links;
    
    public Customer(){
        
    }

    public Long getId() {
        return credentials.getId();
    }

    public void setId(Long id) {
        credentials.setId(id);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @XmlTransient
    @JsonIgnore
    public String getUsername(){
        return credentials.getUsername();
    }
    
    public void setUsername(String username){
        credentials.setUsername(username);
    }
    
    @XmlTransient
    @JsonIgnore
    public String getPassword() {
        return credentials.getPassword();
    }

    public void setPassword(String password) {
        credentials.setPassword(password);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
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

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
    
    public void addArticle(Article article){
        if(article != null){
            if (!this.articles.contains(article)){
                this.articles.add(article);
                article.setAuthor(this);
            }
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((getId() == null && other.getId() != null) || (getId() != null && !getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Customer[ name=" + firstName + " ]";
    }
}
