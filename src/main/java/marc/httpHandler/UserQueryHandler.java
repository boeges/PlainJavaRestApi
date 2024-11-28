package marc.httpHandler;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import marc.dto.NewUserDto;
import marc.entity.User;
import marc.httpExceptions.HttpException;
import marc.repository.UserRepo;
import marc.repository.dbRepo.UserDbRepo;

public class UserQueryHandler extends BaseRestHttpHandler {

	private UserRepo userRepo = UserDbRepo.instance;
	
	@Override
	protected HttpResponse get(HttpExchange exchange) throws IOException {
		Collection<User> allUsers = userRepo.findAll();
		return new HttpResponse(200, allUsers);
	}
	
	@Override
	protected HttpResponse post(HttpExchange exchange) throws IOException, HttpException {
		ObjectMapper objectMapper = new ObjectMapper();
		NewUserDto userDto = objectMapper.readValue(exchange.getRequestBody(), NewUserDto.class);
		
		if (userDto.getName() == null || userDto.getPassword() == null) {
			throw new HttpException(400, "name and password must be not null!");
		}
		
		User user = userRepo.create(userDto);
		return new HttpResponse(user);
	}
	
}
