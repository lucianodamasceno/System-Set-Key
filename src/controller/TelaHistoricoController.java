/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Chave;
import model.Historico;

/**
 * FXML Controller class
 *
 * @author Luciano
 */
public class TelaHistoricoController extends MainController {

    int chave;


    @FXML // TABELA CHAVE
    private TableView<Chave> tbBuscaChave;

    @FXML
    private TableColumn<Chave, Integer> tbChaveChave;

    @FXML
    private TableColumn<Chave, String> tbChaveLocal;

    @FXML
    private TableColumn<Chave, String> tbChaveDPTO;

    @FXML // TABELA HISTORICO
    private TableView<Historico> tblHistorico;

    @FXML
    private TableColumn<Historico, String> tblHistoricoNome;

    @FXML
    private TableColumn<Historico, String> tblHistoricoCPF;

    @FXML
    private TableColumn<Historico, String> tblHistoricoTel;

    @FXML
    private TableColumn<Historico, String> tblHistoricoEmpresa;

    @FXML
    private TableColumn<Historico, String> tblHistoricoDiaSaida;

    @FXML
    private TableColumn<Historico, String> tblHistoricoHoraSaida;

    @FXML
    private TableColumn<Historico, String> tblHistoricoDiaRetorno;

    @FXML
    private TableColumn<Historico, String> tblHistoricohoraRetorno;

    @FXML
    private AnchorPane tbPane;

    @FXML
    private TextField txtBuscaChave;

    @FXML
    private void selectLinha(MouseEvent event) {
        chave = tbBuscaChave.getSelectionModel().getSelectedItem().getNumChave();
        atualizaTabelaHistorico();
    }

    @FXML
    private void enterBuscaChave(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            Busca();
        }
    }

    @FXML
    private void Busca() {
        tbBuscaChave.setItems(BuscaChave());
    }

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        atualizaTabelaHistorico();
    }

    public ObservableList<Chave> BuscaChave() {
        Chave c = new Chave();
        String chave = txtBuscaChave.getText();
        c.setBuscaChave(chave);
        return FXCollections.observableArrayList(c.BuscaChave());
    }

    private void initTable() {
        tbChaveChave.setCellValueFactory(new PropertyValueFactory("NumChave"));
        tbChaveLocal.setCellValueFactory(new PropertyValueFactory("local"));
        tbChaveDPTO.setCellValueFactory(new PropertyValueFactory("departamento"));

        tbBuscaChave.setItems(atualizaTabelaChave());
    }

    public ObservableList<Chave> atualizaTabelaChave() {
        Chave c = new Chave();
        c.setNumChave(chave);
        c.BuscaChave();
        return FXCollections.observableArrayList(c.allChave());
    }

    private void atualizaTabelaHistorico() {
        tblHistoricoNome.setCellValueFactory(new PropertyValueFactory("nome"));
        tblHistoricoCPF.setCellValueFactory(new PropertyValueFactory("cpf"));
        tblHistoricoTel.setCellValueFactory(new PropertyValueFactory("telefine"));
        tblHistoricoEmpresa.setCellValueFactory(new PropertyValueFactory("empresa"));
        tblHistoricoDiaSaida.setCellValueFactory(new PropertyValueFactory("diaSaida"));
        tblHistoricoHoraSaida.setCellValueFactory(new PropertyValueFactory("horaSaida"));
        tblHistoricoDiaRetorno.setCellValueFactory(new PropertyValueFactory("diaRetorno"));
        tblHistoricohoraRetorno.setCellValueFactory(new PropertyValueFactory("horaRetorno"));

        tblHistorico.setItems(tbHistorico());
    }

    public ObservableList<Historico> tbHistorico() {

        Historico h = new Historico();
        h.setChave(chave);
        return FXCollections.observableArrayList(h.allHistorico());
    }

    private void listHistorico() {
        Historico h = new Historico();
        h.setChave(chave);

    }

}
