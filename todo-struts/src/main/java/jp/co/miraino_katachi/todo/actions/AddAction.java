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
import jp.co.miraino_katachi.todo.dao.UserDAO;
import jp.co.miraino_katachi.todo.entity.User;
import jp.co.miraino_katachi.todo.exceptions.DAOException;
import jp.co.miraino_katachi.todo.logic.AddActionLogic;

public class AddAction extends ActionSupport implements SessionAware {
	private static final Logger logger = LoggerFactory.getLogger(AddAction.class);

	private Map<String, Object> session = null;

	private String name;
	private int userId;
	private Date expireDate;

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
		return expireDate;
	}

	@RequiredFieldValidator(
			message = "${getText('errors.expiredate.required')}")
	@DateRangeFieldValidator(
			max = "2100/12/31",
			message = "${getText('errors.expiredate.range')}")
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Action(value="add", results = {
		@Result(name = ActionSupport.SUCCESS, location = "/WEB-INF/jsp/add.jsp"),
		@Result(name = ActionSupport.ERROR, location = "/WEB-INF/jsp/error.jsp")
	})
	@SkipValidation()	// 登録画面表示時はバリデーションを行わない
	public String show() {
		logger.trace("Enter");

		try {
			// ユーザプルダウンメニュー用のユーザリスト取得
			UserDAO userDAO = DAOFactory.createUserDAO();
			List<User> userList = userDAO.getUsers();
			session.put("users", userList);

			// 期限の初期値は現在の日付
			expireDate = new Date();

		} catch(DAOException e) {
			logger.error(e.getMessage());
			addActionError(getText("errors.add"));
			return ERROR;
		}

		logger.trace("Exit");
		return SUCCESS;
	}

	@Action(value = "add_action", results = {
		@Result(name = ActionSupport.SUCCESS, location = "list", type = "chain"),
		@Result(name = ActionSupport.INPUT, location = "/WEB-INF/jsp/add.jsp"),
		@Result(name = ActionSupport.ERROR, location = "/WEB-INF/jsp/error.jsp")
	})
	public String execute() {
		logger.trace("Enter");
		logger.debug("name = {}, userId = {}, expireDate = {}", name, userId, expireDate);

		// 入力用セッションパラメータを削除
		session.remove("users");

		try {
			// 作業登録処理
			boolean isSuccess =
					new AddActionLogic().execute(name, userId, expireDate);
			if (!isSuccess) {
				logger.error("作業項目登録失敗");

				// エラーメッセージをリクエスト属性に保存
				addActionError(getText("errors.add_action"));

				// エラーページへフォワード
				return ERROR;
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());

			// エラーメッセージをリクエスト属性に保存
			addActionError(getText("errors.add_action"));

			// エラーページへフォワード
			return ERROR;
		}

		logger.trace("Exit");
		return SUCCESS;
	}
}
