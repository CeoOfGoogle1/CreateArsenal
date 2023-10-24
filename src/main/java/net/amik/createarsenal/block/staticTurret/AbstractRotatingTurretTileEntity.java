package net.amik.createarsenal.block.staticTurret;

import net.amik.createarsenal.util.IntUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;


public abstract class AbstractRotatingTurretTileEntity extends AbstractTurretTileEntity {
    public AbstractRotatingTurretTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected Vec3 bulletPos(Direction direction) {
        double theta = Math.random() * 2 * Math.PI;

        return super.bulletPos(direction).add(
                3/16F * IntUtil.toInt(direction.getStepZ() != 0) * Math.sin(theta)
               ,3/16F * Math.cos(theta)
               ,3/16F * IntUtil.toInt(direction.getStepX() != 0) * Math.sin(theta)
        );
    }
}
