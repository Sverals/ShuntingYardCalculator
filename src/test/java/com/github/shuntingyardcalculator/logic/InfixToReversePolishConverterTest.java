package com.github.shuntingyardcalculator.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class InfixToReversePolishConverterTest {
    private InfixToReversePolishConverter converter;
    @BeforeEach
    void setUp() {
        converter = new InfixToReversePolishConverter();
    }
    //number grabbing
    @Test
    void getNumberAndRemoveReturnsProperlyAndRemoves() {
        StringBuilder sb = new StringBuilder("502+600");
        assertEquals("502", converter.getNumberAndRemove(sb));
        assertEquals("+600", sb.toString());
    }

    @Test
    void convertProcessesNumbers() {
        assertEquals("[502]", converter.convert("502+602"));
    }

    @Test
    void convertProcessesNumbersLeftParenthesis() {
        assertEquals("[500]", converter.convert("(500"));
    }

    @Test
    void convertProcessesLeftParenthesis() {
        converter.convert("(500");
        assertEquals("[(]", converter.getOperatorStack().toString());
    }
  
}