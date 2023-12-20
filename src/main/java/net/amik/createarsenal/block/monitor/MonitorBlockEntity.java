package net.amik.createarsenal.block.monitor;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public class MonitorBlockEntity extends SmartBlockEntity {
    public MonitorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static final int RANGE_WIDTH = 256;
    public static final int RANGE_HEIGHT = 4;
    private float animation;

    public int tickSinceLastWork = 0;


    List<Entity> scannedEntities = new ArrayList<>();


    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    public void tick() {
        super.tick();

        if (tickSinceLastWork > 0)
            tickSinceLastWork--;
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        scanEntities();
    }

    public float getAnimation()
    {
        animation+=.005f;
        if(animation>=1) animation=0f;
        return animation;
    }

    public List<Entity> getDisplayEntities(){
        return scannedEntities;
    }
    public boolean hasDisplayEntities(){
        return !getDisplayEntities().isEmpty();
    }

    public void scanEntities(){
        if(level==null)return;
        scannedEntities=level.getEntities(null, this.getRenderBoundingBox().inflate(RANGE_WIDTH,RANGE_HEIGHT,RANGE_WIDTH));

    }


    public boolean wrenchClick(BlockHitResult pHit) {
        return false;
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        if (tag.contains("tickSinceLastWork"))
            tickSinceLastWork = tag.getInt("tickSinceLastWork");
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putInt("tickSinceLastWork", tickSinceLastWork);
    }
}
