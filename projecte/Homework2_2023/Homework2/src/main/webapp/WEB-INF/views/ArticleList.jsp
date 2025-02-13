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
<body style="background-color: #fde4ff">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    <div class="container" style="padding-top: 10px;">
        <form action="${mvc.uri('see-articles')}" method="GET">
            <input type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}" />
            <div class="row  gy-4">
                <div class="col-lg-4">
                    <div class="form-group">
                        <label for="topic1">Select Topic 1</label>
                        <select name="topic" id="topic1" class="form-control">
                            <option value="">-- Select Topic 1 --</option>
                            <c:forEach var="topic" items="${topicList}">
                                <option value="${topic}">${topic}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="form-group">
                        <label for="topic2">Select Topic 2</label>
                        <select name="topic" id="topic2" class="form-control">
                            <option value="">-- Select Topic 2 --</option>
                            <c:forEach var="topic" items="${topicList}">
                                <option value="${topic}">${topic}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="form-group">
                        <label for="authorName">Author</label>
                        <input type="text" name="authorName" id="authorName" class="form-control" placeholder="Author Name">
                    </div>
                </div>
            </div>
            <div class="row  gy-4">
                <div class="col-lg-6  mx-auto">
                    <button type="submit" class="btn w-100" style="background-color: #ff00e0; color: black; border: none;">Filter</button>
                </div>
            </div>
        </form>
        <!-- Inicializamos el contador -->
        <c:set var="counter" value="0" />
        
        <!-- Iterar sobre los artículos -->
        <c:forEach var="article" items="${articleList}">
            <!-- Abrimos una nueva fila cada 4 artículos -->
            <c:if test="${counter % 4 == 0}">
                <div class="row gy-4" style="padding-top: 10px;">
            </c:if>

            <div class="col-lg-3">
                <a href="<c:url value="/Web/Article/${article.id}" />" class="text-decoration-none">
                    <div class="card">
                        <c:if test="${article.isPublic == false}">
                        <div class="card-header">Private</div>
                        </c:if>
                        <img class="card-img-top" src="http://localhost:8080/Homework2/resources/img/${article.featuredImageUrl}" alt="article photo">
                        <div class="card-body">
                            <h5 class="card-title">${article.title}</h5>
                            <img src="http://localhost:8080/Homework2/resources/img/${article.authorPhoto}" height="20" style="border-radius:30px;" alt="article author">
                            <font size="2">${article.author}</font>
                            <p class="card-text mt-1">${article.resume}</p>
                        </div>
                    </div>
                </a>
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
        <c:if test="${empty articleList}">
        <div class="alert alert-info">No articles found.</div>
        </c:if>
    </div>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
