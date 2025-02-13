package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.AlertMessage;
import deim.urv.cat.homework2.model.Customer;
import deim.urv.cat.homework2.model.SignUpAttempts;
import deim.urv.cat.homework2.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.binding.BindingResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import java.util.logging.Logger;

@Path("Profile")
@Controller
public class ProfileController {
    @Inject Models models;

    @GET
    public String showProfilePage(@Context HttpServletRequest request) {
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return "redirect:/Login"; // Redirigir al login si no está autenticado
        }
        models.put("customer", customer);
        return "profile-page.jsp"; // Vista para mostrar el perfil del usuario
    }
    
    @Path("Articles")
    @GET
    public String showProfileArticles(@Context HttpServletRequest request) {
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            return "redirect:/Login"; // Redirigir al login si no está autenticado
        }
        models.put("customer", customer);
        return "CustomerArticles.jsp"; // Vista para mostrar el perfil del usuario
    }
    
}

