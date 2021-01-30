package jp.co.miraino_katachi.todo.exceptions;

/**
 * アプリケーション独自の例外クラス
 * SQLException等、内部ロジックの例外を隠匿し、コントロール層とモデル層の結合を粗にする。
 */
public class DAOException extends Exception {
	public DAOException(String str) {
		super(str);
	}

	public DAOException(Throwable e) {
		super(e);
	}
}
