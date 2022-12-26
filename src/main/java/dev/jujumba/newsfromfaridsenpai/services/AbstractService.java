package dev.jujumba.newsfromfaridsenpai.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author Jujumba
 */
@Service
@AllArgsConstructor
@Scope("prototype")
@Getter
public class AbstractService<T> {
    protected final JpaRepository<T, Integer> repository;

    public List<T> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void save(T t) {
        repository.save(t);
    }

    @Transactional
    public void saveAll(Collection<T> collection) {
        repository.saveAll(collection);
    }
    @Transactional
    public void delete(T t) {
        repository.delete(t);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public boolean exists(int id) {
        return repository.existsById(id);
    }
}
