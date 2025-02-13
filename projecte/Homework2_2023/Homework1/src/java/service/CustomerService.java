package service;

import authn.Credentials;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entities.*;
import authn.Secured;
import com.sun.xml.messaging.saaj.util.Base64;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Stateless
@Path("customer")
public class CustomerService extends AbstractFacade<Customer> {
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    @PersistenceContext(unitName = "Homework1PU")
    private EntityManager em;
    
    public CustomerService() {
        super(Customer.class);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        List<Customer> customers = super.findAll();
        List<CustomerDTO> updatedCustomers = new ArrayList<>();  // Nueva lista para almacenar los clientes con su artículo

        // Itera sobre los clientes
        for (Customer customer : customers) {
            CustomerDTO customer2 = new CustomerDTO(customer, false);
            try {
                // Ejecutar la consulta para obtener el artículo más reciente del cliente
                List<Article> articles = em.createNamedQuery("Article.findLatestArticleIdByCustomerId", Article.class)
                                            .setParameter("customerId", customer.getId())
                                            .getResultList();
                Article article = null;
                if (articles.get(0)!= null) {
                    article = articles.get(0);
                }
                
                // Crea un nuevo enlace y asigna el artículo más reciente al cliente
                Link link = new Link();
                if (article != null) {
                    link.setArticle(article);
                }

                customer2.setLinks(link);  // Asigna el Link al cliente
            } catch (Exception e) {
                // Si ocurre una excepción (por ejemplo, si no se encuentra el artículo), simplemente no asignamos un artículo.
                // Log o manejar la excepción según sea necesario
                customer2.setLinks(null);  // Asigna null si no hay artículo
            }

            updatedCustomers.add(customer2);  // Agrega el cliente con su artículo (o sin artículo) a la lista nueva
        }

        // Retorna la lista de clientes con su artículo más reciente
        return Response.ok(updatedCustomers).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") Long id) {
        Customer customer = super.find(id);

        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Customer not found for id: " + id)
                           .build();
        }

