package com.example.praktikam;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloController {
    @FXML private ComboBox<String> comboVidRem;
    @FXML private Button btnVidRem;
    @FXML private Button btnBackVid;
    @FXML private Button btnZayavk;
    @FXML private Button btnAuto;
    @FXML private TextField tfName;
    @FXML private TextField tfPhone;
    @FXML private Label lbZayavk;
    @FXML private Pane paneVidRem;


    private final String[] listRem = {"Ремонт 'Стандарт'","Ремонт 'Евро'", "Ремонт 'Элит'"};
    private String selectTypeRem = "";



    @FXML
    void initialize() {
        comboVidRem.getItems().addAll(listRem);
        comboVidRem.setValue("Ремонт 'Стандарт'");
        comboVidRem.setOnAction(this::getTypeRoom);

        btnVidRem.setOnAction(event -> {
            paneVidRem.setVisible(true);
        });

        btnBackVid.setOnAction(event -> {
            paneVidRem.setVisible(false);
        });

        btnAuto.setOnAction(event -> {
            btnAuto.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("admin.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent room = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Ремонт квартир");
            stage.setScene(new Scene(room));
            stage.show();
        });

        btnZayavk.setOnAction(event -> {
            if (!(tfName.getText().isEmpty() || tfPhone.getText().isEmpty()) || isNumeric(tfPhone.getText())
                    && tfPhone.getText().length() == 11) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/prak",
                            "root", "Qwerty123456")){
                        PreparedStatement statement = conn.prepareStatement
                                ("INSERT into contract(Имя, Телефон, Ремонт) VALUES(?,?,?)");
                        statement.setString(1, tfName.getText());
                        statement.setString(2, tfPhone.getText());
                        statement.setString(3, comboVidRem.getValue());
                        statement.executeUpdate();
                    }}
                catch (Exception e) {
                    System.out.println("Ошибка с заполнением");
                }
                lbZayavk.setText("Ваша заявка принята");}
        });
    }

    public static boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void getTypeRoom(javafx.event.ActionEvent actionEvent1) {
        selectTypeRem = comboVidRem.getValue();
    }

}