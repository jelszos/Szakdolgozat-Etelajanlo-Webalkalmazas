package com.szakdologzat.repiceapp.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class User_ extends com.szakdologzat.repiceapp.domain.AbstractAuditingEntity_ {

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PASSWORD = "password";
	public static final String LANG_KEY = "langKey";
	public static final String ID = "id";
	public static final String AVATAR = "avatar";
	public static final String LOGIN = "login";
	public static final String EMAIL = "email";
	public static final String AUTHORITIES = "authorities";
	public static final String ACTIVATED = "activated";

	
	/**
	 * @see com.szakdologzat.repiceapp.domain.User#firstName
	 **/
	public static volatile SingularAttribute<User, String> firstName;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.User#lastName
	 **/
	public static volatile SingularAttribute<User, String> lastName;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.User#password
	 **/
	public static volatile SingularAttribute<User, String> password;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.User#langKey
	 **/
	public static volatile SingularAttribute<User, String> langKey;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.User#id
	 **/
	public static volatile SingularAttribute<User, Long> id;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.User#avatar
	 **/
	public static volatile SingularAttribute<User, Integer> avatar;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.User#login
	 **/
	public static volatile SingularAttribute<User, String> login;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.User
	 **/
	public static volatile EntityType<User> class_;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.User#email
	 **/
	public static volatile SingularAttribute<User, String> email;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.User#authorities
	 **/
	public static volatile SetAttribute<User, Authority> authorities;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.User#activated
	 **/
	public static volatile SingularAttribute<User, Boolean> activated;

}

