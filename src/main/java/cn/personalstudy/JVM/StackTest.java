package cn.personalstudy.JVM;

import java.util.Stack;

/**
 * 使用栈实现一个计算器功能
 *
 * @author: qiaohaojie
 * @date: 2024-12-11 22:31:28
 */
public class StackTest {

    public static void main(String[] args) {
        String expression = "2 + 5 / 3 * (6 - 2)";
        double result = evaluate(expression);
        System.out.println("Result: " + result);
    }


    public static double evaluate(String expression) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // 判断是否为空格，空格跳过
            if (Character.isWhitespace(ch)) {
                continue;
            }

            // 判断是否为数字
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                operandStack.push(Double.parseDouble(sb.toString()));
                i--; // 因为循环结束时会多加一次，这里减回去
            } else if (ch == '(') {
                operatorStack.push(ch);
            } else if (ch == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    operandStack.push(applyOperator(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
                }
                operatorStack.pop(); // 弹出左括号
            }
            // 判断是否为运算符
            else if (isOperator(ch)) {
                // operatorStack.peek() 查看栈顶元素
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(ch)) {
                    operandStack.push(applyOperator(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
                }
                operatorStack.push(ch);
            }
        }

        while (!operatorStack.isEmpty()) {
            operandStack.push(applyOperator(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
        }

        return operandStack.pop();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    /**
     * 这个方法用来判断执行的优先级
     *
     * @param op op操作符
     * @return
     */
    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private static double applyOperator(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return a / b;
        }
        return 0;
    }
}
