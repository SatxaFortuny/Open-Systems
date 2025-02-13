<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar sesión</title>
    <!-- Bootstrap CSS -->
    <link href="https://getbootstrap.esdocu.com/docs/5.3/dist/css/bootstrap.min.css" 
          rel="stylesheet" 
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" 
          crossorigin="anonymous" />
    <!-- FontAwesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet" />
    <style>
      /* Fondo del cuerpo */
      body {
        background-color: #333 !important; /* Fondo negro */
        color: #fff; /* Texto blanco */
        font-family: Arial, sans-serif;
      }

      /* Estilo del formulario principal */
      .form-container {
        margin: 50px auto;
        padding: 20px;
        background-color: #222; /* Fondo gris oscuro */
        color: #fff;
        border-radius: 10px;
        box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.5);
        max-width: 600px;
      }

      .form-container h2 {
        text-align: center;
        color: #f0ad4e !important; /* Color amarillo */
      }

      .form-container label {
        font-weight: bold;
        font-size: 1.5rem;
      }

      .form-control {
        background-color: #333; /* Fondo gris oscuro */
        color: #fff; /* Texto blanco */
        border: 1px solid #444; /* Borde gris */
        border-radius: 5px;
        font-size: 1.5rem;
      }

      .form-control::placeholder {
        color: #aaa; /* Placeholder gris claro */
      }

      .form-control:focus {
        border-color: #007bff !important; /* Borde azul */
        box-shadow: 0 0 5px rgba(0, 123, 255, 0.7) !important; /* Sombra azul */
        outline: none;
      }

      .btn-custom {
        background-color: #007bff !important; /* Azul */
        color: #fff !important; /* Texto blanco */
        border: none !important;
        padding: 10px 20px !important;
        font-size: 1.3rem !important;
        border-radius: 5px !important;
        transition: background-color 0.3s ease, color 0.3s ease;
      }

      .btn-custom:hover {
        background-color: #0056b3 !important; /* Azul más oscuro */
        color: #fff !important; /* Texto blanco */
      }

      .alert-danger {
        background-color: #ff5c5c; /* Fondo rojo */
        color: #fff; /* Texto blanco */
        border-radius: 5px;
      }

      .modal-content {
        background-color: #333; /* Fondo gris oscuro */
        color: #fff;
        border-radius: 10px;
      }

      .modal-header h2 {
        color: #007bff !important; /* Color amarillo */
      }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header text-center">
                        <h3>Iniciar sesión</h3>
                    </div>
                    <div class="card-body">
                        <form action="${mvc.uri('log-in')}" method="POST" enctype="application/x-www-form-urlencoded">
                            <input type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}" />

                            <div class="mb-3">
                                <label for="username" class="form-label">Usuari</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                                    <input type="text" name="username" id="username" value="${username}" class="form-control" placeholder="Enter your username" />
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Contraseña</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                    <input type="password" name="password" id="password" value="${password}" class="form-control" placeholder="Enter your password" />
                                </div>
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="rememberMe">
                                <label class="form-check-label" for="rememberMe">Recuérdame</label>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">Iniciar sesión</button>
                        </form>
                    </div>
                    <div class="card-footer text-center">
                        <p class="mb-0">¿No tienes cuenta? <a href="/Homework2/Web/SignUp">Regístrate aquí</a></p>
                    </div>
                    <c:if test="${not empty message}">
                        <div class="alert alert-danger mt-3">
                          ${message}        
                        </div>
                    </c:if>
                    <c:if test="${attempts.hasExceededMaxAttempts()}">
                        <div id="too-many-signup-attempts" class="modal top fade" role="alert" tabindex="-1" data-mdb-backdrop="static" data-mdb-keyboard="true">
                          <div class="modal-dialog">
                              <div class="modal-content">
                                      <div class="modal-header">
                                          <h2 class="modal-title" id="too-many-signup-attempts">Please try again later.</h2>
                                      </div>
                                      <div class="modal-body">
                                            <div class="alert alert-danger" role="alert">
                                                <img class="mb-4" src="<c:url value='/resources/img/Invalid.png' />" alt="" width="134" height="92" />
                                                The maximum number of sign up attempts has been exceeded!
                                            </div>
                                      </div>
                              </div>
                          </div>
                        </div>
                    <script>
                        $("#too-many-signup-attempts").modal('show');
                    </script>
                    </c:if>
                  <jsp:include page="/WEB-INF/views/layout/alert.jsp" />
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz4fnFO9gybB3vs3T8FvyMvK7r+uYwa1e4BAdUuT4ksz+aMxun3sZp3+XIM" crossorigin="anonymous"></script>
    <script src="https://getbootstrap.esdocu.com/docs/5.3/dist/js/bootstrap.min.js" integrity="sha384-cuTj8EHRvh5iFZ5VtRbU5qppxHZ9A9geWh5KY2u3ht57sFz6AC5hOYkpMl2hd72E" crossorigin="anonymous"></script>
</body>
</html>
