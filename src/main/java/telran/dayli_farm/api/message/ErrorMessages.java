package telran.dayli_farm.api.message;

public interface ErrorMessages {

	String PASSWORD_IS_NOT_VALID = "Password must be at least 8 characters long";
	String PASSWORD_IS_REQUIRED = "Password is required";
	String OLD_NEW_PASSWORD_REQUARED = "Old password and new password - requared field";
			

	String WRONG_USER_NAME_OR_PASSWORD = "Wrong user name or password";
	String USER_NOT_FOUND = "User is not found";
	String INVALID_TOKEN = "Invalid or expired JWT token";

	String FARMER_WITH_THIS_EMAIL_EXISTS = "Farmer with this email exists";
	String FARMER_WITH_THIS_EMAIL_IS_NOT_EXISTS = "Farmer with this email is not exists";

	String CUSTOMER_WITH_THIS_EMAIL_EXISTS = "Customer with this email exists";
	String CUSTOMER_WITH_THIS_EMAIL_IS_NOT_EXISTS = "Customer with this email is not exists";
	

}
