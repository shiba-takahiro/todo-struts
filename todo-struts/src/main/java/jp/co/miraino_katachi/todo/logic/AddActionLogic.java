package jp.co.miraino_katachi.todo.logic;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo.dao.DAOFactory;
import jp.co.miraino_katachi.todo.dao.ItemDAO;
import jp.co.miraino_katachi.todo.dao.UserDAO;
import jp.co.miraino_katachi.todo.entity.Item;
import jp.co.miraino_katachi.todo.entity.User;
import jp.co.miraino_katachi.todo.exceptions.DAOException;

public class AddActionLogic {
	private static final Logger logger = LoggerFactory.getLogger(AddActionLogic.class);

	public boolean execute(String name, int userID, Date expireDate)
			throws DAOException {
		logger.trace("Enter");

		// ユーザ情報を取得
		UserDAO userDAO = DAOFactory.createUserDAO();
		User user = userDAO.getUser(userID);
		if (user == null) {
			logger.error("ユーザ情報が見つかりません");
			return false;
		}

		Item targetItem = new Item();
		targetItem.setName(name);
		targetItem.setUser(user);
		targetItem.setExpireDate(expireDate);

		ItemDAO itemDAO = DAOFactory.createItemDAO();
		if(!itemDAO.add(targetItem)) {
			logger.error("作業項目の登録に失敗しました");
			return false;
		}

		logger.trace("Exit");
		return true;
	}
}
