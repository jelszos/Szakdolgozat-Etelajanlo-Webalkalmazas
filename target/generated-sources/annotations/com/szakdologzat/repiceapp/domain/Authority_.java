package com.szakdologzat.repiceapp.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Authority.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Authority_ {

	public static final String NAME = "name";

	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Authority#name
	 **/
	public static volatile SingularAttribute<Authority, String> name;
	
	/**
	 * @see com.szakdologzat.repiceapp.domain.Authority
	 **/
	public static volatile EntityType<Authority> class_;

}

