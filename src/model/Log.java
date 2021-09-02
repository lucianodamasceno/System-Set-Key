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
    
    public void criaDoc(String versao, String dev){
        DateTime dt = new DateTime();
        String initDia = dt.getDia();
        String initHora = dt.getHora();
        
        String doc = "*** Log de edições do System Set Key\n"
                     + "*** Versão "+versao+";\n"
                     + "*** Inicialização: "+initDia+"|"+initHora+";\n"
                     + "*** Criado e desenvolvido por: "+dev+";\n\n"
                     + "********************************* LOG DE EDIÇÕES ********************************* \n\n";
        
        FileWriter fw = null;
        File file = new File("log.txt");
        if(file.exists()){
            ///
        }else{
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
    public void editar( String data, String hora, int chaveA,String dptoA, String locaA, int chaveN, String localN,String dptoN ){
//        String log = "["+data+";"+hora+"]["+chaveA+";"+locaA+""+dptoA+"]editado->["+chaveN+";"+localN+";"+dptoN+"];";
        String log = String.format("[%s;%s][%s;%s;%s]editado->[%s;%s;%s];",data,hora,chaveA, dptoA, locaA, chaveN, localN, dptoN);
        FileWriter fw;
        File File = new File("log.txt");
        try {
            fw = new FileWriter("log.txt",true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(log);
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    public void excluir( String data, String hora, int chaveA, String locaA, String dpto){
//        String log = "["+data+";"+hora+"]["+chaveA+";"+locaA+"]excluido;";
String log = String.format("[%s;%s][%s;%s;%s]",data,hora, chaveA, locaA, dpto);
        FileWriter fw;
        File File = new File("log.txt");
        try {
            fw = new FileWriter("log.txt",true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(log);
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
