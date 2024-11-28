package marc.repository.dbRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import marc.dto.NewUserDto;
import marc.entity.User;
import marc.repository.UserRepo;

public class UserDbRepo implements UserRepo {
	
	public static final UserDbRepo instance = new UserDbRepo();
	
	public UserDbRepo() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Collection<User> findAll() {
		ArrayList<User> users = new ArrayList<User>(8);
		String sql = "select id,name,password from user;";
		
		// try to connect
        try (Connection connection = DbConnectionFactory.getConnection();) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				String password = resultSet.getString("password");
				User user = new User(Long.valueOf(id), name, password);
				users.add(user);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
        
		return users;
	}

	@Override
	public User findById(Long id) {
		User user = null;
		String sql = "select id,name,password from user where id=?;";

		// try to connect
		try (Connection connection = DbConnectionFactory.getConnection();) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				long id2 = resultSet.getLong("id");
				String name = resultSet.getString("name");
				String password = resultSet.getString("password");
				user = new User(Long.valueOf(id2), name, password);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
        
		return user;
	}

	@Override
	public User create(NewUserDto newUser) {
		User user = null;
		String sql = "insert into user (id, name, password) values (null, ?, ?);";

		// try to connect
		try (Connection connection = DbConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, newUser.getName());
			statement.setString(2, newUser.getPassword());
			
			statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();
		    if (rs.next()) {
		        Long id = rs.getLong(1);
		        user = new User(id, newUser.getName(), newUser.getPassword());
		    }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
        
		return user;
	}

	@Override
	public User update(User user) {
		String sql = "update user set name=?, password=? where id=?;";

		// try to connect
		try (Connection connection = DbConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getPassword());
			statement.setLong(3, user.getId());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
        
		return user;
	}

	@Override
	public void delete(User user) {
		String sql = "delete from user where id=?;";

		// try to connect
		try (Connection connection = DbConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setLong(0, user.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


}
