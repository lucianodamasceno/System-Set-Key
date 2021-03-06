/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Access.Connection;

/**
 *
 * @author Luciano
 */
public class Chave {

    private int NumChave = -1;
    private int Where = -1;
    private String Local;
    private String Departamento;
    private String buscaChave;
    private String Disponivel;
    java.sql.ResultSet rsBusca;
    private int width;
    private int height;

    private ImageView photo;

    public Chave(int NumChave, String Local, String Departamento, String Disponivel) {
        this.NumChave = NumChave;
        this.Local = Local;
        this.Departamento = Departamento;
        this.Disponivel = Disponivel;
    }

    public Chave(int NumChave, String Local, String Departamento, ImageView photo) {
        this.NumChave = NumChave;
        this.Local = Local;
        this.Departamento = Departamento;
        this.photo = photo;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;

    }

    public Chave() {
    }

    public int getWhere() {
        return Where;
    }

    public void setWhere(int Where) {
        this.Where = Where;
    }

    public String getBuscaChave() {
        return buscaChave;
    }

    public void setBuscaChave(String buscaChave) {
        this.buscaChave = buscaChave;
    }

    public int getNumChave() {
        return NumChave;
    }

    public void setNumChave(int NumChave) {
        this.NumChave = NumChave;
    }

    public String getLocal() {
        return Local;
    }

    public void setLocal(String Local) {
        this.Local = Local;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String Departamento) {
        this.Departamento = Departamento;
    }

    public String getDisponivel() {
        return Disponivel;
    }

    public void setDisponivel(String Disponivel) {
        this.Disponivel = Disponivel;
    }

    Connection con = new Connection();
    java.sql.Connection Conexao;

    public ImageView img(String valida) {
        String caminhoIms = null;
        ImageView img;

        if (valida.contains("SIM")) {
            caminhoIms = "/image/sim.png";
        } else {
            caminhoIms = "/image/nao.png";
        }
        img = new ImageView(new Image(this.getClass().getResourceAsStream(caminhoIms)));
        return img;
    }

    public List<Chave> allChave() {
        ArrayList<Chave> result = new ArrayList<>();
        con.Open();
        try {
            PreparedStatement strComandoSQL = con.getConexao().prepareStatement("SELECT * FROM TBChave ");
            ResultSet rs = strComandoSQL.executeQuery();
            while (rs.next()) {
                Chave c = new Chave(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        img(rs.getString(4)));
                result.add(c);
            }
        } catch (SQLException ex) {
            Alert("Erro em listar chave", "Problema com ArrayList", Alert.AlertType.ERROR);
        } finally {
            con.Close();
        }
        return result;
    }

    public void AlteraChave() {
        con.Open();
        PreparedStatement strComandoSQL;
        Conexao = con.getConexao();
        try {
            strComandoSQL = Conexao.prepareStatement("UPDATE TBChave SET "
                    + "Num_Chave = " + NumChave + ", "
                    + "local ='" + Local + "', "
                    + "departamento ='" + Departamento + "' "
                    + "WHERE Num_Chave =" + Where);
            int intRegistro = strComandoSQL.executeUpdate();
            if (intRegistro != 0) {
                Alert("Altera????o de dados", "Altera????o efetuada!", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException ex) {
            Alert("Altera????o de dados", "Voce est?? tentando substituir uma chave j?? existente. Excluda a atual primero!", Alert.AlertType.ERROR);
        } finally {
            con.Close();
        }
    }

    public void InsereChave() {
        con.Open();
        Conexao = con.getConexao();
        PreparedStatement strComandoSQL;
        try {
            strComandoSQL = Conexao.prepareStatement("INSERT INTO TBChave (Num_Chave, local, departamento, disponivel)VALUES(?,?,?,?)");
            strComandoSQL.setInt(1, NumChave);
            strComandoSQL.setString(2, Local);
            strComandoSQL.setString(3, Departamento);
            strComandoSQL.setString(4, Disponivel);
            int intRegistro = strComandoSQL.executeUpdate();
            if (intRegistro != 0) {
                Alert("Cadastro efetuado!\n",
                        "Chave: " + NumChave
                        + "\nLocal: " + Local
                        + "\nDepartamento: " + Departamento, Alert.AlertType.INFORMATION);
            } else {
                Alert("Erro na inser????o", "Chave n??o adicionada! Checar banco de dados!", Alert.AlertType.ERROR);
            }
        } catch (HeadlessException | SQLException Excecao) {
            Alert("Chave nao inserida", "N??mero da chave j?? existente!", Alert.AlertType.INFORMATION);
        } finally {
            con.Close();
        }
    }

    public void excluir() {
        con.Open();
        PreparedStatement strComandoSQL;
        Conexao = con.getConexao();
        if (Where > 0) {
            try {
                strComandoSQL = Conexao.prepareStatement("DELETE FROM TBChave WHERE Num_Chave =" + Where);
                int intRegistro = strComandoSQL.executeUpdate();
                if (intRegistro != 0) {
                    Alert("Exclus??o de dados", "Chave exclu??da!", Alert.AlertType.INFORMATION);
                }
            } catch (SQLException ex) {
                Alert("Exclus??o de dados", "Erro ao excluir chave!", Alert.AlertType.ERROR);
            } finally {
                con.Close();
            }
        }
    }

    public void forceBaixa() {
        DateTime dt = new DateTime();
        String dia = dt.getDia();
        String hora = dt.getHora();
        con.Open();
        PreparedStatement strComandoSQL;
        Conexao = con.getConexao();
        if (Where > 0) {
            try {
                strComandoSQL = Conexao.prepareStatement("UPDATE TBChave SET disponivel = 'SIM' WHERE Num_Chave = " + Where);
                int intRegistro = strComandoSQL.executeUpdate();
                strComandoSQL = Conexao.prepareStatement("UPDATE TBHistorico SET dataRetorno = '" + dia + "',"
                        + " horaRetorno ='" + hora + "' WHERE idChave = " + Where + " AND horaRetorno is null");
                intRegistro = strComandoSQL.executeUpdate();

                if (intRegistro != 0) {
                    Alert("Baixa for??ada", "Baixa dada", Alert.AlertType.INFORMATION);
                }
            } catch (SQLException ex) {
                Alert("Baixa for??ada", "Erro ao for??ar baixa!", Alert.AlertType.ERROR);
            }
        }
    }

    public List<Chave> BuscaChave() {
        ArrayList<Chave> result = new ArrayList<>();
        con.Open();
        try {
            PreparedStatement strComandoSQL = con.getConexao().prepareStatement("SELECT * FROM TBChave WHERE Num_Chave LIKE '*"
                    + buscaChave + "*' or local LIKE '*" + buscaChave + "*'");
            ResultSet rs = strComandoSQL.executeQuery();
            while (rs.next()) {
                Chave c = new Chave(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        img(rs.getString(4)));
                result.add(c);
            }
        } catch (SQLException ex) {
            Alert("Tabela de chave", "Erro ao listar dados de chave!", Alert.AlertType.ERROR);
        } finally {
            con.Close();
        }
        return result;
    }

    private void Alert(String Tittle, String ContentText, Alert.AlertType tipo) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();
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
