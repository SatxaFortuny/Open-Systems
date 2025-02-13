package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Customer;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Controller
@Path("Dashboard")
public class DashboardController {

    @Inject Models models;
    @Inject HttpServletRequest request;

    @GET
    public String showDashboard() {
        // Recuperar el usuario desde la sesión
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        // Si no hay usuario, redirige a la página de login
        if (customer == null) {
            return "redirect:/Homework2/Web/Login";
        }

        // Añadir el usuario al modelo
        models.put("user", customer);

        // Retornar la vista
        return "dashboard.jsp";
    }
}
