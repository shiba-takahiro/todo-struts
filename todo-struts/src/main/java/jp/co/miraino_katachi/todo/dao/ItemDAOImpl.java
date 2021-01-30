package jp.co.miraino_katachi.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo.entity.Item;
import jp.co.miraino_katachi.todo.entity.User;
import jp.co.miraino_katachi.todo.exceptions.DAOException;

public class ItemDAOImpl implements ItemDAO {
	private static final Logger logger = LoggerFactory.getLogger(ItemDAOImpl.class);

	public List<Item> getItemList() throws DAOException {

		ArrayList<Item> itemList = new ArrayList<Item>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = DAOUtils.getDataSource().getConnection();
			st = conn.prepareStatement(
					"SELECT TODO_ITEMS.ID" +
					", TODO_ITEMS.ITEM_NAME" +
					", USERS.ID" +
					", USERS.FAMILY_NAME" +
					", USERS.FIRST_NAME" +
					", EXPIRE_DATE" +
					", FINISHED_DATE" +
					" FROM USERS" +
					", TODO_ITEMS" +
					" WHERE USERS.ID=TODO_ITEMS.USER_ID" +
					" ORDER BY EXPIRE_DATE");

			rs = st.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("USERS.ID"));
				user.setFamilyName(rs.getString("USERS.FAMILY_NAME"));
				user.setFirstName(rs.getString("USERS.FIRST_NAME"));

				Item item = new Item();
				item.setId(rs.getInt("TODO_ITEMS.ID"));
				item.setName(rs.getString("TODO_ITEMS.ITEM_NAME"));
				item.setUser(user);
				item.setExpireDate(rs.getDate("EXPIRE_DATE"));
				item.setFinishedDate(rs.getDate("FINISHED_DATE"));

				itemList.add(item);
			}
		} catch(SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			DAOUtils.close(conn, st, rs);
		}
		return itemList;
	}

	public Item getItem(int id) throws DAOException {
		Item item = null;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = DAOUtils.getDataSource().getConnection();
			st = conn.prepareStatement(
					"SELECT TODO_ITEMS.ID" +
					", TODO_ITEMS.ITEM_NAME" +
					", USERS.ID" +
					", USERS.FAMILY_NAME" +
					", USERS.FIRST_NAME" +
					", EXPIRE_DATE" +
					", FINISHED_DATE" +
					" FROM USERS" +
					", TODO_ITEMS" +
					" WHERE USERS.ID=TODO_ITEMS.USER_ID" +
					" AND TODO_ITEMS.ID=?");
			st.setInt(1, id);

			rs = st.executeQuery();
			if(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("USERS.ID"));
				user.setFamilyName(rs.getString("USERS.FAMILY_NAME"));
				user.setFirstName(rs.getString("USERS.FIRST_NAME"));

				item = new Item();
				item.setId(rs.getInt("TODO_ITEMS.ID"));
				item.setName(rs.getString("TODO_ITEMS.ITEM_NAME"));
				item.setUser(user);
				item.setExpireDate(rs.getDate("EXPIRE_DATE"));
				item.setFinishedDate(rs.getDate("FINISHED_DATE"));
			}
		} catch(SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			DAOUtils.close(conn, st, rs);
		}
		return item;
	}

	public List<Item> getItemListByKeyword(String keyword) throws DAOException {

		ArrayList<Item> itemList = new ArrayList<Item>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		// WHERE句内の検索条件作成
		StringBuffer where = new StringBuffer();
		String[] fields = new String[] { "TODO_ITEMS.ITEM_NAME", "TODO_ITEMS.EXPIRE_DATE",
				"USERS.FAMILY_NAME", "USERS.FIRST_NAME", "TODO_ITEMS.FINISHED_DATE" };
		for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			if (where.length() > 0) {
				where.append(" OR ");
			}
			where.append(field);
			where.append(" LIKE ?");
		}
		logger.debug(where.toString());

		try {
			conn = DAOUtils.getDataSource().getConnection();
			st = conn.prepareStatement(
					"SELECT TODO_ITEMS.ID" +
					", TODO_ITEMS.ITEM_NAME" +
					", USERS.ID" +
					", USERS.FAMILY_NAME" +
					", USERS.FIRST_NAME" +
					", EXPIRE_DATE" +
					", FINISHED_DATE" +
					" FROM USERS" +
					", TODO_ITEMS" +
					" WHERE USERS.ID=TODO_ITEMS.USER_ID" +
					" AND (" + where + ")" +
					" ORDER BY EXPIRE_DATE");
			for (int i = 0; i < fields.length; i++) {
				st.setString(i + 1, "%" + keyword + "%");
			}
			logger.debug(st.toString());

			rs = st.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("USERS.ID"));
				user.setFamilyName(rs.getString("USERS.FAMILY_NAME"));
				user.setFirstName(rs.getString("USERS.FIRST_NAME"));

				Item item = new Item();
				item.setId(rs.getInt("TODO_ITEMS.ID"));
				item.setName(rs.getString("TODO_ITEMS.ITEM_NAME"));
				item.setUser(user);
				item.setExpireDate(rs.getDate("EXPIRE_DATE"));
				item.setFinishedDate(rs.getDate("FINISHED_DATE"));

				itemList.add(item);
			}
		} catch(SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			DAOUtils.close(conn, st, rs);
		}
		return itemList;
	}

	public boolean add(Item item) throws DAOException {
		boolean isSuccess = false;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = DAOUtils.getDataSource().getConnection();
			st = conn.prepareStatement(
					"INSERT INTO TODO_ITEMS (USER_ID, ITEM_NAME, REGISTRATION_DATE, EXPIRE_DATE, FINISHED_DATE) VALUES (?, ?, ?, ?, NULL)");
			st.setInt(1, item.getUser().getId());
			st.setString(2, item.getName());
			st.setDate(3, DAOUtils.sqlDate(new Date()));
			st.setDate(4, DAOUtils.sqlDate(item.getExpireDate()));

			if(st.executeUpdate() == 1) {
				isSuccess = true;
			}
		} catch(SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			DAOUtils.close(conn, st, rs);
		}
		return isSuccess;
	}

	public boolean update(Item item) throws DAOException {
		boolean isSuccess = false;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = DAOUtils.getDataSource().getConnection();
			st = conn.prepareStatement(
					"UPDATE TODO_ITEMS SET USER_ID=?, ITEM_NAME=?, EXPIRE_DATE=?, FINISHED_DATE=? WHERE ID=?");
			st.setInt(1, item.getUser().getId());
			st.setString(2, item.getName());
			st.setDate(3, DAOUtils.sqlDate(item.getExpireDate()));
			st.setDate(4, DAOUtils.sqlDate(item.getFinishedDate()));
			st.setInt(5, item.getId());

			if(st.executeUpdate() == 1) {
				isSuccess = true;
			}
		} catch(SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			DAOUtils.close(conn, st, rs);
		}
		return isSuccess;
	}

	public boolean delete(int id) throws DAOException {
		boolean isSuccess = false;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = DAOUtils.getDataSource().getConnection();
			st = conn.prepareStatement(
					"DELETE FROM TODO_ITEMS WHERE ID=?");
			st.setInt(1, id);

			if(st.executeUpdate() == 1) {
				isSuccess = true;
			}
		} catch(SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			DAOUtils.close(conn, st, rs);
		}
		return isSuccess;
	}
}
