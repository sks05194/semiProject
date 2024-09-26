<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="kr">

<head>
	<meta charset="UTF-8">
	<title>BeansPaM Coffee</title>
	<link rel="stylesheet" type="text/css" href="/BeansPaM/css/index.css">
</head>

<body>
	<section id="hero">
		<div>
			<h1>Welcome to BeansPaM</h1>
			<p>Discover the finest coffee beans from around the world.</p>
			<a href="" class="btn-primary">Shop Now</a>
		</div>
	</section>

	<main>
		<section id="about">
			<h2>About Us</h2>
			<p>At BeansPaM, we are passionate about sourcing the highest quality coffee beans from ethical and
				sustainable farms. Each bean is handpicked and roasted to perfection to give you the best coffee
				experience.</p>
		</section>

		<section id="featured-products">
			<h2>Featured Coffee Beans</h2>
			<div class="product">
				<img src="/BeansPaM/img/colombian.jpg" alt="Colombian Coffee">
				<h3>Colombian Supremo</h3>
				<p>Rich and full-bodied with a smooth finish.</p>
				<a href="" class="btn-secondary">Learn More</a>
			</div>
			<div class="product">
				<img src="/BeansPaM/img/ethiopian.jpg" alt="Ethiopian Coffee">
				<h3>Ethiopian Yirgacheffe</h3>
				<p>Floral notes with a hint of citrus, perfect for pour-over.</p>
				<a href="" class="btn-secondary">Learn More</a>
			</div>
			<div class="product">
				<img src="/BeansPaM/img/brazilian.jpg" alt="Brazilian Coffee">
				<h3>Brazilian Santos</h3>
				<p>Nutty flavor with a hint of chocolate, great for espresso.</p>
				<a href="" class="btn-secondary">Learn More</a>
			</div>
		</section>

		<section id="contact">
			<h2>Contact Us</h2>
			<p>If you have any questions or would like to know more about our coffee, feel free to reach out!</p>
			<form action="/BeansPaM/contact" method="post">
				<label for="name">Name:</label>
				<input type="text" id="name" name="name" required>

				<label for="email">Email:</label>
				<input type="email" id="email" name="email" required>

				<label for="message">Message:</label>
				<textarea id="message" name="message" required></textarea>

				<button type="button" class="btn-primary">Send Message</button>
			</form>
		</section>
	</main>
	<script src="/BeansPaM/js/menu.js"></script>
</body>

</html>