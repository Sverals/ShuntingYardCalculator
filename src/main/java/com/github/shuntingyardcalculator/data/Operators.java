package com.github.shuntingyardcalculator.data;

public enum Operators {
    PLUS('+'),
    MINUS('-'),
    MULTIPLY('x'),
    DIVIDE('÷'),
    LEFTPARENTHESIS('('),
    RIGHTPARENTHESIS(')');

    private final char type;

    Operators(char type) {
        this.type = type;
    }

    public char getCharVersion() {
        return type;
    }

    public boolean isOperator(char operator) {
        for (Operators currentOperator : Operators.values()) {
            if (operator == currentOperator.getCharVersion()) {
                return true;
            }
        }
        return false;
    }



    public String getStringVersion() {
        return String.valueOf(getCharVersion());
    }
}
