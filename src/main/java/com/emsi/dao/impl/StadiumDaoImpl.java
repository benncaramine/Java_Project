package com.emsi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.emsi.dao.StadiumDao;
import com.emsi.entities.Stadium;

public class StadiumDaoImpl implements StadiumDao {

	private Connection conn = DB.getConnection();

	@Override
	public void insert(Stadium stadium) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("INSERT INTO Stadium (name, city, address, constructionYear, capacity) " +
			"VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, stadium.getName());
			ps.setString(2, stadium.getCity());
			ps.setString(3, stadium.getAddress());
			ps.setInt(4, stadium.getConstructionYear());
			ps.setDouble(5, stadium.getCapacity());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);

					stadium.setId(id);
				}

				DB.closeResultSet(rs);
			} else {
				System.out.println("Aucune ligne renvoyée");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// System.err.println("problème d'insertion d'un stade");
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Stadium stadium) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("UPDATE Stadium SET name = ?, city = ?, address = ?, constructionYear = ?, capacity = ? " +
			"WHERE id = ?");

			ps.setString(1, stadium.getName());
			ps.setString(2, stadium.getCity());
			ps.setString(3, stadium.getAddress());
			ps.setInt(4, stadium.getConstructionYear());
			ps.setDouble(5, stadium.getCapacity());
			ps.setInt(6, stadium.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("problème de mise à jour d'un stade");
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("DELETE FROM Stadium WHERE id = ?");

			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("problème de suppression d'un stade");
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public Stadium findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM Stadium WHERE id = ?");

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Stadium stadium = new Stadium();

				stadium.setId(rs.getInt("id"));
				stadium.setName(rs.getString("name"));
				stadium.setCity(rs.getString("city"));
				stadium.setAddress(rs.getString("address"));
				stadium.setConstructionYear(rs.getInt("constructionYear"));
				stadium.setCapacity(rs.getDouble("capacity"));

				return stadium;
			}

			return null;
		} catch (SQLException e) {
			System.err.println("Problème de requête pour trouver un stade");
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	@Override
	public List<Stadium> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM Stadium");
			rs = ps.executeQuery();

			List<Stadium> listStadium = new ArrayList<>();

			while (rs.next()) {
				Stadium stadium = new Stadium();

				stadium.setId(rs.getInt("id"));
				stadium.setName(rs.getString("name"));
				stadium.setCity(rs.getString("city"));
				stadium.setAddress(rs.getString("address"));
				stadium.setConstructionYear(rs.getInt("constructionYear"));
				stadium.setCapacity(rs.getDouble("capacity"));

				listStadium.add(stadium);
			}

			return listStadium;
		} catch (SQLException e) {
			System.err.println("Problème de requête pour sélectionner une liste de stades");
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	@Override
	public boolean validateUser(String username, String password) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM Auth WHERE username = ? AND password = ?");
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			System.err.println("Problème de requête pour trouver un utilisateur");
			return false;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}
}
