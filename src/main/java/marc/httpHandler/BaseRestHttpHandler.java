package marc.httpHandler;

import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import marc.httpExceptions.HttpException;
import marc.util.HttpConstants;

public class BaseRestHttpHandler implements HttpHandler {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			HttpResponse httpResponse = handleExchange(exchange);
			sendResponse(exchange, httpResponse);
		} catch (Exception e) {
			handleException(exchange, e);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			exchange.close();
		}
	}

	private void handleException(HttpExchange exchange, Exception exception) {
		HttpResponse httpResponse;
		if (exception instanceof HttpException) {
			HttpException httpException = (HttpException) exception;
			httpResponse = new HttpResponse(httpException.getStatusCode(), httpException.getMessage());
		} else {
//			httpResponse = new HttpResponse(500, exception.getClass().getSimpleName());
			httpResponse = new HttpResponse(500, exception.toString());
			exception.printStackTrace();
		}
		try {
			sendResponse(exchange, httpResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendResponse(HttpExchange exchange, HttpResponse httpResponse) throws IOException {
		exchange.getResponseHeaders().add(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.CONTENT_TYPE_JSON);
		if (httpResponse.getBody() == null) {
			// Send only status code, no body
			exchange.sendResponseHeaders(httpResponse.getStatusCode(), -1);
		} else {
			byte[] bodyBytes = objectMapper.writeValueAsBytes(httpResponse.getBody());
			exchange.sendResponseHeaders(httpResponse.getStatusCode(), bodyBytes.length);
			OutputStream output = exchange.getResponseBody();
			output.write(bodyBytes);
			output.flush();
		}
	}

	protected HttpResponse handleExchange(HttpExchange exchange) throws IOException, HttpException {
		switch (exchange.getRequestMethod()) {
		case "GET":
			return get(exchange);
		case "POST":
			return post(exchange);
		case "PUT":
			return put(exchange);
		case "DELETE":
			return delete(exchange);
		default:
			return handleOtherMethods(exchange);
		}
	}

	protected HttpResponse get(HttpExchange exchange) throws IOException, HttpException {
		return replyNotImplemented(exchange);
	}

	protected HttpResponse post(HttpExchange exchange) throws IOException, HttpException {
		return replyNotImplemented(exchange);
	}

	protected HttpResponse put(HttpExchange exchange) throws IOException, HttpException {
		return replyNotImplemented(exchange);
	}

	protected HttpResponse delete(HttpExchange exchange) throws IOException, HttpException {
		return replyNotImplemented(exchange);
	}

	protected HttpResponse handleOtherMethods(HttpExchange exchange) throws IOException, HttpException {
		return replyNotImplemented(exchange);
	}

	private HttpResponse replyNotImplemented(HttpExchange exchange) throws IOException {
		return new HttpResponse(405, null);
	}

}
