package marc;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import marc.httpHandler.HelloHandler;
import marc.httpHandler.UserPathHandler;
import marc.httpHandler.UserQueryHandler;

public class Application {

	public static void main(String[] args) {
		int serverPort = 8000;
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

			HttpContext context;
			
			context = server.createContext("/api/hello", new HelloHandler());
			context = server.createContext("/api/user", new UserQueryHandler());
			context = server.createContext("/api/users", new UserPathHandler());

			server.setExecutor(null); // creates a default executor
			server.start();
			System.out.println("Started HTTP Server");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
