package com.apiservletmavenpostgre.model.dao.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.apiservletmavenpostgre.config.LocalEntityManagerFactory;
import com.apiservletmavenpostgre.model.common.IOperations;

/**
 * @author eloi eloibilek@gmail.com
 */
public abstract class AbstractJpaDao<T extends Serializable> extends AbstractDAO<T> implements IOperations<T> {

	private EntityManager em;

	@SuppressWarnings("unchecked")
	public T findById(final long id) {
		Object obj = null;
		try {
			em = LocalEntityManagerFactory.createEntityManager();
			obj = em.find(clazz, Long.valueOf(id));
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
		}
		return (T) obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		List<T> list = new ArrayList<>();
		try {
			em = LocalEntityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			list = ((List<T>) em.createQuery("SELECT obj FROM " + clazz.getSimpleName() + " obj ORDER BY id").getResultList());
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
		}
		return list;
	}

	@Override
	public T create(final T entity) {
		try {
			em = LocalEntityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
		}
		return entity;
	}

	@Override
	public T update(final T entity) {
		try {
			em = LocalEntityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
		}
		return entity;
	}

	@Override
	public void delete(final T entity) {
		try {
			em = LocalEntityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
		}
	}

	@Override
	public void deleteById(final long entityId) {
		try {
			em = LocalEntityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.remove(em.find(clazz, Long.valueOf(entityId)));
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
		}
	}
}
