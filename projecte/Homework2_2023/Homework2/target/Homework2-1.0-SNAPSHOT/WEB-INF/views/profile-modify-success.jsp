<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logout</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #000; /* Fondo negro */
            color: #fff; /* Texto blanco */
            font-family: Arial, sans-serif;
            padding: 0;
            margin: 0;
        }

        .container {
            margin-top: 100px;
            padding: 40px;
            background-color: #222; /* Fondo oscuro */
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            width: 400px;
            text-align: center;
        }

        h1 {
            color: #f0ad4e; /* Color amarillo */
            font-size: 1.8rem;
        }

        .message {
            background-color: #f0ad4e; /* Color de fondo para mensaje */
            color: #000; /* Color del texto */
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            font-size: 1.2rem;
            border: none;
        }

        button {
            background-color: #f0ad4e;
            color: #000;
            border: none;
            padding: 12px 24px;
            font-size: 1.3rem;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
        }

        button:hover {
            background-color: #e09e3e;
            color: #fff;
        }
    </style>
</head>
<body>

    <div class="container">
        <!-- Mensaje de éxito -->
        <div class="message">
            Customer updated successfully!
        </div>
        
        <h1>Please, log in again to update the data.</h1>
        <form action="/Homework2/Web/Logout" method="GET">
            <button type="submit">Logout</button>
        </form>
    </div>

    <!-- Bootstrap Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
