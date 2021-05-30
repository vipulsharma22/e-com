package com.nitsoft.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;

@Log4j2
public class TransformUtil {

    private TransformUtil() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Returns a JSON {@link String} from the <strong>obj</strong> provided using {@link ObjectMapper}
     *
     * @param obj - {@link Object}
     * @return {@link String} - JSON string
     */
    public static String toJson(Object obj) {
        try {
            if (obj != null) {
                return objectMapper.writeValueAsString(obj);
            }
        } catch (JsonProcessingException e) {
            log.error("Error in toJson(), obj: " + obj + " ; Exception: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Returns the parsed {@link Object} from the {@link String jsonString} provided using
     * {@link ObjectMapper} - will need a type cast
     *
     * @param jsonString - {@link String}
     * @param valueType  - {@link Class}
     * @return {@link Object}
     */
    public static <T> T fromJson(String jsonString, Class<T> valueType) {
        try {
            if (jsonString != null) {
                return objectMapper.readValue(jsonString, valueType);
            }
        } catch (Exception e) {
            log.error(
                    "Error in fromJson(), jsonString: " + jsonString + " ; Exception: " + e.getMessage(), e);
        }
        return null;
    }

    public static <T> T fromObject(Object fromValue, Class<T> valueType) {
        try {
            if (fromValue != null) {
                return objectMapper.convertValue(fromValue, valueType);
            }
        } catch (Exception e) {
            log.error(
                    "Error in fromJson(), fromValue: " + fromValue + " ; Exception: " + e.getMessage(), e);
        }
        return null;
    }

    public static String getParamString(Map<Object, Object> params) {
        List<String> paramStrings = new ArrayList<>();
        for (Map.Entry<Object, Object> param : params.entrySet()) {
            if (param.getValue() instanceof Collection && CollectionUtils.isNotEmpty((Collection<?>) param.getValue())) {
                paramStrings.add(param.getKey() + "=" + StringUtils.join((Collection<?>) param.getValue(), ','));
            } else if (param.getValue() instanceof String && StringUtils.isNotEmpty((String) param.getValue())) {
                paramStrings.add(param.getKey() + "=" + param.getValue());
            } else if (null != param.getValue()) {
                paramStrings.add(param.getKey() + "=" + param.getValue());
            }
        }
        return StringUtils.join(paramStrings, '&');
    }

    public static <T> List<List<T>> inBatches(List<T> list, int batchSize, T fillValue) {
        return inBatches(list, batchSize, fillValue, null);
    }

    public static <T> List<List<T>> inBatches(List<T> list, int batchSize) {
        return inBatches(list, batchSize, null);
    }

    /**
     * @param list                    {@link List} of {@link T}
     * @param batchSize               - {@link int}
     * @param fillValue               - {@link T}
     * @param ascendingfillBatchSizes - {@link int[]}
     * @return {@link List}<{@link List}<{@link T}>> - {@link List} of {@link List} of {@link T}, in
     * which every batch will have <strong>batchSize</strong> elements, the last one might
     * have less than <strong>batchSize</strong> or if {@link T fillValue} is not NULL, it
     * fills with it till the nearest limit in <strong>ascendingfillBatchSizes</strong> if
     * it's NOT NULL or <strong>batchSize</strong> otherwise
     */
    public static <T> List<List<T>> inBatches(List<T> list, int batchSize, T fillValue, int[] ascendingfillBatchSizes) {
        if (list == null) {
            return null;
        }
        if (batchSize < 1) {
            batchSize = 100;
        }
        List<List<T>> batches = new ArrayList<List<T>>();
        int start = 0;
        while (start < list.size()) {
            int end = Math.min(start + batchSize, list.size());
            batches.add(list.subList(start, end));
            start = end;
        }
        if (fillValue != null && batches.size() > 0) {
            List<T> lastBatch = batches.get(batches.size() - 1);
            if (ascendingfillBatchSizes == null || ascendingfillBatchSizes.length == 0) {
                ascendingfillBatchSizes = new int[] { batchSize };
            }
            int lastBatchSize = lastBatch.size();
            for (int j = 0; j < ascendingfillBatchSizes.length; ++j) {
                int fillBatchSize = ascendingfillBatchSizes[j];
                if (lastBatchSize == fillBatchSize) {
                    break;
                } else if (lastBatchSize < fillBatchSize) {
                    List<T> fillList = new ArrayList<T>();
                    int remainingSize = fillBatchSize - lastBatchSize;
                    for (int i = 0; i < remainingSize; ++i) {
                        fillList.add(fillValue);
                    }
                    List<T> filledLastBatch = new ArrayList<T>();
                    filledLastBatch.addAll(lastBatch);
                    filledLastBatch.addAll(fillList);
                    batches.set(batches.size() - 1, filledLastBatch);
                    break;
                }
            }
        }
        return batches;
    }

    /**
     * Indexes a list into map
     *
     * @param list     - {@link List}<{@link U}>
     * @param fun to get key - {@link Function} with input type of {@link T} and output type of
     *                 {@link U}
     * @return {@link Map}<{@link T}, {@link U}>
     */
    public static <T, U> Map<T, U> index(List<U> list, Function<U, T> fun) {
        Map<T, U> map = new HashMap<>();
        list.forEach(item -> {
            T key = fun.apply(item);
            map.put(key, item);
        });
        return map;
    }

    /**
     * Indexes a list into map
     *
     * @param list     - {@link List}<{@link U}>
     * @param keyFun to get key - {@link Function} with input type of {@link T} and output type of
     *                 {@link U}
     * @param valFun to get value - {@link Function} with input type of {@link T} and output type of
     *                 *                 {@link V}
     * @return {@link Map}<{@link U}, {@link V}>
     */
    public static <T, U, V> Map<U, V> index(Collection<T> list, Function<T, U> keyFun, Function<T, V> valFun) {
        Map<U, V> map = new HashMap<>();
        list.forEach(item -> {
            U key = keyFun.apply(item);
            if (key == null) {
                return;
            }
            V value = valFun.apply(item);
            map.put(key, value);
        });
        return map;
    }

    /**
     * Round the given decimal value to gven decimalCount
     *
     * @param value        the value
     * @param decimalCount the decimal count( should be > 0 )
     * @return the double
     */
    public static Double round(Double value, int decimalCount) {
        String pattern = "#." + StringUtils.repeat("#",decimalCount);
        DecimalFormat df = new DecimalFormat(pattern);
        return Double.valueOf(df.format(value));
    }

    public static <T, U> List<T> extract(List<U> list, Function<U, T> fun) {
        if (CollectionUtils.isEmpty(list))
            return Collections.emptyList();
        List<T> resList = new ArrayList<>();
        list.forEach(item -> {
            T extract = fun.apply(item);
            resList.add(extract);
        });
        return resList;
    }

    public static String encode(String value, String key) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < value.length(); i++) {
            sb.append((char)(value.charAt(i) ^ key.charAt(i % key.length())));
        }
        String result = sb.toString();
        try {
            result = Base64.getEncoder().encodeToString(result.getBytes());
        } catch (Exception e) {
//            throw new Exception();
        }
        return result;
    }
}
