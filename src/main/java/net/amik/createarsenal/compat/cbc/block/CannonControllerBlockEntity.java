package net.amik.createarsenal.compat.cbc.block;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CannonControllerBlockEntity extends KineticBlockEntity {
    public CannonControllerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected void addStressImpactStats(List<Component> tooltip, float stressAtBase) {
        super.addStressImpactStats(tooltip, stressAtBase);
    }
}
