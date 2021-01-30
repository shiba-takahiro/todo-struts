package jp.co.miraino_katachi.todo.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ユーザ追加用のツール
 */
public class CreateUser {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			System.out.print("ユーザ名：");
			String user = br.readLine();
			System.out.print("パスワード：");
			String pass = br.readLine();
			System.out.print("姓：");
			String family = br.readLine();
			System.out.print("名：");
			String first = br.readLine();

			// パスワードをハッシュ化
			pass = PasswordUtil.getSafetyPassword(pass, user);

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/todo_sample", "todouser", "todouser");
			st = conn.prepareStatement(
					"INSERT INTO USERS (USER, PASS, FAMILY_NAME, FIRST_NAME) VALUES (?, ?, ?, ?)");
			st.setString(1, user);
			st.setString(2, pass);
			st.setString(3, family);
			st.setString(4, first);

			if(st.executeUpdate() != 1) {
				System.out.println("ユーザの追加に失敗しました");
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ユーザの追加に失敗しました");
		} finally {
			DAOUtils.close(conn, st, rs);
		}

	}

}
