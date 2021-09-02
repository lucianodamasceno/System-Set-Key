/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Access.Connection;
import model.Log;

/**
 *
 * @author Luciano
 */
public class Main extends Application {

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final int width = gd.getDisplayMode().getWidth();
    private final int height = gd.getDisplayMode().getHeight();
    private final String nameSystem = "SYSTEM SET KEY ";
    private final String versao = "1.4";
    private final String dev = "Luciano Damasceno";

    public String getNameSystem() {
        return nameSystem;
    }

    public String getVersao() {
        return versao;
    }

    public String getDev() {
        return dev;
    }
    
    
    @Override
    public void start(Stage stage) throws Exception {
    Log log = new Log();
        log.criaDoc(versao, dev);
        Connection con = new Connection();
        con.ReadCaminhoBD();
        con.cleanBD();
        stage.setOnCloseRequest(event -> System.exit(0));
        Parent root = FXMLLoader.load(getClass().getResource("/view/TelaInicial.fxml"));
        Scene scene = new Scene(root, 1440, 830);
        Image icon = new Image(getClass().getResourceAsStream("/image/Circle.png"));
        stage.setTitle(nameSystem + versao);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);

        stage.setMinHeight(830);
        stage.setMinWidth(1440);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }
}
