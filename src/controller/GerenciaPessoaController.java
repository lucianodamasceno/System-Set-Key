/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTabPane;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Pessoa;
import model.ValidaCPF;
import model.TextFieldFormatter;

/**
 *
 * @author Luciano
 */
public class GerenciaPessoaController extends MainController {

    private int id;
    private String nome;
    private String CPF;
    private String telefone;
    private String empresa;
    private String EditExlui;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final int width = gd.getDisplayMode().getWidth();
    private final int height = gd.getDisplayMode().getHeight();
    MainController mc = new MainController();

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txtnovoNome;

    @FXML
    private TextField txtnovoCPF;

    @FXML
    private TableView<Pessoa> tbBuscaPessoa;

    @FXML
    private TableColumn<Pessoa, Integer> tbPessoaID;

    @FXML
    private TableColumn<Pessoa, String> tbPessoaNome;

    @FXML
    private TableColumn<Pessoa, String> tbPessoaCPF;

    @FXML
    private TableColumn<Pessoa, String> tbPessoaEmpresa;

    @FXML
    private TableColumn<Pessoa, String> tbPessoaTel;

    @FXML
    private Button btnCadastrar;

    @FXML
    private TextField txtEditEmpresa;

    @FXML
    private TextField txtEditCPF;

    @FXML
    private TextField txtID;

    @FXML
    private Button btnEditar;

    @FXML
    private TextField txtBuscaPessoa;

    @FXML
    private TextField txtEditTel;

    @FXML
    private TextField txtEditNome;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtnovaEmpresa;

    @FXML
    private TextField txtnovoTel;

    @FXML
    private Button btnExcluir;

    @FXML
    private JFXTabPane tbPane;

    @FXML
    void Cancelar(ActionEvent event) {
        navegacao("select");
    }

    @FXML
    void Confirmar(ActionEvent event) {
        EditExlui();
        navegacao("navegar");
        txtEditNome.setText("");
        txtEditNome.setText("");
        txtEditTel.setText("");
        txtEditEmpresa.setText("");
    }

    @FXML
    void Exclluir(ActionEvent event) {
        navegacao("excluir");
    }

    @FXML
    void Editar(ActionEvent event) {
        navegacao("editar");
    }

