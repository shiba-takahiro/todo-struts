package jp.co.miraino_katachi.todo.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

import jp.co.miraino_katachi.todo.dao.DAOFactory;
import jp.co.miraino_katachi.todo.dao.ItemDAO;
import jp.co.miraino_katachi.todo.entity.Item;
import jp.co.miraino_katachi.todo.exceptions.DAOException;
import jp.co.miraino_katachi.todo.logic.DeleteActionLogic;

public class DeleteAction extends ActionSupport {
	private static final Logger logger = LoggerFactory.getLogger(DeleteAction.class);

	private int itemId;
	private Item item;

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemId() {
		return itemId;
	}

	public Item getItem() {
		return item;
	}

	@Action(value="delete", results = {
			@Result(name = ActionSupport.SUCCESS, location = "/WEB-INF/jsp/delete.jsp"),
			@Result(name = ActionSupport.ERROR, location = "/WEB-INF/jsp/error.jsp")
		})
	public String show() {
		logger.trace("Enter");
		logger.debug("itemId={}", itemId);

		try {
			// 削除対象アイテム取得
			ItemDAO itemDAO = DAOFactory.createItemDAO();
			item = itemDAO.getItem(itemId);
		} catch(DAOException e) {
			logger.error(e.getMessage());

			addActionError(getText("errors.delete"));

			return ERROR;
		}

		logger.trace("Exit");
		return SUCCESS;
	}

	@Action(value = "delete_action", results = {
		@Result(name = ActionSupport.SUCCESS, location = "list", type = "chain"),
		@Result(name = ActionSupport.ERROR, location = "/WEB-INF/jsp/error.jsp")
	})
	public String execute() {
		logger.trace("Enter");
		logger.trace("itemId={}", itemId);

		try {
			// 削除処理
			boolean isSuccess =
					new DeleteActionLogic().execute(itemId);
			if (!isSuccess) {
				logger.error("削除失敗 id = {}", itemId);

				addActionError(getText("errors.delete_action"));
				return ERROR;
			}
		} catch(DAOException e) {
			logger.error(e.getMessage());

			addActionError(getText("errors.delete_action"));
			return ERROR;
		}
		logger.trace("Exit");
		return SUCCESS;
	}
}
