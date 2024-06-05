package net.amik.createarsenal.block.staticTurret.modularGun.normalGun;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class NormalGunBlockEntity extends KineticBlockEntity {

    private static final int MAX_BARREL_LENGTH = 4;


    public NormalGunBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }



}
