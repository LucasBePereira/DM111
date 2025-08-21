package br.inatel.pos.dm111.vfr.api.promo;

import java.util.List;

public record PromoRequest(String userId,
                                List<String> categories,
                                List<ProductRequest> products) {

}
