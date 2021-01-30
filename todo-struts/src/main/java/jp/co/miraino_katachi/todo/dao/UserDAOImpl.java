package jp.co.miraino_katachi.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo.entity.User;
import jp.co.miraino_katachi.todo.exceptions.DAOException;

/**
 * ユーザテーブル用DAOクラス
 *
 */
public class UserDAOImpl implements UserDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	public User getUser(String name, String pass) throws DAOException {

		User user = null;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		// パスワードをハッシュ化
		pass = PasswordUtil.getSafetyPassword(pass, name);

		try {
			conn = DAOUtils.getDataSource().getConnection();
			// ユーザ名とパスワードでユーザテーブルを検索する
			st = conn.prepareStatement(
					"SELECT ID, FAMILY_NAME, FIRST_NAME FROM USERS WHERE USER = ? AND PASS = ?");
			st.setString(1, name);
			st.setString(2, pass);

			rs = st.executeQuery();

			// 最初に見つかったレコードからユーザ情報を作成する
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("ID"));
				user.setFamilyName(rs.getString("FAMILY_NAME"));
				user.setFirstName(rs.getString("FIRST_NAME"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			DAOUtils.close(conn, st, rs);
		}
		return user;
	}

	public List<User> getUsers() throws DAOException {
		ArrayList<User> userList = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// 登録されている全ユーザ情報を取得する
			conn = DAOUtils.getDataSource().getConnection();
			st = conn.prepareStatement(
					"SELECT ID, FAMILY_NAME, FIRST_NAME FROM USERS");

			rs = st.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("ID"));
				user.setFamilyName(rs.getString("FAMILY_NAME"));
				user.setFirstName(rs.getString("FIRST_NAME"));
				userList.add(user);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			DAOUtils.close(conn, st, rs);
		}
		return userList;
	}

	public User getUser(int id) throws DAOException {
		User user = null;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// IDとパスワードでユーザテーブルを検索する
			conn = DAOUtils.getDataSource().getConnection();
			st = conn.prepareStatement(
					"SELECT ID, FAMILY_NAME, FIRST_NAME FROM USERS WHERE ID = ?");
			st.setInt(1, id);

			rs = st.executeQuery();

			// 最初に見つかったレコードからユーザ情報を作成する
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("ID"));
				user.setFamilyName(rs.getString("FAMILY_NAME"));
				user.setFirstName(rs.getString("FIRST_NAME"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			DAOUtils.close(conn, st, rs);
		}
		return user;
	}
}
