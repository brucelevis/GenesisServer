package com.genesis.test.core.serviceinit.servicedep;

import com.genesis.core.serviceinit.ServiceInitializeRequired;

public class Service6 implements ServiceInitializeRequired {

    private static Service6 Instance = new Service6();

    private Service6() {
    }

    public static Service6 service() {
        return Instance;
    }

    @Override
    public void init() {
        StatisticsDep.initService6();
    }

}
