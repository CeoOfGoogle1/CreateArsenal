package net.amik.createarsenal.block.radar.monitor;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.amik.createarsenal.block.radar.base.RadarBaseBlockTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.*;


public class MonitorBlockEntity extends SmartBlockEntity {
    public MonitorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private int size = 1;
    BlockPos controllerPos = BlockPos.ZERO;
    private BlockPos radarPos = BlockPos.ZERO;
    private Entity targetEntity;
    private MonitorFilter filter = MonitorFilter.ALL_ENTITIES;
    private int ticksSinceLastUpdate = 0;

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    public void tick() {
        super.tick();
        if (ticksSinceLastUpdate > 0)
            ticksSinceLastUpdate--;
        else
            radarPos = BlockPos.ZERO;

    }

    public BlockPos getTargetPos() {
        if (targetEntity == null)
            return null;
        if (getRadar().isPresent()) {
            RadarBaseBlockTileEntity radar = getRadar().get();
            if (radar.getEntityPositions(filter).containsKey(targetEntity))
                return radar.getEntityPositions(filter).get(targetEntity);
        }
        return null;
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        controllerPos = NbtUtils.readBlockPos(tag.getCompound("controllerPos"));
        size = tag.getInt("size");
        radarPos = NbtUtils.readBlockPos(tag.getCompound("radarPos"));
        filter = MonitorFilter.values()[tag.getInt("filter")];
        ticksSinceLastUpdate = tag.getInt("ticksSinceLastUpdate");
    }


    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.put("controllerPos", NbtUtils.writeBlockPos(controllerPos));
        tag.putInt("size", size);
        tag.put("radarPos", NbtUtils.writeBlockPos(radarPos));
        tag.putInt("filter", filter.ordinal());
        tag.putInt("ticksSinceLastUpdate", ticksSinceLastUpdate);
    }

    public Optional<RadarBaseBlockTileEntity> getRadar() {
        if (level == null)
            return Optional.empty();
        if (radarPos == null || radarPos.equals(BlockPos.ZERO))
            return Optional.empty();
        if (level.getBlockEntity(radarPos) instanceof RadarBaseBlockTileEntity radar)
            return Optional.of(radar);
        return Optional.empty();
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().inflate(5);
    }


    public void setControllerPos(BlockPos controllerPos, int size) {
        this.controllerPos = controllerPos;
        this.size = size;
        notifyUpdate();
    }

    public BlockPos getControllerPos() {
        if (controllerPos == null || controllerPos.equals(BlockPos.ZERO))
            return this.getBlockPos();
        return controllerPos;
    }

    public boolean isControllerPos() {
        return controllerPos != null && controllerPos.equals(this.getBlockPos());
    }

    public boolean hasController() {
        if (level == null)
            return false;
        if (controllerPos == null || controllerPos.equals(BlockPos.ZERO))
            return false;
        return level.getBlockEntity(controllerPos) instanceof MonitorBlockEntity controller
                && controller.isControllerPos();
    }


    public MonitorBlockEntity getController() {
        if (isControllerPos())
            return this;
        if (!hasController()) {
            controllerPos = getBlockPos();
            return this;
        }
        if (level == null)
            return null;
        if (level.getBlockEntity(controllerPos) instanceof MonitorBlockEntity controller)
            return controller;
        return null;
    }

    public int getSize() {
        return size;
    }

    public void handleClick(Player player, BlockHitResult hit) {
        if (getController() == null)
            return;
        if (!isControllerPos()) {
            getController().handleClick(player, hit);
            return;
        }
        if (player.isCrouching()) {
            targetEntity = null;
            notifyUpdate();
            return;
        }
        if (getRadar().isPresent()) {
            Random random = new Random();
            Map<Entity, BlockPos> entityPositions = getRadar().get().getEntityPositions(filter);
            if (!entityPositions.isEmpty()) {
                List<Entity> entities = new ArrayList<>(entityPositions.keySet());
                Entity selectedEntity = entities.get(random.nextInt(entities.size()));
                targetEntity = selectedEntity;
            }
            notifyUpdate();
        }
    }

    public BlockPos getRadarPos() {
        return radarPos;
    }


    public void setRadarPos(BlockPos blockPos) {
        radarPos = blockPos;
    }

    public void setFilter(MonitorFilter monitorFilter) {
        filter = monitorFilter;
    }

    public MonitorFilter getFilter() {
        return filter;
    }

    public void setActive() {
        ticksSinceLastUpdate = 120;
    }

    public AABB getMultiblockBounds(LevelAccessor level, BlockPos pos) {
        Direction facing = level.getBlockState(getBlockPos())
                .getValue(MonitorBlock.FACING).getClockWise();
        AABB aabb = new AABB(getBlockPos(), getBlockPos().offset(facing.getStepX() * (size), size, facing.getStepZ() * (size)));
        return aabb;
    }
}
