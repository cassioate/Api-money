package com.tessaro.moneyapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.tessaro.moneyapi.annotations.validator.UserValidator;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UserValidator.class})
public @interface UserValido {
	
	String message() default "O email já foi utilizado para cadastro, favor inserir outro endereço de email.";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};

}
