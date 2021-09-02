/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Calendar;

/**
 *
 * @author luciano
 */
public class DateTime {

    private Calendar c = Calendar.getInstance();
    private int intDia;
    private int intMes;
    private int intAno;
    private int intHora;
    private int intMinuto;
    private int intSegundo;
    private String strDia;
    private String strMes;
    private String strAno;
    private String strHora;
    private String strMinuto;
    private String strSegundo;
    private String zero = "0";

    public String getDia() {
        intDia = c.get(Calendar.DAY_OF_MONTH);
        if (intDia < 10) {
            strDia = zero + Integer.toString(intDia);
        } else {
            strDia = Integer.toString(intDia);
        }
        intMes = c.get(Calendar.MONTH);
        intMes += 1;
        if (intMes < 10) {
            strMes = zero + Integer.toString(intMes);
        } else {
            strMes = Integer.toString(intMes);
        }
        intAno = c.get(Calendar.YEAR);
        strAno = Integer.toString(intAno);
        return strDia + "/" + strMes + "/" + strAno;
    }

    public String getHora() {
        intHora = c.get(Calendar.HOUR_OF_DAY);
        if (intHora < 10) {
            strHora = zero + Integer.toString(intHora);
        } else {
            strHora = Integer.toString(intHora);
        }
        intMinuto = c.get(Calendar.MINUTE);
        if (intMinuto < 10) {
            strMinuto = zero + Integer.toString(intMinuto);
        } else {
            strMinuto = Integer.toString(intMinuto);
        }
        intSegundo = c.get(Calendar.SECOND);
        if(intSegundo <10){
            strSegundo = zero + Integer.toString(intSegundo);
        }else{
            strSegundo = Integer.toString(intSegundo);
        }
        return strHora + ":" + strMinuto+":"+strSegundo;
    }

} // FINAL
