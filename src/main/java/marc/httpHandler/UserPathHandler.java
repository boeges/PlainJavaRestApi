package marc.httpHandler;

import java.util.Collection;

import com.sun.net.httpserver.HttpExchange;

import marc.entity.Post;
import marc.entity.User;
import marc.httpExceptions.HttpException;
import marc.httpExceptions.InvalidPathException;
import marc.httpExceptions.ResourceNotFoundException;
import marc.repository.UserRepo;
import marc.repository.dbRepo.UserDbRepo;
import marc.util.HttpUtil;

public class UserPathHandler extends BaseRestHttpHandler {
	
	private UserRepo userRepo = UserDbRepo.instance;

	@Override
	protected HttpResponse get(HttpExchange exchange) throws HttpException {

		String restPath = HttpUtil.extractRestPath(exchange);
		System.out.println("restPath=" + restPath);
		
		
		Object replyObject = null;

		if (restPath.length() == 0) {
			// All users
			replyObject = getAllUsers();
		} else {
			String[] restPathParts = restPath.split("/");
			if (restPathParts.length == 1) {
				// example: <id>
				replyObject = getUserById(exchange, restPathParts);
			} else if (restPathParts.length >= 2) {
				// example: <id>/posts
				// or: 	    <id>/posts/<id>
				replyObject = getUserProperty(exchange, restPathParts);
			}
		}

		return new HttpResponse(replyObject);
	}

	private Object getUserProperty(HttpExchange exchange, String[] restPathParts) throws HttpException {
		User user = getUserById(exchange, restPathParts);

		if (restPathParts.length == 2 || restPathParts.length == 3) {
			if ("posts".equals(restPathParts[1])) {
				Collection<Post> posts = user.getPosts();
				if (restPathParts.length == 2) {
					// example: <user_id>/posts
					return posts;
				}
				else if (restPathParts.length == 3) {
					// example: <user_id>/posts/<post_id>
					Long postId;
					try {
						postId = Long.valueOf(restPathParts[0]);
					} catch (Exception e) {
						throw new InvalidPathException("Invalid post id");
					}
					return user.getPostById(postId);
				}
			}
		}
		
		throw new ResourceNotFoundException();
	}

	private User getUserById(HttpExchange exchange, String[] restPathParts) throws HttpException {
		Long userId;
		try {
			userId = Long.valueOf(restPathParts[0]);
		} catch (Exception e) {
			throw new InvalidPathException("Invalid user id");
		}

		User user = userRepo.findById(userId);
		if (user == null) {
			throw new ResourceNotFoundException("No user found with this id");
		}

		return user;

	}

	private Collection<User> getAllUsers() {
		Collection<User> allUsers = userRepo.findAll();
		return allUsers;
	}

}
