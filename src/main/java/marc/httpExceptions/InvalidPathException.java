package marc.httpExceptions;

public class InvalidPathException extends HttpException {
	private static final long serialVersionUID = 1L;
	private static final int STATUS_CODE = 404;

	public InvalidPathException() {
		super(STATUS_CODE);
	}

	public InvalidPathException(String message) {
		super(STATUS_CODE, message);
	}
	
	
}
