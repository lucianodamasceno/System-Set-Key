/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
/**
 *
 * @author luciano
 */
public class ValidaCPF {

    public boolean validaCPF(String CPF) {
        
        String[] nCPF = CPF.split("[.-]");
        char[] NCPF = new char[11];
        int i = 0;
        int j = 10;
        int soma1 = 0;
        int soma2 = 0;
        int[] intCPF = new int[11];
        CPF ="";
        for(i=0;i<4;i++){
            CPF += nCPF[i];            
        }
        i =0;
        for (i = 0; i < 11; i++) {
            NCPF[i] = CPF.charAt(i);
            intCPF[i] = Character.getNumericValue(NCPF[i]);
        }
        i = 0;
        while (j >1) {
            soma1 += intCPF[i] * j;
            j--;
            i++;
        }
        int resto1 = (11-(soma1%11));

        if(resto1 >=10){
            resto1 = 0;
        }
        
        i = 0;
        j = 11;
        intCPF[10] = resto1;
        
        
         while (j >1) {
            soma2 += intCPF[i] * j;
            j--;
            i++;
        }
         int resto2 = (11-(soma2%11));
        
         if(resto2 >=10){
             resto2 =0;
         }
         i=0;
         for (i = 0; i < 11; i++) {
            NCPF[i] = CPF.charAt(i);
            intCPF[i] = Character.getNumericValue(NCPF[i]);
        }
         if(resto1 == intCPF[9] && resto2 == intCPF[10]){
             return true;
         }
         else{
             return false;
         }        
    }

}
