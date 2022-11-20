package com.example.managelibrary.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {
   
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "user_display_name",nullable = false)
    private String UserDisplayName;

    @Column(name = "about_me",nullable = true)
    private String AboutMe;

    @Column(name = "password",nullable = false)
    private String Password;

    @Column(name = "creation_date",nullable = false)
    private String CreationDate;

    @Column(name = "role",nullable = false)
    private String Role;

    
    public User() {
		super();
	}


	public User(String email, String userDisplayName, String aboutMe, String password, String creationDate,
			String role) {
		this.email = email;
		UserDisplayName = userDisplayName;
		AboutMe = aboutMe;
		Password = password;
		CreationDate = creationDate;
		Role = role;
	}


	public Long getID() {
		return ID;
	}


	public void setID(Long iD) {
		ID = iD;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUserDisplayName() {
		return UserDisplayName;
	}


	public void setUserDisplayName(String userDisplayName) {
		UserDisplayName = userDisplayName;
	}


	public String getAboutMe() {
		return AboutMe;
	}


	public void setAboutMe(String aboutMe) {
		AboutMe = aboutMe;
	}


	public String getPassword() {
		return Password;
	}


	public void setPassword(String password) {
		Password = password;
	}


	public String getCreationDate() {
		return CreationDate;
	}


	public void setCreationDate(String creationDate) {
		CreationDate = creationDate;
	}


	public String getRole() {
		return Role;
	}


	public void setRole(String role) {
		Role = role;
	}
	
}
