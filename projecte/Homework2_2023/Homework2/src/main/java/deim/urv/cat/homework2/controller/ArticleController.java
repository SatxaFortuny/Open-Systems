package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.model.ArticleDTO;
import deim.urv.cat.homework2.model.Customer;
import deim.urv.cat.homework2.service.ArticleServiceImpl;
import jakarta.inject.Inject;
import deim.urv.cat.homework2.model.Topic;
import deim.urv.cat.homework2.model.ViewCredentials;
import deim.urv.cat.homework2.service.CustomerServiceImpl;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.UriRef;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@Path("Article")
public class ArticleController{
    @Inject
    private ArticleServiceImpl articleService;  // Servicio que maneja la lógica de negocio.
    @Inject
    private Models models;
    @Inject
    private CustomerServiceImpl customerService;  // Servicio que maneja la lógica de negocio.
    @Inject 
    private HttpServletRequest request;
    @Inject Logger log;
    
    @GET
    @UriRef("see-article")
    @Path("{id}")
    public String getArticleById(@PathParam("id") Long id) {    
        try{
            System.out.println("id controller: " + id);
            Customer customer = (Customer) request.getSession().getAttribute("customer");
            ViewCredentials credentials = new ViewCredentials(null, null);
            log.log(Level.SEVERE, "customer = {0}", customer);
            if (customer != null){
                credentials = new ViewCredentials(customer.getUsername(), customer.getPassword());
            }
            Article article = articleService.getArticleById(id, credentials);
            if (article == null) {
                return "Error404.jsp";
            }
            models.put("article", article);
            return "Article.jsp";
        } catch (RuntimeException e){
            String currentUrl = request.getRequestURL().toString();
            String relativeUrl = currentUrl.replace("http://localhost:8080/Homework2/Web/", "");
            request.getSession().setAttribute("redirectUrl", relativeUrl);
            return "redirect:/Login";
        }
    }
    
    @GET
    @UriRef("see-articles")
    public String findAll(@QueryParam("topic")List<Topic> topic, @QueryParam("authorName") String authorName){
        System.out.println("topics Controller: " + topic);
        System.out.println("author Controller: " + authorName);
        List<ArticleDTO> articleList = articleService.findAll(topic, authorName);
        //for(ArticleDTO article : articleList) System.out.println("Resposta: " + article.toString());
        models.put("articleList", articleList);
        Topic topicList[] = Topic.values();
        models.put("topicList", topicList);
        return "ArticleList.jsp";
    }
    
    @POST
    //@UriRef("delete")
    @Path("Delete/{id}")
    public String deleteArticle(@PathParam("id") Long id) {
        log.log(Level.SEVERE, "id = {0}", id);
        if (id == null || id < 1) {
            // Redirigir a la pàgina d'error si la id és invàlida
            return "Error404.jsp";
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        log.log(Level.SEVERE, "customer = {0}", customer);
        ViewCredentials credentials = new ViewCredentials(customer.getUsername(), customer.getPassword());
        Boolean deleted = articleService.deleteArticle(id, credentials);
        if (Boolean.FALSE.equals(deleted)) {
            log.log(Level.SEVERE, "deleted = {0}", deleted);
            // Si no es pot eliminar, mostrar la pàgina d'error
            return "Error404.jsp";
        }
        customer = customerService.authenticateCustomer(credentials);
        request.getSession().setAttribute("customer", customer);
        // Si tot va bé, redirigir a la pàgina de confirmació d'eliminació
        return "CustomerArticles.jsp";
    }
}
