package net.amik.createarsenal.util;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;

public abstract class HorizontalDirectionalKineticBlock extends DirectionalKineticBlock {
    public HorizontalDirectionalKineticBlock(Properties properties) {
        super(properties);
    }

    public Direction getPreferredFacing(BlockPlaceContext context) {
        return super.getPreferredFacing(context) == null || !super.getPreferredFacing(context).getAxis().isHorizontal() ? context.getHorizontalDirection() : super.getPreferredFacing(context).getOpposite();
    }
}
