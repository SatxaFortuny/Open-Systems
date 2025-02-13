<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-8 mx-auto mt-5 text-center">
                <h1 class="text-danger">An Error Occurred</h1>
                <p>${errorMessage}</p> <!-- Mostrar el mensaje de error -->
                <a href="/Homework2/Web/ModifyProfile" class="btn btn-primary">Try Again</a>
            </div>
        </div>
    </div>
</body>
</html>
