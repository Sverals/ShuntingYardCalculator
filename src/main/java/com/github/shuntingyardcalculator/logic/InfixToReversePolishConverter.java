package com.github.shuntingyardcalculator.logic;

import com.github.shuntingyardcalculator.data.Operators;
import com.github.shuntingyardcalculator.data.Stack;

import java.util.ArrayList;

public class InfixToReversePolishConverter {
    private final ArrayList<String> outputString;
    private final Stack operatorStack;

    public InfixToReversePolishConverter() {
        this.outputString = new ArrayList<>();
        this.operatorStack = new Stack();
    }

    public String convert(String inFixString) {
        StringBuilder inFixStringBuilder = new StringBuilder(inFixString);
        while (!inFixStringBuilder.isEmpty()) {
            char currentChar = inFixStringBuilder.charAt(0);
            if (Character.isDigit(currentChar)) { //Step 1: output operand if symbol
                String numberFound = this.getNumberAndRemove(inFixStringBuilder);
                if (!numberFound.isEmpty()) {
                    this.outputString.add(numberFound);
                }
            } else if (currentChar == Operators.LEFTPARENTHESIS.getCharVersion()) { //Step 2: push left parenthesis to stack
                operatorStack.pushToTop(String.valueOf(currentChar));
                inFixStringBuilder.deleteCharAt(0);
            } else if (currentChar == Operators.RIGHTPARENTHESIS.getCharVersion()) { // Step 3:If the incoming symbol is a right parenthesis: discard the right parenthesis, pop and print the stack symbols until you see a left parenthesis. Pop the left parenthesis and discard it.
                inFixStringBuilder.deleteCharAt(0); //discard right parenthesis
                System.out.println(inFixStringBuilder);
                this.processRightParenthesis();
            } else if (isOperator(currentChar) && (this.operatorStack.getStack().isEmpty() || this.operatorStack.getTopValue().equals(Operators.LEFTPARENTHESIS.getStringVersion()))) { //step 4: If the incoming symbol is an operator and the stack is empty or contains a left parenthesis on top, push the incoming operator onto the stack.
                this.operatorStack.pushToTop(String.valueOf(currentChar));
                inFixStringBuilder.deleteCharAt(0);
            } else if (isOperator(currentChar)) {
                Operators currOp = this.convertStringToOperator(currentChar);
                Operators topOp = this.convertStringToOperator(this.operatorStack.getTopValue().charAt(0));
                int currOpPres = currOp.getPrecedence();
                int topOpPres = topOp.getPrecedence();

                if ((currOpPres > topOpPres) ||
                        ((currOpPres == topOpPres) && currOp.getAssociativity().equals("RIGHT"))  ||
                        (this.operatorStack.getStack().isEmpty()) ||
                        (this.operatorStack.getTopValue().equals(Operators.LEFTPARENTHESIS.getStringVersion()))) {

                    this.operatorStack.pushToTop(String.valueOf(currentChar));
                    inFixStringBuilder.deleteCharAt(0);
                    continue;
                }
                while (!this.operatorStack.getStack().isEmpty()) {
                    if ((currOpPres < topOpPres) ||
                       ((currOpPres == topOpPres) && currOp.getAssociativity().equals("LEFT"))) {
                            topOp = this.convertStringToOperator(this.operatorStack.getTopAndPop().charAt(0));
                            this.outputString.add(String.valueOf(topOp.getType()));
                            topOpPres = topOp.getPrecedence();
                    } else {
                        this.outputString.add(String.valueOf(currentChar));
                        inFixStringBuilder.deleteCharAt(0);
                        break;
                    }
                }
            }

        }

        if (!this.operatorStack.getStack().isEmpty()) {
            outputString.addAll(this.operatorStack.getStack());
        }
        return outputString.toString();
    }


    public Operators convertStringToOperator(char input) {
        for (Operators operator : Operators.values()) {
            if (operator.getCharVersion() == input) {
                return operator;
            }
        }
        return null;
    }


    public static boolean isOperator(char operator) {
        for (Operators currentOperator : Operators.values()) {
            if (operator == currentOperator.getCharVersion()) {
                return true;
            }
        }
        return false;
    }
    public void processRightParenthesis() {
        System.out.println(this.operatorStack.getStack());
            while (this.operatorStack.getTopValue().charAt(0) != Operators.LEFTPARENTHESIS.getCharVersion()) { //loop until left parenthesis

                String currentOperator = this.operatorStack.getTopAndPop(); //adds operators until left parenthesis
                this.outputString.add(currentOperator);
                if (this.operatorStack.getStack().isEmpty()) {
                    break;
                }
            }
            if (!this.operatorStack.getStack().isEmpty()) {
                if (this.operatorStack.getTopValue().equals(Operators.LEFTPARENTHESIS.getStringVersion())) {   //removes leftParenthesis and discards
                    this.operatorStack.popTop();
                }
            }

    }

    public String getNumberAndRemove(StringBuilder input) {
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if ((!Character.isDigit(currentChar) && currentChar != '.')) {
                String toReturn = input.substring(0, i);
                input.replace(0, i, "");
                return toReturn;
            }
        }
        outputString.add(input.toString());
        input.replace(0, input.length(), "");
        return input.toString();
    }

    public Stack getOperatorStack() {
        return operatorStack;
    }


}
