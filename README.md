<h1> At the moment, taking a break from working on this website to learn more HTML, CSS, and JavaScript. I will still try to come back every now and then to work on this though. </h1>

The website will include a backend for a website to display different dogs and such.
Front-end will be made with Thymeleaf and HTML/CSS at the moment (and with JavaScript in the future).

Finally managed to get two databases (MySQL) running in the application, and containerizing them with Docker.

To run this program, Docker, MySQL, and SQLTools will need to be installed (when using VSCode)

<h2>Feautures of the website</h2>

<ol>
  <li>
    Connection to a MySQL db which is inside of a Docker container
  </li>
  <li>
    HTTPS is used for connectiom to the website; certificate from mkcert
  </li>
  <li>
    OAuth2.0 implemented to allow users to login with their Google account
  </li>
</ol>

<h2>Current status</h2>

I've finally managed to implement OAuth2 properly, with a logout feature as well.</br>
I think next I will focus more on the frontend.

# Extra notes
1. I've restarted the whole process of adding OAuth2, since I kind of forgot what I did previously, and it wasn't working properly.
