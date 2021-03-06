package com.genesis.network2client.process;

import com.google.protobuf.GeneratedMessage;
import com.genesis.network2client.session.IClientSession;

public interface IClientMsgHandler<Msg extends GeneratedMessage> {

    void handle(IClientSession session, Msg msg);
}
