package jp.co.miraino_katachi.todo.dao;

import java.util.List;

import jp.co.miraino_katachi.todo.entity.User;
import jp.co.miraino_katachi.todo.exceptions.DAOException;

/**
 *
 * ユーザ情報用DAOインタフェース
 *
 */
public interface UserDAO {

	/**
	 * ユーザ名とパスワードからユーザ情報を取得する
	 *
	 * @param name ユーザ名
	 * @param pass パスワード
	 * @return IDとパスワードが一致するユーザ情報を返す
	 * @throws DAOException
	 */
	public User getUser(String name, String pass) throws DAOException;

	/**
	 * 全ユーザ情報を取得する
	 *
	 * @return 登録されているUserのArrayList
	 * @throws DAOException
	 */
	public List<User> getUsers() throws DAOException;

	/**
	 * IDからユーザ情報を取得する
	 *
	 * @param id ユーザID
	 * @return IDが一致するユーザ情報を返す
	 * @throws DAOException
	 */
	public User getUser(int id) throws DAOException;

}
