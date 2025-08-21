package br.inatel.pos.dm111.vfu.persistence.user;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface UserRepository {

    List<User> getAll() throws ExecutionException, InterruptedException;

    Optional<User> getById(String id) throws ExecutionException, InterruptedException;

    Optional<User> getByEmail(String email) throws ExecutionException, InterruptedException;

    User save(User user);

    void delete(String id) throws ExecutionException, InterruptedException;

}
