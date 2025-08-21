package br.inatel.pos.dm111.vfr.persistence.restaurant;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface RestaurantRepository {
    List<Restaurant> getAll() throws ExecutionException, InterruptedException;

    Optional<Restaurant> getById(String id) throws ExecutionException, InterruptedException;
    Optional<Restaurant> getByUserId(String userId) throws ExecutionException, InterruptedException;

    Restaurant save(Restaurant restaurant);

    void delete(String id) throws ExecutionException, InterruptedException;
}
