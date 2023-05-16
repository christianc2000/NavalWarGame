/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente.Hilos;

import Cliente.Eventos.ClienteSocketEventListener;
import Cliente.Eventos.ReconexionEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Christian
 */
public class HiloReconexion extends Thread {

    EventListenerList listenerList = new EventListenerList();
    private final String host;
    private final int puerto;

    public HiloReconexion(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;

    }

    @Override
    public void run() {
        Socket socket;
        while (true) {

            try {
                this.sleep(3000);
                try {
                    socket = new Socket(host, puerto);
                    ReconexionEvent ecc = new ReconexionEvent(socket, this);
                    this.notificarOnConexion(ecc);
                    break;
                } catch (IOException e) {
                    // Si hay un error al conectar, seguimos intentando
                    System.out.println("intentando conectar......");
                    //System.out.println(e);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloReconexion.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println("finaliza Hilo Conexion cliente");
    }
//********************************************************************************

    public void addMyEventListener(ClienteSocketEventListener listener) {
        listenerList.add(ClienteSocketEventListener.class, listener);
    }

    public void removeMyEventListener(ClienteSocketEventListener listener) {
        listenerList.remove(ClienteSocketEventListener.class, listener);
    }

    void notificarOnConexion(ReconexionEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ClienteSocketEventListener.class) {
                ((ClienteSocketEventListener) listeners[i + 1]).onClientReconexion(evt);
            }
        }
    }

}
