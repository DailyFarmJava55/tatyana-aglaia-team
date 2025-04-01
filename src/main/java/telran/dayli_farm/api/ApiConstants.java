package telran.dayli_farm.api;

public interface ApiConstants {

	//FARMER
	String FARMER_REGISTER = "/farmer/register";
	String FARMER_LOGIN = "/farmer/login";
	String FARMER_LOGOUT = "/farmer/logout";
	String FARMER_CURRENT = "/farmer/profile";
	
	String FARMER_EDIT = "/farmer/edit";
	String FARMER_CHANGE_PASSWORD = "/farmer/password";
	String FARMER_CHANGE_EMAIL = "/farmer/email";
	String FARMER_CHANGE_COMPANY_NAME = "/farmer/company";
	String FARMER_CHANGE_ADDRESS = "/farmer/address";
	String FARMER_CHANGE_COORDINATES = "/farmer/coordinates";
	String FARMER_CHANGE_PHONE = "/farmer/phone";
	
	String FARMER_REMOVE = "/farmer/remove";
	String FARMER_REFRESH_TOKEN = "/farmer/refresh";
	String RESET_PASSWORD = "/farmer/password-reset";
	
	
	//CUSTOMER
	
	String CUSTOMER_REGISTER = "/customer/register";
	String CUSTOMER_LOGIN = "/customer/login";
	String CUSTOMER_LOGOUT = "/customer/logout";
	String CUSTOMER_CURRENT = "/customer/profile";
	
	String CUSTOMER_EDIT = "/customer/edit";
	String CUSTOMER_CHANGE_PASSWORD = "/customer/password";
	String CUSTOMER_CHANGE_EMAIL = "/customer/email";
	String CUSTOMER_CHANGE_FIRST_LAST_NAME = "/customer/name";
	String CUSTOMER_CHANGE_PHONE = "/customer/phone";
	
	String CUSTOMER_REMOVE = "/customer/remove";
	String CUSTOMER_REFRESH_TOKEN = "/customer/refresh";
	String CUSTOMER_RESET_PASSWORD = "/customer/password-reset";
	
	//SURPRISE_BAG
	
	String ADD_SURPRISE_BAG = "/surprise_bag";
	String GET_ALL_SURPRISE_BAGS = "/surprise_bag/all"; 
	String GET_ALL_SURPRISE_BAGS_FOR_FARMER = "/surprise_bag/farmer";
	
    String UPDATE_SURPRISE_BAG = "/surprise_bag"; 
    String GET_ALL_SURPRISE_BAGS_BY_SIZE = "/surprise_bag/size";
    String GET_ALL_SURPRISE_BAGS_BY_CATEGORY = "/surprise_bag/category";
    String DELETE_SURPRISE_BAG = "/surprise_bag"; 
    
    String GET_SURPRISE_BAG_BY_ID = "/surprise_bag/{id}";
    String SURPRISE_BAG_INC = "/surprise_bag/{id}/increment";
    String SURPRISE_BAG_DEC = "/surprise_bag/{id}/decrement";
    
    

    String GET_CATEGORIES = "/categories";
    String GET_SIZES = "/sizes";
	
	//ORDER
	String CREATE_ORDER = "/customer/order";
	String CANCEL_ORDER = "/customer/order";
	
}
