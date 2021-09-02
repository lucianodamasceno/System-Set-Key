/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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
public class emUso {

    private int idHistorico;
    private int idPessoa;
    private int chave;
    private String local;
    private String pessoa;
    private String diaSaida;
    private String horaSaida;
    private String diaRetorno;
    private String horaRetorno;
    private int intRegistro;
    private int totUso;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final int width = gd.getDisplayMode().getWidth();
    private final int height = gd.getDisplayMode().getHeight();
    Connection con = new Connection();
    java.sql.Connection Conexao;

    public emUso() {

    }

    public int getIntRegistro() {
        return intRegistro;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
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

    public int getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getPessoa() {
        return pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = pessoa;
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

    public emUso(int idHistorico, int chave, String local, String Pessoa, String dia, String hora) {
        this.idHistorico = idHistorico;
        this.chave = chave;
        this.local = local;
        this.pessoa = Pessoa;
        this.diaSaida = dia;
        this.horaSaida = hora;
    }

    public int Tot() {
        totUso = 0;
        con.Open();
        Conexao = con.getConexao();
        PreparedStatement strComandoSQL;
        try {
            strComandoSQL = Conexao.prepareStatement("SELECT * FROM TBHistorico WHERE horaRetorno is null");
            ResultSet rs = strComandoSQL.executeQuery();
            while (rs.next()) {
                totUso++;
            }
        } catch (SQLException ex) {
            Alert("Contagem", "Erro ao contabilizar chaves em uso!", Alert.AlertType.NONE);
        } finally {
            con.Close();
        }
        return totUso;
    }

    public List<emUso> AllemUso() {
        ArrayList<emUso> result = new ArrayList<>();
        con.Open();
        Conexao = con.getConexao();
        try {
            PreparedStatement strComandoSQL;
            strComandoSQL = Conexao.prepareStatement("SELECT  idHistorico, Num_Chave, local, nome, dataSaida, horaSaida from((TBHistorico"
                    + " INNER JOIN TBChave ON TBHistorico.idChave = TBChave.Num_Chave)"
                    + " INNER JOIN TBPessoa ON TBHistorico.idPessoa = TBPessoa.idPessoa) WHERE horaRetorno is null "
                    + " ORDER BY idHistorico DESC");
            ResultSet rs = strComandoSQL.executeQuery();
            while (rs.next()) {
                emUso e = new emUso(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6));
                result.add(e);
                totUso++;
            }
        } catch (SQLException ex) {
            Alert("Erro em listar chave(s) em uso", "Problema com ArrayList", Alert.AlertType.ERROR);
        } finally {
            con.Close();
        }
        return result;
    }

    public boolean checaDispo() {
        con.Open();
        Conexao = con.getConexao();
        PreparedStatement strComandoQSL;
        boolean tf = false;
        try {
            strComandoQSL = con.getConexao().prepareStatement("SELECT disponivel FROM TBChave WHERE Num_Chave = " + chave);
            ResultSet rs = strComandoQSL.executeQuery();
            while (rs.next()) {
                String status = rs.getString(1);
                if ("SIM".equals(status)) {
                    tf = true;
                    con.Close();
                    return tf;
                } else {
                    tf = false;
                    Alert("Cautela", "Chave em uso!", Alert.AlertType.INFORMATION);
                    return tf;
                }
            }
        } catch (SQLException ex) {
            Alert("Erro", "Checar método checaDispo!", Alert.AlertType.NONE);
        } finally {
            con.Close();
        }
        return tf;
    }

    public void cautela() {
        int intRegistro2;
        con.Open();
        PreparedStatement strComandoSQL;
        Conexao = con.getConexao();
        try {
            strComandoSQL = Conexao.prepareStatement("INSERT INTO TBHIstorico (idPessoa, idChave,dataSaida,"
                    + "horaSaida) VALUES(?,?,?,?)");
            strComandoSQL.setInt(1, idPessoa);
            strComandoSQL.setInt(2, chave);
            strComandoSQL.setString(3, diaSaida);
            strComandoSQL.setString(4, horaSaida);
            intRegistro = strComandoSQL.executeUpdate();
            strComandoSQL = Conexao.prepareStatement("UPDATE TBChave SET disponivel = 'NÂO' WHERE Num_Chave =" + chave);
            intRegistro2 = strComandoSQL.executeUpdate();
            if (intRegistro != 0 && intRegistro2 != 0) {
            } else {
                Alert("Cautela", "Erro ao efetuar cautela!", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException ex) {
            Alert("Cautela", "Erro ao efetuar cautela. Checar SQLException!", Alert.AlertType.INFORMATION);
        } finally {
            con.Close();
        }
    }

    public void descautela() {

        con.Open();
        Conexao = con.getConexao();
        PreparedStatement strComandoSQL;

        try {
            strComandoSQL = Conexao.prepareStatement("UPDATE TBChave SET disponivel = 'SIM' WHERE Num_Chave =" + chave);
            intRegistro = strComandoSQL.executeUpdate();
            strComandoSQL = Conexao.prepareStatement("UPDATE TBHistorico SET dataRetorno ='" + diaRetorno + "'  WHERE idHistorico = " + idHistorico);
            intRegistro = strComandoSQL.executeUpdate();
            strComandoSQL = Conexao.prepareStatement("UPDATE TBHistorico SET horaRetorno ='" + horaRetorno + "' WHERE idHistorico = " + idHistorico);
            intRegistro = strComandoSQL.executeUpdate();
        } catch (SQLException ex) {
            Alert("Baixa", "Erro ao dar baixa! Chegar SQLException", Alert.AlertType.INFORMATION);
        } finally {
            con.Close();
        }
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
