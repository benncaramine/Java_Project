package com.emsi.dao;

import java.util.List;

import com.emsi.entities.Stadium;

public interface StadiumDao {
	void insert(Stadium stadium);

	void update(Stadium stadium);

	void deleteById(Integer register);

	Stadium findById(Integer register);

	List<Stadium> findAll();

	boolean validateUser(String username, String password);
}
