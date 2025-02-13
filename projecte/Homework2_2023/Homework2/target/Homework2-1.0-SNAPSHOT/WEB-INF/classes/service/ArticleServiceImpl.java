package deim.urv.cat.homework2.service;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.model.ArticleDTO;
import deim.urv.cat.homework2.model.Topic;
import deim.urv.cat.homework2.model.ViewCredentials;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class ArticleServiceImpl implements ArticleService {
    private final WebTarget webTarget;
    private final jakarta.ws.rs.client.Client client;
    private static final String BASE_URI = "http://localhost:8080/Homework1/rest/api/v1";

    public ArticleServiceImpl() {
        client = jakarta.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("article");
    }
    
    @Override
    public Article getArticleById(Long id, ViewCredentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        // Concatenar username:password
        String credenciales = username + ":" + password;

        // Codificar las credenciales en Base64
        String encodedCredentials = Base64.getEncoder().encodeToString(credenciales.getBytes(StandardCharsets.UTF_8));
        System.out.println("id service: " + id);
        WebTarget creatingTarget = webTarget.path(id.toString());
        System.out.println("creatingTarget: " + creatingTarget);

        Response response = creatingTarget.request(MediaType.APPLICATION_JSON)
                                          .header("Authorization", "Basic " + encodedCredentials)
                                          .get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            Article article = response.readEntity(Article.class);
            System.out.println("article author photo: " + article.getAuthorPhoto());
            return article;
        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            throw new RuntimeException("Unauthorized: Private article. Please log in to read it.");
        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new RuntimeException("Not Found: Article with ID " + id + " does not exist");
        } else {
            throw new RuntimeException("Unexpected error: HTTP " + response.getStatus());
        }
    }


    @Override
    public List<ArticleDTO> findAll(List<Topic> topic, String authorName) {
        /*Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .get();*/
        WebTarget creatingTarget = webTarget;
        if (topic!=null && !topic.isEmpty()){
            for (Topic onetopic : topic){
                System.out.println("topics service: " + onetopic);
                if (onetopic!=null) creatingTarget = creatingTarget.queryParam("topic", onetopic);
            }
        }
        if (authorName!=null && !authorName.trim().isEmpty()){
            System.out.println("author service: " + authorName);
            creatingTarget = creatingTarget.queryParam("authorName", authorName);
        }
        
        System.out.println("creatingTarget: " + creatingTarget);
        Response response = creatingTarget.request(MediaType.APPLICATION_JSON).get();
        //System.out.println("Resposta: " + response);
        ArticleDTO articleList[] = response.readEntity(ArticleDTO[].class);
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            for(ArticleDTO article : articleList) System.out.println("Resposta: " + article.toString());
            return Arrays.asList(articleList);
        }
        else {
            return null;
        }
    }
    
    @Override
    public boolean deleteArticle(Long id, ViewCredentials credentials) {
        try {
            // Verificar si el ID es válido
            if (id == null || id < 1) {
                return false;
            }

            // Construir el encabezado Authorization para Basic Authentication
            String username = credentials.getUsername();
            String password = credentials.getPassword();

            // Concatenar username:password
            String credenciales = username + ":" + password;

            // Codificar las credenciales en Base64
            String encodedCredentials = Base64.getEncoder().encodeToString(credenciales.getBytes(StandardCharsets.UTF_8));

            // Realizar la solicitud DELETE con el encabezado Authorization
            Response response = webTarget.path(String.valueOf(id))
                                         .request(MediaType.APPLICATION_JSON)
                                         .header("Authorization", "Basic " + encodedCredentials)
                                         .delete();

            // Verificar si el estado de la respuesta es 200 (OK)
            return response.getStatus() == 200;
        } catch (Exception ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

}
