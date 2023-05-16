/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServidorSocket;

import ServidorSocket.Eventos.ConexionEvent;
import ServidorSocket.Eventos.MensajeEvent;
import ServidorSocket.Eventos.PingEvent;
import ServidorSocket.Eventos.ServidorSocketEventListener;
import ServidorSocket.Hilos.HiloConexion;
import ServidorSocket.Hilos.HiloMensaje;
import ServidorSocket.Hilos.HiloPing;
import ServidorSocket.Hilos.HiloTask;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian
 */
public class ServidorSocket implements ServidorSocketEventListener {

    ServerSocket serverSocket;
    private ConcurrentHashMap<String, Socket> Clientes = new ConcurrentHashMap<>();

    public ServidorSocket(int puerto) {
        try {
            this.serverSocket = new ServerSocket(puerto);
            System.out.println("ping");
        } catch (IOException ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initServer() {
        if (serverSocket != null) {
            HiloPing ping = new HiloPing(this);
            ping.addMyEventListener(this);
            ping.start();
            System.out.println("Servidor iniciado exitosamente");
            HiloConexion hc = new HiloConexion(serverSocket);
            hc.addMyEventListener(this);
            hc.start();

        }
    }

    public synchronized ConcurrentHashMap<String, Socket> getClientes() {
        return Clientes;
    }

    public void sendMessageId(String Id, String mensaje) {
        //ExecutorService executor = Executors.newFixedThreadPool(1);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        HiloTask task = new HiloTask();
        Socket socket = (Socket) Clientes.get(Id);
        executor.execute(() -> task.sendMessageSocket(socket, mensaje));

    }

    public String agregarCliente(Socket socket) {

        String clienteId = generarClienteId();
        Clientes.put(clienteId, socket); // Agregar el cliente al mapa usando el identificador único como clave
        return clienteId;
    }

    public String generarClienteId() {
        return UUID.randomUUID().toString(); // Utilizar la clase UUID para generar un identificador único
    }

    @Override
    public void onClientConnected(ConexionEvent ev) {
        String clienteId = agregarCliente(ev.getSocket());
        sendMessageId(clienteId, "INICIO," + clienteId);
        HiloMensaje hm = new HiloMensaje(ev.getSocket());
        hm.addMyEventListener(this);
        hm.start();

        System.out.println("Cliente nuevo conectado con ID: " + clienteId);
        System.out.println("*********************************************************");
        System.out.println("Clientes conectados: " + Clientes.size());
    }

    @Override
    public void onClientMessage(MensajeEvent ev) {
        System.out.println("mensaje: " + ev.getMensaje());
    }

    @Override
    public void onClientPing(PingEvent ev) {
        Clientes.remove(ev.getId());
        System.out.println("*********************************************************");
        System.out.println("Se removió el cliente: " + ev.getId());
        System.out.println("clientes conectados: " + Clientes.size());
    }

    public static void main(String[] args) {
        ServidorSocket ss = new ServidorSocket(5000);
        ss.initServer();
    }

}
