package com.apiservletmavenpostgre.model.dao.common;

import java.io.Serializable;

import com.google.common.base.Preconditions;

/**
 * @author eloi eloibilek@gmail.com
 */
public abstract class AbstractDAO<T extends Serializable> {

	protected Class<T> clazz;

	protected final void setClazz(final Class<T> clazzToSet) {
		clazz = Preconditions.checkNotNull(clazzToSet);
	}

}
