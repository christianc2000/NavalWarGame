/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metodos;

/**
 *
 * @author Christian
 */
public class Metodos {

    public boolean contieneComando(String comando, String data) {
        for (int i = 0; i < comando.length(); i++) {
            if (data.charAt(i) != comando.charAt(i)) {
                return false;
            }
        }
        return true;
    }
//CLIENTE

    public String[] ParsearMensajeFromClient(String mensaje) {
        //TIPO MENSAJE, DATOS
        String msj[] = mensaje.split(",");
        if (msj.length == 2) {
            if (msj[0].equals("REGISTRO") || msj[0].equals("LOGIN") || msj[0].equals("JUGADA") || msj[0].equals("INICIO")) {
                return msj;
            }
        }
        return null;
    }
//SERVIDOR

    public String[] ParsearMensajeFromServer(String mensaje) {
        //TIPO MENSAJE, DATOS

        String msj[] = mensaje.split(",");
        if (msj.length == 2) {
            if (msj[0].equals("REGISTRO") || msj[0].equals("LOGIN")) {
                String data[] = msj[1].split(":");
                if (data.length == 3) {
                    if (contieneComando("ID", data[0]) && contieneComando("NICKNAME", data[1]) && contieneComando("PASSWORD", data[2])) {
                        data[0] = data[0].substring(3, data[0].length());
                        data[1] = data[1].substring(9, data[1].length());
                        data[2] = data[2].substring(9, data[2].length());
                        return data;
                    }
                }
            }
            if (msj[0].equals("JUGADA")) {
                String data[] = msj[1].split(":");
                if (data.length == 2) {
                    if (contieneComando("ID", data[0]) && contieneComando("MOVIDA", data[1])) {
                        data[0] = data[0].substring(3, data[0].length());
                        data[1] = data[1].substring(7, data[1].length());
                        return data;
                    }
                }
            }
        }
        return null;
    }
}
