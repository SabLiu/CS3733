package definitions;

public class Response<Model> {
	public final Model model;
	public final int statusCode;
	public final String error;
	
	public Response(Model model, int code) {
		this.model = model;
		this.statusCode = code;
		this.error = "";
	}
	
	public Response(int code, String errorMessage) {
		this.model = null;
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		String str  = "Response: " + statusCode + " " + error;
		if(model != null) {
			str += "\n" + model.toString();
		}
		
		return str;
	}
}
