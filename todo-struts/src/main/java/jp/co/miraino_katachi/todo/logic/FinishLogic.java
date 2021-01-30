package jp.co.miraino_katachi.todo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo.dao.DAOFactory;
import jp.co.miraino_katachi.todo.dao.ItemDAO;
import jp.co.miraino_katachi.todo.entity.Item;
import jp.co.miraino_katachi.todo.exceptions.DAOException;

public class FinishLogic {
	private static final Logger logger = LoggerFactory.getLogger(FinishLogic.class);

	public boolean execute(int itemID) throws DAOException {
		logger.trace("Enter");

		// 更新対象の作業項目を取得
		ItemDAO itemDAO = DAOFactory.createItemDAO();
		Item item = itemDAO.getItem(itemID);
		if (item == null) {
			logger.error("作業項目情報が見つかりません");
			return false;
		}

		// 完了/未完了を入れ替える
		item.setFinished(!item.isFinished());
		logger.debug("完了日を{}に変更", item.getFinishedDate());

		// 更新する
		if(!itemDAO.update(item)) {
			logger.error("完了状態の更新に失敗しました");
			return false;
		}

		logger.trace("Exit");
		return true;
	}
}
