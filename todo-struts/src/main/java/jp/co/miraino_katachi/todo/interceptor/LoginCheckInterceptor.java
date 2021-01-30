package jp.co.miraino_katachi.todo.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginCheckInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();

		if (session.get("currentUser") == null) {
			return ActionSupport.LOGIN;
		} else {
			return invocation.invoke();
		}
	}

}
