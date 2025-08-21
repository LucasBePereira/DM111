package br.inatel.pos.dm111.vfr.api.promo;

public record ProductRequest(String name,
                             String description,
                             String category,
                             float price) {
}
