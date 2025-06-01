package com.sv.app.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sv.app.dto.error.Error;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Path.Node;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public final class ValidationRequest {

	public static <T> List<Error> validationRequest(T object){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		String bindindError;
		Set<ConstraintViolation<T>> violations = validator.validate(object);
		List<Error> errors = new ArrayList<>();
		for (ConstraintViolation<T> violation: violations) {
			String title = "";
			String description = "";
			
			bindindError = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
			String fieldName = getFielName(violation.getPropertyPath());
			if(bindindError.equalsIgnoreCase("NotNull")) {
				title = "Parametro con valor nulo";
				description = String.format("El parametro %s no puede ser un valor nulo ",fieldName) ;
			}
			else if(bindindError.equalsIgnoreCase("NotEmpty")) {
				title = "Parametro con valor vacio";
				description = String.format("El parametro %s no puede ser un valor vacio ",fieldName) ;
			}
			else if(bindindError.equalsIgnoreCase("Pattern")){
				title = "Formato invalido";
				description = String.format(violation.getMessage(),fieldName) ;
			}
			else if(bindindError.equalsIgnoreCase("Email")){
				title = "Formato email no valido";
				description = String.format("El formado email no es valido ",fieldName) ;
			}
			else {
				title = "error generico";
				description = violation.getMessage();
			}
			
			errors.add(new Error(title, description));
			
		}
		return errors;
	}
	
	private static String getFielName(Path path) {
		List<Node> node = new ArrayList<>();
		path.forEach(node::add);
		
		if(!node.isEmpty()) {
			return node.get(node.size()-1).getName();
		}
		
		return "Unknown field";
	}
}
