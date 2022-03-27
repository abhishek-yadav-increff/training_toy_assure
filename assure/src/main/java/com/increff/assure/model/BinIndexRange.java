package com.increff.assure.model;

import lombok.Getter;
import lombok.Setter;

/**
 * BinIndexRange
 */
@Setter
@Getter
public class BinIndexRange {
    private Long smIndex;
    private Long bgIndex;

    public BinIndexRange(Long smIndex, Long bgIndex) {
        this.smIndex = smIndex;
        this.bgIndex = bgIndex;
    }

    @Override
    public String toString() {
        return "BinIndexRange [bgIndex=" + bgIndex + ", smIndex=" + smIndex + "]";
    }
}
