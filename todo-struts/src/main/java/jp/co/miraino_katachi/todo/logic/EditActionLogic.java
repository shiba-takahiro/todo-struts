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

public class EditActionLogic {
	private static final Logger logger = LoggerFactory.getLogger(EditActionLogic.class);

	public boolean execute(int itemID, String name, int userID, Date expireDate, boolean isFinished)
			throws DAOException {
		logger.trace("Enter");

		// 更新対象の作業項目を取得
		ItemDAO itemDAO = DAOFactory.createItemDAO();
		Item item = itemDAO.getItem(itemID);
		if (item == null) {
			logger.error("作業項目情報が見つかりません");
			return false;
		}

		// ユーザ情報を取得
		UserDAO userDAO = DAOFactory.createUserDAO();
		User user = userDAO.getUser(userID);
		if (user == null) {
			logger.error("ユーザ情報が見つかりません");
			return false;
		}

		// 作業項目を更新
		item.setUser(user);
		item.setExpireDate(expireDate);
		item.setName(name);
		item.setFinished(isFinished);

		// 作業項目再登録
		if(!itemDAO.update(item)) {
			logger.error("項目の更新に失敗しました");
			return false;
		}

		logger.trace("Exit");
		return true;
	}
}
