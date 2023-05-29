package net.amik.createarsenal.block.staticTurret.FourBarrelTurret;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.amik.createarsenal.registrate.sound.ModSoundEvents;
import net.amik.createarsenal.shell.BulletEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;


public class FourBarrelStaticTurretTileEntity extends KineticBlockEntity {

    int counter;
    Direction direction;

    public FourBarrelStaticTurretTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        direction = getBlockState().getValue(FACING).getOpposite();
    }

    @Override
    public void tick() {
        super.tick();
        shoot();
        ModSoundEvents.FIRE_MEDIUM_TURRET.playOnServer(level, this.getBlockPos());

    }

    private void shoot() {
        if (!level.isClientSide) {
            counter += Math.abs(getSpeed());
            if (counter >= 1280) {
                counter = 0;
                BulletEntity bullet = new BulletEntity(getLevel());
                bullet.setPos(Vec3.atCenterOf(getBlockPos()).add(level.random.nextInt(10)/100D,level.random.nextInt(10)/100D,level.random.nextInt(10)/100D));
                bullet.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), 2.0F, 0.9F);
                level.addFreshEntity(bullet);
            }
        }
    }


}
