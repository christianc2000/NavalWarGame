/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import Cliente.Eventos.ClienteSocketEventListener;
import Cliente.Eventos.MensajeServidorEvent;
import Cliente.Eventos.PingEvent;
import Cliente.Eventos.ReconexionEvent;
import Cliente.Hilos.HiloEscuchaServidor;
import Cliente.Hilos.HiloPing;
import Cliente.Hilos.HiloReconexion;
import Metodos.Metodos;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian
 */
public class Cliente implements ClienteSocketEventListener {

    private String id;
    String host;
    int puerto;
    Socket socket;
    Metodos m;
    BufferedReader entrada;
    PrintWriter salida;
    HiloEscuchaServidor hes;
    HiloPing hp;
    HiloReconexion hr;

    public Cliente(String host, int puerto) {

        this.host = host;
        this.puerto = puerto;
        m = new Metodos();
    }

    public void reconexion(Socket socket) {
        this.socket = socket;
        try {
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.salida = new PrintWriter(socket.getOutputStream(), true);
            // hp.removeMyEventListener(this);
            hp = new HiloPing(socket);
            hp.addMyEventListener(this);
            hp.start();
            // hes.removeMyEventListener(this);
            hes = new HiloEscuchaServidor(socket);
            hes.addMyEventListener(this);
            hes.start();
        } catch (SocketException e) {
            // Manejo de la excepción SocketException
            System.out.println("Se ha cerrado la conexión del cliente de manera abrupta.");
            // Puedes cerrar los recursos y finalizar el hilo de manera adecuada aquí
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setId(String id) {
        this.id = id;
    }

    public void initCliente() {

        System.out.println("iniciando cliente....");
        this.hr = new HiloReconexion(host, puerto);
        this.hr.addMyEventListener(this);
        this.hr.start();

    }

    public static void main(String[] args) {
        Cliente c = new Cliente("localhost", 5000);
        c.initCliente();
    }

    @Override
    public void onServerMessage(MensajeServidorEvent ev) {
        System.out.println("mensaje: " + ev.getMensaje());
        String data[] = m.ParsearMensajeFromClient(ev.getMensaje());
        if (data[0] != null) {
            switch (data[0]) {
                case "INICIO":
                    this.id = data[1];
                    System.out.println("ID CLIENTE: " + this.id);
                    break;
                case "LOGIN":
                    if (data[1].equals("OK")) {
                        System.out.println("LOGIN ACEPTADO");
                    } else {
                        System.out.println("ERROR: LOGIN INVALIDO");
                    }
                    break;
                case "REGISTRO":
                    if (data[1].equals("OK")) {
                        System.out.println("REGISTRO ACEPTADO");
                    } else {
                        System.out.println("ERROR: REGISTRO INVALIDO");
                    }
                    break;
                default:
                    System.out.println("FORMATO NO VÁLIDO");
                    // Aquí va el código a ejecutar si opcion no coincide con ninguno de los casos anteriores
                    break;
            }
        } else {
            System.out.println("ERROR: FORMATO NO VÁLIDO");
        }
    }

    @Override
    public void onClientReconexion(ReconexionEvent ev) {
        System.out.println("Se logró conectar con el servidor");
        reconexion(ev.getSocket());
    }

    @Override
    public void onClientPing(PingEvent ev) {
        hp.removeMyEventListener(this);
        hr.removeMyEventListener(this);
        hr = new HiloReconexion(host, puerto);
        System.out.println(" iniciando reconeccion");
        hr.addMyEventListener(this);
        hr.start();
    }
}
