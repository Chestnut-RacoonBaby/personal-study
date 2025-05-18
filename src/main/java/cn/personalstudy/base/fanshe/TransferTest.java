package cn.personalstudy.base.fanshe;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型类型转换使用
 *
 * @author: qiaohaojie
 * @date: 2025-05-18 16:18:49
 */
public class TransferTest {

    /**
     * 上界限定符：常用于协变场景，允许我们对泛型集合进行只读操作。读取T类或其子类，但是不能向其中添加对象。
     * 下界限定符：常用于逆变场景，允许我们对泛型集合进行写入操作。可以添加T类或其父类，但是不保证读取到的对象类型。
     *
     * @param args
     */
    public static void main(String[] args) {
        List<? extends Number> numberList = new ArrayList<>();
//        numberList.add(1); 编译错误，不能向上界类型中添加元素

        List<? super Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);

        Object object = integerList.get(0);
//        Integer num = integerList.get(1); 编译错误，不能保证一定读为Integer类型
    }
}
