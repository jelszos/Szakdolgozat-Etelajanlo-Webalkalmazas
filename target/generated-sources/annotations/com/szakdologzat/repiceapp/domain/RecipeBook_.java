package com.szakdologzat.repiceapp.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;

@StaticMetamodel(RecipeBook.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class RecipeBook_ {

	public static final String CREATED_DATE = "createdDate";
	public static final String DESCRIPTION = "description";
	public static final String RECIPE_BOOK_RELATIONS = "recipeBookRelations";
	public static final String THEME = "theme";
	public static final String ID = "id";
	public static final String IS_PRIVATE = "isPrivate";
	public static final String TITLE = "title";
	public static final String USER = "user";
	public static final String TAGS = "tags";

	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBook#createdDate
	 **/
	public static volatile SingularAttribute<RecipeBook, ZonedDateTime> createdDate;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBook#description
	 **/
	public static volatile SingularAttribute<RecipeBook, String> description;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBook#recipeBookRelations
	 **/
	public static volatile SetAttribute<RecipeBook, RecipeBookRelation> recipeBookRelations;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBook#theme
	 **/
	public static volatile SingularAttribute<RecipeBook, String> theme;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBook#id
	 **/
	public static volatile SingularAttribute<RecipeBook, Long> id;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBook#isPrivate
	 **/
	public static volatile SingularAttribute<RecipeBook, Boolean> isPrivate;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBook#title
	 **/
	public static volatile SingularAttribute<RecipeBook, String> title;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBook
	 **/
	public static volatile EntityType<RecipeBook> class_;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBook#user
	 **/
	public static volatile SingularAttribute<RecipeBook, User> user;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBook#tags
	 **/
	public static volatile SingularAttribute<RecipeBook, String> tags;

}

