package co.edu.uniquindio.proyecto.sockets;

import co.edu.uniquindio.proyecto.entidades.Chat;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SocketService socketService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MensajeS sendMessage(MensajeS message) {

        //socketService.saveSession(sessionId, message.getNombre());
        //Do something
        return new MensajeS("Message with text : " + message.getTexto()
                + " received ", " from " + message.getNombre());
    }

}
