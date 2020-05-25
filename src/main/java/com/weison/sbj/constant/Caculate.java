package com.weison.sbj.constant;

import com.weison.sbj.util.DataUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

public enum Caculate {
    LEVEL1((count) -> count == 1, () -> DataUtils.mockFloatBetween(3.00f, 4.00f)),
    LEVEL2((count) -> 2 <= count, () -> DataUtils.mockFloatBetween(1.00f, 3.00f));

    @Getter
    private Predicate<Long> predicate;

    @Getter
    private Supplier<Float> supplier;


    Caculate(Predicate<Long> predicate, Supplier<Float> supplier) {
        this.predicate = predicate;
        this.supplier = supplier;
    }

    public static Float getRedEnvelopeAmount(Long count) {

        return Arrays.stream(Caculate.values())
                .filter(caculate -> caculate.getPredicate().test(count))
                .findAny()
                .map(caculate -> caculate.getSupplier().get())
                .orElse(0.00f);
    }
}