    @FXML
    void CadastrarPessoa(ActionEvent event) {
        ValidaCPF validacpf = new ValidaCPF();
        Pessoa p = new Pessoa();
        boolean statusCPF;
        try {
            statusCPF = validacpf.validaCPF(txtnovoCPF.getText());
            if (statusCPF == true) {
                CadastarPessoaMtd();
                iniTable();
                txtnovoNome.setText("");
                txtnovoCPF.setText("");
                txtnovoTel.setText("");
                txtnovaEmpresa.setText("");
            } else {
                alert("Cadastro - CPF", "CPF inválido!", Alert.AlertType.ERROR);
            }
        } catch (Exception ex) {
            alert("Cadastro de pessoa", "Há dado(s) em branco!", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void selectLinha(MouseEvent e) {
        navegacao("select");
        id = tbBuscaPessoa.getSelectionModel().getSelectedItem().getId();
        nome = tbBuscaPessoa.getSelectionModel().getSelectedItem().getNome();
        CPF = tbBuscaPessoa.getSelectionModel().getSelectedItem().getCPF();
        telefone = tbBuscaPessoa.getSelectionModel().getSelectedItem().getTelefone();
        empresa = tbBuscaPessoa.getSelectionModel().getSelectedItem().getEmpresa();

        txtID.setText(String.valueOf(id));
        txtEditNome.setText(nome);
        txtEditCPF.setText(CPF);
        txtEditTel.setText(telefone);
        txtEditEmpresa.setText(empresa);
    }

    @FXML
    private void maskCPF_Novo() {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###.###.###-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtnovoCPF);
        tff.formatter();
    }

    @FXML
    private void maskCPF_Edit() {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("###.###.###-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtEditCPF);
        tff.formatter();
    }

    @FXML
    private void maskTel_Novo() {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask(("(##)#####-####"));
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtnovoTel);
        tff.formatter();
    }

    @FXML
    private void maskTel_Edit() {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask(("(##)#####-####"));
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtEditTel);
        tff.formatter();
    }

    @FXML
    private void cmdEnterPessoa(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            BuscaPessoatbl();
        }
    }

    @FXML
    private void cmdPessoaNull() {
        BuscaPessoatbl();
    }

    public void initialize(URL url, ResourceBundle rb) {
        iniTable();
        navegacao("navegar");
    }

    private void CadastarPessoaMtd() {
        String cpf = txtnovoCPF.getText();
        String Nome = txtnovoNome.getText().toUpperCase();
        String Telefone = txtnovoTel.getText();
        String Empresa = txtnovaEmpresa.getText().toUpperCase();
        Pessoa p = new Pessoa(Nome, cpf, Telefone, Empresa);
        p.InserePessoa();
    }

    private void iniTable() {
        tbPessoaID.setCellValueFactory(new PropertyValueFactory("id"));
        tbPessoaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        tbPessoaCPF.setCellValueFactory(new PropertyValueFactory("CPF"));
        tbPessoaTel.setCellValueFactory(new PropertyValueFactory("telefone"));
        tbPessoaEmpresa.setCellValueFactory(new PropertyValueFactory("empresa"));

        tbBuscaPessoa.setItems(atualizaTabela());
    }

    public ObservableList<Pessoa> atualizaTabela() {
        Pessoa p = new Pessoa();
        return FXCollections.observableArrayList(p.allPessoa());
    }

    public void BuscaPessoatbl() {
        tbPessoaID.setCellValueFactory(new PropertyValueFactory("id"));
        tbPessoaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        tbPessoaCPF.setCellValueFactory(new PropertyValueFactory("CPF"));
        tbPessoaTel.setCellValueFactory(new PropertyValueFactory("Telefone"));
        tbPessoaEmpresa.setCellValueFactory(new PropertyValueFactory("Empresa"));

        tbBuscaPessoa.setItems(buscaPessoa());
    }

    public ObservableList<Pessoa> buscaPessoa() {
        Pessoa p = new Pessoa();
        String where = txtBuscaPessoa.getText();
        p.setWhere(where);
        p.BuscaPessoa();
        return FXCollections.observableArrayList(p.BuscaPessoa());
    }

    private void EditExlui() {
        int ID = Integer.parseInt(txtID.getText());
        String Nome = txtEditNome.getText().toUpperCase();
        String cpf = txtEditCPF.getText();
        String tel = txtEditTel.getText();
        String EMpresa = txtEditEmpresa.getText().toUpperCase();
        switch (EditExlui) {
            case "editar": {
                Pessoa p = new Pessoa(Nome, tel, EMpresa);
                p.setId(ID);
                p.AlteraPessoa();
            }
            break;
            case "excluir": {
                Pessoa p = new Pessoa();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/image/Circle.png").toString()));
                alert.setTitle("Exclusão de dados");
                alert.setHeaderText("Você irá excluir:"
                        + "\nPessoa: " + Nome
                        + "\nCPF: " + cpf
                        + "\nTelefone: " + tel
                        + "\nEmpresa/dpto: " + EMpresa);
                alert.setContentText("A exclusão não poderá ser desfeita!\nDeseja continuar?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    p.setId(ID);
                    p.excluir();

                } else {
                    alert("Exclusão de dados", "Exclusão cancelada!", Alert.AlertType.INFORMATION);
                }
            }
            break;
        }
        EditExlui = "navegar";
        txtBuscaPessoa.setText("");
        iniTable();
    }

    private void navegacao(String tipo) {
        switch (tipo) {
            case "editar": {
                btnExcluir.setDisable(true);
                btnEditar.setDisable(true);
                txtEditCPF.setDisable(true);
                tbBuscaPessoa.setDisable(true);
                btnConfirmar.setDisable(false);
                btnCancelar.setDisable(false);
                txtEditNome.setDisable(false);
                txtEditTel.setDisable(false);
                txtEditEmpresa.setDisable(false);
                EditExlui = "editar";
            }
            break;
            case "excluir": {
                btnEditar.setDisable(true);
                btnExcluir.setDisable(true);
                txtEditCPF.setDisable(true);
                txtEditTel.setDisable(true);
                txtEditEmpresa.setDisable(true);
                txtEditNome.setDisable(true);
                tbBuscaPessoa.setDisable(true);
                btnConfirmar.setDisable(false);
                btnCancelar.setDisable(false);
                EditExlui = "excluir";
            }
            break;
            case "navegar": {
                btnConfirmar.setDisable(true);
                btnCancelar.setDisable(true);
                txtEditCPF.setDisable(true);
                txtEditTel.setDisable(true);
                txtEditEmpresa.setDisable(true);
                txtEditNome.setDisable(true);
                btnExcluir.setDisable(true);
                btnEditar.setDisable(true);
                tbBuscaPessoa.setDisable(false);
                txtID.setText("");
                txtEditNome.setText("");
                txtEditCPF.setText("");
                txtEditTel.setText("");
                txtEditEmpresa.setText("");
                EditExlui = "navegar";
            }
            break;
            case "select": {
                btnConfirmar.setDisable(true);
                btnCancelar.setDisable(true);
                txtEditCPF.setDisable(true);
                txtEditTel.setDisable(true);
                txtEditEmpresa.setDisable(true);
                txtEditNome.setDisable(true);
                tbBuscaPessoa.setDisable(false);
                btnExcluir.setDisable(false);
                btnEditar.setDisable(false);
                EditExlui = "select";
            }
            break;
        }
    }

    private void alert(String title, String tentext, Alert.AlertType type) {
        Alert alert = new Alert(type);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/image/Circle.png").toString()));

        alert.setTitle(title);
        alert.setContentText(tentext);
        alert.setHeaderText(null);
        alert.setWidth(5);
        alert.setX(width / 2 - 150);
        alert.setY(height / 2 - 150);
        alert.showAndWait();
    }

}
