/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Access.Connection;

/**
 *
 * @author Luciano
 */
public class Log {

    public void criaDoc(String versao, String dev) {
        DateTime dt = new DateTime();
        String initDia = dt.getDia();
        String initHora = dt.getHora();

        String doc = "*** Log de edições do System Set Key\n"
                + "*** Versão " + versao + "\n"
                + "*** Inicialização: " + initDia + "|" + initHora + "\n"
                + "*** Criado e desenvolvido por: " + dev + "\n\n"
                + "********************************* LOG DE EDIÇÕES ********************************* \n\n";

        FileWriter fw = null;
        File file = new File("log.txt");
        if (file.exists()) {
        } else {
            try {
                fw = new FileWriter("log.txt");
            } catch (IOException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
            PrintWriter pw = new PrintWriter(fw);
            pw.println(doc);
            pw.close();
        }
    }

    public void editar(String data, String hora, int chaveA, String dptoA, String locaA, int chaveN, String localN, String dptoN) {

        int lengthA = String.valueOf(chaveA).length() + locaA.length() + dptoA.length();
        int lenghtB = String.valueOf(chaveN).length() + localN.length() + dptoN.length();
        int length_ = 0;
        String nSubli = "";
        String subli = "_";

        if (lengthA > lenghtB) {
            length_ = lengthA + 40;
        } else {
            length_ = lenghtB + 40;
        }
        for (int i = 0; i < length_; i++) {
            nSubli += subli;
        }
        String log = String.format(""
                + "%s\n[%s;%s] -> edição\n"
                + "[Chave: %s; Local: %s; Departamento: %s]\n"
                + "[Chave: %s; Local: %s; Departamento: %s]", nSubli, data, hora, chaveA, dptoA, locaA, chaveN, localN, dptoN);
        FileWriter fw;
        try {
            fw = new FileWriter("log.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(log);
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(String data, String hora, int chaveA, String locaA, String dpto) {
        int length_ = String.valueOf(chaveA).length() + locaA.length() + dpto.length() + 40;
        String subli = "_";
        String nSubli = "";
        for (int i = 0; i < length_; i++) {
            nSubli += subli;
        }

        String log = String.format(
                "%s\n[%s;%s] -> exclusão\n"
                + "[Chave: %s; Local: %s; Departamento: %s]", nSubli, data, hora, chaveA, locaA, dpto);
        FileWriter fw;
        try {
            fw = new FileWriter("log.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(log);
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
