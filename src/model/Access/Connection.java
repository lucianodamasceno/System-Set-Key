/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Access;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.DateTime;

/**
 *
 * @author luciano
 */
public class Connection {

    java.sql.Connection Conexao;
    java.sql.Statement Comando;
    java.sql.ResultSet rsBusca;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final int width = gd.getDisplayMode().getWidth();
    private final int height = gd.getDisplayMode().getHeight();
    private int count = 0;
    public static String localBD;

    public ResultSet getRsBusca() {
        return rsBusca;
    }

    public void setRsBusca(ResultSet rsBusca) {
        this.rsBusca = rsBusca;
    }

    public void setLocalBD(String localBD) {
        Connection.localBD = localBD;
    }

    public Connection() {

    }

    public java.sql.Connection getConexao() {
        return Conexao;
    }

    public void setConexao(java.sql.Connection Conexao) {
        this.Conexao = Conexao;
    }

    public Statement getComando() {
        return Comando;
    }

    public void setComando(Statement Comando) {
        this.Comando = Comando;
    }

    public void Open() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String path = localBD;
            String url = "jdbc:ucanaccess://" + path;

            try {
                Conexao = DriverManager.getConnection(url);
            } catch (SQLException ex) {
                AlertError("Conexão com banco de dados:", "Erro no Statment!");
                Connection con = new Connection();
                con.ReadCaminhoBD();
            }
            try {
                Comando = Conexao.createStatement();
            } catch (SQLException ex) {
                AlertError("Conexão com banco de dados:", "Erro ao ler as configurações do banco de dados!");
            }
        } catch (ClassNotFoundException ex) {
            AlertError("Conexão com banco de dados:", "Erro no Class ForName!");
        }
    }

    public void Close() {

        try {
            Comando.close();
            Conexao.close();
        } catch (SQLException ex) {
            AlertError("Conexão com banco de dados:", "Erro ao fechar conexão!");
        }
    }

    public void SaveCaminhoBD() {
        String caminhoRaiz = System.getProperty("user.dir");
        String camminhoBD;
        if (caminhoRaiz.contains("\\")) {
            camminhoBD = caminhoRaiz + "\\BDCautela.accdb";
        } else {
            camminhoBD = caminhoRaiz + "/BDCautela.accdb";
        }
        FileWriter fw;
        File file = new File("configBD.ini");

        if (file.exists()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar subistuição");
            alert.setHeaderText("Já existe o arquivo config.BD");
            alert.setContentText("Confirmar substituição?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                file.delete();
                SaveCaminhoBD();
            } else {
                ReadCaminhoBD();
            }
        } else {
            try {
                fw = new FileWriter("configBD.ini");
                PrintWriter pw = new PrintWriter(fw);
                pw.println(camminhoBD);
                pw.close();
                ReadCaminhoBD();
            } catch (IOException ex) {
                AlertError("Gravação das configurações:", "Erro ao salvar arquivo de configurações!");
            }
        }
    }

    public void ReadCaminhoBD() {
        File arquivo = new File("configBD.ini");
        FileReader fr;

        try {
            fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            try {
                while (br.ready()) {
                    localBD = br.readLine();
                }
            } catch (IOException ex) {
                AlertError("Configurações:", "Erro ao ler as configurações do banco de dados!");
            }
        } catch (FileNotFoundException ex) {
            // AlertError("Banco de dados:", "Caminho do banco de dados não encontrado!!");
            SaveCaminhoBD();
            ReadCaminhoBD();
        }
    }

    public void cleanBD() {
        Open();
        PreparedStatement comandoSQL = null;
        Conexao = getConexao();

        DateTime data = new DateTime();
        String ano = data.getDia().substring(6);
        int ano_2 = Integer.parseInt(ano);
        ano_2 = ano_2 - 2;
        int intRegistro = 0;

        try {
            while (ano_2 != 2015) {
                comandoSQL = Conexao.prepareStatement("DELETE FROM TBHistorico WHERE dataRetorno like " + "'*" + ano_2 + "'");
                ano_2--;
                intRegistro = comandoSQL.executeUpdate();
                if (intRegistro != 0) {
                    AlertInfo("Auto limpeza de banco de dados:", "Histórico com mais de 2 anos foram limpos");
                }
            }
        } catch (Exception ex) {
            AlertError("Limpeza do banco de dados:", "Erro ao efetuar limpeza de dados antigos");
        } finally {
            Close();
        }
    }

    private void AlertError(String Tittle, String ContentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/image/Circle.png").toString()));
        alert.setTitle(Tittle);
        alert.setX(width / 2 - 150);
        alert.setY(height / 2 - 150);
        alert.setContentText(ContentText);
        alert.setHeaderText(null);
        alert.setWidth(5);
        alert.showAndWait();
    }

    private void AlertInfo(String Tittle, String ContentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/image/Circle.png").toString()));
        alert.setTitle(Tittle);
        alert.setX(width / 2 - 150);
        alert.setY(height / 2 - 150);
        alert.setContentText(ContentText);
        alert.setHeaderText(null);
        alert.setWidth(5);
        alert.showAndWait();
    }

}
