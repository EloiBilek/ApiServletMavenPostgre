/**
 * 
 */
package com.apiservletmavenpostgre.model.service.common;

import java.io.Serializable;
import java.util.List;

import com.apiservletmavenpostgre.model.common.IOperations;

/**
 * @author eloi eloibilek@gmail.com
 */
public abstract class AbstractService<T extends Serializable> implements IOperations<T> {

	protected abstract IOperations<T> getDao();
	
	@Override
	public T findById(final long id) {
		return getDao().findById(id);
	}

	@Override
	public List<T> findAll() {
		return getDao().findAll();
	}

	@Override
	public T create(final T entity) {
		return getDao().create(entity);
	}

	@Override
	public T update(final T entity) {
		return getDao().update(entity);
	}

	@Override
	public void delete(final T entity) {
		getDao().delete(entity);
	}

	@Override
	public void deleteById(final long entityId) {
		getDao().deleteById(entityId);
	}

}
