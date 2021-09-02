/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Chave;
import model.DateTime;
import model.Log;
import model.Pessoa;
import model.emUso;

/**
 * FXML Controller class
 *
 * @author Luciano
 */
public class MainController implements Initializable {

    private boolean WindowGerChaveOpend;
    private static boolean WindowGerPessoaOpend;
    private static boolean WindowsHistorico;
    private int idChave;
    private int idPessoa;
    private int idHistorico;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final int width = gd.getDisplayMode().getWidth();
    private final int height = gd.getDisplayMode().getHeight();
    private int TotUso;
    private int posC;
    private int posP;
    private int posE;
    private int delay = 0;

    // TABELA CHAVE
    @FXML
    private TableView<Chave> tbBuscaChave;

    @FXML
    private TextField txtBuscaChave;

    @FXML
    private TableColumn<Chave, Integer> tbChaveChave;

    @FXML
    private TableColumn<Chave, String> tbChaveLocal;

    @FXML
    private TableColumn<Chave, String> tbChaveDPTO;

    @FXML
    private TableColumn<Chave, String> tbChaveDisponivel;

    // TABELA PESSOA
    @FXML
    private TableView<Pessoa> tbBuscaPessoa;

    @FXML
    private TextField txtBuscaPessoa;

    @FXML
    private TableColumn<Pessoa, Integer> tbPessoaID;

    @FXML
    private TableColumn<Pessoa, String> tbPessoaNome;

    @FXML
    private TableColumn<Pessoa, String> tbPessoaCPF;

    @FXML
    private TableColumn<Pessoa, String> tbPessoaTelefone;

    @FXML
    private TableColumn<Pessoa, String> tbPessoaEmpresa;

    //TABELA EM USO
    @FXML
    private TableView<emUso> tblEmUso;

    @FXML
    private TableColumn<emUso, Integer> tblEmUsoidHistorico;

    @FXML
    private TableColumn<emUso, Integer> tbEmUsoChave;

    @FXML
    private TableColumn<emUso, String> tbEmUsoLocal;

    @FXML
    private TableColumn<emUso, String> tbEmUsoPessoa;

    @FXML
    private TableColumn<emUso, String> tbEmUsoDia;

    @FXML
    private TableColumn<emUso, String> tbEmUsoHora;

    // BOTOES E MENUS
    @FXML
    private MenuItem btnGerenciaChave;

    @FXML
    private MenuItem btnGerenciaPessoa;

    @FXML
    private Button btnCautelar;

    @FXML
    private Button btnBaixa;

    @FXML
    private MenuBar menuSuperior;

    @FXML
    private Circle c1;

    @FXML
    private Circle c2;

    @FXML
    private Circle c3;

    @FXML
    private Label lblTotalUso;

    @FXML
    void GerenciaChave(ActionEvent event) {
        if (WindowGerChaveOpend == true) {
            Alert("Abrir janela", "Janela já está aberta!", Alert.AlertType.INFORMATION);
        } else {
            WindowGerChaveOpend = true;
            TelaGerenciaChave();
        }
    }

    @FXML
    void GerenciaPessoa(ActionEvent event) {
        if (WindowGerPessoaOpend == true) {
            Alert("Abrir janela", "Janela já está aberta!", Alert.AlertType.INFORMATION);
        } else {
            WindowGerPessoaOpend = true;
            TelaGerenciaPessoa();
        }
    }

    @FXML
    void Historico(ActionEvent event) {
        if (WindowsHistorico == true) {
            Alert("Abrir janela", "Janela já está aberta!", Alert.AlertType.INFORMATION);

        } else {
            WindowsHistorico = true;
            TelaHistorico();
        }
    }

    @FXML
    private void Cautela(ActionEvent e) {
        CautelaChave();
    }

    @FXML
    private void Baixa(ActionEvent e) {
        BaixaChave();
    }
    @FXML
    private Menu MenuGerenciaDados;

    @FXML
    private MenuItem btnHistorico;

