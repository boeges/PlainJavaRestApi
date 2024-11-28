package marc.repository;

import java.util.Collection;

import marc.dto.NewUserDto;
import marc.entity.User;

public interface UserRepo {
	
	Collection<User> findAll();
	
	User findById(Long id);
	
	User create(NewUserDto newUser);
	
	User update(User user);
	
	void delete(User user);

}
