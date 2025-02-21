package repository;

public interface Repository <T> {
    T findById(String id);
}
