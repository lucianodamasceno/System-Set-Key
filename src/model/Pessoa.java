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
public class Pessoa {

    private int id;
    private String nome;
    private String CPF;
    private String Telefone;
    private String Empresa;
    private String where;
    private int width;
    private int height;

    public Pessoa() {
    }

    public Pessoa(String nome, String Telefone, String Empresa) {
        this.nome = nome;
        this.Telefone = Telefone;
        this.Empresa = Empresa;
    }

    public Pessoa(String nome, String CPF, String Telefone, String Empresa) {
        this.nome = nome;
        this.CPF = CPF;
        this.Telefone = Telefone;
        this.Empresa = Empresa;
    }

    public Pessoa(int id, String nome, String CPF, String Telefone, String Empresa) {
        this.id = id;
        this.nome = nome;
        this.CPF = CPF;
        this.Telefone = Telefone;
        this.Empresa = Empresa;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String Empresa) {
        this.Empresa = Empresa;
    }

    Connection con = new Connection();
    java.sql.Connection Conexao;

    public List<Pessoa> allPessoa() {
        ArrayList<Pessoa> result = new ArrayList<>();
        con.Open();
        try {
            PreparedStatement strComandoSQL = con.getConexao().prepareStatement("SELECT * FROM TBPessoa ORDER BY nome");
            ResultSet rs = strComandoSQL.executeQuery();
            while (rs.next()) {
                Pessoa p = new Pessoa(
                        rs.getInt(1),
                        rs.getString(2),
                        ocultaCPF(rs.getString(3)),
                        rs.getString(4),
                        rs.getString(5));
                result.add(p);
            }
        } catch (SQLException ex) {
            Alert("Erro em listar pessoa", "Problema com ArrayList de pessoa", Alert.AlertType.NONE);
        } finally {
            con.Close();
        }
        return result;
    }

    private String ocultaCPF(String cpf) throws SQLException {
        String[] Ncpf;
        Ncpf = cpf.split("[.-]");
        Ncpf[1] = ".***.";
        Ncpf[2] = "***-";
        int i = 0;
        cpf = "";
        while (i < Ncpf.length) {
            cpf += Ncpf[i];
            i++;
        }
        return cpf;
    }

    public void InserePessoa() {
        Encrypt enc = new Encrypt("QueZxsweqqER39zZ");

        con.Open();
        Conexao = con.getConexao();
        PreparedStatement strComandoSQL;
        try {
            strComandoSQL = Conexao.prepareStatement("INSERT INTO TBPessoa(nome, CPF, telefone, empresa) VALUES (?,?,?,?)");
            strComandoSQL.setString(1, nome);
            strComandoSQL.setString(2, CPF);
            strComandoSQL.setString(3, Telefone);
            strComandoSQL.setString(4, Empresa);
            int intRegistro = strComandoSQL.executeUpdate();
            if (intRegistro != 0) {
                Alert("Cadastro efetuado!\n",
                        "Nome: " + nome
                        + "\nCPF: " + CPF
                        + "\nTelefone: " + Telefone
                        + "\nEmpresa/dpto: " + Empresa, Alert.AlertType.INFORMATION);
            } else {
                Alert("Erro na inserção", "Chave não adicionada! Checar banco de dados!", Alert.AlertType.ERROR);
            }
        } catch (SQLException ex) {
            Alert("Cadastro não efetuado", "CPF já cadastrado!", Alert.AlertType.INFORMATION);
        } finally {
            con.Close();
        }
    }

    public List<Pessoa> BuscaPessoa() {
        ArrayList<Pessoa> result = new ArrayList<>();
        con.Open();
        try {
            PreparedStatement strComandoSQL = con.getConexao().prepareStatement("SELECT * FROM TBPessoa WHERE nome LIKE '%*"
                    + where + "*%' or CPF LIKE '*" + where + "*'");
            ResultSet rs = strComandoSQL.executeQuery();
            while (rs.next()) {
                Pessoa p = new Pessoa(
                        rs.getInt(1),
                        rs.getString(2),
                        ocultaCPF(rs.getString(3)),
                        rs.getString(4),
                        rs.getString(5));
                result.add(p);
            }
        } catch (SQLException ex) {
            Alert("Tabela de pessoa", "Erro ao listar dados de pessoa!", Alert.AlertType.ERROR);
        } finally {
            con.Close();
        }
        return result;
    }

    public String BuscaPessoaTooTip() {
        con.Open();
        String pessoa;
        try {
            PreparedStatement strComandoSQL = con.getConexao().prepareStatement("SELECT idPessoa from tbHistorico where idHistorico =" + id);
            ResultSet rs = strComandoSQL.executeQuery();
            while (rs.next()) {
                int idP = rs.getInt(1);
                strComandoSQL = con.getConexao().prepareStatement("SELECT nome, CPF, TELEFONE, empresa FROM TBPessoa WHERE idPessoa = " + idP);
                rs = strComandoSQL.executeQuery();
                while (rs.next()) {
                    pessoa
                            = "Nome: " + rs.getString(1)
                            + "\nCPF: " + ocultaCPF(rs.getString(2))
                            + "\nTelefone: " + rs.getString(3)
                            + "\nEmpresa/dpto: " + rs.getString(4);
                    return pessoa;
                }
                return null;
            }
        } catch (SQLException ex) {
            Alert("Tabela de pessoa:", "Erro ao listar dados de pessoa!", Alert.AlertType.ERROR);
            con.Close();
        } finally {
            con.Close();
        }
        return null;
    }

    public void AlteraPessoa() {
        con.Open();
        PreparedStatement strComandoSQL;
        Conexao = con.getConexao();
        try {
            strComandoSQL = Conexao.prepareStatement("UPDATE TBPessoa SET "
                    + "nome ='" + nome + "', "
                    //                    + "CPF ='" + CPF + "', "
                    + "telefone ='" + Telefone + "', "
                    + "empresa ='" + Empresa + "' "
                    + "WHERE idPessoa ='" + id + "'");
            int intRegistro = strComandoSQL.executeUpdate();
            if (intRegistro != 0) {
                Alert("Alteração de dados:", "Alteração efetuada!", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException ex) {
            Alert("Alteração de dados", "Voce está tentando substituir uma chave já existente. Excluda a atual primero!", Alert.AlertType.ERROR);
        } finally {
            con.Close();
        }
    }

    public void excluir() {
        con.Open();
        PreparedStatement strComandoSQL;
        Conexao = con.getConexao();
        if (id > 0) {
            try {
                strComandoSQL = Conexao.prepareStatement("DELETE FROM TBPessoa WHERE idPessoa =" + id);
                int intREgistro = strComandoSQL.executeUpdate();
                if (intREgistro == 1) {
                    Alert("Exclusão de dados:", "Pessoa excluída!", Alert.AlertType.INFORMATION);
                }
            } catch (SQLException ex) {
                Alert("Exclusão de dados:", "Erro ao excluir pessoa!", Alert.AlertType.ERROR);
            } finally {
                con.Close();
            }
        }
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
