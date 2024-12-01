package com.szakdologzat.repiceapp.domain;

import com.szakdologzat.repiceapp.domain.enumeration.FoodCategory;
import com.szakdologzat.repiceapp.domain.enumeration.FoodType;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.ZonedDateTime;

@StaticMetamodel(Recipe.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Recipe_ {

	public static final String INSTRUCTIONS = "instructions";
	public static final String FOOD_TYPE = "foodType";
	public static final String DESCRIPTION = "description";
	public static final String TITLE = "title";
	public static final String CREATED_DATE = "createdDate";
	public static final String FAVORITE_RELATIONS = "favoriteRelations";
	public static final String RATINGS = "ratings";
	public static final String IMAGE_URL = "imageUrl";
	public static final String RECIPE_BOOK_RELATIONS = "recipeBookRelations";
	public static final String ID = "id";
	public static final String FOOD_CATEGORY = "foodCategory";
	public static final String USER = "user";
	public static final String INGREDIENT_NAMES = "ingredientNames";

	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#instructions
	 **/
	public static volatile SetAttribute<Recipe, Instruction> instructions;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#foodType
	 **/
	public static volatile SingularAttribute<Recipe, FoodType> foodType;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#description
	 **/
	public static volatile SingularAttribute<Recipe, String> description;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#title
	 **/
	public static volatile SingularAttribute<Recipe, String> title;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#createdDate
	 **/
	public static volatile SingularAttribute<Recipe, ZonedDateTime> createdDate;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#favoriteRelations
	 **/
	public static volatile SetAttribute<Recipe, FavoriteRelation> favoriteRelations;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#ratings
	 **/
	public static volatile SetAttribute<Recipe, Rating> ratings;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#imageUrl
	 **/
	public static volatile SingularAttribute<Recipe, String> imageUrl;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#recipeBookRelations
	 **/
	public static volatile SetAttribute<Recipe, RecipeBookRelation> recipeBookRelations;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#id
	 **/
	public static volatile SingularAttribute<Recipe, Long> id;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe
	 **/
	public static volatile EntityType<Recipe> class_;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#foodCategory
	 **/
	public static volatile SingularAttribute<Recipe, FoodCategory> foodCategory;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#user
	 **/
	public static volatile SingularAttribute<Recipe, User> user;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Recipe#ingredientNames
	 **/
	public static volatile SingularAttribute<Recipe, String> ingredientNames;

}

