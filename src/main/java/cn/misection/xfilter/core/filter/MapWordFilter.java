package cn.misection.xfilter.core.filter;

import cn.misection.xfilter.common.util.NullSafe;
import cn.misection.xfilter.core.constant.CoreString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName StateOwnedLegalPersonFilter
 * @Description TODO
 * @CreateTime 2021年06月11日 16:38:00
 */
public class MapWordFilter implements Filterable {

    private final List<List<String>> dataLineListRef;

    private final List<String> filterWordList;

    private final int preLineStartIndex;

    private final int safeSize;

    public MapWordFilter(List<List<String>> dataLineListRef,
                         List<String> filterWordList,
                         int preLineStartIndex,
                         int safeSize) {
        this.dataLineListRef = dataLineListRef;
        this.filterWordList = filterWordList;
        this.preLineStartIndex = preLineStartIndex;
        this.safeSize = safeSize;
    }

    public static MapWordFilter requireDefaultStart(
            List<List<String>> dataLineListRef,
            List<String> filterWordList,
            int safeSize) {
        return new MapWordFilter(dataLineListRef, filterWordList, 2, safeSize);
    }

    @Override
    public void filter() throws NumberFormatException {
        for (List<String> line : dataLineListRef) {
            sumPreLine(line);
        }
    }

    private void sumPreLine(List<String> line) throws NumberFormatException {
        if (line != null && line.size() < safeSize) {
            return;
        }
        Map<String, Double> filterWordMap = new HashMap<String, Double>(filterWordList.size()) {{
            put(CoreString.TOTAL_SUM_KEY.value(), Double.valueOf(0));
        }};
        for (String filterWord : filterWordList) {
            filterWordMap.put(filterWord, Double.valueOf(0));
        }
        Double totalSum = Double.valueOf(0);
        for (int i = preLineStartIndex; i < line.size(); i++) {
            if (line.size() == 3) {
                System.out.println("debug");
            }
            double value = Double.parseDouble(NullSafe.safeDoubleString(line.get(i + 1)));
            filterWordMap.put(CoreString.TOTAL_SUM_KEY.value(),
                    filterWordMap.get(CoreString.TOTAL_SUM_KEY.value()) + value);
            String curColumnName = line.get(i);
            curColumnName = NullSafe.safeString(curColumnName).trim();
            // 防止越界;
            if (filterWordMap.containsKey(curColumnName) && i != line.size() - 1) {
                filterWordMap.put(curColumnName, filterWordMap.get(curColumnName) + value);
            }
            // 这一行一定数字, 跳过;
            ++i;
        }
        filterWordMap.forEach(
                (key, value) -> {
                    line.add(key);
                    line.add(String.valueOf(value));
                }
        );
    }
}

