package marc.util;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public abstract class HttpUtil {
	
	public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
		Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		if (query==null) {
			return query_pairs;
		}
		String[] pairs = query.split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
			if (idx <= 0) {
				continue;
			}
			query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
					URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
		}
		return query_pairs;
	}
	
	// result: Without starting and trailing "/"
	public static String extractRestPath(HttpExchange exchange) {
		String restPath;
		if (exchange.getRequestURI().getPath().length() > exchange.getHttpContext().getPath().length()) {
			int restPathStartOffset = exchange.getHttpContext().getPath().charAt(exchange.getHttpContext().getPath().length()-1) == '/' ? 0 : 1;
			int restPathEndOffset = exchange.getRequestURI().getPath().charAt(exchange.getRequestURI().getPath().length()-1) == '/' ? 1 : 0;
			int restPathStart = exchange.getHttpContext().getPath().length() + restPathStartOffset;
			int restPathEnd = exchange.getRequestURI().getPath().length() - restPathEndOffset;
			restPath = exchange.getRequestURI().getPath().substring(restPathStart, restPathEnd);
		} else {
			restPath = "";
		}
		return restPath;
	}


}
