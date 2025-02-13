<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Async Await Article</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .article-header img {
      width: 100%;
      height: auto;
      border-radius: 8px;
    }
    .article-author img {
      width: 10%;
      height: auto;
      border-radius:2px;border-radius:30px;
    }
    .article-title {
      font-weight: bold;
      font-size: 36px;
      margin: 1rem 0;
    }
    .article-details {
      color: #6c757d;
      font-size: 20px;
      margin-bottom: 1rem;
    }
    .article-views-date {
      color: #6c757d;
      font-size: 20px;
      margin-bottom: 1rem;
    }
    .article-content {
      font-size: 16px;
      line-height: 1.6;
    }
    @media (max-width: 575px) {
        .article-title {
            font-size: 20px;
        }
        .article-details {
            font-size: 15px;
        }
        .article-views-date {
            font-size: 15px;
        }
        .article-content {
            font-size: 15px;
        }
    }
  </style>
</head>
<body style="background-color: #fde4ff">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    <div class="container mt-5">
        <div class="article-header" style="display:flex;justify-content:center;align-items: center">
            <img src="http://localhost:8080/Homework2/resources/img/${article.featuredImageUrl}" alt="Article header" style="width:50%">
        </div>
        <div class="article-title" style="text-align: center;">${article.title}</div>
        <div class="article-details d-flex justify-content-between">
            <div class="article-author">
                <img src="http://localhost:8080/Homework2/resources/img/${article.authorPhoto}" alt="article author">
                <span>${article.authorName}</span>
            </div>
            <div class="text-end">
                <span>
                    <c:forEach var="onetopic" items="${article.topic}">
                        ${onetopic}
                    </c:forEach>
                </span>
            </div>
        </div>
        <div class="article-views-date" >${article.views} views | published on ${article.publishDate}</div>
        <div class="article-content">
            <p>${article.content}</p>
        </div>
    </div>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</body>
</html>
