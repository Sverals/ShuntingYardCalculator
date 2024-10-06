package com.github.shuntingyardcalculator.mainApp;

import com.github.shuntingyardcalculator.logic.InfixToReversePolishConverter;
import com.github.shuntingyardcalculator.logic.PolishToCalculationConverter;
import com.github.shuntingyardcalculator.ui.CalculatorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("CalculatorView.fxml"));
        CalculatorController controller = new CalculatorController();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        InfixToReversePolishConverter converter = new InfixToReversePolishConverter();
        ArrayList<String> values = converter.convert("√9");
        System.out.println(PolishToCalculationConverter.calculateValue(values));
        //launch();
    }
}