package net.amik.createarsenal.block.staticTurret.FourBarrelTurret;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.lwjgl.system.CallbackI;

import static net.amik.createarsenal.block.staticTurret.FourBarrelTurret.FourBarrelStaticTurret.FACING;

public class FourBarrelStaticTurretTileEntity extends KineticTileEntity {

    public FourBarrelStaticTurretTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void lazyTick() {
        if(targetInSight()&&getSpeed()>8)
        {
            System.out.println("Fire!");
        }
    }

    private boolean targetInSight()
    {
        Direction dir= getBlockState().getValue(FACING);
        AABB range= new AABB(getBlockPos().below(),getBlockPos().relative(dir,-16));
        System.out.println(range);
        System.out.println(level.getEntities(null,range));
        return !level.getEntities(null,range).isEmpty();
    }
}
