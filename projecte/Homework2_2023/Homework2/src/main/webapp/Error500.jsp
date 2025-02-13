<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>500 Error Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" />
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <style>
        .card {
            position: relative;
            display: flex;
            flex-direction: column;
            min-width: 0;
            word-wrap: break-word;
            background-color: #f8d7da;
            background-clip: border-box;
            border: 1px solid rgba(0, 0, 0, 0.125);
            border-radius: .25rem;
        }

        .card .card-header {
            background-color: #fff;
            border-bottom: none;
        }

        .btn-info {
            background-color: #007bff;
            border-color: #007bff;
        }

        .btn-info:hover {
            background-color: #0056b3;
            border-color: #004085;
        }
    </style>
</head>

<body>
    <div class="container">
        <div class="row text-center">
            <div class="col-md-12 col-sm-12">
                <div class="card shadow-lg border-0 rounded-lg mt-5 mx-auto" style="width: 30rem;">
                    <h3 class="card-header display-1 text-muted text-center">
                        500
                    </h3>
                    <h1>Internal Server Error</h1>
                    <p>There was a problem on the server. Please try again later.</p>
                    <p>Error Message: ${requestScope['javax.servlet.error.message']}</p>
                    <p>Specific Error: ${requestScope.errorMessage}</p>
                    <span class="card-subtitle mb-2 text-muted text-center">
                        The server encountered an unexpected condition.
                    </span>
                    <div class="card-body mx-auto">
                        <a href="<c:url value='/Web/SignUp' />" class="btn btn-sm btn-info text-white">Back To Home</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
