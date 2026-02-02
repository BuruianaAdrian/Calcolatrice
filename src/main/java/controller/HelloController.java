package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class HelloController {
    @FXML
    public TextField display;
    @FXML
    public Button btn0;
    @FXML
    public Button btn1;
    @FXML
    public Button btn2;
    @FXML
    public Button btn3;
    @FXML
    public Button btn4;
    @FXML
    public Button btn5;
    @FXML
    public Button btn6;
    @FXML
    public Button btn7;
    @FXML
    public Button btn8;
    @FXML
    public Button btn9;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnSub;
    @FXML
    public Button btnMul;
    @FXML
    public Button btnDiv;
    @FXML
    public Button btnPow;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnDel;
    @FXML
    public Button btnEnter;

    @FXML
    public void numeri(ActionEvent event){
        Button bottone = (Button) event.getSource();
        String testo = bottone.getText();
        display.setText(display.getText() + testo);
    }
    @FXML
    public void operatori(ActionEvent event){
        Button bottone = (Button) event.getSource();
        String testo = bottone.getText();
        display.setText(display.getText() + testo);
    }
//    Scene.processKeyEvent(KeyEvent e){
//        String keyText = e.getText();
//        display.setText(display.getText() + keyText);
//    }

    @FXML
    public void clear(){
        display.setText("");
    }

    @FXML
    private void del() {
        String text = display.getText();
        if (!text.isEmpty()) {
            display.setText(text.substring(0, text.length() - 1));
        }
    }
}