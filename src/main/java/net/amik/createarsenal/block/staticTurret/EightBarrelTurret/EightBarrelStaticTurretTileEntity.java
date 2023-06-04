package net.amik.createarsenal.block.staticTurret.EightBarrelTurret;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.amik.createarsenal.registrate.sound.ModSoundEvents;
import net.amik.createarsenal.shell.BulletEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;


public class EightBarrelStaticTurretTileEntity extends KineticBlockEntity {

    int counter;
    Direction direction;

    public EightBarrelStaticTurretTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
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

        }
    }


}
