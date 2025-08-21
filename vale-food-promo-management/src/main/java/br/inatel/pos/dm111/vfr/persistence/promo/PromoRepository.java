package br.inatel.pos.dm111.vfr.persistence.promo;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface PromoRepository {
    List<Promo> getAll() throws ExecutionException, InterruptedException;

    Optional<Promo> getById(String id) throws ExecutionException, InterruptedException;
    Optional<Promo> getByUserId(String userId) throws ExecutionException, InterruptedException;
    Optional<Promo> getByCategory(String category);

    Promo save(Promo promo);

    void delete(String id) throws ExecutionException, InterruptedException;


}
