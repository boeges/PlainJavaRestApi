package marc.httpExceptions;

public class HttpException extends Exception {
	private static final long serialVersionUID = 1L;

	private int statusCode;

	public HttpException(int statusCode, String message, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public HttpException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}

	public HttpException(int statusCode, Throwable cause) {
		super(cause);
		this.statusCode = statusCode;
	}

	public HttpException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}
	
	public HttpException() {
		this(500);
	}

	public int getStatusCode() {
		return statusCode;
	}

}
