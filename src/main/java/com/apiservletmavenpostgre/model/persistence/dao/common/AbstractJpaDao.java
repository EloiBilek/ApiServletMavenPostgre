package com.apiservletmavenpostgre.model.persistence.dao.common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.apiservletmavenpostgre.config.LocalEntityManagerFactory;
import com.apiservletmavenpostgre.model.persistence.IOperations;

/**
 * @author eloi eloibilek@gmail.com
 */
public abstract class AbstractJpaDao<T extends Serializable> extends AbstractDAO<T> implements IOperations<T> {

	private EntityManager em = LocalEntityManagerFactory.createEntityManager();

	public T findById(final long id) {
		return em.find(clazz, Long.valueOf(id));
	}

	@Override
	public List<T> findAll() {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<T> cq = cb.createQuery(clazz);
		final Root<T> rootEntry = cq.from(clazz);
		final CriteriaQuery<T> all = cq.select(rootEntry);
		final TypedQuery<T> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	@Override
	public T create(final T entity) {
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		return entity;
	}

	@Override
	public T update(final T entity) {
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		return entity;
	}

	@Override
	public void delete(final T entity) {
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	@Override
	public void deleteById(final long entityId) {
		delete(findById(entityId));
	}
}
