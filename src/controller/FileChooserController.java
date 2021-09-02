/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import model.Access.Connection;

/**
 *
 * @author Luciano
 */
public class FileChooserController {

    private static String CaminhoBD;

    public static String getCaminhoBD() {
        return CaminhoBD;
    }

    public static void setCaminhoBD(String CaminhoBD) {
        FileChooserController.CaminhoBD = CaminhoBD;
    }

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txtCaminho;

    @FXML
    private Button btnBuscar;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    void cmdConfirmar(ActionEvent event) {
        Connection con = new Connection();
        CaminhoBD = txtCaminho.getText();
        con.setLocalBD(CaminhoBD);
        con.SaveCaminhoBD();
    }

    @FXML
    void cmdBuscar(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Selecionar arquivo Microsoft Access accdb");
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        File file = fc.showOpenDialog(stage);

        if (file != null) {
            txtCaminho.setText(file.getAbsolutePath());
        }
    }
}
