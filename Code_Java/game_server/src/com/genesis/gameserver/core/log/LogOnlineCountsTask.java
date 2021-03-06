package com.genesis.gameserver.core.log;

import com.genesis.core.timeaxis.ITimeEvent;
import com.genesis.core.timeaxis.ITimeEventType;
import com.genesis.gameserver.core.global.ServerGlobals;
import com.genesis.gameserver.login.OnlinePlayerService;
import com.genesis.gameserver.player.PlayerManagerActor;

/**
 * @author ChangXiao
 *
 */
public class LogOnlineCountsTask implements ITimeEvent<PlayerManagerActor> {
    private final ServerGlobals sGlobals;
    private final OnlinePlayerService onlinePlayerService;

    public LogOnlineCountsTask(ServerGlobals sGlobals, OnlinePlayerService onlinePlayerService) {
        this.sGlobals = sGlobals;
        this.onlinePlayerService = onlinePlayerService;
    }

    @Override
    public ITimeEventType getEventType() {
        return OpLogType.ONLINE_COUNTS;
    }

    @Override
    public long getEventId() {
        return 0;
    }

    @Override
    public void eventOccur(PlayerManagerActor timeAxisHost) {
        //日志ID
        String iEventId = sGlobals.genLogEventId();
        int curCounts = onlinePlayerService.getCurOnlinePlayerCount();
        int MaxCounts = onlinePlayerService.getMaxOnlinePlayerCount();
        //重置最大在线玩家数量为当前在线玩家数量
        onlinePlayerService.resetMaxOnlinePlayerCount();
        //运营日志-记录在线玩家日志
        sGlobals.getLogService()
                .logOnlineCount(iEventId, curCounts, MaxCounts, curCounts, MaxCounts, curCounts,
                        MaxCounts);
    }


}
