package com.szakdologzat.repiceapp.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Instruction.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Instruction_ {

	public static final String REQUIRED_TIME = "requiredTime";
	public static final String RECIPE = "recipe";
	public static final String DESCRIPTION = "description";
	public static final String INGREDIENTS = "ingredients";
	public static final String ID = "id";
	public static final String TITLE = "title";

	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Instruction#requiredTime
	 **/
	public static volatile SingularAttribute<Instruction, Integer> requiredTime;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Instruction#recipe
	 **/
	public static volatile SingularAttribute<Instruction, Recipe> recipe;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Instruction#description
	 **/
	public static volatile SingularAttribute<Instruction, String> description;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Instruction#ingredients
	 **/
	public static volatile SetAttribute<Instruction, Ingredient> ingredients;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Instruction#id
	 **/
	public static volatile SingularAttribute<Instruction, Long> id;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Instruction#title
	 **/
	public static volatile SingularAttribute<Instruction, String> title;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Instruction
	 **/
	public static volatile EntityType<Instruction> class_;

}

