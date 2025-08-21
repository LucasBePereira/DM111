package br.inatel.pos.dm111.vfr.persistence.promo;

import com.google.cloud.firestore.Firestore;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Profile("test")
@Component
public class FirebasePromoRepositoryImpl implements PromoRepository {

    private static final String COLLECTION_NAME = "promos";

    private final Firestore firestore;

    public FirebasePromoRepositoryImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public List<Promo> getAll() throws ExecutionException, InterruptedException {
        return firestore.collection(COLLECTION_NAME)
                .get()
                .get()
                .getDocuments()
                .parallelStream()
                .map(doc -> doc.toObject(Promo.class))
                .toList();
    }

    @Override
    public Optional<Promo> getById(String id) throws ExecutionException, InterruptedException {
        var promo = firestore.collection(COLLECTION_NAME)
                .document(id)
                .get()
                .get()
                .toObject(Promo.class);

        return Optional.ofNullable(promo);
    }

    @Override
    public Optional<Promo> getByUserId(String userId) throws ExecutionException, InterruptedException {
        return firestore.collection(COLLECTION_NAME)
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(doc -> doc.toObject(Promo.class))
                .filter(promo -> promo.userId().equalsIgnoreCase(userId))
                .findFirst();
    }

    @Override
    public Optional<Promo> getByCategory(String category) {
        return Optional.empty();
    }


    @Override
    public Promo save(Promo promo) {
        firestore.collection(COLLECTION_NAME)
                .document(promo.id())
                .set(promo);
        return promo;
    }

    @Override
    public void delete(String id) throws ExecutionException, InterruptedException {
        firestore.collection(COLLECTION_NAME)
                .document(id)
                .delete()
                .get();
    }
}