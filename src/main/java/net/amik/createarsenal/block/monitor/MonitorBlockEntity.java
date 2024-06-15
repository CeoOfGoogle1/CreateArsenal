package net.amik.createarsenal.block.monitor;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class MonitorBlockEntity extends SmartBlockEntity {
    public MonitorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public int widthRange = 256;
    public int heightRange = 4;
    private float animation;
    private int size = 1;

    BlockPos controllerPos = BlockPos.ZERO;
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
        if (tag.contains("controllerPos"))
            controllerPos = NbtUtils.readBlockPos(tag.getCompound("controllerPos"));
        if (tag.contains("size"))
            size = tag.getInt("size");
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().inflate(3);
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putInt("tickSinceLastWork", tickSinceLastWork);
        tag.putInt("widthRange", widthRange);
        tag.putInt("heightRange", heightRange);
        tag.put("controllerPos", NbtUtils.writeBlockPos(controllerPos));
        tag.putInt("size", size);
    }

    public void setControllerPos(BlockPos controllerPos, int size) {
        this.controllerPos = controllerPos;
        this.size = size;
    }

    public BlockPos getControllerPos() {
        return controllerPos;
    }

    public boolean isControllerPos() {
        return controllerPos.equals(this.getBlockPos());
    }

    public boolean hasController() {
        if (level == null)
            return false;
        return level.getBlockEntity(controllerPos) instanceof MonitorBlockEntity controller
                && controller.isControllerPos();
    }


    public MonitorBlockEntity getController() {
        if (isControllerPos() || !hasController())
            return this;
        if (level == null)
            return null;
        if (level.getBlockEntity(controllerPos) instanceof MonitorBlockEntity controller)
            return controller;
        return null;
    }

    public int getSize() {
        return size;
    }
}
