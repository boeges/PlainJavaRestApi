package marc.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class User {

	private Long id;
	private String name;
	private String password;
	private Collection<Post> posts;

	public User(Long id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.posts = new ArrayList<Post>(4);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Post> getPosts() {
		return posts;
	}

	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}
	
	public Post getPostById(Long id) {
		Objects.requireNonNull(id);
		return posts.stream().filter(p -> id.equals(p.getId())).findFirst().orElse(null);
	}

}
