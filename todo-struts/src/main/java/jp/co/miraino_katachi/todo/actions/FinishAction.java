package jp.co.miraino_katachi.todo.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

import jp.co.miraino_katachi.todo.exceptions.DAOException;
import jp.co.miraino_katachi.todo.logic.FinishLogic;

@Results({
	@Result(name = ActionSupport.SUCCESS, location = "list", type = "chain"),
	@Result(name = "search", location = "search", type = "chain"),
	@Result(name = ActionSupport.ERROR, location = "/WEB-INF/jsp/error.jsp")
})
public class FinishAction extends ActionSupport {
	private static final Logger logger = LoggerFactory.getLogger(FinishAction.class);

	private int itemId;
	private String keyword;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Action("finish")
	public String execute() {
		logger.trace("Enter");
		logger.debug("itemId={}, keyword={}", itemId, keyword);

		try {
			// 完了状態更新処理
			if(!(new FinishLogic().execute(itemId))) {
				logger.error("完了状態更新失敗 id = {}", itemId);

				addActionError(getText("errors.finish"));
				return ERROR;
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());

			addActionError(getText("errors.finish"));
			return ERROR;
		}

		logger.trace("Exit");

		// 呼びだし元画面(一覧or検索)によって遷移先を変更(検索画面の場合keywordあり)
		if (keyword == null) {
			return SUCCESS;
		} else {
			return "search";
		}
	}
}
