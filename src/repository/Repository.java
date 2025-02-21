package repository;

import java.util.List;

public interface Repository <T> {
    T findById(String id);
    List<T> findAll(Pagination pagination, Order order);
    T deleteById(String id);
    T create(T id);
    T update(T id);
    T crupdate(T id);
}
