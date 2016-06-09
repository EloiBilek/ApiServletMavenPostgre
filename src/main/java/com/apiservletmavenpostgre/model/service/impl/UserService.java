package com.apiservletmavenpostgre.model.service.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.apiservletmavenpostgre.model.common.IOperations;
import com.apiservletmavenpostgre.model.dao.IUserDAO;
import com.apiservletmavenpostgre.model.entity.User;
import com.apiservletmavenpostgre.model.service.IUserService;
import com.apiservletmavenpostgre.model.service.common.AbstractService;

/**
 * @author eloi eloibilek@gmail.com
 */
@RequestScoped
public class UserService extends AbstractService<User> implements IUserService {

	@Inject
	private IUserDAO dao;

	public UserService() {
	}

	@Override
	protected IOperations<User> getDao() {
		return dao;
	}

}
