package telran.dayli_farm.security.login;

public class LoginRoleContext {
	  private static final ThreadLocal<String> roleHolder = new ThreadLocal<>();

	    public static void setRole(String role) {
	        roleHolder.set(role);
	    }

	    public static String getRole() {
	        return roleHolder.get();
	    }

	    public static void clear() {
	        roleHolder.remove();
	    }
}
