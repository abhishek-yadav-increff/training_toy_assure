package com.increff.assure.dto.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.increff.assure.util.StringUtil;

/**
 * utils
 */
public class CommonsHelper {

    public static String normalize(String s) {
        if (s != null)
            return StringUtil.toLowerCase(s).trim();
        return null;
    }

    public static Double normalize(Double d) {
        if (d != null) {
            BigDecimal bigDecimal = new BigDecimal(d).setScale(2, RoundingMode.HALF_DOWN);
            Double scaledDouble = bigDecimal.doubleValue();
            return scaledDouble;
        }
        return null;
    }

    public static String doubleToString(Double d) {
        if (d != null)
            return String.format("%.2f", d);
        return null;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static <T> List<T> distinctList(List<T> list, Function<? super T, ?>... keyExtractors) {
        return list.stream().filter(distinctByKeys(keyExtractors)).collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();
        return t -> {
            final List<?> keys = Arrays.stream(keyExtractors).map(ke -> ke.apply(t))
                    .collect(Collectors.toList());
            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
