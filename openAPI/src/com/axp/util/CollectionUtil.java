package com.axp.util;

import java.util.List;
import java.util.Set;

public class CollectionUtil {

    /**
     * 判断所给的id值，是否在所给的集合中；
     *
     * @param id
     * @param map
     * @return
     */
    public static Boolean isInMap(Integer id, Set<Integer> map) {
        //参数判断；
        if (id == null) {
            return false;
        }
        if (map.size() == 0) {
            return false;
        }
        //遍历集合，寻找id在不在集合中；
        for (Integer each : map) {
            if (id == each) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两个结合是否完全相同，包括顺序；
     *
     * @param list1
     * @param list2
     * @return
     */
    public static Boolean compareList(List<Integer> list1, List<Integer> list2) {

        //该方法被使用在了商品规格的对比上，商品可以没有规格，所以都为空或者长度为0时，也判为正确；
        if (list1 == null && (list2 == null || list2.size() == 0)
                || list1.size() == 0 && (list2 == null || list2.size() == 0)) {
            return true;
        }

        //长度判断；
        if (list1.size() != list2.size()) {
            return false;
        }

        //循环遍历；
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i) == null || list2.get(i) == null) {
                if (list1.get(i) != null || list2.get(i) != null) {
                    return false;
                }
                continue;
            }
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }

        return true;
    }
}
