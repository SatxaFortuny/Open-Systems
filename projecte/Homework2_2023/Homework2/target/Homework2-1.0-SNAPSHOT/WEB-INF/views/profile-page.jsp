<!DOCTYPE html>
<html lang="es">
<head>
  <title>User Profile</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" />

  <!-- jQuery and Bootstrap JS -->
  <script src="<c:url value='/resources/js/jquery-1.11.1.min.js' />"></script>
  <script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>

  <!-- Viewport meta tag for responsiveness -->
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

  <!-- FontAwesome for icons -->
  <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />

<style>
  /* Custom styles */
  body {
    background-color: #333 !important; /* Fondo negro */
    color: #fff; /* Texto blanco para mejor contraste */
    font-family: Arial, sans-serif;
  }

  .form-container {
    margin: 50px auto;
    padding: 20px;
    background-color: #222; /* Fondo oscuro para el contenedor */
    color: #fff; /* Texto blanco dentro del contenedor */
    border-radius: 10px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
    max-width: 900px;
  }

  .form-container h2 {
    text-align: center;
    color: #f0ad4e;
    font-size: 2rem;
  }

  .form-container label {
    font-weight: bold;
    font-size: 1.3rem;
    color: #f0ad4e; /* Color de las etiquetas */
  }

  .form-control {
    background-color: #333; /* Fondo oscuro para los campos de entrada */
    color: #fff;
    border: 1px solid #555; /* Borde más oscuro */
    border-radius: 5px;
    font-size: 1.3rem;
  }

  .form-control:focus {
    border-color: #f0ad4e;
    box-shadow: 0 0 5px rgba(240, 173, 78, 0.7);
  }

  .btn-custom {
    background-color: #f0ad4e !important;
    color: #000 !important;
    border: none !important;
    padding: 10px 20px !important;
    font-size: 1.3rem !important;
    border-radius: 5px !important;
  }

  .btn-custom:hover {
    background-color: #e09e3e !important;
    color: #fff !important;
  }

  .profile-info {
    font-size: 1.5rem;
    font-weight: bold;
  }

  input[disabled] {
    background-color: #444;
    cursor: not-allowed;
  }
</style>
</head>
<body>
  <jsp:include page="/WEB-INF/views/layout/header.jsp" />

  <c:if test="${customer != null}">
    <div class="form-container">
      <h2>User Profile</h2>
      
      <!-- User Profile Information -->
      <div class="row">
        <div class="col-md-4">
          <div class="text-center">
            <!-- Mostrar la imagen de perfil -->
            <img src="${pageContext.request.contextPath}/resources/img/${customer.featuredImageUrl}" alt="Profile Picture" class="rounded-circle img-fluid" style="width: 300px;">
            <h5 class="my-3 profile-info"><c:out value="" /></h5>
            <h5 class="my-3 profile-info"><c:out value="" /></h5>
            <h5 class="my-3 profile-info"><c:out value="" /></h5>
          </div>
        </div>

        <div class="col-md-8">
          <form>
            <div class="mb-3">
              <label for="fullName">Full Name</label>
              <!-- Mostrar el nombre completo -->
              <input type="text" id="fullName" class="form-control" value="${customer.firstName} ${customer.lastName}" readonly />
            </div>

            <div class="mb-3">
              <label for="email">Email</label>
              <!-- Mostrar el email -->
              <input type="email" id="email" class="form-control" value="${customer.email}" readonly />
            </div>
            
            <div class="mb-3">
              <label for="username">Username</label>
              <!-- Mostrar el username -->
              <input type="text" id="username" class="form-control" value="${customer.credentials.username}" readonly />
            </div>
            
            <div class="mb-3 text-center">
            <!-- Botón para modificar datos -->
            <a href="/Homework2/Web/ModifyProfile" class="btn btn-custom">Modificar datos</a>
            </div>
            
          </form>
        </div>
      </div>
    </div>
  </c:if>

  <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
