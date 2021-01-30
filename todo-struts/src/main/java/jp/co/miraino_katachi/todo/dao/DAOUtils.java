package jp.co.miraino_katachi.todo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.miraino_katachi.todo.exceptions.DAOException;

/**
 *
 * DAOパッケージのユーティリティクラス
 *
 */
public final class DAOUtils {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtils.class);
	private static DataSource ds = null;

	/**
	 * コンストラクタ
	 * クラスがインスタンス化できないよう、あえてprivateで定義する
	 */
	private DAOUtils() {
	}

	public static java.sql.Date sqlDate(java.util.Date date) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(cal.getTimeInMillis());
	}

	public static DataSource getDataSource() throws DAOException {
		/*
		 * コネクションプーリングするだけでなく、
		 * nameing サービスから DataSource を取得する処理も軽くないので
		 * DataSource もキャッシュする
		 * ※ DataSource はスレッドセーフ(のはず)なのでキャッシュしても問題ない
		 */
		if (ds == null) {
			try {
				Context context = new InitialContext();
				ds = (DataSource)context.lookup("java:comp/env/jdbc/mysql");
			} catch (NamingException e) {
				throw new DAOException(e);
			}
		}
		return ds;
	}

	public static void close(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
