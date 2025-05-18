package cn.personalstudy.base.fanshe;

import java.util.ArrayList;
import java.util.List;

/**
 * 下界限定符
 * ? super T表示通配符类型必须是T类型或T的父类
 *
 * @author: qiaohaojie
 * @date: 2025-05-18 16:11:14
 */
public class SuperTest {

    public static void addIntegers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
    }

    public static void main(String[] args) {
        List<Number> numbers = new ArrayList<>();
        addIntegers(numbers);

        List<Object> objectList = new ArrayList<>();
        addIntegers(objectList);

        List<Double> doubleList = new ArrayList<>();
//        addIntegers(doubleList); // 编译错误
    }
}
