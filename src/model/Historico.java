/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Access.Connection;

/**
 *
 * @author Luciano
 */
public class Historico {

    private int chave;
    private String nome;
    private String cpf;
    private String telefone;
    private String empresa;
    private String diaSaida;
    private String horaSaida;
    private String diaRetorno;
    private String horaRetorno;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    private final int width = gd.getDisplayMode().getWidth();
    private final int height = gd.getDisplayMode().getHeight();

    public Historico() {
    }

    public Historico(String nome, String cpf, String telefone, String empresa, String diaSaida, String horaSaida, String diaRetorno, String horaRetorno) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.empresa = empresa;
        this.diaSaida = diaSaida;
        this.horaSaida = horaSaida;
        this.diaRetorno = diaRetorno;
        this.horaRetorno = horaRetorno;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDiaSaida() {
        return diaSaida;
    }

    public void setDiaSaida(String diaSaida) {
        this.diaSaida = diaSaida;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public String getDiaRetorno() {
        return diaRetorno;
    }

    public void setDiaRetorno(String diaRetorno) {
        this.diaRetorno = diaRetorno;
    }

    public String getHoraRetorno() {
        return horaRetorno;
    }

    public void setHoraRetorno(String horaRetorno) {
        this.horaRetorno = horaRetorno;
    }

    Connection con = new Connection();
    java.sql.Connection Conexao;

    public List<Historico> allHistorico() {
        ArrayList<Historico> result = new ArrayList<>();
        con.Open();
        try {
            PreparedStatement strComandoSQL = con.getConexao().prepareStatement("SELECT nome,cpf, telefone, empresa, dataSaida, horaSaida, dataRetorno, horaRetorno FROM TBPessoa p\n"
                    + "INNER JOIN TBHistorico h\n"
                    + "ON p.idPessoa = h.idPessoa\n"
                    + "where idChave =" + chave + " ORDER BY idHistorico DESC");
            ResultSet rs = strComandoSQL.executeQuery();
            while (rs.next()) {
                Historico h = new Historico(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8));
                result.add(h);
            }
        } catch (SQLException ex) {
            Alert("Erro em listar histórico", "Problema com ArrayList de histórico", Alert.AlertType.INFORMATION);
        } finally {
            con.Close();
        }

        con.Close();
        return result;
    }

    private void Alert(String Tittle, String ContentText, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
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
