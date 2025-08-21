package br.inatel.pos.dm111.vfr.api.promo.service;

import br.inatel.pos.dm111.vfr.api.core.ApiException;
import br.inatel.pos.dm111.vfr.api.core.AppErrorCode;
import br.inatel.pos.dm111.vfr.api.promo.ProductRequest;
import br.inatel.pos.dm111.vfr.api.promo.ProductResponse;
import br.inatel.pos.dm111.vfr.api.promo.PromoRequest;
import br.inatel.pos.dm111.vfr.api.promo.PromoResponse;
import br.inatel.pos.dm111.vfr.persistence.promo.Product;
import br.inatel.pos.dm111.vfr.persistence.promo.Promo;
import br.inatel.pos.dm111.vfr.persistence.promo.PromoRepository;
import br.inatel.pos.dm111.vfr.persistence.user.User;
import br.inatel.pos.dm111.vfr.persistence.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class PromoService {

    private static final Logger log = LoggerFactory.getLogger(PromoService.class);

    private final PromoRepository promoRepository;
    private final UserRepository userRepository;

    public PromoService(PromoRepository repository, UserRepository userRepository) {
        this.promoRepository = repository;
        this.userRepository = userRepository;
    }

    public List<PromoResponse> searchPromos() throws ApiException {
        return retrievePromos().stream()
                .map(this::buildPromoResponse)
                .toList();
    }

    public PromoResponse searchPromo(String id) throws ApiException {
        return retrievePromoById(id)
                .map(this::buildPromoResponse)
                .orElseThrow(() -> {
                    log.warn("Promotion was not found. Id: {}", id);
                    return new ApiException(AppErrorCode.RESTAURANT_NOT_FOUND);
                });
    }

    public PromoResponse createPromo(PromoRequest request) throws ApiException {
        // validate user exist and its type is RESTAURANT
        validatePromo(request);

        var promo = buildPromo(request);
        promoRepository.save(promo);
        log.info("Promotion was successfully created. Id: {}", promo.id());

        return buildPromoResponse(promo);
    }

    public PromoResponse updatePromo(PromoRequest request, String id) throws ApiException {
        // check promo by id exist
        var promoOpt = retrievePromoById(id);

        if (promoOpt.isEmpty()) {
            log.warn("Promotion was not found. Id: {}", id);
            throw new ApiException(AppErrorCode.RESTAURANT_NOT_FOUND);
        } else {
            var promo = promoOpt.get();

            validatePromo(request);

            var updatedPromo = buildPromo(request, promo.id());
            promoRepository.save(updatedPromo);
            log.info("Promotion was successfully updated. Id: {}", promo.id());

            return buildPromoResponse(updatedPromo);
        }
    }

    public void removePromo(String id) throws ApiException {
        var promoOpt = retrievePromoById(id);
        if (promoOpt.isPresent()) {
            try {
                promoRepository.delete(id);
            } catch (ExecutionException | InterruptedException e) {
                log.error("Failed to delete a promotion from DB by id {}.", id, e);
                throw new ApiException(AppErrorCode.INTERNAL_DATABASE_COMMUNICATION_ERROR);
            }
        } else {
            log.info("The provided promotion id was not found. id: {}", id);
        }

    }

    private void validatePromo(PromoRequest request) throws ApiException {
        var userOpt = retrieveUserById(request.userId());

        if (userOpt.isEmpty()) {
            log.warn("User was not found. Id: {}", request.userId());
            throw new ApiException(AppErrorCode.USER_NOT_FOUND);
        } else {
            var user = userOpt.get();
            if (!User.UserType.RESTAURANT.equals(user.type())) {
                log.info("User provided is not valid for this operation. UserId: {}", request.userId());
                throw new ApiException(AppErrorCode.INVALID_USER_TYPE);
            }
        }
    }

    private Promo buildPromo(PromoRequest request) {
        var id = UUID.randomUUID().toString();
        return buildPromo(request, id);
    }

    private Promo buildPromo(PromoRequest request, String id) {
        var products = request.products().stream()
                .map(this::buildProduct)
                .toList();

        return new Promo(id,
                request.userId(),
                request.categories(),
                products);
    }

    private Product buildProduct(ProductRequest request) {
        var id = UUID.randomUUID().toString();

        return new Product(id,
                request.name(),
                request.description(),
                request.category(),
                request.price());
    }

    private PromoResponse buildPromoResponse(Promo promo) {
        var products = promo.products().stream()
                .map(this::buildProductResponse)
                .toList();

        return new PromoResponse(promo.id(),
                promo.userId(),
                promo.categories(),
                products);
    }

    private ProductResponse buildProductResponse(Product product) {
        return new ProductResponse(product.id(),
                product.category(),
                product.price());
    }

    private List<Promo> retrievePromos() throws ApiException {
        try {
            return promoRepository.getAll();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Failed to read all promotion from DB.", e);
            throw new ApiException(AppErrorCode.INTERNAL_DATABASE_COMMUNICATION_ERROR);
        }
    }

    private Optional<Promo> retrievePromoById(String id) throws ApiException {
        try {
            return promoRepository.getById(id);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Failed to read a promotion from DB by id {}.", id, e);
            throw new ApiException(AppErrorCode.INTERNAL_DATABASE_COMMUNICATION_ERROR);
        }
    }

    private Optional<User> retrieveUserById(String id) throws ApiException {
        try {
            return userRepository.getById(id);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Failed to read an user from DB by id {}.", id, e);
            throw new ApiException(AppErrorCode.INTERNAL_DATABASE_COMMUNICATION_ERROR);
        }
    }
}
