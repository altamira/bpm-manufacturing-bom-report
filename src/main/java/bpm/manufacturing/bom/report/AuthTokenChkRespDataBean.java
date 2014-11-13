package bpm.manufacturing.bom.report;

public class AuthTokenChkRespDataBean {
	private String access_token;
	private String user_name;
	private String last_name;
	private String loggedin_since;
	private String first_name;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getLoggedin_since() {
		return loggedin_since;
	}
	public void setLoggedin_since(String loggedin_since) {
		this.loggedin_since = loggedin_since;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
}
