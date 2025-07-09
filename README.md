<h1> At the moment, taking a break from working on this website to learn more HTML, CSS, and JavaScript. </h1>
<h1>Trying to make a basic login page and registration form</h1>

The website will include a backend for a website to display different dogs and such.
Front-end will be made with Thymeleaf and HTML/CSS at the moment (and with JavaScript in the future).

Finally managed to get two databases (MySQL) running in the application, and containerizing them with Docker.

To run this program, Docker, MySQL, and SQLTools will need to be installed (when using VSCode)

# Use of classes, validators, methods, etc. 
This is mainly just for me so I can keep better track of what classes, validators, methods, etc. that I use throughout my project and adding descriptions to them to understand them.

| Class, etc. | Description | Use Case |
| --- | --- | --- |
| @Target | @Target means the ValidEmail annotation can annotate the specified types | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/2nd-branch/src/main/java/com/justice/dogs/login/ValidEmail.java#L14-L15) |
| @Target(ElementType.TYPE) | TYPE means the annotation can be used for classes, interfaces, and enums | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/main/src/main/java/com/justice/dogs/login/MatchingPassword.java#L15-L16) |
| @Retention(RetentionPolicy.RUNTIME) | @Retention determines when the annotation (ValidEmail) gets discarded. <br>RUNTIME means it'll be discarded when the program is running, after compile time | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/main/src/main/java/com/justice/dogs/login/ValidEmail.java#L17-L18) |
| @Constraint(validatedBy = EmailValidator.class) | @Constraint is used to add a custom validation constraint. <br>Which I think means it uses the class specified by validatedBy to check for any error checking if it is included in that class?? | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/main/src/main/java/com/justice/dogs/login/ValidEmail.java#L20-L21) |
| @Documented | @Documented just defines an annotation and ensures that the custom annotation shows up in the JavaDoc | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/main/src/main/java/com/justice/dogs/login/ValidEmail.java#L22-L23) |
| Various Thymeleaf HTML formats | ${} indicates variable names. <br>*{} selects the child of the chosen object and injects it into the template file (HTML). <br>#{} brings externalized text (such as those in a .properties file) into the template file. | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/main/src/main/resources/templates/registration.html) |
| @Component | Allows Spring to automatically detect and register custom beans | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/basic-login-branch/src/main/java/com/justice/dogs/services/JwtAuthFilter.java#L5-L6) | 
| @Data | Used by lombok, automatically adds getters and setters to a class | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/basic-login-branch/src/main/java/com/justice/dogs/user/UserEntity.java#L14-L15) |
| @AllArgsConstructor and @NoArgsConstructor | Used by lombok, automatically creates a constructor for a classes | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/basic-login-branch/src/main/java/com/justice/dogs/user/UserEntity.java#L15-L16) | 


# Issues

1. One major issues I've run into so far in this branch was getting two databases connected to this application. This could've been happening because I'm using Docker Compose, but i don't really know.<br>
I kept getting the error:  
**java.lang.IllegalStateException: Cannot get a connection as the driver manager is not properly initialized.**<br>
and also the error:  
**Failed to initialize JPA EntityManagerFactory: Unable to create requested service [org.hibernate.engine.jdbc.env.spi.JdbcEnvironment] due to: Error calling DriverManager.getConnection() [Access denied for user 'justice'@'%' (using password: NO)]**<br>
So in order to fix these issues, I had to go into the Docker containers for each of the databases, and did the following:  
    1. Go to Exec
    2. type "mysql -u root -p" (no double quotes)
    3. (If user is not created) type "CREATE USER 'justice'@'%' IDENTIFIED BY 'password';" (no double quotes)
    4. type "GRANT ALL PRIVILEGES ON \*.\* TO 'justice'@'%' WITH GRANT OPTION;" (no double quotes)<br>
This took way longer than expected to figure out but I fixed my issue.  

2. Another issue I encountered was a cycle occured, with the error:  
Description:  
**The dependencies of some of the beans in the application context form a cycle:**  
**|  jwtAuthFilter defined in file [C:\Users\Frost\Documents\Coding_Things\Dogs-Spring-Boot\target\classes\com\justice\dogs\services\JwtAuthFilter.class]**
**|  userInfoService (field private org.springframework.security.crypto.password.PasswordEncoder com.justice.dogs.services.UserInfoService.encoder)**
**|  securityConfig defined in file [C:\Users\Frost\Documents\Coding_Things\Dogs-Spring-Boot\target\classes\com\justice\dogs\config\SecurityConfig.class]**  
To fix this issue, I had to define PasswordEncoder in a seperate class (CommonConfig) and that fixed the issue.

<h2>Current issue</h2>

This time I'm restarting the whole process for login and registration, since the current tutorial I was following didn't work. 
I will try to read the docs and use those instead. Likely going to try implement OAuth2.0

# Extra notes
1. The reason CSRF (Cross-site request forgery) protection is disabled is because JWT tokens are stateless. JWT tokens are sent in the authorization header so they are not automatically included in request made by the browser (like cookies).<br>
    a. Stateless sessions are sessions that do no store any information/data (cookies) in the server about a user's previous interactions.<br/>
    b. (Personal Note) OAUTH and JWT are used for authentication and authorization, meaning they are meant to be used after a user has created and registered an account.

3. Now OAuth2.0 has been implemented into the program. Next, I want to figure out how to make a registration and login page instead of using Postman to register new users. Most likely what will be implemented is an email system for registration and/or allowing logging in through other accounts/websites such as Google and Github.

4. When storing a secret key for JWT authentication, the best way to store it would be using a vault like AWS Secret Manager. However, at the moment I will store it within an .env file, since it is more secure than storing it within a class in the application.
