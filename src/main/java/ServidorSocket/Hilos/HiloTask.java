/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServidorSocket.Hilos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian
 */
public class HiloTask extends Thread {

    public void sendMessageSocket(Socket socket, String mensaje) {

        PrintWriter salida;
        try {
            salida = new PrintWriter(socket.getOutputStream(), true);
            salida.println(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
