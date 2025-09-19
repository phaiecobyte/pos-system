package com.phaiecobyte.possystem.payload.res;

public record ItemRes(
     long id,
     long categoryId,
     String name,
     double priceIn,
     double priceOut,
     int stock,
     String description
) {
}
