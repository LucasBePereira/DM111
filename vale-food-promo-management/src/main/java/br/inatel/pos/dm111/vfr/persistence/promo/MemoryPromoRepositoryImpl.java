package br.inatel.pos.dm111.vfr.persistence.promo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Profile("local")
@Component
public class MemoryPromoRepositoryImpl implements PromoRepository {

    private Map<String, Promo> db = new HashMap<>();

    @Override
    public List<Promo> getAll() {
        return db.values().stream().toList();
    }

    @Override
    public Optional<Promo> getById(String id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public Optional<Promo> getByUserId(String userId) {
        return db.values().stream().filter(promo -> promo.userId().equals(userId)).findAny();
    }

    @Override
    public Optional<Promo> getByCategory(String category) {
        return Optional.empty();
    }

    @Override
    public Promo save(Promo promo) {
        return db.put(promo.id(),promo);
    }

    @Override
    public void delete(String id) {
        db.values().removeIf(promo -> promo.id().equals(id));
    }
}
