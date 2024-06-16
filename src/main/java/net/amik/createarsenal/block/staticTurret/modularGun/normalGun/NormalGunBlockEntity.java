package net.amik.createarsenal.block.staticTurret.modularGun.normalGun;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.amik.createarsenal.registrate.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class NormalGunBlockEntity extends KineticBlockEntity {

    private static final int MAX_BARREL_LENGTH = 4;
    private static final int MIN_SPEED = 16;


    public NormalGunBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public float getSpeed() {
        if (ModBlocks.NORMAL_GUN.has(getBlockState()))
            return MIN_SPEED;
        return super.getSpeed();
    }
}
