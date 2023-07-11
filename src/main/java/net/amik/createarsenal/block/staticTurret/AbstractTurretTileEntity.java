package net.amik.createarsenal.block.staticTurret;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.amik.createarsenal.shell.BulletEntity;
import net.amik.createarsenal.util.IntUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;

public abstract class AbstractTurretTileEntity extends KineticBlockEntity {
    public AbstractTurretTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    int counter;

    @Override
    public void tick() {
        super.tick();
        if (canShoot())
            shoot();
    }

    protected abstract float shootingInterval();

    protected boolean canShoot() {
        if (level == null)
            return false;

        counter += Math.abs(getSpeed());
        if (counter < shootingInterval() * 256) {
            return false;
        }
        counter = 0;
        return true;
    }

    protected abstract float getBarrelLength();

    protected void shoot() {
        if (level == null || level.isClientSide) return;

        BulletEntity bullet = new BulletEntity(getLevel());

        Direction direction = getBlockState().getValue(FACING).getOpposite();

        bullet.setPos(
                new Vec3(
                        getBlockPos().getX() +
                                IntUtil.toInt(direction.getStepX() < 0)
                                + Math.abs(direction.getStepZ()/2F)
                                + direction.getStepX() * (getBarrelLength() + 2)
                        ,
                        getBlockPos().relative(direction, 4).getY()
                                + 1/4F
                        ,
                        getBlockPos().getZ() +
                                IntUtil.toInt(direction.getStepZ() < 0)
                                + Math.abs(direction.getStepX()/2F)
                                + direction.getStepZ() * (getBarrelLength() + 2)
                )
        );

        bullet.shoot(direction.getStepX(), 0, direction.getStepZ(), 3.0F, 0F);

        whenBulletCreated(bullet);

        level.addFreshEntity(bullet);
    }

    protected void whenBulletCreated(BulletEntity bullet) {}
}
