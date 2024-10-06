package com.github.shuntingyardcalculator.data;

import java.util.ArrayList;

public class Stack {
    ArrayList<String> stack;

    public Stack() {
        this.stack = new ArrayList<>();
    }

    public String getTopAndPop() {
        String first = this.getTopValue();
        stack.removeFirst();
        return first;
    }

    public void popTop() {
        stack.removeFirst();
    }

    public String getTopValue() {
        return stack.getFirst();
    }

    public void pushToTop(String value) {
        this.stack.addFirst(value);
    }

    public ArrayList<String> getStack() {
        return stack;
    }

    public void setStack(ArrayList<String> stack) {
        this.stack = stack;
    }
}
