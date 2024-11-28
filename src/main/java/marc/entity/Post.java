package marc.entity;

import java.time.LocalDate;

public class Post {

	private Long id;
	private String text;
	private LocalDate postedDate;
	private Device postedFromDevice;

	public static enum Device {
		WINDOES_PC, ANDROID_PHONE, APPLE_PHONE
	}

	public Post(Long id, String text, LocalDate postedDate, Device postedFromDevice) {
		super();
		this.text = text;
		this.postedDate = postedDate;
		this.postedFromDevice = postedFromDevice;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDate getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}

	public Device getPostedFromDevice() {
		return postedFromDevice;
	}

	public void setPostedFromDevice(Device postedFromDevice) {
		this.postedFromDevice = postedFromDevice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
