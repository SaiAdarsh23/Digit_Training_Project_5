<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Fail</title>
</head>
<body>
<head>
	<meta charset="ISO-8859-1">
	<title>Success</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f0f0f0;
			margin: 0;
			padding: 0;
			display: flex;
			justify-content: center;
			align-items: center;
			min-height: 100vh;
			background-image: url("/BankApplication/images/BankApp1.png");
			/* Replace with your background image URL */
			background-size: cover;
			background-position: center;
		}
		body::before {
			content: "";
			background-image: inherit;
			background-size: cover;
			background-repeat: no-repeat;
			filter: blur(2px);
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			z-index: -1;
		}
		.container {
			max-width: 400px;
			padding: 30px;
			background-color: rgba(255, 255, 255, 0.9);
			border-radius: 10px;
			box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
			text-align: center;
		}

		h4 {
			color: #3366cc;
			margin-bottom: 20px;
		}

		a {
			display: inline-block;
			padding: 10px 20px;
			background-color: #3366cc;
			color: #fff;
			text-decoration: none;
			border-radius: 5px;
			font-weight: bold;
			transition: background-color 0.3s ease;
		}

		a:hover {
			background-color: #005580;
		}
	</style>
</head>

<body>
	<div class="container">
		<h1>Unable to fetch Details. Try again later</h1>
		<br>
		<a href="Home.jsp">Redirect to Home Page</a>
	</div>

</body>
</html>
