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
public class PingEvent extends EventObject {
    String id;

    public PingEvent(String id, Object source) {
        super(source);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
