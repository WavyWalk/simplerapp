package at.wavywalk.simpler.websockets.endpoints

import javax.websocket.Endpoint
import javax.websocket.EndpointConfig
import javax.websocket.MessageHandler
import javax.websocket.Session
import javax.websocket.CloseReason

class FrontWebsocketEndpoint: Endpoint() {

    override fun onOpen(session: Session, config: EndpointConfig) {
        session.requestURI
        session.addMessageHandler(MessageHandler.Whole<String> {

        })
    }

    override fun onClose(session: Session, closeReason: CloseReason) {

    }

    override fun onError(session: Session?, thr: Throwable?) {

    }


}