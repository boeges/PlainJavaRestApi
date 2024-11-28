package marc.httpHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.net.httpserver.HttpExchange;

import marc.util.HttpUtil;

public class HelloHandler extends BaseRestHttpHandler {

	@Override
	protected HttpResponse get(HttpExchange exchange) throws IOException {
		System.out.println("query="+exchange.getRequestURI().getRawQuery());
		
		Map<String, String> params = HttpUtil.splitQuery(exchange.getRequestURI().getRawQuery());
		
		String noNameText = "Anonymous";
		String name = params.getOrDefault("name", noNameText);
		String respText = String.format("Hello %s!", name);

		for (Entry<String, String> entry : params.entrySet()) {
			System.out.println(String.format("  %s=%s", entry.getKey(), entry.getValue()));
		}
		
		return new HttpResponse(200, respText);
	}
	
	@Override
	protected HttpResponse delete(HttpExchange exchange) throws IOException {
		return new HttpResponse(200, "Deleted!");
	}

}
