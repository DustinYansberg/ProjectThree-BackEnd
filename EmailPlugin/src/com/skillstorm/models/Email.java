package com.skillstorm.models;

import java.sql.Date;

public class Email {

    private int id;
    private String name;
    private String subject;
    private String body;
    private Date createdAt;
    private Date updatedAt;

    public Email() {
	super();
    }

    public Email(int id, String name, String subject, String body, Date createdAt, Date updatedAt) {
	super();
	this.id = id;
	this.name = name;
	this.subject = subject;
	this.body = body;
	this.createdAt = createdAt;
	this.updatedAt = updatedAt;
    }

    public Email(String name, String subject, String body) {
	super();
	this.name = name;
	this.subject = subject;
	this.body = body;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public String getBody() {
	return body;
    }

    public void setBody(String body) {
	this.body = body;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
	return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
	this.updatedAt = updatedAt;
    }

}
