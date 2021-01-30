package jp.co.miraino_katachi.todo.actions;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

import jp.co.miraino_katachi.todo.dao.DAOFactory;
import jp.co.miraino_katachi.todo.dao.UserDAO;
import jp.co.miraino_katachi.todo.entity.User;
import jp.co.miraino_katachi.todo.exceptions.DAOException;

@InterceptorRefs({
	@InterceptorRef("defaultStack")
})
@Results({
	@Result(name = ActionSupport.SUCCESS, location = "list", type = "chain"),
	@Result(name = ActionSupport.INPUT, location = "/WEB-INF/jsp/login.jsp"),
	@Result(name = ActionSupport.ERROR, location = "/WEB-INF/jsp/error.jsp")
})
public class LoginAction extends ActionSupport implements SessionAware {
	private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);

	private Map<String, Object> session;

	private String username;
	private String password;

	public LoginAction() {
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	/*
	 * アノテーションによるバリデーションの場合、
	 * 属性名に'_'がついているとValidationできなかった
	 */
	@RequiredStringValidator(
			message = "${getText('errors.user.required')}")
	@StringLengthFieldValidator(
			minLength = "1",
			maxLength = "50",
			message = "${getText('errors.user.length')}")
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	@RequiredStringValidator(
			message = "${getText('errors.password.required')}")
	@RegexFieldValidator(
			regex = "\\p{Graph}+",
			message = "${getText('errors.password.regex')}")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	@Action("login_action")
	public String execute() {
		logger.trace("Enter");
		logger.info("user_id={}", username);

		try {
			// ユーザ情報の取得
			UserDAO userDAO = DAOFactory.createUserDAO();
			User user = userDAO.getUser(username, password);

			if (user == null) {
				logger.info("ログイン失敗");

				addActionError(getText("errors.login_action.input"));
				return INPUT;

			} else {
				logger.info("ログイン成功");

				// ログインユーザ情報をセッションに格納する
				session.put("currentUser", user);

				logger.trace("Exit");
				return SUCCESS;

			}
		} catch (DAOException e) {
			logger.error(e.getMessage());

			addActionError(getText("errors.login_action.exception"));
			return ERROR;
		}
	}
}
