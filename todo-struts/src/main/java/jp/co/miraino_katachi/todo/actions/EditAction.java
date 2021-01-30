package jp.co.miraino_katachi.todo.actions;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.DateRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

import jp.co.miraino_katachi.todo.dao.DAOFactory;
import jp.co.miraino_katachi.todo.dao.ItemDAO;
import jp.co.miraino_katachi.todo.dao.UserDAO;
import jp.co.miraino_katachi.todo.entity.Item;
import jp.co.miraino_katachi.todo.entity.User;
import jp.co.miraino_katachi.todo.exceptions.DAOException;
import jp.co.miraino_katachi.todo.logic.EditActionLogic;

public class EditAction extends ActionSupport implements SessionAware {
	private static final Logger logger = LoggerFactory.getLogger(EditAction.class);

	private Map<String, Object> session;

	private int itemId;
	private boolean finished;
	private String name;
	private int userId;
	private Date expireDate;

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemId() {
		return itemId;
	}

	public String getName() {
		return name;
	}

	@RequiredStringValidator(
			message = "${getText('errors.name.required')}")
	@StringLengthFieldValidator(
			minLength = "1",
			maxLength = "100",
			message = "${getText('errors.name.length')}")
	public void setName(String name) {
		this.name = name;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getExpireDate() {
		return this.expireDate;
	}

	@RequiredFieldValidator(
			message = "${getText('errors.expiredate.required')}")
	@DateRangeFieldValidator(
			max = "2100/12/31",
			message = "${getText('errors.expiredate.range')}")
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public boolean getFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public EditAction() {
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Action(value="edit", results = {
		@Result(name = ActionSupport.SUCCESS, location = "/WEB-INF/jsp/edit.jsp"),
		@Result(name = ActionSupport.ERROR, location = "/WEB-INF/jsp/error.jsp")
	})
	@SkipValidation()	// 登録画面表示時はバリデーションを行わない
	public String show() {
		logger.trace("Enter");
		logger.debug("itemId={}", itemId);

		try {
			// 更新対象アイテム取得
			ItemDAO itemDAO = DAOFactory.createItemDAO();
			Item item = itemDAO.getItem(itemId);
			session.put("item", item);

			// ユーザプルダウンメニュー用のユーザリスト取得
			UserDAO userDAO = DAOFactory.createUserDAO();
			List<User> userList = userDAO.getUsers();
			session.put("users", userList);

		} catch(DAOException e) {
			logger.error(e.getMessage());

			addActionError(getText("errors.edit"));

			return ERROR;
		}

		logger.trace("Exit");
		return SUCCESS;
	}

	@Action(value = "edit_action", results = {
		@Result(name = ActionSupport.SUCCESS, location = "list", type = "chain"),
		@Result(name = ActionSupport.INPUT, location = "/WEB-INF/jsp/edit.jsp"),
		@Result(name = ActionSupport.ERROR, location = "/WEB-INF/jsp/error.jsp")
	})
	public String execute() {
		logger.trace("Enter");
		logger.trace("itemId={}, name={}, userId={}, expireDate={} finished={}", itemId, name, userId, expireDate, finished);

		// セッションに保存した入力用パラメータを削除
		session.remove("item");
		session.remove("users");

		try {
			// 編集処理
			boolean isSuccess =
					new EditActionLogic().execute(itemId, name, userId, expireDate, finished);
			if (!isSuccess) {
				logger.error("作業項目更新失敗 id = {}",itemId);

				addActionError(getText("errors.edit_action"));
				return ERROR;
			}
		} catch(DAOException e) {
			logger.error(e.getMessage());

			addActionError(getText("errors.edit_action"));
			return ERROR;
		}

		logger.trace("Exit");
		return SUCCESS;
	}
}