    @FXML
    private void selectLinhaChave(MouseEvent e) {
        posC = (int) e.getY();
        try {
            if (e.getButton().equals(MouseButton.SECONDARY)) {
                idChave = tbBuscaChave.getSelectionModel().getSelectedItem().getNumChave();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/image/Circle.png").toString()));
                alert.setTitle("Forar baixa");
                alert.setHeaderText("Forçar baixa é utilizado quando chave não aparece na tabela em uso e não"
                        + " deixa ser cautelada!");
                alert.setContentText("Ao confirmar, a hora e data atual será inserida "
                        + "no histórico da chave selecionada!\nDeseja continuar?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    Chave c = new Chave();
                    c.setWhere(idChave);
                    c.forceBaixa();
                    initTableChave();
                    initTableEmUso();
                    initTablePessoa();
                    idChave = 0;
                    idPessoa = 0;
                    txtBuscaChave.setText("");
                    txtBuscaPessoa.setText("");
                }
            } else {
                idChave = tbBuscaChave.getSelectionModel().getSelectedItem().getNumChave();
            }
        } catch (Exception ex) {
        }
    }

    @FXML
    private void selectLinhaPessoa(MouseEvent e) {
        posP = (int) e.getY();
        try {
            idPessoa = tbBuscaPessoa.getSelectionModel().getSelectedItem().getId();
        } catch (Exception ex) {
        }
    }

    @FXML
    private void selectLinhaUso(MouseEvent e) {
        posE = (int) e.getY();

        try {
            if (e.getButton().equals(MouseButton.SECONDARY)) {
                int id = tblEmUso.getSelectionModel().getSelectedItem().getIdHistorico();
                Pessoa p = new Pessoa();
                p.setId(id);
                String pessoa = p.BuscaPessoaTooTip();
                Alert("Retirado por", pessoa, Alert.AlertType.INFORMATION);
            } else {
                idHistorico = tblEmUso.getSelectionModel().getSelectedItem().getIdHistorico();
                idChave = tblEmUso.getSelectionModel().getSelectedItem().getChave();
            }
        } catch (Exception ex) {
        }
    }

