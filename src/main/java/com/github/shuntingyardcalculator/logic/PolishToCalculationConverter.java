package com.github.shuntingyardcalculator.logic;

import com.github.shuntingyardcalculator.data.Operators;
import com.github.shuntingyardcalculator.data.Stack;

import java.util.ArrayList;

public class PolishToCalculationConverter {



    public static double calculateValue(ArrayList<String> reversePolishNotationString) {
        if (reversePolishNotationString.size() == 1) {
            if (reversePolishNotationString.getFirst().charAt(0) == '√') {

                return Math.sqrt(Double.parseDouble(reversePolishNotationString.getFirst().substring(1)));
            }
        }
        Stack numberStack = new Stack();
        convertAllSqrt(reversePolishNotationString);
        for (String s : reversePolishNotationString) {

            if (Character.isDigit(s.charAt(0)) || s.length() > 1) {
                numberStack.pushToTop(s);
            }

            if (InfixToReversePolishConverter.isOperator(s.charAt(0)) && s.length() == 1) {
                String num1 = numberStack.getTopAndPop();
                String num2 = numberStack.getTopAndPop();
                numberStack.pushToTop(String.valueOf(operatorToValue(num1, num2, s)));
            }
        }
        return Double.parseDouble(numberStack.getTopAndPop());
    }


    public static double operatorToValue(String num1, String num2, String operator) {
        Operators currentOperation = InfixToReversePolishConverter.convertStringToOperator(operator.charAt(0));
        if (currentOperation == Operators.PLUS) {
            return Double.parseDouble(num1) + Double.parseDouble(num2);
        }
        if (currentOperation == Operators.MINUS) {
            return Double.parseDouble(num2) - Double.parseDouble(num1);
        }
        if (currentOperation == Operators.MULTIPLY) {
            return Double.parseDouble(num1) * Double.parseDouble(num2);
        }
        if (currentOperation == Operators.DIVIDE) {
            return Double.parseDouble(num2) / Double.parseDouble(num1);
        }
        if (currentOperation == Operators.POWER) {
            return Math.pow(Double.parseDouble(num2), Double.parseDouble(num1));
        }

        throw new IllegalArgumentException("Unknown operator: " + operator);
    }

    public static void convertAllSqrt(ArrayList<String> reversePolishNotationString) {
        for (int i = 0; i < reversePolishNotationString.size(); i++) {
            String currentValue = reversePolishNotationString.get(i);
            if (currentValue.charAt(0) == '√') {
                currentValue = currentValue.substring(1);
                double sqrt = Math.sqrt(Double.parseDouble(currentValue));
                reversePolishNotationString.set(i, String.valueOf(sqrt));
            }
        }
    }
}
