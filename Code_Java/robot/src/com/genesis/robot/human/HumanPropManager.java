package com.genesis.robot.human;

import com.genesis.common.human.HumanPropContainer;
import com.genesis.common.human.HumanPropId;

import java.util.List;

public class HumanPropManager extends HumanPropContainer {

    private Human human;

    public HumanPropManager(Human human) {
        this.human = human;
    }

    public Human getHuman() {
        return human;
    }

    public void load(List<Long> longPropList) {
        if (longPropList == null || longPropList.isEmpty()) {
            return;
        }

        HumanPropId[] propIds = HumanPropId.values();
        if (propIds.length != longPropList.size()) {
            String error = String.format(
                    "HumanPropId count==[%d],msg's longPropList count==[%d],not equal!",
                    propIds.length, longPropList.size());
            throw new RuntimeException(error);
        }

        for (int i = 0; i < propIds.length; i++) {
            this.set(propIds[i], longPropList.get(i));
        }
    }

}
