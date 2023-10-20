package net.amik.createarsenal.block.staticTurret.gunBearing;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.amik.createarsenal.registrate.ModPartials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


import java.util.List;

import static net.amik.createarsenal.util.HorizontalDirectionBlock.FACING;

public class GunBarrelBlockEntity extends SmartBlockEntity {

    NormalGunBlockEntity.Size size= NormalGunBlockEntity.Size.NONE;


    int barrelCount;



    public GunBarrelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        size= NormalGunBlockEntity.Size.values()[compound.getInt("size")];
        barrelCount=compound.getInt("barrelCount");

    }


    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putInt("size",size.ordinal());
        compound.putInt("barrelCount",barrelCount);
    }


    public void addBarrel(NormalGunBlockEntity.Size size) {
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



    public int getBarrelCount() {
        return barrelCount;
    }

}
