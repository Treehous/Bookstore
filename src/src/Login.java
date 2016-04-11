package src;

public class Login {
	private String user, pass;
	public boolean check;
	
	public Login() {
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getPass() {
		return pass;
	}
	
	public boolean verify(Boolean check){
		return check;
	}

}