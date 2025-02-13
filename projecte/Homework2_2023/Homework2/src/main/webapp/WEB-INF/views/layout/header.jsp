<%@ page import="deim.urv.cat.homework2.model.Customer" %>
<!doctype html>
<html lang="es">
  <head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Honk&display=swap" rel="stylesheet">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Autenticación</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
      header {
        background-color: #000; /* Fondo negro */
        padding: 1rem;
      }

      .nav-link {
        font-size: 1.3rem;
        color: #fff !important;
      }

      .nav-link:hover {
        text-decoration: underline;
      }

      .btn-outline-light {
        background-color: #fff;
        color: #000;
        border: 1px solid #fff;
        font-size: 1.3rem;
      }

      .btn-outline-light:hover {
        background-color: #ddd;
        color: #000;
      }

      /* Imagen de perfil */
      .profile-img {
        width: 40px; /* Tamaño de la imagen */
        height: 40px; /* Tamaño de la imagen */
        border-radius: 50%; /* Imagen redonda */
        object-fit: cover; /* Ajusta la imagen para que no se deforme */
      }

      .welcome-message {
        display: flex;
        align-items: center;
      }

      .header-right {
        display: flex;
        align-items: center;
      }

      .header-left {
        display: flex;
        align-items: center;
      }
      
      .custom-text {
        font-size: 24px;
        font-weight: bold;
      }
      
      @media (max-width: 768px) {
        .custom-text {
            font-size: 10px; /* Texto más grande solo para móviles */
        }
    }

    .honk-nom_pagina {
      font-family: "Honk", serif;
      font-optical-sizing: auto;
      font-size: 60px;
      font-style: normal;
      font-variation-settings:
        "MORF" 15,
        "SHLN" 50;
    }
    
    .custom-botton {
        background-color: #fde4ff; 
        color: black; 
        border: none;
        width: 150px; 
        height: 50px; 
        font-size: 25px;
    }
    
    @media (max-width: 768px) {
        .honk-nom_pagina {
            font-family: "Honk", serif;
            font-optical-sizing: auto;
            font-size: 40px;
            font-style: normal;
            font-variation-settings:
              "MORF" 15,
              "SHLN" 50;
        }
        
        .custom-botton {
        background-color: #fde4ff; 
        color: black; 
        border: none;
        width: 60px; 
        height: 23px; 
        font-size: 10px;
        }
    }

    
    
    
    </style>
  </head>
  <body>
    <header style="background-color: #ff00e0">
      <div class="container-fluid">
        <div class="d-flex flex-wrap align-items-center justify-content-between">
          <!-- Logo -->
          <div class="d-flex justify-content-between w-100">
            <!-- Imagen de perfil y mensaje de bienvenida (si está autenticado) -->
            <div class="header-left">
              <a href="http://localhost:8080/Homework2/Web/Article">
                <img src="http://localhost:8080/Homework2/resources/img/gorilamono.png" alt="Logo" class="profile-img" style="width: 75px; height: 75px;">
              </a>

              <!-- Verificar si el atributo "customer" está en la sesión usando pageContext -->
              <%
                Object customer = pageContext.findAttribute("customer");
                if (customer != null) {
              %>
                  <p class="mb-0 text-white custom-text">Benvingut, 
                    <a href="/Homework2/Web/Profile" class="text-warning custom-text"><%= ((Customer) customer).getFirstName() %></a>!
                  </p>
              <%
                } else {
              %>
                  <p class="mb-0 text-white custom-text">Benvingut, visitant!</p>
              <%
                }
              %>
            </div>
                
            <div class="header-center">
              <p class="mb-0 honk-nom_pagina">Blogilla</p>
            </div>

            <!-- Botón de Login/Logout a la derecha -->
            <div class="header-right">
              <%
                if (customer != null) {
              %>
                <a href="/Homework2/Web/Profile/Articles" class="btn btn-outline-light ms-3 custom-botton" >MyArticles</a>
                <a href="/Homework2/Web/Logout" class="btn btn-outline-light ms-3 custom-botton">Logout</a>
              <%
                } else {
              %>
                <a href="/Homework2/Web/SignUp" class="btn btn-outline-light ms-3 custom-botton">SignUp</a>
                <a href="/Homework2/Web/Login" class="btn btn-outline-light ms-3 custom-botton">Login</a>
              <%
                }
              %>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- Bootstrap Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
