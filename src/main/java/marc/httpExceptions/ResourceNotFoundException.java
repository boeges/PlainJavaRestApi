package marc.httpExceptions;

public class ResourceNotFoundException extends HttpException {
	private static final long serialVersionUID = 1L;
	private static final int STATUS_CODE = 404;

	public ResourceNotFoundException() {
		super(STATUS_CODE);
	}

	public ResourceNotFoundException(String message) {
		super(STATUS_CODE, message);
	}
	
	
}
