/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ServidorSocket.Eventos;

import java.util.EventListener;

/**
 *
 * @author Christian
 */
public interface ServidorSocketEventListener extends EventListener {
    void onClientConnected(ConexionEvent ev);
    void onClientMessage(MensajeEvent ev);
    void onClientPing(PingEvent ev);
}
