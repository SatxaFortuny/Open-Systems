/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deim.urv.cat.homework2.model;

/**
 *
 * @author miria
 */
public class Link {
    private String article;
    
    public String getArticle() {
        return fixNull(this.article);
    }

    public void setArticle(String article) {
        this.article = article;
    }

    private String fixNull(String in) {
        return (in == null) ? "" : in;
    }
}
