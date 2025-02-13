<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Article List</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <script src="<c:url value='/resources/js/jquery-1.11.1.min.js' />"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    <div class="container">          

        <!-- Inicializamos el contador -->
        <c:set var="counter" value="0" />
        
        <!-- Iterar sobre los artículos -->
        <c:forEach var="article" items="${customer.articles}">
            <!-- Abrimos una nueva fila cada 4 artículos -->
            <c:if test="${counter % 4 == 0}">
                <div class="row gy-4">
            </c:if>

            <div class="col-lg-3">
                <a href="http://localhost:8080/Homework2/Web/Article/${article.id}" class="text-decoration-none">
                    <div class="card">
                        <c:if test="${article.isPublic == false}">
                        <div class="card-header">Private</div>
                        </c:if>
                        <img src="http://localhost:8080/Homework2/resources/img/${article.featuredImageUrl}" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title">${article.title}</h5>
                            <p class="card-text mt-1">${article.resume}</p>
                        </div>
                    </div>
                </a>
                <!-- Botó Delete -->
                <form id="deleteForm${article.id}" action="http://localhost:8080/Homework2/Web/Article/Delete/${article.id}" method="POST">
                    <input type="hidden" name="_method" value="DELETE">
                    <button type="submit" class="btn btn-danger btn-sm mt-2">Delete</button>
                </form>

            </div>

            <!-- Cerramos la fila después de cada 4 columnas -->
            <c:set var="counter" value="${counter + 1}" />
            <c:if test="${counter % 4 == 0}">
                </div>
            </c:if>
        </c:forEach>

        <!-- Cerramos la última fila si no tiene 4 artículos -->
        <c:if test="${counter % 4 != 0}">
            </div>
        </c:if>
    </div>
</body>
</html>
