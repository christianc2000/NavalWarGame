/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServidorSocket.Hilos;

import ServidorSocket.Eventos.PingEvent;
import ServidorSocket.Eventos.ServidorSocketEventListener;
import ServidorSocket.ServidorSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Christian
 */
public class HiloPing extends Thread {

    EventListenerList listenerList = new EventListenerList();
    private ServidorSocket SS;

    public HiloPing(ServidorSocket SS) {
        this.SS = SS;
    }

    public synchronized void run() {
        while (true) {
            try {
                this.sleep(2000);
                ConcurrentHashMap<String, Socket> clients = SS.getClientes();
                System.out.println("Clientes conectados en el hilo: " + clients.size());

                for (ConcurrentHashMap.Entry<String, Socket> entry : clients.entrySet()) {
                    String key = entry.getKey();
                    Socket socket = entry.getValue();

                    if (socket.isClosed() || !socket.isConnected()||!isClientConnected(socket)) {
                        System.out.println("Cliente desconectado: " + key);
                        PingEvent evento = new PingEvent(key, this);
                        this.notificarClientePing(evento);
                    } else {
                        System.out.println("conectado: "+isClientConnected(socket));
                        System.out.println("Cliente conectado: " + key);
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloPing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean isClientConnected(Socket clientSocket) {
        try {
            // Realizar una pequeña operación de verificación de conexión, como enviar un ping al cliente
            // Puedes adaptar esta parte según tus necesidades específicas

            InetAddress address = clientSocket.getInetAddress();
            if (address.isReachable(1000)) { // 1000ms timeout
                return true;
            }
        } catch (IOException e) {
            // Manejo de excepciones
        }
        return false;
    }

    //********************************************************************************************************
    public void addMyEventListener(ServidorSocketEventListener listener) {
        listenerList.add(ServidorSocketEventListener.class, listener);
    }

    public void removeMyEventListener(ServidorSocketEventListener listener) {
        listenerList.remove(ServidorSocketEventListener.class, listener);
    }

    void notificarClientePing(PingEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ServidorSocketEventListener.class) {
                ((ServidorSocketEventListener) listeners[i + 1]).onClientPing(evt);
            }
        }
    }
}
