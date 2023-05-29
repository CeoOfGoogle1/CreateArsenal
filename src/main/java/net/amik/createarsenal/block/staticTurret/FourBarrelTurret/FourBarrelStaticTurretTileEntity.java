package net.amik.createarsenal.block.staticTurret.FourBarrelTurret;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.amik.createarsenal.registrate.ModProjectiles;
import net.amik.createarsenal.shell.ShellEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.CallbackI;

import java.util.Random;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;


public class FourBarrelStaticTurretTileEntity extends KineticBlockEntity {

    int counter;
    int xoffsett;
    int yoffsett;
    int zoffsett;
    public FourBarrelStaticTurretTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);

    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            counter += Math.abs(getSpeed());
            if (counter >= 1280) {
                counter = 0;
                ShellEntity bullet = new ShellEntity(getLevel());
                Direction direction = getBlockState().getValue(FACING).getOpposite();
                bullet.setPos(Vec3.atCenterOf(getBlockPos()).add(level.random.nextInt(10)/100D,level.random.nextInt(10)/100D,level.random.nextInt(10)/100D));
                bullet.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), 2.0F, 0.9F);
                level.addFreshEntity(bullet);
            }
        }
    }


}
