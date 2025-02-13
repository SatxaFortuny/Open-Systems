package deim.urv.cat.homework2.service;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.model.ArticleDTO;
import deim.urv.cat.homework2.model.Topic;
import deim.urv.cat.homework2.model.ViewCredentials;

import jakarta.ws.rs.QueryParam;
import java.util.List;
        
public interface ArticleService {

    public Article getArticleById(Long id, ViewCredentials credentials);
    
    public List<ArticleDTO> findAll(@QueryParam("topic")List<Topic> topic, @QueryParam("author") String author);
    
    public boolean deleteArticle(Long id, ViewCredentials credentials);
    
    //public boolean create(Article article);
}
