package cn.personalstudy.base.fanshe;

import java.util.ArrayList;
import java.util.List;

/**
 * 上界限定符
 * ? extends T 表示通配符类型必须是T类型或T的子类
 *
 * @author: qiaohaojie
 * @date: 2025-05-18 15:52:53
 */
public class ExtendTest {

    /**
     * number的子类：byte、short、int、long、float、double
     *
     * @param list
     * @param <T>
     */
    public static <T extends Number> void printNumbers(List<T> list) {
        for (T num : list) {
            System.out.println(num);
        }
    }

    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);

        List<Double> doubleList = new ArrayList<>();
        doubleList.add(3.14);
        doubleList.add(4.56);

        // Integer和Double都是Number的子类，可以使用泛型参数T
        printNumbers(integerList);
        printNumbers(doubleList);

        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("World");
//        printNumbers(stringList);
    }
}
