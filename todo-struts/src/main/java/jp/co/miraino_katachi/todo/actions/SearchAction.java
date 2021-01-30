package jp.co.miraino_katachi.todo.actions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

import jp.co.miraino_katachi.todo.dao.DAOFactory;
import jp.co.miraino_katachi.todo.dao.ItemDAO;
import jp.co.miraino_katachi.todo.entity.Item;
import jp.co.miraino_katachi.todo.exceptions.DAOException;

@Results({
	@Result(name = ActionSupport.SUCCESS, location = "/WEB-INF/jsp/search.jsp"),
	@Result(name = ActionSupport.ERROR, location = "/WEB-INF/jsp/error.jsp")
})
public class SearchAction extends ActionSupport {
	private static final Logger logger = LoggerFactory.getLogger(SearchAction.class);

	private String keyword;
	private Item[] items;

	public SearchAction() {
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Item[] getItems() {
		return items;
	}

	@Action("search")
	public String execute() {
		logger.trace("Enter");
		logger.debug("keyword={}", keyword);

		try {
			// 検索処理
			ItemDAO itemDAO = DAOFactory.createItemDAO();
			List<Item> itemList = itemDAO.getItemListByKeyword(keyword);
			items = itemList.toArray(new Item[0]);

		} catch (DAOException e) {
			logger.error(e.getMessage());
			addActionError(getText("errors.search"));
			return ERROR;
		}

		logger.trace("Exit");
		return SUCCESS;
	}
}
