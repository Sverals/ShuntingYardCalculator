package com.github.shuntingyardcalculator.ui;

import com.github.shuntingyardcalculator.logic.InfixToReversePolishConverter;
import com.github.shuntingyardcalculator.logic.PolishToCalculationConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {
   // public static final Calculator = new Calculator();
    @FXML
    private GridPane inputGrid;
    @FXML
    private Label outputLabel;
    @FXML
    private HBox scrollingHBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane mainAnchor;

    public static final HashMap<KeyCode, String> KEY_TO_STRING_HASHMAP = new HashMap<>() {{
        put(KeyCode.DIGIT1, "1");
        put(KeyCode.DIGIT2, "2");
        put(KeyCode.DIGIT3, "3");
        put(KeyCode.DIGIT4, "4");
        put(KeyCode.DIGIT7, "7");
        put(KeyCode.DIGIT8, "8");
        put(KeyCode.NUMPAD1, "1");
        put(KeyCode.NUMPAD2, "2");
        put(KeyCode.NUMPAD3, "3");
        put(KeyCode.NUMPAD4, "4");
        put(KeyCode.NUMPAD7, "7");
        put(KeyCode.NUMPAD8, "8");
    }};

    public static final HashMap<String, String> OPERATOR_TO_STRING_HASHMAP = new HashMap<>() {{
        put("%", "%");
        put("^", "^");
        put("*", "x");
        put("+", "+");
        put("-", "-");
        put("/", "÷");
        put(".", ".");
        put("6", "6");
        put("5", "5");
        put("(", "(");
        put(")", ")");
        put("9", "9");
        put("0", "0");
    }};

    public CalculatorController() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Button> buttonList = retrieveButtonList();
        addListenersToAllButtons(buttonList);
        addListenerToAnchor();
        this.outputLabel.requestFocus();

    }

    private void addListenerToAnchor() {
        this.mainAnchor.setOnKeyPressed(keyEvent -> {
            String valueToAdd = KEY_TO_STRING_HASHMAP.get(keyEvent.getCode());
            if (valueToAdd != null) {
            if (!valueToAdd.isEmpty()) {
                addTextToLabel(valueToAdd);
            }}
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                this.processEnterRequest();
            } else if (keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                this.processUndoRequest();
            } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                this.processClearRequest();
            }
        });
        this.mainAnchor.setOnKeyTyped(keyEvent -> {
            String operatorToAdd = OPERATOR_TO_STRING_HASHMAP.get(keyEvent.getCharacter());
            if (operatorToAdd != null) {
                if (!operatorToAdd.isEmpty()) {
                    addTextToLabel(operatorToAdd);
                }
            }
        });
    }

    private void addTextToLabel(String text) {
        outputLabel.setText(outputLabel.getText() + text);
    }

    private ArrayList<Button> retrieveButtonList() {
        ArrayList<Button> buttonList = new ArrayList<>();
        for (Node currNode : inputGrid.getChildren()) {
            if (currNode instanceof Button) {
                buttonList.add((Button) currNode);
            }
        }
        return buttonList;
    }

    private void addListenersToAllButtons(ArrayList<Button> buttonList) {
        addListenersToNumberButtons(buttonList);
        addListenersToNonNumberButtons(buttonList);
    }

    private void addListenersToNumberButtons(ArrayList<Button> buttonList) {
        for (Button currButton : buttonList) {
            String currentButtonValue = currButton.getText();
            if (Character.isDigit(currentButtonValue.charAt(0))) {
                currButton.setOnAction(event -> {
                    scrollPane.setHvalue(1.0);

                    StringBuilder sb = new StringBuilder(outputLabel.getText());
                    sb.append(currentButtonValue);
                    outputLabel.setText(sb.toString());
                });
            }

        }
    }
    private void addListenersToNonNumberButtons(ArrayList<Button> buttonList) {
       for (Button currButton : buttonList) {
           addListenersToNonDigitOutputButtons(currButton);
        }
       for (Button currButton : buttonList) {
           addListenersToNonDegitLogicButtons(currButton);
       }
    }

    private void addListenersToNonDigitOutputButtons(Button currentButton) {
        String currentButtonValue = currentButton.getText();
        char buttonChar = currentButtonValue.charAt(0);
        if (currentButtonValue.equals("^") || buttonChar == 'U' || buttonChar == 'C' || buttonChar == '=') {
            return;
        }
        if (!Character.isDigit(buttonChar)) { //prevents non text buttons being added to output

            currentButton.setOnAction(event -> {
                scrollPane.setHvalue(1.0);
                outputLabel.setText(outputLabel.getText() + currentButtonValue);
            });
        }

    }

    private void addListenersToNonDegitLogicButtons(Button currentButton) {
       String currentButtonValue = currentButton.getText();
       char buttonChar = currentButtonValue.charAt(0);
       switch (buttonChar) {
           case 'U': //Undo Button action
               currentButton.setOnAction(event -> {
                    this.processUndoRequest();
               });
               break;
           case 'C': //Clear button action
               currentButton.setOnAction(event -> {
                    this.processClearRequest();
               });
               break;
           case '=': //Enter button action
               currentButton.setOnAction(event -> {
                   this.processEnterRequest();
               });
               break;
           default: break;
       }
       if (currentButtonValue.equals("^")) { //adds power to output
           scrollPane.setHvalue(1.0);
           currentButton.setOnAction(event -> {
           outputLabel.setText(outputLabel.getText() + "^");
           });
       }
    }

    private void processUndoRequest() {
        if (!outputLabel.getText().isEmpty()) {
            scrollPane.setHvalue(1.0);
            String newText = outputLabel.getText().substring(0, outputLabel.getText().length() - 1);
            outputLabel.setText(newText);
        }
    }

    private void processEnterRequest() {
        try {
            double testValueIfValid = Double.parseDouble(outputLabel.getText());
        } catch (NumberFormatException exception) {
            if (outputLabel.getText().charAt(0) == 'I') {
                outputLabel.setText("");
            }
        }
        try {
            InfixToReversePolishConverter converter = new InfixToReversePolishConverter();
            ArrayList<String> polishConversion = converter.convert(outputLabel.getText());
            outputLabel.setText(String.valueOf(PolishToCalculationConverter.calculateValue(polishConversion)));
        } catch (Exception e) {

            outputLabel.setText("Invalid Expression: " + outputLabel.getText());
        }
    }

    private void processClearRequest() {
        outputLabel.setText("");
    }


}
