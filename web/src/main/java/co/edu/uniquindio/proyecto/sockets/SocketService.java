package co.edu.uniquindio.proyecto.sockets;

import co.edu.uniquindio.proyecto.servicios.ChatServicio;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SocketService {

    private Map<String, String> nameSession = new HashMap<>();
    private ChatServicio chatServicio;

    public String getNameBySession(String session) {
        return nameSession.get(session);
    }

    public void saveSession(String sessionId, String name) {
        nameSession.put(sessionId, name);
    }

    public void removeSession(String sessionId) {
        nameSession.remove(sessionId);
    }

    public void guardarMsj(){

    }

}