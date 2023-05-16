/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Cliente.Eventos;

import java.util.EventListener;

/**
 *
 * @author Christian
 */
public interface ClienteSocketEventListener extends EventListener {
    void onServerMessage(MensajeServidorEvent ev);
    void onClientReconexion(ReconexionEvent ev);
    void onClientPing(PingEvent ev);
}
