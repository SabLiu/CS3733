package definitions;

public class Response<Model> {
	public Model model;
	public int statusCode;
	public String error;
	
	public Response(Model model, int code) {  
		this.model = model;
		this.statusCode = code;
		this.error = ""; 
	}
	
	public Model getModel() {
		return model;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getError() {
		return error;
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
