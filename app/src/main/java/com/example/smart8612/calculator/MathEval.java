package com.example.smart8612.calculator;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;
import java.math.BigDecimal;

/**
 * Created by smart8612 on 2018. 1. 19..
 */

public class MathEval {

    public static void main(String[] args) {
        // Math Evaluation Test Method

        String[] form = {
                "5/3"

        };

        for (String str : form) {
            Double k = postfixEval(str);

            System.out.println(k);
        }
    }

    public static double postfixEval(String orgExp) {
        try {
            Object[] postfixExp = convertToPostfix(orgExp).toArray();

            Stack stack = new Stack();

            for (Object exp : postfixExp) {
                String strExp = String.valueOf(exp);

                if (isNumber(strExp)) {
                    double tmpNum = Double.valueOf(strExp);
                    stack.push(tmpNum);

                } else {
                    double firstNum = Double.parseDouble(stack.pop().toString());
                    double secondNum = Double.parseDouble(stack.pop().toString());
                    char operator = exp.toString().charAt(0);

                    stack.push(calcRE(firstNum, secondNum, operator));

                }
            }

            return Double.parseDouble(stack.pop().toString());

        } catch (EmptyStackException e) {
            return 0.0;

        } catch (ArithmeticException e) {
            return -1;
        }
    }

    static Stack convertToPostfix(String orgExp) {
        try {
            Stack Answer = new Stack();

            ArrayList<String> expList = new ArrayList<String>();
            Stack optStack = new Stack();

            StringTokenizer st = new StringTokenizer(orgExp, "+-*/()", true);

            while (st.hasMoreTokens()) {
                String tmpExp = st.nextToken();

                if (isNumber(tmpExp)) {
                    expList.add(tmpExp);

                } else {
                    if (tmpExp.equals("(") || optStack.isEmpty()) {
                        optStack.push(tmpExp);

                    } else if (tmpExp.equals(")")) {
                        String opt = optStack.pop().toString();
                        while (!opt.equals("(")) {
                            expList.add(opt);
                            opt = optStack.pop().toString();
                        }

                        // Is expression(operator) have relatively high weight?
                    } else if (getWeight(tmpExp) > getWeight(optStack.peek().toString())) {
                        optStack.push(tmpExp);

                    } else {
                        while (!optStack.isEmpty() && getWeight(tmpExp) <= getWeight(optStack.peek().toString())) {
                            expList.add(optStack.pop().toString());

                        }
                        optStack.push(tmpExp);
                    }
                }
            }
            while (!optStack.isEmpty()) {
                expList.add(optStack.pop().toString());
            }

            for (String exp : expList) {
                Answer.push(exp);
            }

            return Answer;

        } catch (EmptyStackException e) {
            return new Stack();
        }
    }

    private static double calcRE(double num1, double num2, char operator) {
        BigDecimal preNum = new BigDecimal(num1);
        BigDecimal postNum = new BigDecimal(num2);

        switch (operator) {
            case '+':
                return preNum.add(postNum).doubleValue();

            case '-':
                return postNum.subtract(preNum).doubleValue();

            case '*':
                return preNum.multiply(postNum).doubleValue();

            case '/':
                return postNum.divide(preNum, 16, BigDecimal.ROUND_CEILING).doubleValue();
        }
        return -1;
    }

    private static boolean isNumber(String exp) {
        try {
            Double.parseDouble(exp);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static Integer getWeight(String exp) {
        switch (exp) {
            case "*":
                return 2;
            case "/":
                return 2;

            case "+":
                return 1;
            case "-":
                return 1;

            case "(":
                return 0;

            default:
                return -1;
        }
    }
}
