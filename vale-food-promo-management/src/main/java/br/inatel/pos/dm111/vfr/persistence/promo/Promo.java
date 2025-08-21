package br.inatel.pos.dm111.vfr.persistence.promo;

import java.util.List;

public record Promo(String id,
                         String userId,
                         List<String> categories,
                         List<Product> products) {
}