        return Response.ok(new CustomerDTO(customer, true)).build();
    }

    @PUT
    @Secured
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateCustomer(@PathParam("id") Long id, Customer updatedData, @HeaderParam("Authorization") String reg) {
        // Buscar el cliente existente en la base de datos
        Customer existingCustomer = super.find(id);
        if (existingCustomer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No s'ha trobat el teu usuari.").build(); // Cliente no encontrado
        }
        
        if (!isSameUser(reg, id)){
            return Response.status(Response.Status.FORBIDDEN).entity("Les identificacions no coincideixen.").build(); // Cliente intenta modificar a otro
        }
        
        // Verificar si el objeto de credentials está presente
        if (updatedData.getCredentials() != null) {
            if (updatedData.getCredentials().getUsername() != null && !updatedData.getCredentials().getUsername().isEmpty()) {
                TypedQuery<Credentials> query = em.createNamedQuery("Credentials.findUser", Credentials.class);
                Credentials credentials = null;
                try {
                    credentials = query.setParameter("username", updatedData.getCredentials().getUsername()).getSingleResult();
                } catch (NoResultException e) {
                    // No credentials found; this is acceptable
                }
                if(credentials != null){
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Aquest username ja està en ús!").build(); 
                }
                existingCustomer.getCredentials().setUsername(updatedData.getCredentials().getUsername());
            }

            if (updatedData.getCredentials().getPassword() != null && !updatedData.getCredentials().getPassword().isEmpty()) {
                existingCustomer.getCredentials().setPassword(updatedData.getCredentials().getPassword());
            }
        }

        // Actualizar otros campos si están presentes
        if (updatedData.getFirstName() != null && !updatedData.getFirstName().isEmpty()) {
            existingCustomer.setFirstName(updatedData.getFirstName());
        }
        
        if (updatedData.getLastName() != null && !updatedData.getLastName().isEmpty()) {
            existingCustomer.setLastName(updatedData.getLastName());
        }
        
        if (updatedData.getEmail() != null && !updatedData.getEmail().isEmpty()) {
            if (!EmailValidator.isValid(updatedData.getEmail()))
                return Response.status(Response.Status.BAD_REQUEST).entity("Format de l'email no vàlid").build();
            TypedQuery<String> query = em.createNamedQuery("Customer.findEmail", String.class);
            String email = null;
            try {
                email = query.setParameter("email", updatedData.getEmail()).getSingleResult();
            } catch (NoResultException e) {
                // No email found; this is acceptable
            }
            if(email != null){
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Aquest email ja està en ús!").build(); 
            }
            existingCustomer.setEmail(updatedData.getEmail());
        }

        if (updatedData.getFeaturedImageUrl() != null && !updatedData.getFeaturedImageUrl().isEmpty()) {
            existingCustomer.setFeaturedImageUrl(updatedData.getFeaturedImageUrl());
        }
        
        if (updatedData.getArticles() != null && !updatedData.getArticles().isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).entity("No pots modificar articles!").build(); 
        }

        // Actualizar el cliente en la base de datos
        em.merge(existingCustomer);
        
        // Retornar respuesta de éxito
        return Response.status(Response.Status.OK)
                           .entity("Les dades s'han modificat correctament.")
                           .build();
    }

    // Método para saber si el cliente que está siendo modificado es el mismo que el autentificado
    private boolean isSameUser(String reg, Long id) {
        
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

            // Buscar credenciales en la base de datos
            TypedQuery<Credentials> query = em.createNamedQuery("Credentials.findUser", Credentials.class);
            Credentials credentials = query.setParameter("username", username).getSingleResult();
            
            // Validar ids
            return credentials.getId().equals(id);
        } catch (IllegalArgumentException | NoResultException e) {
            // Error en la decodificación o usuario no encontrado
            return false;
        }
    }
    
    
    @GET
    @Path("auth")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateCustomer(@HeaderParam("Authorization") String reg) {
        reg = reg.replace(AUTHORIZATION_HEADER_PREFIX, "");
        String decode = Base64.base64Decode(reg);
        StringTokenizer tokenizer = new StringTokenizer(decode, ":");
        String username = tokenizer.nextToken();
        String password = tokenizer.nextToken();
        if (username == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Username and password must be provided.")
                           .build();
        }

        try {
            // Buscar las credenciales del usuario
            TypedQuery<Credentials> query = em.createNamedQuery("Credentials.findUser", Credentials.class);
            query.setParameter("username", username);
            Credentials customerCred = query.getSingleResult();

            // Verificar si las credenciales son correctas
            if (customerCred != null && customerCred.getPassword().equals(password)) {
                Customer customer = super.find(customerCred.getId());
                return Response.ok(customer).build(); // Autenticación exitosa
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password.").build();
            }
        } catch (NoResultException e) {
            // Si no se encuentra el usuario
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("No user found with the provided username.")
                           .build();
        } catch (Exception e) {
            // Error inesperado
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("An error occurred while processing your request.")
                           .build();
        }
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response registerCustomer(Customer nouCustomer) {
        // Verificar si el objeto de credentials está presente
        if (nouCustomer.getCredentials() != null) {
            if (nouCustomer.getCredentials().getUsername() != null || nouCustomer.getCredentials().getUsername().isEmpty()) {
                TypedQuery<Credentials> query = em.createNamedQuery("Credentials.findUser", Credentials.class);
                Credentials credentials = null;
                try {
                    credentials = query.setParameter("username", nouCustomer.getCredentials().getUsername()).getSingleResult();
                } catch (NoResultException e) {
                    // No credentials found; this is acceptable
                }
                if (credentials != null) {
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Aquest username ja està en ús!").build();
                }
            }
            else {
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Has d'indicar un nom d'usuari!").build();
            }
            if (nouCustomer.getCredentials().getPassword() == null || nouCustomer.getCredentials().getPassword().isEmpty()) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Has d'indicar una contrasenya!").build();
            }
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Falten credencials!").build();
        }

        // Actualizar otros campos si están presentes
        if (nouCustomer.getFirstName() == null || nouCustomer.getFirstName().isEmpty()) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Has de posar un nom!").build();
        }

        if (nouCustomer.getLastName() == null || nouCustomer.getLastName().isEmpty()) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Has de posar un cognom!").build();
        }

        if (nouCustomer.getEmail() != null && !nouCustomer.getEmail().isEmpty()) {
            if (!EmailValidator.isValid(nouCustomer.getEmail())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("El correu electrònic no és vàlid!").build();
            }

            TypedQuery<String> query = em.createNamedQuery("Customer.findEmail", String.class);
            String email = null;
            try {
                email = query.setParameter("email", nouCustomer.getEmail()).getSingleResult();
            } catch (NoResultException e) {
                // No email found; this is acceptable
            }

            if (email != null) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Aquest email ja està en ús!").build();
            }
            nouCustomer.setEmail(nouCustomer.getEmail());
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Has de posar un correu electrònic!").build();
        }

        if (nouCustomer.getFeaturedImageUrl() != null && nouCustomer.getFeaturedImageUrl().isEmpty()) {
            nouCustomer.setFeaturedImageUrl(nouCustomer.getFeaturedImageUrl());
        }

        if (nouCustomer.getArticles() != null && nouCustomer.getArticles().isEmpty()) {
            return Response.status(Response.Status.FORBIDDEN).entity("No pots modificar articles!").build();
        }
        
        // Actualizar el cliente en la base de datos
        em.persist(nouCustomer.getCredentials());
        em.persist(nouCustomer);

        // Retornar respuesta de éxito
        return Response.status(Response.Status.OK)
                       .entity("Usuari creat correctament.")
                       .build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}

