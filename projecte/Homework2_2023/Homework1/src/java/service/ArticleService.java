package service;

import model.entities.ArticleDTO;
import authn.Credentials;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import authn.Secured;
import com.sun.xml.messaging.saaj.util.Base64;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.StringTokenizer;
import model.entities.Article;
import model.entities.Customer;
import model.entities.Topic;

/**
 *
 * @author satxa
 */
@Stateless
@Path("article")
public class ArticleService{
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;
    @Inject
    private CustomerService cs;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(
        @QueryParam("topic")List<Topic> topic,
        @QueryParam("authorName") String authorName) {
        String baseQuery = "SELECT a FROM Article a WHERE 1=1";
        System.out.println("AL BACKEND author: " + authorName + " topics: " + topic);
        // Afegir condicions només si hi ha filtres específics
        // Agregamos condiciones al query en base a los valores
        if (authorName != null) {
            baseQuery += " AND a.authorName = :authorName";
        }
        if(topic != null && !topic.isEmpty()){
            for(int i=0; i<topic.size(); i++){
                baseQuery += " AND :topic"+i+" MEMBER OF a.topics";
            }
        }
        // Ordenar per popularitat
        baseQuery += " ORDER BY a.views DESC";

        // Crear la consulta
        Query query = em.createQuery(baseQuery);
           
        // Assignar els paràmetres si cal
        if (authorName != null ) {
            query.setParameter("authorName", authorName);
        }
        if (topic != null && !topic.isEmpty()) {
            for(int i=0; i<topic.size(); i++){
                query.setParameter("topic"+i+"", topic.get(i));
            }
        }
        
        // Executar la consulta
        List<Article> resultList = query.getResultList();
        if (resultList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Transformar articles seceers a previsualitzacions
        List<ArticleDTO> resultDTOList = new LinkedList<>();
        ArticleDTO dto;
        for(Article article:resultList){
            dto = new ArticleDTO(article.getId(), article.getTitle(), article.getPublishDate(), article.getViews(), article.getFeaturedImageUrl(), article.getIsPublic(), article.getResume(), article.getAuthorName(), article.getAuthorPhoto());
            resultDTOList.add(dto);
        }

        // Retornar la resposta JSON
        return Response.ok(resultDTOList).build();
    }
            
    //GET /rest/api/v1/article/${id}
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getArticleById(@PathParam("id") Long id, @HeaderParam("Authorization") String reg) {
        try {
            // Buscar el artículo por su ID usando una consulta
            Article article = em.createQuery("SELECT a FROM Article a WHERE a.id = :id", Article.class)
                                .setParameter("id", id)
                                .getSingleResult();
            
            // Si el artículo no es público, verifica el registro
            if (!article.getIsPublic()) {
                if (reg == null || !isRegistered(reg, null)) {
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }
            }

            // Incrementar el contador de vistas y actualizar en la base de datos
            article.setViews(article.getViews() + 1);
            article.setAuthorPhoto(article.getAuthor().getFeaturedImageUrl());
            em.persist(article);
            System.out.println("AL BACKEND article: " + article.getAuthorPhoto());
            // Retornar la información del artículo con un 200 OK
            return Response.ok(article).build();

        } catch (NoResultException e) {
            // Si no se encuentra el artículo, devolver 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).entity("Artículo no encontrado").build();
        } catch (Exception e) {
            // Manejar otros errores inesperados
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno").build();
        }
    }
    
     private boolean isRegistered(String reg, Long id) {
        
        // Validar si el encabezado es válido
        if (reg == null || reg.isEmpty()) {
            return false;
        }

        try {
            // Decodificar Base64 del encabezado Authorization
            reg = reg.replace(AUTHORIZATION_HEADER_PREFIX, "");
            String decode = Base64.base64Decode(reg);
            StringTokenizer tokenizer = new StringTokenizer(decode, ":");
            String username = tokenizer.nextToken();
            String password = tokenizer.nextToken();

            // Buscar credenciales en la base de datos
            TypedQuery<Credentials> query = em.createNamedQuery("Credentials.findUser", Credentials.class);
            Credentials credentials = query.setParameter("username", username).getSingleResult();
            
            if(id != null){
                return ((credentials.getPassword().equals(password)) && (credentials.getId().equals(id)));
            }
            // Validar contraseña
            return credentials.getPassword().equals(password);
        } catch (IllegalArgumentException | NoResultException e) {
            // Error en la decodificación o usuario no encontrado
            return false;
        }
    }
    
    @Secured
    @DELETE
    @Path("{id}")
    public Response deleteArticle(@PathParam("id") Long id, @HeaderParam("Authorization") String reg) {
        // Iniciar la transacción si no es gestionada automáticamente

        try {
            // Verificar la autorización del usuario
            Long identificacio = em.createQuery("SELECT a.author.credentials.id FROM Article a WHERE a.id = :id", Long.class)
                                    .setParameter("id", id)
                                    .getSingleResult();

            if (!isRegistered(reg, identificacio)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            // Buscar el artículo para eliminar
            Article article = em.find(Article.class, id);
            if (article == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Article not found").build();
            }

            // Eliminar el artículo
            em.remove(article);  // Elimina el artículo de la base de datos

            // Si necesitas hacer algo con el Customer, como revisar su lista de artículos
            Customer customer = em.find(Customer.class, identificacio);
            if (customer != null) {
                // Si quieres hacer algo con el Customer después de la eliminación
                // Por ejemplo, verificar su lista de artículos, no es necesario hacer refresh
                // Si lo necesitas, puedes hacer un refresh pero no es necesario en este caso
                em.refresh(customer);
            }
        } catch (Exception e) {
            // Si algo falla, se devuelve el código 500
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error: " + e.getMessage())
                           .build();
        }

        // Retorna un 200 OK cuando el artículo se ha eliminado correctamente
        return Response.ok().build();
    }
    
   
    @Secured
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Article article, @HeaderParam("Authorization") String reg){
        try {
            reg = reg.replace(AUTHORIZATION_HEADER_PREFIX, "");

            // Decodificar las credenciales Base64
            String decoded = Base64.base64Decode(reg);
            StringTokenizer tokenizer = new StringTokenizer(decoded, ":");
            String username = tokenizer.nextToken();
            String password = tokenizer.nextToken();

            // Verificar las credenciales en la base de datos
            TypedQuery<Long> query = em.createQuery(
                "SELECT c.id FROM Credentials c WHERE c.username = :username AND c.password = :password", 
                Long.class);
            Long id = query.setParameter("username", username)
                           .setParameter("password", password)
                           .getSingleResult();

            // Obtener el objeto de Credentials y el Customer asociado
            Credentials client = em.find(Credentials.class, id);
            if (client == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                               .entity("Unauthorized access to private article")
                               .build(); // Credenciales no válidas
            }

            // Obtener el Customer asociado a las credenciales
            TypedQuery<Customer> query2 = em.createQuery(
                "SELECT c FROM Customer c WHERE c.credentials.id = :id", 
                Customer.class);
            query2.setParameter("id", id);
            Customer customer = query2.getSingleResult();

            // Validar los tópicos del artículo
            if (article.getTopic() == null || article.getTopic().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("Necessites mínim un tòpic.")
                               .build(); // Si no hay tópicos válidos
            }
            for (Topic topic : article.getTopic()) {
                if (!isValidTopic(topic)) {
                    return Response.status(Response.Status.BAD_REQUEST)
                                   .entity("Tòpic no vàlid: " + topic)
                                   .build(); // Si algún tema no es válido
                }
            }

            // Establecer el autor y la fecha de publicación
            article.setAuthor(customer);
            customer.addArticle(article);
            article.setPublishDate(LocalDate.now());

            // Persistir el artículo
            em.persist(article);
            em.merge(customer);

            // Se devuelve CREATED y el id del artículo
            return Response.status(Response.Status.CREATED)
                           .entity("Article creat amb l'ID: " + article.getId())
                           .build();

        } catch (Exception e) {
            // Si algo falla, se devuelve el código 500
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error: " + e.getMessage())
                           .build();
        }
    }

    private boolean isValidTopic(Topic topic) {
        try {
            Topic.valueOf(topic.name());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
