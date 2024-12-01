package com.szakdologzat.repiceapp.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;

@StaticMetamodel(Rating.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Rating_ {

	public static final String CREATED_DATE = "createdDate";
	public static final String RATE = "rate";
	public static final String IMAGE_URL = "imageUrl";
	public static final String RECIPE = "recipe";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String USER = "user";

	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Rating#createdDate
	 **/
	public static volatile SingularAttribute<Rating, ZonedDateTime> createdDate;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Rating#rate
	 **/
	public static volatile SingularAttribute<Rating, Integer> rate;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Rating#imageUrl
	 **/
	public static volatile SingularAttribute<Rating, String> imageUrl;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Rating#recipe
	 **/
	public static volatile SingularAttribute<Rating, Recipe> recipe;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Rating#description
	 **/
	public static volatile SingularAttribute<Rating, String> description;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Rating#id
	 **/
	public static volatile SingularAttribute<Rating, Long> id;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Rating#title
	 **/
	public static volatile SingularAttribute<Rating, String> title;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Rating
	 **/
	public static volatile EntityType<Rating> class_;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Rating#user
	 **/
	public static volatile SingularAttribute<Rating, User> user;

}

