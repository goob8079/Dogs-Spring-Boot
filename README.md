Trying to make a backend for a website to display different dogs and such
Front-end will be made with Thymeleaf and HTML at the moment

To run this program, Docker, MySQL, and SQLTools will need to be installed (when using VSCode)

# Use of classes, validators, methods, etc. 
This is mainly just for me so I can keep better track of what classes, validators, methods, etc. that I use throughout my project and adding descriptions to them to understand them.

| Class, etc. | Description | Use Case |
| --- | --- | --- |
| @Target | @Target means the ValidEmail annotation can annotate the specified types | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/2nd-branch/src/main/java/com/justice/dogs/login/ValidEmail.java#L14-L15) |
| @Retention(RetentionPolicy.RUNTIME) | @Retention determines when the annotation (ValidEmail) gets discarded. 
RUNTIME means it'll be discarded when the program is running, after compile time | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/main/src/main/java/com/justice/dogs/login/ValidEmail.java#L17-L18) |

|@Constraint(validatedBy = EmailValidator.class) | @Constraint is used to add a custom validation constraint. 
Which I think means it uses the class specified by validatedBy to check for any error checking if it is included in that class?? | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/main/src/main/java/com/justice/dogs/login/ValidEmail.java#L20-L21) |

| @Documented | @Documented just defines an annotation and ensures that the custom annotation shows up in the JavaDoc | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/main/src/main/java/com/justice/dogs/login/ValidEmail.java#L22-L23) |
| Various Thymeleaf HTML formats | ${} indicates variable names.
*{} selects the child of the chosen object and injects it into the template file (HTML).
#{} brings externalized text (such as those in a .properties file) into the template file. | [Here](https://github.com/goob8079/Dogs-Spring-Boot/blob/main/src/main/resources/templates/registration.html) |

