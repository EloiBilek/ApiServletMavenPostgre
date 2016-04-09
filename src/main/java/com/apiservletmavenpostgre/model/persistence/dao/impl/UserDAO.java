package com.apiservletmavenpostgre.model.persistence.dao.impl;

import com.apiservletmavenpostgre.model.entity.User;
import com.apiservletmavenpostgre.model.persistence.dao.IUserDAO;
import com.apiservletmavenpostgre.model.persistence.dao.common.AbstractJpaDao;

/**
 * @author eloi eloibilek@gmail.com
 */
public class UserDAO extends AbstractJpaDao<User> implements IUserDAO {

	public UserDAO() {
		setClazz(User.class);
	}

}
