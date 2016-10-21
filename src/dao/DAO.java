package dao;

import java.util.List;

public interface DAO<T, K> {
	T findOne(K id);
	List<T> findAll();
	void save(T entity);
	void delete(T entity);
}
