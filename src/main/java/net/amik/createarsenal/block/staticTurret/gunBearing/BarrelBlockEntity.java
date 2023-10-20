package net.amik.createarsenal.block.staticTurret.gunBearing;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.amik.createarsenal.registrate.ModPartials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import static net.amik.createarsenal.util.HorizontalDirectionBlock.FACING;


import java.util.List;

public class BarrelBlockEntity extends SmartBlockEntity {

    NormalGunBlockEntity.Size size= NormalGunBlockEntity.Size.NONE;

    public NormalGunBlockEntity.Size getSize() {
        return size;
    }

    public int getBarrelCount() {
        return barrelCount;
    }

    int barrelCount;
    public BarrelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    public void addBarrel(NormalGunBlockEntity.Size size, int barrelCount) {
        this.size=size;
        this.barrelCount++;
    }

    public PartialModel getPartialModel() {
        if(size.equals(NormalGunBlockEntity.Size.SMALL))
            return ModPartials.SMALL_BARREL;

        if(size.equals(NormalGunBlockEntity.Size.MEDIUM))
            return ModPartials.MEDIUM_BARREL;

        if(size.equals(NormalGunBlockEntity.Size.LARGE))
            return ModPartials.LARGE_BARREL;
        return null;
    }

}
