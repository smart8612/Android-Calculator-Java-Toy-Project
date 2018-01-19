package com.example.smart8612.calculator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Created by smart8612 on 2018. 1. 19..
 */

public class MathEval {

    public static void main(String[] args) {
        String form = "(2+5)*3*(2+1)";

        double k = postfixEval(postfixChanger(form));

        System.out.println(k);
    }

    public static double postfixEval(String form) {
        Deque<Double> stack = new ArrayDeque<Double>();

        for (char chr : form.toCharArray()) {
            if (isCharDouble(chr)) {
                double tmpNum = Double.valueOf(String.valueOf(chr));
                stack.push(tmpNum);

            } else {
                double firstNum = stack.pop().doubleValue();
                double secondNum = stack.pop().doubleValue();

                stack.push(calcRE(firstNum, secondNum, chr));

            }
        }
        return stack.pop();

    }

    public static String postfixChanger(String form) {
        String Answer = "";

        ArrayList<Character> expList = new ArrayList<Character>();
        Deque<Character> optStack = new ArrayDeque<Character>();

        for (char chr : form.toCharArray()) {

            // isNumber or isOperator
            if (isCharDouble(chr)) {
                expList.add(chr);

            } else {
                if (chr == '(' || optStack.isEmpty()) {
                    optStack.push(chr);

                } else if (chr == ')') {
                    char opt = optStack.pop();

                    while (opt != '(') {
                        expList.add(opt);
                        opt = optStack.pop();
                    }

                // Is chr have high weight?
                } else if (getWeight(chr) > getWeight(optStack.peek())) {
                    optStack.push(chr);
                } else {
                    while (!optStack.isEmpty() && getWeight(chr) <= getWeight(optStack.peek())) {
                        expList.add(optStack.pop());

                    }
                    optStack.push(chr);
                }
            }
        }
        while (!optStack.isEmpty()) {
            expList.add(optStack.pop());
        }

        for (char k : expList) {
            Answer += k;
        }

        return Answer;
    }

    public static double calcRE(double num1, double num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;

            case '-':
                return num1 - num2;

            case '*':
                return num1 * num2;

            case '/':
                return num1 / num2;
        }
        return -1;
    }

    public static boolean isCharDouble(char s) {
        try {
            Double.parseDouble(s + "");
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Integer getWeight(char chr) {
        if (chr == '*' || chr == '/') {
            return 2;
        } else if (chr == '+' || chr == '-') {
            return 1;
        } else if (chr == '(') {
            return 0;
        } else {
            return -1;
        }
    }
}
