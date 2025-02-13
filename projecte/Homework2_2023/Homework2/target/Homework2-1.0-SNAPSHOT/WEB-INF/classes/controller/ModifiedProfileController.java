package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.AlertMessage;
import deim.urv.cat.homework2.model.Customer;
import deim.urv.cat.homework2.model.SignUpAttempts;
import deim.urv.cat.homework2.model.ViewCredentials;
import deim.urv.cat.homework2.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.UriRef;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.binding.ParamError;
import jakarta.mvc.security.CsrfProtected;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("ModifyProfile")
@Controller
public class ModifiedProfileController {
    @Inject BindingResult bindingResult;
    @Inject Logger log;
    @Inject CustomerService service;
    @Inject Models models;
    @Inject AlertMessage flashMessage;
    @Inject SignUpAttempts attempts;
    @Inject HttpServletRequest request;
    
    private Customer customer;

    @GET
    @Controller
    public String showModifyPage(@Context HttpServletRequest request) {
        this.customer = (Customer) request.getSession().getAttribute("customer");
        if (this.customer == null) {
            models.put("error", new Error("Session expired. Please log in again."));
            return "redirect:/Login"; // Redirigir al Login si no está autorizado
        }
        return "profile-modify.jsp";
    }
    
    @POST
    @UriRef("modify")
    @CsrfProtected
    public String updateCustomer(@Valid @BeanParam CustomerUpdate customerForm) {
        if (bindingResult.isFailed()) {
            AlertMessage alert = AlertMessage.danger("Validation failed!");
            bindingResult.getAllErrors().forEach((ParamError t) -> {
                alert.addError(t.getParamName(), "", t.getMessage());
            });
            log.log(Level.WARNING, "Data binding for CustomerForm failed.");
            models.put("errors", alert);
            return "signup-form.jsp";
        }

        // Obtener el cliente de la sesión
        this.customer = (Customer) request.getSession().getAttribute("customer");
        if (this.customer == null) {
            models.put("error", new Error("Session expired. Please log in again."));
            return "redirect:/Login"; // Redirigir si no se ha encontrado al customer
        }

        // Crear objeto de credenciales
        ViewCredentials credentials = new ViewCredentials(this.customer.getUsername(), this.customer.getPassword());

        // Crear objeto Customer con los datos del formulario
        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName(customerForm.getFirstName());
        updatedCustomer.setLastName(customerForm.getLastName());
        updatedCustomer.setEmail(customerForm.getEmail());
        ViewCredentials cred = new ViewCredentials(customerForm.getUsername(), customerForm.getPassword());
        updatedCustomer.setCredentials(cred);

        // Llamar al service para actualizar el cliente
        try {
            service.updateCustomer(this.customer.getCredentials().getId(), credentials, updatedCustomer);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error during updating: " + e.getMessage(), e);
            models.put("message", e.getMessage());  // Pasar el mensaje de error a la vista
            return "profile-modify.jsp";  // Redirigir a la página de error
        }
        // Redirigir a la página de perfil
        return "redirect:/Logout/Success";
    }
}

