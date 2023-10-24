package net.amik.createarsenal.block.staticTurret.modularGun.rotaryGun;

import net.amik.createarsenal.block.staticTurret.modularGun.normalGun.NormalGunBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


//todo delete and use Normal for both. Need to make custom Renderer and Instance for Rotary shaft
public class RotaryGunBlockEntity extends NormalGunBlockEntity {


    public RotaryGunBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    public float getSpeed() {
        if (overStressed)
            return 0;
        return getTheoreticalSpeed();
    }
}
