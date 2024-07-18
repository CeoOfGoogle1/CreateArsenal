package net.amik.createarsenal.block.radar.base;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.ControlledContraptionEntity;
import com.simibubi.create.content.contraptions.bearing.MechanicalBearingBlockEntity;
import net.amik.createarsenal.block.radar.monitor.MonitorFilter;
import net.amik.createarsenal.block.radar.receiver.AbstractRadarFrame;
import net.amik.createarsenal.block.radar.receiver.RadarReceiverBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RadarBaseBlockTileEntity extends MechanicalBearingBlockEntity {

    private static final double BASE_FOV = 30.0;
    private static final double FOV_INCREMENT = .5;
    private static final double MAX_FOV = 90.0;
    private static final int BASE_RANGE = 20;
    private static final int RANGE_INCREMENT = 10;
    private static final int MAX_RANGE = 1300;

    private int dishCount;
    private boolean hasReceiver;
    private Direction facing;

    List<Entity> scannedEntities = new ArrayList<>();

    public Map<Entity, BlockPos> getEntityPositions(MonitorFilter filter) {
        if (filter == MonitorFilter.ALL_ENTITIES)
            return entityPositions;
        Map<Entity, BlockPos> filteredEntityPositions = new HashMap<>();
        for (Map.Entry<Entity, BlockPos> entry : entityPositions.entrySet()) {
            Entity entity = entry.getKey();
            BlockPos pos = entry.getValue();
            if (filter == MonitorFilter.NO_MOBS && !(entity instanceof Mob))
                filteredEntityPositions.put(entity, pos);
            if (filter == MonitorFilter.PLAYERS_ONLY && entity instanceof Player)
                filteredEntityPositions.put(entity, pos);
            if (filter == MonitorFilter.PROJECTILES_ONLY && entity instanceof Projectile)
                filteredEntityPositions.put(entity, pos);
        }
        return filteredEntityPositions;
    }

    Map<Entity, BlockPos> entityPositions = new HashMap<>();

    public RadarBaseBlockTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        dishCount = 0;
        hasReceiver = false;
        facing = Direction.NORTH;
    }

    @Override
    public void tick() {
        super.tick();
        if (!isRunning())
            return;
        scanEntities();
        updateEntityPositions();
        removeEntitiesOutsideOfRange();
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        dishCount = compound.getInt("dishCount");
        hasReceiver = compound.getBoolean("hasReceiver");
        facing = Direction.from3DDataValue(compound.getInt("facing"));
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putInt("dishCount", dishCount);
        compound.putBoolean("hasReceiver", hasReceiver);
        compound.putInt("facing", facing.get3DDataValue());
    }


    public void assembleNextTick() {
        this.assembleNextTick = true;
        assemble();
    }


    @Override
    public void assemble() {
        super.assemble();
        updateDishCount();
        notifyUpdate();
    }

    private void updateDishCount() {
        dishCount = 0;
        hasReceiver = false;

        if (!isRunning()) return;

        ControlledContraptionEntity contraptionEntity = getMovedContraption();
        if (contraptionEntity == null)
            return;

        Contraption contraption = contraptionEntity.getContraption();
        if (contraption == null)
            return;

        for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> block : contraption.getBlocks().entrySet()) {
            StructureTemplate.StructureBlockInfo info = block.getValue();

            if (info.state().getBlock() instanceof AbstractRadarFrame)
                dishCount++;

            if (info.state().getBlock() instanceof RadarReceiverBlock) {
                hasReceiver = true;
                facing = info.state().getValue(RadarReceiverBlock.FACING).getOpposite();
            }
        }
    }


    public void scanEntities() {
        double angleRad = Math.toRadians(getAngle());
        double fovRad = Math.toRadians(getFOV());

        final AABB box = getAabb(angleRad, fovRad);

        scannedEntities = level.getEntities(this.movedContraption, box)
                .stream()
                .filter(entity -> isInFieldOfView(entity.blockPosition(), -5))
                .collect(Collectors.toList());
    }

    private @NotNull AABB getAabb(double angleRad, double fovRad) {
        return this.getRenderBoundingBox().inflate(getRange(), 25, getRange());
    }

    private void updateEntityPositions() {
        // Iterate over the scanned entities
        for (Entity entity : scannedEntities) {
            // Update the position of each entity in the map
            entityPositions.put(entity, entity.getOnPos());
        }

        // Create a list of entities to remove
        List<Entity> entitiesToRemove = new ArrayList<>();

        // Iterate over the entityPositions map
        for (Map.Entry<Entity, BlockPos> entry : entityPositions.entrySet()) {
            Entity entity = entry.getKey();
            BlockPos pos = entry.getValue();

            // If the entity is in the field of view but not in the scannedEntities list, add it to the list of entities to remove
            if (!entity.isAlive() && !scannedEntities.contains(entity)) {
                entitiesToRemove.add(entity);
            }

        }

        // Remove the entities from the map
        for (Entity entity : entitiesToRemove) {
            entityPositions.remove(entity);
        }

    }

    private void removeEntitiesOutsideOfRange() {
        List<Entity> entitiesToRemove = new ArrayList<>();
        int radarRange = getRange();
        BlockPos radarPos = this.getBlockPos();

        for (Map.Entry<Entity, BlockPos> entry : entityPositions.entrySet()) {
            Entity entity = entry.getKey();
            BlockPos entityPos = entry.getValue();
            double distance = Math.sqrt(entityPos.distSqr(radarPos));

            if (distance > radarRange) {
                entitiesToRemove.add(entity);
            }
        }

        for (Entity entity : entitiesToRemove) {
            entityPositions.remove(entity);
        }
    }

    private boolean isInFieldOfView(BlockPos pos, double fovModifier) {
        // Calculate the angle to the position
        double angleToPos = -Math.toDegrees(Math.atan2(pos.getZ() - getBlockPos().getZ(), pos.getX() - getBlockPos().getX()));

        // Calculate the difference between the angle to the position and the current angle
        angleToPos = (angleToPos + 270);
        if (angleToPos > 360)
            angleToPos = angleToPos % 360;
        if (angleToPos < -360)
            angleToPos = angleToPos % 360;

        double diff = getAngle() - (angleToPos);
        if (diff > 360)
            diff = diff % 360;
        if (diff < -360)
            diff = diff % 360;

        double fov = getFOV() + fovModifier;
        // Check if the position is within the field of view
        return diff >= -fov / 2 && diff <= fov / 2;
    }


    public void setDishCount(int widthRange) {
        this.dishCount = widthRange;
    }

    public int getDishCount() {
        return dishCount;
    }

    public int getRange() {
        return Math.min(BASE_RANGE + RANGE_INCREMENT * getDishCount(), MAX_RANGE);
    }

    public double getFOV() {
        return Math.min(BASE_FOV + FOV_INCREMENT * getDishCount(), MAX_FOV);
    }

    public boolean hasReceiver() {
        return hasReceiver;
    }

    public Direction getInitialFacing() {
        return facing;
    }

    @Override
    public float getAngularSpeed() {
        return super.getAngularSpeed() / (Math.max(4, 16 * (getRange() / MAX_RANGE)));
    }

    public float getAngle() {
        float baseAngle = angle;
        switch (getInitialFacing()) {
            case EAST:
                baseAngle -= 90;
                break;
            case SOUTH:
                baseAngle -= 180;
                break;
            case WEST:
                baseAngle -= 270;
                break;
            default:
                break;
        }
        baseAngle = (baseAngle + 360) % 360; // Ensure the angle is within [0, 360)
        return baseAngle;
    }
}
