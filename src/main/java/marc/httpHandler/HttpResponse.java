package marc.httpHandler;

public class HttpResponse {

	private int statusCode;
	private Object body;

	public HttpResponse(int statusCode, Object body) {
		super();
		this.statusCode = statusCode;
		this.body = body;
	}

	public HttpResponse(int statusCode) {
		this(statusCode, null);
	}

	public HttpResponse(Object body) {
		this(200, body);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

}
