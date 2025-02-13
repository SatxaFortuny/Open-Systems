<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    background-color: #333 !important; /* Fondo gris oscuro */
    font-family: Arial, sans-serif;
    color: #fff; /* Texto blanco para contraste */
    margin: 0;
    padding: 0;
  }

  .form-container {
    margin: 50px auto;
    padding: 20px;
    background-color: #000; /* Fondo negro */
    border-radius: 10px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.5);
    max-width: 900px;
    color: #fff; /* Texto blanco dentro del contenedor */
  }

  .form-container h2 {
    text-align: center;
    color: #f0ad4e;
    font-size: 2rem;
  }

  .form-container label {
    font-weight: bold;
    font-size: 1.3rem;
    color: #f0ad4e;
  }

  .form-control {
    background-color: #444; /* Fondo gris oscuro */
    color: #fff;
    border: 1px solid #555;
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
  
  #changePhotoBtn {
    display: inline-block;
    margin-top: 10px;
    font-size: 1.2rem;
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
      <form action="${mvc.uri('modify')}" method="POST">
        <input type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}" />
        <h2>User Profile</h2>

        <!-- User Profile Information -->
        <div class="row">
          <div class="col-md-4">
            <div class="text-center">
              <!-- Mostrar la imagen de perfil -->
              <img src="${pageContext.request.contextPath}/resources/img/${customer.featuredImageUrl}" 
                   alt="Profile Picture" 
                   class="rounded-circle img-fluid" 
                   style="width: 300px;">

              <!-- Botón para cambiar foto -->
              <button type="button" class="btn btn-custom mt-3" id="changePhotoBtn">
                Change Photo
              </button>

              <!-- Input de archivo oculto -->
              <input type="file" 
                     id="photoInput" 
                     name="profilePicture" 
                     class="form-control mt-2 d-none" 
                     accept="image/*" 
                     onchange="handleFileChange(this)" />
            </div>
          </div>

          <div class="col-md-8">
            <div class="mb-3">
              <label for="firstName">First Name</label>
              <input type="text" name="firstName" id="firstName" class="form-control" value="${firstName}" placeholder="${customer.firstName}"/>
            </div>

            <div class="mb-3">
              <label for="lastName">Last Name</label>
              <input type="text" name="lastName" id="lastName" class="form-control" value="${lastName}" placeholder="${customer.lastName}"/>
            </div>

            <div class="mb-3">
              <label for="email">Email</label>
              <input type="email" name="email" id="email" class="form-control" value="${email}" placeholder="${customer.email}"/>
            </div>

            <div class="mb-3">
              <label for="username">Username</label>
              <input type="text" name="username" id="username" class="form-control" value="${username}" placeholder="${customer.username}"/>
            </div>

            <div class="mb-3">
              <label for="password">Password</label>
              <input type="password" name="password" id="password" class="form-control" value="${password}" placeholder="${customer.password}"/>
            </div>
            
            <!-- Botón para guardar cambios -->
            <div class="form-group text-center mt-4">
              <button type="submit" class="btn btn-custom">Save Changes</button>
            </div>
          </div>
        </div>
      </form>
      <c:if test="${not empty message}">
        <div class="alert alert-danger mt-3">
          ${message}        
        </div>
      </c:if>
    </div>
  </c:if>

  <jsp:include page="/WEB-INF/views/layout/footer.jsp" />

  <script>
    // Mostrar el input para subir foto al hacer clic en el botón
    document.getElementById('changePhotoBtn').addEventListener('click', function() {
      document.getElementById('photoInput').classList.remove('d-none');
    });

    // Función para manejar cambios en el input de archivo
    function handleFileChange(input) {
      if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
          // Mostrar la imagen seleccionada en el perfil (opcional)
          const imgElement = document.querySelector('.text-center img');
          imgElement.src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
      }
    }
  </script>
</body>
</html>
