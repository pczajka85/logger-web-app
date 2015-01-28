package pl.cyfrowypolsat.validators;

import java.util.regex.PatternSyntaxException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "pl.cyfrowypolsat.validators.PatternSyntaxValidator")
public class PatternSyntaxValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		try{
			"Some String".matches(arg2.toString());
		}catch(PatternSyntaxException e){
			FacesMessage msg = new FacesMessage("Regular expression is invalid.", "Invalid regexp format");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
	}

}