    @FXML
    private void cmdEnterChave(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tbBuscaChave.setItems(BuscaChave());
        }
    }

    @FXML
    private void cmdEnterPessoa(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            BuscaPessoatbl();
        }
    }

    @FXML
    private void cmdChaveNull() {
        tbBuscaChave.setItems(BuscaChave());
    }

    @FXML
    private void cmdPessoaNull() {
        BuscaPessoatbl();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        setRotate(c1, 360, 1, 1, false);
        setRotate(c2, -360, 1, 1, false);
        setRotate(c3, 360, 1, 1, false);

        setRotate(c1, 360, 1, 1, true);
        setRotate(c2, -360, 1, 3, true);
        setRotate(c3, 360, 1, 4, true);
        
        boot();
    }

    public void boot() {
        initTableChave();
        initTablePessoa();
        initTableEmUso();
        lblTotalUso.setText(Integer.toString(TotUso));
    }

    public void setRotate(Circle c, int angle, int duration, int delay, boolean infinite) {
        RotateTransition rt = new RotateTransition(Duration.seconds(duration), c);

        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(delay));
        if (infinite == true) {
            rt.setRate(0.3);
            rt.setCycleCount(2);
            rt.setCycleCount(TranslateTransition.INDEFINITE);
            rt.setAutoReverse(true);
        } else {
            rt.setRate(10);
            rt.setCycleCount(5);
        }
        rt.play();
    }

    private void CautelaChave() {
        emUso uso = new emUso();
        uso.setChave(idChave);
        uso.setIdPessoa(idPessoa);
        if (idChave <= 0) {
            Alert("Cautela", "Chave não selecionada!", Alert.AlertType.INFORMATION);

        } else if (idPessoa <= 0) {
            Alert("Cautela", "Pessoa não selecionada!", Alert.AlertType.INFORMATION);

        } else if (idChave <= 0 && idPessoa <= 0) {
            Alert("Cautela", "Chave e pessoa não selecionados!", Alert.AlertType.INFORMATION);

        } else {

            if (uso.checaDispo() == true) {
                DateTime dt = new DateTime();
                String dia = dt.getDia();
                String hora = dt.getHora();
                uso.setDiaSaida(dia);
                uso.setHoraSaida(hora);
                uso.cautela();
                boot();
                setRotate(c2, 360, 1, 0, false);
                idChave = 0;
                idPessoa = 0;
                idHistorico = 0;
                txtBuscaChave.setText("");
                txtBuscaPessoa.setText("");
            }
        }
    }

    private void BaixaChave() {
        if (idHistorico <= 0) {
            Alert("Baixa", "Dados não selecionados!", Alert.AlertType.INFORMATION);
        } else {
            DateTime dt = new DateTime();
            String diaR = dt.getDia();
            String horaR = dt.getHora();

            emUso uso = new emUso();
            uso.setIdHistorico(idHistorico);
            uso.setChave(idChave);

            uso.setDiaRetorno(diaR);
            uso.setHoraRetorno(horaR);
            uso.descautela();
            setRotate(c3, 360, 1, 0, false);
            idPessoa = 0;
            idHistorico = 0;
            txtBuscaChave.setText("");
            txtBuscaPessoa.setText("");
            boot();
        }
    }

    private void closeChave() {
        WindowGerChaveOpend = false;
        initTableChave();
    }

    private void TelaGerenciaChave() {
        Stage stage = new Stage();
        try {
            stage.setOnCloseRequest(event -> closeChave());

            Parent fxmlGereneciaChave = FXMLLoader.load(getClass().getResource("/view/TelaGerenciaChave.fxml"));
            Scene gerenciaChave = new Scene(fxmlGereneciaChave, 600, 500);
            Image icon = new Image(getClass().getResourceAsStream("/image/chaveIco.png"));
            stage.getIcons().add(icon);
            stage.setScene(gerenciaChave);
            stage.setTitle("GERÊNCIA DE CHAVE");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Alert("Tela gerenência chave", "Erro ao abrir janela", Alert.AlertType.ERROR);
        }
    }

    private void closePessoa() {
        WindowGerPessoaOpend = false;
        initTablePessoa();
    }

    private void TelaGerenciaPessoa() {
        Stage stage = new Stage();
        try {
            stage.setOnCloseRequest(event -> closePessoa());

            Parent fxmlGereneciaPessoa = FXMLLoader.load(getClass().getResource("/view/TelaGerenciaPessoa.fxml"));
            Scene gerenciaChave = new Scene(fxmlGereneciaPessoa, 600, 600);
            Image icon = new Image(getClass().getResourceAsStream("/image/peopleIco.png"));
            stage.getIcons().add(icon);
            stage.setScene(gerenciaChave);
            stage.setTitle("GERÊNCIA DE PESSOA");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Alert("Tela gerenência pessoa", "Erro ao abrir janela", Alert.AlertType.ERROR);
        }
    }

    private void TelaHistorico() {
        Stage stage = new Stage();
        try {
            stage.setOnCloseRequest(event -> WindowsHistorico = false);

            Parent fxmlTelaHistorico = FXMLLoader.load(getClass().getResource("/view/TelaHistorico.fxml"));
            Scene gerenciaChave = new Scene(fxmlTelaHistorico, 1000, 560);
            Image icon = new Image(getClass().getResourceAsStream("/image/historiaIco.png"));
            stage.getIcons().add(icon);
            stage.setScene(gerenciaChave);
            stage.setTitle("HISTÓRICO");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Alert("Tela histórico", "Erro ao abrir janela", Alert.AlertType.ERROR);

        }
    }

    private void initTableEmUso() {

        tblEmUsoidHistorico.setCellValueFactory(new PropertyValueFactory("idHistorico"));
        tbEmUsoChave.setCellValueFactory(new PropertyValueFactory("chave"));
        tbEmUsoLocal.setCellValueFactory(new PropertyValueFactory("local"));
        tbEmUsoPessoa.setCellValueFactory(new PropertyValueFactory("pessoa"));
        tbEmUsoDia.setCellValueFactory(new PropertyValueFactory("diaSaida"));
        tbEmUsoHora.setCellValueFactory(new PropertyValueFactory("horaSaida"));

        tblEmUso.setItems(tabelaEmUso());
//        lblTotalUso.setText(Integer.toString(TotUso));

    }

    private ObservableList<emUso> tabelaEmUso() {
        emUso EmUso = new emUso();
        TotUso = EmUso.Tot();
        return FXCollections.observableArrayList(EmUso.AllemUso());
    }

    public void initTablePessoa() {
        tbPessoaID.setCellValueFactory(new PropertyValueFactory("id"));
        tbPessoaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        tbPessoaCPF.setCellValueFactory(new PropertyValueFactory("CPF"));
        tbPessoaTelefone.setCellValueFactory(new PropertyValueFactory("Telefone"));
        tbPessoaEmpresa.setCellValueFactory(new PropertyValueFactory("Empresa"));

        tbBuscaPessoa.setItems(tabelaPessoa());
    }

    private ObservableList<Pessoa> tabelaPessoa() {
        Pessoa p = new Pessoa();
        return FXCollections.observableArrayList(p.allPessoa());
    }

    public void BuscaPessoatbl() {
        tbPessoaID.setCellValueFactory(new PropertyValueFactory("id"));
        tbPessoaNome.setCellValueFactory(new PropertyValueFactory("nome"));
        tbPessoaCPF.setCellValueFactory(new PropertyValueFactory("CPF"));
        tbPessoaTelefone.setCellValueFactory(new PropertyValueFactory("Telefone"));
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

    public void initTableChave() {
        tbChaveChave.setCellValueFactory(new PropertyValueFactory("NumChave"));
        tbChaveLocal.setCellValueFactory(new PropertyValueFactory("local"));
        tbChaveDPTO.setCellValueFactory(new PropertyValueFactory("departamento"));
        tbChaveDisponivel.setCellValueFactory(new PropertyValueFactory<>("disponivel"));
        customiseFactory(tbChaveLocal);

        tbBuscaChave.setItems(tabelaChave());

        customiseFactory(tbChaveLocal);

    }

    public ObservableList<Chave> tabelaChave() {
        Chave c = new Chave();
        return FXCollections.observableArrayList(c.allChave());
    }

    private void customiseFactory(TableColumn<Chave, String> calltypel) {
        calltypel.setCellFactory((TableColumn<Chave, String> column) -> {
            return new TableCell<Chave, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<Chave> currentRow = getTableRow();

                    if (!isEmpty()) {

                        if (item.contains("*")) {
                            currentRow.setStyle("-fx-text-background-color:red");
                        } else {
                            currentRow.setStyle(
                                    ".table-row-cell .table-cell{-fx-text-fill: black;}");
                        }
                    }
                }
            };
        });
    }

    public ObservableList<Chave> BuscaChave() {
        Chave c = new Chave();
        String chave = txtBuscaChave.getText();
        c.setBuscaChave(chave);
        c.BuscaChave();
        return FXCollections.observableArrayList(c.BuscaChave());
    }

    @FXML
    public void Sobre() {
        
      Main main = new Main();
      String nameSys = main.getNameSystem();
      String dev = main.getDev();
      String versao = main.getVersao();
      String email = "luciano.galdinosilva@hotmail.com";
      String cel = "(11)94861-5196";
      
       
        String sobre = "Sistema de controle de gerência de chaves\n\nDesenvolvido por: "+dev;
        sobre += sobre = "\nContato: "+email+" "+cel+ "\n\nVersão "+versao;
        Alert("Sobre o System Set Key:", sobre, Alert.AlertType.INFORMATION);
    }

    private void Alert(String Tittle, String ContentText, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/image/Circle.png").toString()));

        alert.setTitle(Tittle);
        alert.setContentText(ContentText);
        alert.setHeaderText(null);
        alert.setWidth(8);
        alert.setX(width / 2 - 150);
        alert.setY(height / 2 - 150);
        alert.showAndWait();
    }
}
