package com.emsi.service;

import java.util.List;

import com.emsi.dao.StadiumDao;
import com.emsi.dao.impl.StadiumDaoImpl;
import com.emsi.entities.Stadium;

public class StadiumService {
	private StadiumDao stadiumDao = new StadiumDaoImpl();

	public List<Stadium> findAll() {
		return stadiumDao.findAll();
	}

	public void saveOrUpdate(Stadium stadium) {
		if (stadiumDao.findById(stadium.getId()) == null) {
			stadiumDao.insert(stadium);
			System.out.println("adding stadium");
		} else {
			stadiumDao.update(stadium);
		}
	}
	
	public void save(Stadium stadium) {
		stadiumDao.insert(stadium);
	}

	public void update(Stadium stadium) {
		stadiumDao.update(stadium);
	}

	public void remove(Stadium stadium) {
		stadiumDao.deleteById(stadium.getId());
	}
	public boolean validateUser(String username, String password) {
		return stadiumDao.validateUser(username, password);
	}
}
