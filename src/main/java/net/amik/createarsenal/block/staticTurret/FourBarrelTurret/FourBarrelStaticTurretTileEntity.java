package net.amik.createarsenal.block.staticTurret.FourBarrelTurret;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.amik.createarsenal.shell.BulletEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;


public class FourBarrelStaticTurretTileEntity extends KineticBlockEntity {

    int counter;

    public FourBarrelStaticTurretTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        shoot();
    }

    private void shoot() {
        assert level != null;
        if (!level.isClientSide) {
            counter += Math.abs(getSpeed());
            if (counter >= 512) {
                counter = 0;
                BulletEntity bullet = new BulletEntity(getLevel());

                Direction direction = getBlockState().getValue(FACING).getOpposite();

                double theta = Math.random() * 2 * Math.PI;

                bullet.setPos(
                        new Vec3(
                                getBlockPos().relative(direction, 3).getX() +
                                        (direction.getStepX() < 0 ? 1 : 0)
                                        + Math.abs(direction.getStepZ()/2F)
                                        + 3/16F * (direction.getStepZ() != 0 ? 1 : 0) * Math.sin(theta)
                                        + direction.getStepX() * 0.25
                                ,
                                getBlockPos().relative(direction, 3).getY()
                                        + 5/16F
                                        + 3/16F * Math.cos(theta)
                                ,
                                getBlockPos().relative(direction, 3).getZ() +
                                        (direction.getStepZ() < 0 ? 1 : 0)
                                        + Math.abs(direction.getStepX()/2F)
                                        + 3/16F * (direction.getStepX() != 0 ? 1 : 0) * Math.sin(theta)
                                        + direction.getStepZ() * 0.25
                        )
                );

                bullet.setYRot(
                        switch (direction) {
                            case SOUTH -> 180;
                            case EAST -> 90;
                            case WEST -> 270;
                            default -> 0;
                        }
                );

                bullet.shoot(direction.getStepX(), 0, direction.getStepZ(), 3.0F, 0F);
                level.addFreshEntity(bullet);
            }
        }
    }


}
