package net.amik.createarsenal.block.staticTurret;

import net.amik.createarsenal.shell.BulletEntity;
import net.amik.createarsenal.util.IntUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;


public abstract class AbstractRotatingTurretTileEntity extends AbstractTurretTileEntity {
    public AbstractRotatingTurretTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected void whenBulletCreated(BulletEntity bullet) {
        super.whenBulletCreated(bullet);

        Direction direction = getBlockState().getValue(FACING).getOpposite();

        double theta = Math.random() * 2 * Math.PI;

        bullet.setPos(
                new Vec3(
                        bullet.position().x + 3/16F * IntUtil.toInt(direction.getStepZ() != 0) * Math.sin(theta)
                        , bullet.position().y + 3/16F * Math.cos(theta)
                        , bullet.position().z + 3/16F * IntUtil.toInt(direction.getStepX() != 0) * Math.sin(theta)
                )
        );
    }
}
