package cn.personalstudy.base.fanshe;

import java.util.ArrayList;
import java.util.List;

/**
 * 协变和逆变：主要用于描述类型之间的兼容性关系。
 *
 * @author: qiaohaojie
 * @date: 2025-05-18 17:05:32
 */
public class XiebianAndNibianTest {

    /**
     * 协变：子类型可以替换父类型（派生类替换基类）。输出方向（比如方法的返回值）
     * 逆变：父类型可以替换子类型（基类替换派生类）。输入方向（比如方法的参数）
     *
     * @param args
     */
    public static void main(String[] args) {

        // 协变
        List<? extends Animals> animals;
        // 子类型Dog替换父类型Animals
        animals = new ArrayList<Dogs>();


        // 逆变
        List<? super Dogs> dogs;
        // 父类型Animals替换子类型Dog
        dogs = new ArrayList<Animals>();
    }
}
