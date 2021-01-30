package jp.co.miraino_katachi.todo.dao;

import java.util.List;

import jp.co.miraino_katachi.todo.entity.Item;
import jp.co.miraino_katachi.todo.exceptions.DAOException;

/**
 * 作業項目用DAOインタフェース
 *
 */
public interface ItemDAO {

	/**
	 * 登録されている作業をすべて取得する
	 *
	 * @return 全作業のArrayList
	 * @throws DAOException
	 */
	public List<Item> getItemList() throws DAOException;

	/**
	 * 指定した ID の作業項目を取得する
	 *
	 * @param id 項目ID
	 * @return ID に該当する Item
	 * @throws DAOException
	 */
	public Item getItem(int id) throws DAOException;

	/**
	 * 検索キーワードを含む作業を取得する
	 *
	 * @param keyword 検索キーワード
	 * @return キーワードを含む作業のArrayList
	 * @throws DAOException
	 */
	public List<Item> getItemListByKeyword(String keyword) throws DAOException;

	/**
	 * 作業項目を追加する
	 *
	 * @param item 作業項目
	 * @return 追加できたら true
	 * @throws DAOException
	 */
	public boolean add(Item item) throws DAOException;

	/**
	 * 作業項目を更新する
	 *
	 * @param item 更新したい作業項目
	 * @return 更新できたら true
	 * @throws DAOException
	 */
	public boolean update(Item item) throws DAOException;

	/**
	 * 作業項目を削除する
	 *
	 * @param id 項目ID
	 * @return 削除できたら true
	 * @throws DAOException
	 */
	public boolean delete(int id) throws DAOException;

}
