package com.genesis.test.gameserver.battleprop;

import com.genesis.common.prop.battleprop.BattlePropContainer;
import com.genesis.common.prop.battleprop.propholder.IPropHolder;
import com.genesis.common.prop.battleprop.propholder.Prop;
import com.genesis.common.prop.battleprop.source.PropSourceType;

public class Equip implements IPropHolder<EquipPropPart> {

    //	private final long id;
    private final Prop<EquipPropPart> prop;

    public Equip(long id) {
        //		this.id = id;
        this.prop = new Prop<EquipPropPart>(PropSourceType.EQUIP, id, EquipPropPart.class);
    }

    @Override
    public Prop<EquipPropPart> getProp() {
        return this.prop;
    }

    @Override
    public void addProp(BattlePropContainer battlePropContainer) {
        // TODO Auto-generated method stub

    }

}
