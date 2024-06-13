package net.amik.createarsenal.block.seaMine;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SeaMineBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {


    int floatLevel = 0;
    int detonationRadius = 1;
    boolean armed = false;


    public SeaMineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    public void tick() {
        super.tick();
        if (!armed && hasRedstone() && level.getBlockState(getBlockPos().below()).canBeReplaced())
            dropBomb();
        if (shouldTriggerExplosion() && armed)
            detonate();
    }

    private void dropBomb() {
        FallingSeaMineEntity seaMine = new FallingSeaMineEntity(level, new BlockPos(getBlockPos().getX(), floatLevel, getBlockPos().getZ()), detonationRadius);
        seaMine.setPos(getBlockPos().getX() + .5, getBlockPos().getY() - .25, getBlockPos().getZ() + .5);
        level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
        level.addFreshEntity(seaMine);
    }

    private boolean hasRedstone() {
        if (level.hasNeighborSignal(worldPosition))
            return true;
        for (Direction direction : Direction.values()) {
            if (level.hasSignal(worldPosition, direction))
                return true;
        }
        return false;
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        floatLevel = tag.getInt("floatLevel");
        detonationRadius = tag.getInt("detonationRadius");
        armed = tag.getBoolean("armed");
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putInt("floatLevel", floatLevel);
        tag.putInt("detonationRadius", detonationRadius);
        tag.putBoolean("armed", armed);
    }

    public boolean shouldTriggerExplosion() {
        return level.getEntities(null, new AABB(worldPosition).inflate(detonationRadius)).stream()
                .dropWhile(entity -> (entity instanceof ItemEntity))
                .findFirst().isPresent();
    }

    public void detonate() {
        if (level == null)
            return;
        if (level.isClientSide)
            return;
        Vec3 pos = VecHelper.getCenterOf(worldPosition);
        level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
        level.explode(null, pos.x, pos.y, pos.z, 5, Level.ExplosionInteraction.MOB);
    }

    public void setFloatLevel(int floatLevel) {
        this.floatLevel = Math.max(-64, Math.min(255, floatLevel));
        notifyUpdate();
    }

    public void setRange(int detonationRadius) {
        this.detonationRadius = Math.max(1, Math.min(8, detonationRadius));
        notifyUpdate();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        tooltip.add(Component.literal(""));
        if (armed) {
            tooltip.add(Component.translatable("sea_mine.armed").withStyle(ChatFormatting.DARK_RED));
        } else {
            tooltip.add(Component.translatable("sea_mine.float_level").append(": " + floatLevel));
            tooltip.add(Component.translatable("sea_mine.range").append(": " + detonationRadius));
        }
        return true;
    }
}
