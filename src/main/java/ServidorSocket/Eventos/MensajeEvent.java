/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServidorSocket.Eventos;

import java.util.EventObject;

/**
 *
 * @author Christian
 */
public class MensajeEvent extends EventObject{
    String mensaje;

    public MensajeEvent(String mensaje, Object source) {
        super(source);
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
