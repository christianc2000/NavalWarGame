/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente.Hilos;

import Cliente.Eventos.ClienteSocketEventListener;
import Cliente.Eventos.MensajeServidorEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Christian
 */
public class HiloEscuchaServidor extends Thread {

    Socket socket;
    BufferedReader entrada;
    EventListenerList listenerList = new EventListenerList();

    public HiloEscuchaServidor(Socket socket) {
        this.socket = socket;
        try {
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(HiloEscuchaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        try {
            String mensaje;
            // Leemos los mensajes del servidor y los imprimimos en la consola

            while ((mensaje = entrada.readLine()) != null) {
               // System.out.println("S: " + mensaje);
                MensajeServidorEvent mse = new MensajeServidorEvent(mensaje, this);
                this.notificarOnMessageServer(mse);
            }
            //System.out.println("se sale del hilo");
        } catch (SocketException e) {

            System.err.println("Se ha perdido la conexión con el servidor: " + e.getMessage());

        } catch (IOException e) {
            if (e.getMessage().equals("Socket closed")) {

            } else {
                e.printStackTrace();
            }
        } finally {
            System.out.println("Cliente desconectado desde el hilo escucha mensaje, cerrando el hilo...");

            try {
                entrada.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//********************************************************************************
    public void addMyEventListener(ClienteSocketEventListener listener) {
        listenerList.add(ClienteSocketEventListener.class, listener);
    }

    public void removeMyEventListener(ClienteSocketEventListener listener) {
        listenerList.remove(ClienteSocketEventListener.class, listener);
    }

    void notificarOnMessageServer(MensajeServidorEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ClienteSocketEventListener.class) {
                ((ClienteSocketEventListener) listeners[i + 1]).onServerMessage(evt);
            }
        }
    }
}
