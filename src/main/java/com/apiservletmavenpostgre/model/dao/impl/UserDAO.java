package com.apiservletmavenpostgre.model.dao.impl;

import com.apiservletmavenpostgre.model.dao.IUserDAO;
import com.apiservletmavenpostgre.model.dao.common.AbstractJpaDao;
import com.apiservletmavenpostgre.model.entity.User;

/**
 * @author eloi eloibilek@gmail.com
 */
public class UserDAO extends AbstractJpaDao<User> implements IUserDAO {

	public UserDAO() {
		setClazz(User.class);
	}

}
