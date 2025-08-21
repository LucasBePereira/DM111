package br.inatel.pos.dm111.vfr.api.promo;

import java.util.List;

public record PromoResponse(String id,
                                 String userId,
                                 List<String> categories,
                                 List<ProductResponse> products) {

}
