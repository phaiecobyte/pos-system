package com.phaiecobyte.pos.backend.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyUtil {
    public static BigDecimal formatRiel(BigDecimal amount) {
        return amount.setScale(0, RoundingMode.HALF_UP); // សម្រាប់លុយរៀលគ្មានក្បៀស
    }

    public static BigDecimal calculateTax(BigDecimal amount, BigDecimal taxRate) {
        return amount.multiply(taxRate).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }
}
