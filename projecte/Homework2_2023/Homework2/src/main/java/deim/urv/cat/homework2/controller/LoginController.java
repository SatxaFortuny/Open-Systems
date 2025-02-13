package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.exception.PropertyException;
import deim.urv.cat.homework2.model.AlertMessage;
import deim.urv.cat.homework2.model.Customer;
import deim.urv.cat.homework2.model.SignUpAttempts;
import deim.urv.cat.homework2.model.ViewCredentials;
import deim.urv.cat.homework2.service.BeanUtilHelper;
import deim.urv.cat.homework2.service.CustomerService;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.UriRef;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.security.CsrfProtected;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Path("Login")
public class LoginController {

    @Inject BindingResult bindingResult;
    @Inject Logger log;
    @Inject CustomerService service;
    @Inject Models models;
    @Inject AlertMessage flashMessage;
    @Inject SignUpAttempts attempts;
    @Inject HttpServletRequest request;



    @GET
    public String showForm() {
        return "login-form.jsp"; // Inyecta el token CSRF
    } 

    
    @POST 
    @UriRef("log-in")
    @CsrfProtected
    public String authenticateUser(@Valid @BeanParam Credentials credentials) throws IOException {
        if (bindingResult.isFailed()) {
            AlertMessage alert = AlertMessage.danger("Error de validació.");
            bindingResult.getAllErrors().forEach(t -> alert.addError(t.getParamName(), "", t.getMessage()));
            log.log(Level.WARNING, "Error de validació al processar el formulari de inici de sessió.");
            models.put("errors", alert);
            return "login-form.jsp";
        }

        // Crear i copiar dades a un ViewCredentials
        ViewCredentials viewCredentials = new ViewCredentials();
        BeanUtilHelper.copyCredentials(credentials, viewCredentials);

        try {
            Customer customer = service.authenticateCustomer(viewCredentials);
            if (customer == null) {
                log.log(Level.WARNING, "Usuari o contrasenya incorrectes. Intenta-ho de nou.");
                models.put("message", "Usuari o contrasenya incorrectes. Intenta-ho de nou.");
                attempts.increment();
                return "login-form.jsp";
            }
            log.log(Level.INFO, "Inici de sessió amb èxit per l'usuari: {0}", customer.getUsername());
            attempts.reset();
            CustomerViewBean viewCustomer = new CustomerViewBean();
            ViewCredentials credencials2 = new ViewCredentials();
            viewCustomer.setCredentials(credencials2);
            BeanUtilHelper.copyCustomer(customer, viewCustomer);
            
            models.put("customer", customer);
            // Recuperar la URL de redirección desde la sesión
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer); // 'customer' se guarda en la sesión
            session.setAttribute("customerView", viewCustomer);
            String redirectUrl = (String) session.getAttribute("redirectUrl");
            if (redirectUrl != null) {
                session.removeAttribute("redirectUrl"); // Limpiar la URL después de usarla
                return "redirect:"+redirectUrl; // No es necesario devolver una vista ya que hemos hecho una redirección
            }

            // Si no hay URL de redirección, redirigir a la página de perfil
            return "redirect:/Profile"; // Tornar null perquè la redirecció ja s'ha fet
        } catch (PropertyException ex) {
            log.log(Level.WARNING, "Usuari o contrasenya incorrectes. Intenta-ho de nou.");
            models.put("message", "Usuari o contrasenya incorrectes. Intenta-ho de nou.");
            AlertMessage.danger(ex.getMessage());
            attempts.increment();
            return "login-form.jsp";
        }
    }

}
