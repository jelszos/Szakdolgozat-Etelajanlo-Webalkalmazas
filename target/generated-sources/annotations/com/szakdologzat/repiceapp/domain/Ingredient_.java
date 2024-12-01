package com.szakdologzat.repiceapp.domain;

import com.szakdologzat.repiceapp.domain.enumeration.Unit;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Ingredient.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Ingredient_ {

	public static final String AMOUNT = "amount";
	public static final String UNIT = "unit";
	public static final String INSTRUCTION = "instruction";
	public static final String NAME = "name";
	public static final String ID = "id";

	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Ingredient#amount
	 **/
	public static volatile SingularAttribute<Ingredient, Integer> amount;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Ingredient#unit
	 **/
	public static volatile SingularAttribute<Ingredient, Unit> unit;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Ingredient#instruction
	 **/
	public static volatile SingularAttribute<Ingredient, Instruction> instruction;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Ingredient#name
	 **/
	public static volatile SingularAttribute<Ingredient, String> name;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Ingredient#id
	 **/
	public static volatile SingularAttribute<Ingredient, Long> id;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Ingredient
	 **/
	public static volatile EntityType<Ingredient> class_;

}

