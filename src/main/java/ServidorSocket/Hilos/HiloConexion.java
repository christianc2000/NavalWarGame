/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServidorSocket.Hilos;

import ServidorSocket.Eventos.ConexionEvent;
import ServidorSocket.Eventos.MensajeEvent;
import ServidorSocket.Eventos.ServidorSocketEventListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Christian
 */
public class HiloConexion extends Thread {
    
    ServerSocket SS;
    protected EventListenerList listenerList = new EventListenerList();
    
    public HiloConexion(ServerSocket SS) {
        this.SS = SS;
    }
    
    public void run() {
        while (true) {
            try {
                Socket skClient = SS.accept();
                //System.out.println("Nuevo Cliente Conectado....");
                ConexionEvent ev = new ConexionEvent(skClient);
                this.notificarClienteConectado(ev);
                
            } catch (IOException ex) {
                Logger.getLogger(HiloConexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //********************************************************************************************************
    public void addMyEventListener(ServidorSocketEventListener listener) {
        listenerList.add(ServidorSocketEventListener.class, listener);
    }

    public void removeMyEventListener(ServidorSocketEventListener listener) {
        listenerList.remove(ServidorSocketEventListener.class, listener);
    }

    void notificarClienteConectado(ConexionEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ServidorSocketEventListener.class) {
                ((ServidorSocketEventListener) listeners[i + 1]).onClientConnected(evt);
            }
        }
    }
}
