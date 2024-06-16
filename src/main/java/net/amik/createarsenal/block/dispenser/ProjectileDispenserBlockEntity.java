package net.amik.createarsenal.block.dispenser;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.amik.createarsenal.block.landmine.FallingLandMine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

import static net.amik.createarsenal.util.HorizontalDirectionBlock.FACING;

public class ProjectileDispenserBlockEntity extends KineticBlockEntity {

    public static final int MAX_ANGLE = 90;
    public static final int MIN_ANGLE = -90;
    int angle = 90;
    int count = 16;
    int delay = 10;

    public ProjectileDispenserBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);

    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        angle = compound.getInt("angle");
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putInt("angle", angle);
    }

    @Override
    public void tick() {
        super.tick();
        if (getSpeed() < 0 && angle > MIN_ANGLE) {
            angle = (int) Math.max(angle + getSpeed() / 8, MIN_ANGLE);
            notifyUpdate();
        } else if (getSpeed() > 0 && angle < MAX_ANGLE) {
            angle = (int) Math.min(angle + getSpeed() / 8, MAX_ANGLE);
            notifyUpdate();
        }
        if (!hasRedstone())
            delay = 0;
        if (delay > 0) {
            delay--;
            return;
        }
        if (hasRedstone() && hasInventory()) {
            dispense();
            delay = 30;
        }

    }

    private void dispense() {
        Direction facing = getBlockState().getValue(FACING);
        for (int i = 0; i < count; i++) {
            FallingLandMine bomb = new FallingLandMine(level);
            double yPosition = getYPositionBasedOnAngle(angle);
            double xPosition = getXZPositionBasedOnAngle(-angle) * facing.getStepX();
            double zPosition = getXZPositionBasedOnAngle(-angle) * facing.getStepZ();
            bomb.setPos(worldPosition.getX() + 0.5 + xPosition, yPosition + worldPosition.getY() + .2, worldPosition.getZ() + 0.5 + zPosition);

            double speed = 0.6; // Adjust this value to change the speed of the bomb
            double dx = speed * Math.sin(Math.toRadians(-angle)) * facing.getStepX() + (Math.random() * 0.4 - 0.2);
            double dy = speed * Math.cos(Math.toRadians(-angle)) * (1 + Math.random() * 0.4 - 0.1);
            double dz = speed * Math.sin(Math.toRadians(-angle)) * facing.getStepZ() + (Math.random() * 0.4 - 0.2);

            bomb.setDeltaMovement(dx, dy, dz);
            level.addFreshEntity(bomb);
        }
    }

    private double getYPositionBasedOnAngle(int angle) {
        // Convert the angle to radians for the trigonometric functions
        double angleInRadians = Math.toRadians(angle);

        // Calculate the Y position based on the angle
        double yPosition = Math.cos(angleInRadians);

        return Math.max(0.5, yPosition);
    }

    private double getXZPositionBasedOnAngle(int angle) {
        // Convert the angle to radians for the trigonometric functions
        double angleInRadians = Math.toRadians(angle);

        // Calculate the X/Z position based on the angle
        double xzPosition = Math.sin(angleInRadians);

        return xzPosition;
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

    private boolean hasInventory() {
        return true;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        tooltip.add(Component.nullToEmpty("    Angle: " + angle));
        return true;
    }
}
