package net.amik.createarsenal.block.monitor;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class MonitorBlockEntity extends SmartBlockEntity {
    public MonitorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public int widthRange = 256;
    public int heightRange = 4;
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
        scannedEntities=level.getEntities(null, this.getRenderBoundingBox().inflate(widthRange, heightRange, widthRange));

    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        if (tag.contains("tickSinceLastWork"))
            tickSinceLastWork = tag.getInt("tickSinceLastWork");
        if (tag.contains("widthRange"))
            widthRange = tag.getInt("widthRange");
        if (tag.contains("heightRange"))
            heightRange = tag.getInt("heightRange");
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putInt("tickSinceLastWork", tickSinceLastWork);
        tag.putInt("widthRange", widthRange);
        tag.putInt("heightRange", heightRange);
    }
}
