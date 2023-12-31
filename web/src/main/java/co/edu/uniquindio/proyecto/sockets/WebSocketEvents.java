package co.edu.uniquindio.proyecto.sockets;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@RequiredArgsConstructor
public class WebSocketEvents {

    private final SocketService socketService;


    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {

        log.debug("Client with session id {} disconnected", event.getSessionId());

        String sessionId = event.getSessionId();

        String name = socketService.getNameBySession(sessionId);

        log.debug("Client with name {} has been disconnected ", name);

        socketService.removeSession(sessionId);

    }}
