package jp.co.miraino_katachi.todo.entity;

public class User {
	private int id;
	private String familyName;
	private String firstName;

	public User() {
		this.id = 0;
		this.familyName = null;
		this.firstName = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.familyName + " " + this.firstName;
	}

	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(String name) {
		this.familyName = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
