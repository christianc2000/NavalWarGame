/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente.Hilos;

import Cliente.Eventos.ClienteSocketEventListener;
import Cliente.Eventos.PingEvent;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Christian
 */
public class HiloPing extends Thread {

    Socket socket;
    EventListenerList listenerList = new EventListenerList();

    public HiloPing(Socket socket) {
        this.socket = socket;
    }

    public void verifica() {
        while (true) {
            try {
                this.sleep(3000);
                if (socket.isClosed() || !socket.isConnected()) {
                    System.out.println("socket desconectado");
                    PingEvent evento = new PingEvent(this);
                    this.notificarOnDesconexion(evento);
                    break;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloPing.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @Override
    public void run() {
        verifica();
    }

    //*******************************************
    public void addMyEventListener(ClienteSocketEventListener listener) {
        listenerList.add(ClienteSocketEventListener.class, listener);
    }

    public void removeMyEventListener(ClienteSocketEventListener listener) {
        listenerList.remove(ClienteSocketEventListener.class, listener);
    }

    void notificarOnDesconexion(PingEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ClienteSocketEventListener.class) {
                ((ClienteSocketEventListener) listeners[i + 1]).onClientPing(evt);
            }
        }
    }
}
