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

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Path("SignUp")
public class SignUpFormController {    
    @Inject BindingResult bindingResult;
    @Inject Logger log;
    @Inject CustomerService service;
    @Inject Models models;
    @Inject AlertMessage flashMessage;
    @Inject SignUpAttempts attempts;
    @Inject HttpServletRequest request;
    
    @GET
    public String showForm() {
        return "signup-form.jsp";
    }    
    
    @POST
    @UriRef("sign-up")
    @CsrfProtected
    public String signUp(@Valid @BeanParam CustomerViewBean customerForm) { 
        if (bindingResult.isFailed()) {
            AlertMessage alert = AlertMessage.danger("Validation failed!");
            bindingResult.getAllErrors().forEach((ParamError t) -> {
                alert.addError(t.getParamName(), "", t.getMessage());
            });
            log.log(Level.WARNING, "Data binding for CustomerForm failed.");
            models.put("errors", alert);
            return "signup-form.jsp";
        }

        try {
            Customer newCustomer = new Customer();
            newCustomer.setFirstName(customerForm.getFirstName());
            newCustomer.setLastName(customerForm.getLastName());
            newCustomer.setEmail(customerForm.getEmail());
            ViewCredentials cred = new ViewCredentials(customerForm.getUsername(), customerForm.getPassword());
            newCustomer.setCredentials(cred);
            log.log(Level.INFO, "Creating new Customer object from CustomerForm: {0}", newCustomer);
           
            Boolean registered = service.registerCustomer(newCustomer);

            if (!registered) {
                log.log(Level.WARNING, "A user with this e-mail address {0} already exists.", customerForm.getEmail());
                models.put("message", "A user with this e-mail address already exists!");
                return "signup-form.jsp";
            }
            models.put("customer", newCustomer);
            log.log(Level.INFO, "Customer registered successfully.");
            return "signup-success.jsp";
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error during registration: " + e.getMessage(), e);
            models.put("message", e.getMessage());
            return "signup-form.jsp";
        }
    }
}