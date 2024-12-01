package com.szakdologzat.repiceapp.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FavoriteRelation.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class FavoriteRelation_ {

	public static final String RECIPE = "recipe";
	public static final String ID = "id";
	public static final String USER = "user";

	
	/**
	 * @see com.szakdologzat.repiceapp.domain.FavoriteRelation#recipe
	 **/
	public static volatile SingularAttribute<FavoriteRelation, Recipe> recipe;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.FavoriteRelation#id
	 **/
	public static volatile SingularAttribute<FavoriteRelation, Long> id;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.FavoriteRelation
	 **/
	public static volatile EntityType<FavoriteRelation> class_;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.FavoriteRelation#user
	 **/
	public static volatile SingularAttribute<FavoriteRelation, User> user;

}

