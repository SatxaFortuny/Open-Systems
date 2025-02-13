/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author miriam
 */
@XmlRootElement
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String article;

    public String getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = "/article/"+article.getId();
    }
    
}
