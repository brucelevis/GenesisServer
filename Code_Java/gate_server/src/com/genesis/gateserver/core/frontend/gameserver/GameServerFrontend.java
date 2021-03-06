package com.genesis.gateserver.core.frontend.gameserver;

import com.genesis.servermsg.core.config.ServerConfig;
import com.genesis.servermsg.core.msg.IMessage;
import com.genesis.servermsg.core.isc.msg.ServerMessage;
import com.genesis.servermsg.core.isc.remote.IRemote;
import com.genesis.servermsg.core.msg.CSSMessage;
import com.genesis.protobuf.MessageType;
import com.genesis.protobuf.MessageType.CGMessageType;
import com.genesis.protobuf.MessageType.MessageTarget;
import com.genesis.network2client.session.IClientSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

import akka.actor.ActorRef;

public class GameServerFrontend {

    private final IRemote remote;
    private final ServerConfig localConfig;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /** UUID - PlayerActorRef */
    private ConcurrentHashMap<Long, ActorRef> playerMap = null;

    public GameServerFrontend(IRemote remote, ServerConfig localConfig) {
        this.localConfig = localConfig;
        this.remote = remote;
        this.playerMap = new ConcurrentHashMap<>();
    }

    public void route(IClientSession session, CSSMessage msg) {
        CGMessageType msgType = CGMessageType.valueOf(msg.messageType);
        MessageTarget target =
                msgType.getValueDescriptor().getOptions().getExtension(MessageType.tARGET);

        if (isNotLoginMsgAndPlayerNotLogin(msg, target)) {
            log.warn("Received a wired message [{}] from player {} before player login",
                    msg.messageType, msg.agentSessionId);
            return;
        }

        if (target == MessageTarget.PLAYER) {
            playerMap.get(msg.agentSessionId)
                    .tell(new ServerMessage(localConfig.serverType, localConfig.serverId, msg),
                            ActorRef.noSender());
        } else {
            this.remote.sendMessage(msg);
        }
    }

    public void sendMessage(long agentSessionId, IMessage msg) {
        if (msg.getTarget() != MessageTarget.PLAYER) {
            this.remote.sendMessage(msg);
        } else {
            ActorRef ar = playerMap.get(agentSessionId);
            if (ar != null) {
                ar.tell(new ServerMessage(localConfig.serverType, localConfig.serverId, msg),
                        ActorRef.noSender());
            }
        }
    }

    private boolean isNotLoginMsgAndPlayerNotLogin(CSSMessage msg, MessageTarget target) {
        return !isLoginMsg(target) && !playerMap.containsKey(msg.agentSessionId);
    }

    private boolean isLoginMsg(MessageTarget target) {
        return target == MessageTarget.PLAYER_MANAGER;
    }

    public void userLogined(long agentSessionId, ActorRef playerActor) {
        ActorRef pre = playerMap.putIfAbsent(agentSessionId, playerActor);
        if (pre != null) {
            log.warn("User {} has already logined", agentSessionId);
        }
    }

    public int getGameServerId() {
        return this.remote.getServerId();
    }

    public void logoutPlayer(long sessionId) {
        playerMap.remove(sessionId);
    }
}
