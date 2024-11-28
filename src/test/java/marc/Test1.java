package marc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import marc.entity.User;
import marc.repository.UserRepo;
import marc.repository.dbRepo.UserDbRepo;


public class Test1 {
	private final static UserRepo userRepo = UserDbRepo.instance;

	@Test
	void demoTestMethod() {
		System.out.println("Running tests!");
		assertTrue(true);
		
	}
	
	@Test
	void testUserDbRepo() {
		User user = userRepo.findById(1l);
		
		assertEquals(user.getName(), "Alfred");
	}

	@Test
	void testRestEndpoint() {
		User user = userRepo.findById(1l);
		
		assertEquals(user.getName(), "Alfred");
	}

}
