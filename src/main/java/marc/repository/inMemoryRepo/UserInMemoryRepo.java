package marc.repository.inMemoryRepo;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import marc.dto.NewUserDto;
import marc.entity.User;
import marc.repository.UserRepo;

public class UserInMemoryRepo implements UserRepo {
	
	public static final UserInMemoryRepo instance = new UserInMemoryRepo();
	
	private final ConcurrentMap<Long, User> userMap = new ConcurrentHashMap<Long, User>();
	private AtomicLong highestId = new AtomicLong(0);

	@Override
	public Collection<User> findAll() {
		return userMap.values();
	}

	/**
	 * returns the user to which the specified key is mapped, or null if this map contains no mapping for the key.
	 */
	@Override
	public User findById(Long id) {
		return userMap.get(id);
	}

	@Override
	public User create(NewUserDto newUser) {
		return update(new User(null, newUser.getName(), newUser.getPassword()));
	}

	@Override
	public User update(User user) {
		if (user.getId() == null) {
			user.setId(highestId.incrementAndGet());
		}
		userMap.put(user.getId(), user);
		return user;
	}

	@Override
	public void delete(User user) {
		userMap.remove(user.getId());
	}

}
