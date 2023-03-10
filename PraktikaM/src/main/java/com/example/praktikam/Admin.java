package com.example.praktikam;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class Admin {
    @FXML private Button btnBack;
    @FXML private Button btnBack1;
    @FXML private Button btnAuto;
    @FXML private Button btnDelete;
    @FXML private TextField tfLogin;
    @FXML private TextField tfPass;
    @FXML private Pane paneAuto;
    @FXML private Label wtext;
    @FXML private TableView<BDtableZayavk> tableZayavk;
    @FXML private TableColumn<BDtableZayavk,Integer> tcID;
    @FXML private TableColumn<BDtableZayavk,String> tcName;
    @FXML private TableColumn<BDtableZayavk,String> tcPhone;
    @FXML private TableColumn<BDtableZayavk,String> tcRem;

    private BDtableZayavk selectedTable = new BDtableZayavk();
    private final ObservableList<BDtableZayavk> tableDataZayavk = FXCollections.observableArrayList();


    @FXML
    void initialize() {
        tcID.setCellValueFactory(new PropertyValueFactory<>("Code"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        tcRem.setCellValueFactory(new PropertyValueFactory<>("Rem"));

        tablerefresh();

        btnBack.setOnAction(event -> {
            btnBack.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("hello-view.fxml"));

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

        btnBack1.setOnAction(event -> {
            btnBack1.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("hello-view.fxml"));

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

        btnAuto.setOnAction(actionEvent -> {
            String loginText = tfLogin.getText().trim();
            String passwordText = tfPass.getText().trim();
            if (!loginText.equals("") && !passwordText.equals("")) {
                loginAdmin(loginText, passwordText);
            } else if (loginText.equals("") || passwordText.equals("")){
                wtext.setText("Заполните все поля");}
        });

        btnDelete.setOnAction(event -> {
            selectedTable = tableZayavk.getSelectionModel().getSelectedItem();
            deleteTable(selectedTable.getIDCode());
            tableZayavk.getItems().remove(selectedTable);
        });

        tableZayavk.setRowFactory( tv -> {
            TableRow<BDtableZayavk> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                btnDelete.setVisible(true);
            });
            return row ;
        });
    }

    public static void deleteTable(int Code) {
        String querry = "DELETE FROM contract WHERE `idContract` = "+ Code;
        try {Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/prak",
                "root", "Qwerty123456");
            PreparedStatement preparedStatement = conn.prepareStatement(querry);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void tablerefresh(){
        tableDataZayavk.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/prak",
                    "root", "Qwerty123456")) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * from contract");
                while(resultSet.next()){
                    tableDataZayavk.add(new BDtableZayavk(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    ));
                }
            }
            if (!tableDataZayavk.isEmpty()){
                tableZayavk.setItems(tableDataZayavk);
            }
        }catch (Exception e){
            System.out.println("Ошибка с таблицей заявки");
        }}

    void loginAdmin(String loginText, String passwordText) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/prak",
                    "root", "Qwerty123456")) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT Логин, Пароль FROM admin");
                while (resultSet.next()) {
                    if (resultSet.getString("Логин").equals(loginText) &&
                            resultSet.getString("Пароль").equals(passwordText)){
                        paneAuto.setVisible(false);
                        btnBack1.setVisible(true);
                        tableZayavk.setVisible(true);
                    }
                    else if (!resultSet.getString("Логин").equals(loginText) ||
                            !resultSet.getString("Пароль").equals(passwordText)){
                        wtext.setText("Неверный логин или пароль");}
                }}}
        catch (Exception ex) {
            System.out.println("Ошибка авторизации");
        }}
}
