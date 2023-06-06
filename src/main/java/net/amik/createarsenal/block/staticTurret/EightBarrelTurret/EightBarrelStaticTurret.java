package net.amik.createarsenal.block.staticTurret.EightBarrelTurret;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.block.staticTurret.FourBarrelTurret.FourBarrelStaticTurretTileEntity;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class EightBarrelStaticTurret extends DirectionalKineticBlock implements IBE<EightBarrelStaticTurretTileEntity> {

    public EightBarrelStaticTurret(Properties properties) {
        super(properties);
    }


    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.NONE;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction direction) {
        return direction== state.getValue(FACING);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING)
                .getAxis();
    }

    @Override
    public Class<EightBarrelStaticTurretTileEntity> getBlockEntityClass() {
        return EightBarrelStaticTurretTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends EightBarrelStaticTurretTileEntity> getBlockEntityType() {
        return ModBlockEntities.EIGHT_BARREL_STATIC_TURRET_TILE_ENTITY.get();
    }
}
