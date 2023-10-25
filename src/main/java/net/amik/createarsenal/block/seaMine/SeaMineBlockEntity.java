package net.amik.createarsenal.block.seaMine;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SeaMineBlockEntity extends SmartBlockEntity {

    ScrollValueBehaviour floatLevel;


    public SeaMineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        floatLevel =
                new ScrollValueBehaviour(Components.translatable("sea_mine.float_level"), this, new FloatLevelSlot())
                        .between(-64, 256)
                        .withFormatter(i -> i == 0 ? "*" : String.valueOf(i));
        behaviours.add(floatLevel);
    }


    private static class FloatLevelSlot extends ValueBoxTransform.Sided {

        @Override
        protected Vec3 getSouthLocation() {
            return direction == Direction.UP ? Vec3.ZERO : VecHelper.voxelSpace(8, 6, 15.5);
        }

    }

}
