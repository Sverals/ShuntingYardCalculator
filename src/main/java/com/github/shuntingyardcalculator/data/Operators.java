package com.github.shuntingyardcalculator.data;

public enum Operators {
    POWER('^', 4, "RIGHT"),
    PLUS('+', 2, "LEFT"),
    MINUS('-', 2, "LEFT"),
    MULTIPLY('x', 3, "LEFT"),
    DIVIDE('÷', 3, "LEFT"),
    LEFTPARENTHESIS('('),
    RIGHTPARENTHESIS(')');

    private final char type;
    private final int precedence;
    private final String associativity;

    Operators(char type) {
        this.type = type;
        this.precedence = 5;
        this.associativity = "";
    }
    Operators(char type, int precedence, String associativity) {
        this.type = type;
        this.precedence = precedence;
        this.associativity = associativity;
    }

    public char getCharVersion() {
        return type;
    }

    public int getPrecedence() {
        return precedence;
    }

    public String getAssociativity() {
        return associativity;
    }

    public char getType() {
        return type;
    }





    public String getStringVersion() {
        return String.valueOf(getCharVersion());
    }
}
