package com.justice.dogs.account;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// a custom email validator.
// @Target means the ValidEmail annotation can annotate the specified types
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE}) 
// @Retention determines when the annotation (ValidEmail) gets discarded. 
// RUNTIME means it'll be discarded when the program is running, after compile time. 
@Retention(RetentionPolicy.RUNTIME)
// @Constraint is used to add a custom validation constraint.
// which I think means it uses the class specified by validatedBy to check for any error checking if it is included in that class??
@Constraint(validatedBy = EmailValidator.class)
// @Documented just defines an annotation and ensures that the custom annotation shows up in the JavaDoc
@Documented
public @interface ValidEmail {
    String message() default "Invalid Email";
    // the two lines below aren't used often but included since it is a standard part of Bean Validation annotations
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}