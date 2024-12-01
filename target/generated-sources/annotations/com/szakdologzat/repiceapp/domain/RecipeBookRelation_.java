package com.szakdologzat.repiceapp.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(RecipeBookRelation.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class RecipeBookRelation_ {

	public static final String RECIPE_BOOK = "recipeBook";
	public static final String RECIPE = "recipe";
	public static final String ID = "id";

	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBookRelation#recipeBook
	 **/
	public static volatile SingularAttribute<RecipeBookRelation, RecipeBook> recipeBook;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBookRelation#recipe
	 **/
	public static volatile SingularAttribute<RecipeBookRelation, Recipe> recipe;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBookRelation#id
	 **/
	public static volatile SingularAttribute<RecipeBookRelation, Long> id;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.RecipeBookRelation
	 **/
	public static volatile EntityType<RecipeBookRelation> class_;

}

