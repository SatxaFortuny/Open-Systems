<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <title>Sign Up</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" />

  <!-- jQuery y Bootstrap JS -->
  <script src="<c:url value='/resources/js/jquery-1.11.1.min.js' />"></script>
  <script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>

  <!-- Viewport meta tag para responsividad -->
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

  <!-- FontAwesome para íconos -->
  <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
      rel="stylesheet"
      integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN"
      crossorigin="anonymous" />
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
      color: #f0ad4e; /* Color amarillo */
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
      border-color: #f0ad4e; /* Borde amarillo */
      box-shadow: 0 0 5px rgba(240, 173, 78, 0.7); /* Sombra amarilla */
      outline: none;
    }

    .btn-custom {
      background-color: #f0ad4e !important; /* Amarillo */
      color: #000 !important; /* Texto negro */
      border: none !important;
      padding: 10px 20px !important;
      font-size: 1.3rem !important;
      border-radius: 5px !important;
      transition: background-color 0.3s ease, color 0.3s ease;
    }

    .btn-custom:hover {
      background-color: #e09e3e !important; /* Amarillo más oscuro */
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
      color: #f0ad4e; /* Color amarillo */
    }
  </style>
</head>
<body>
  <jsp:include page="/WEB-INF/views/layout/header.jsp" />

  <div class="form-container">
    <h2>Registration</h2>
    <form action="${mvc.uri('sign-up')}" method="POST">
      <input type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}" />
      
      <div class="mb-3">
        <label for="firstname">First Name</label>
        <input type="text" name="firstName" id="firstname" value="${firstName}" class="form-control" placeholder="Enter your first name" />
      </div>
      
      <div class="mb-3">
        <label for="lastname">Last Name</label>
        <input type="text" name="lastName" id="lastname" value="${lastName}" class="form-control" placeholder="Enter your last name" />
      </div>
      
      <div class="mb-3">
        <label for="email">Email</label>
        <input type="text" name="email" id="email" value="${email}" class="form-control" placeholder="Enter your email" />
      </div>
      
      <div class="mb-3">
        <label for="username">Username</label>
        <input type="text" name="username" id="username" value="${username}" class="form-control" placeholder="Enter your username" />
      </div>
      
      <div class="mb-3">
        <label for="password">Password</label>
        <input type="password" name="password" id="password" value="${password}" class="form-control" placeholder="Enter your password" />
      </div>
      
      <div class="d-grid gap-2">
        <button type="submit" class="btn btn-custom">Submit</button>
      </div>
    </form>
    
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

        <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
