package net.amik.createarsenal.compat.cbc;

import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTarget;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import net.amik.createarsenal.block.radar.monitor.MonitorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;
import rbasamoyai.createbigcannons.cannon_control.contraption.PitchOrientedContraptionEntity;

import java.util.List;

public class TurretGuidanceBehavior extends DisplaySource {

    @Override
    public List<MutableComponent> provideText(DisplayLinkContext context, DisplayTargetStats stats) {
        return List.of(Component.literal(" "));
    }

    @Override
    public int getPassiveRefreshTicks() {
        return 1;
    }

    @Override
    public void transferData(DisplayLinkContext context, DisplayTarget activeTarget, int line) {
        super.transferData(context, activeTarget, line);
        if (!(context.getSourceBlockEntity() instanceof CannonMountBlockEntity turret))
            return;

        PitchOrientedContraptionEntity contraptionEntity = turret.getContraption();
        if (contraptionEntity == null)
            return;

        if (!(context.getTargetBlockEntity() instanceof MonitorBlockEntity monitor))
            return;

        BlockPos targetPos = monitor.getTargetPos();
        if (targetPos == null)
            return;

        BlockPos turretPos = turret.getBlockPos();

        double dx = targetPos.getX() - turretPos.getX();
        double dy = targetPos.getY() - turretPos.getY();
        double dz = targetPos.getZ() - turretPos.getZ();

        double targetYaw = calculateTargetYaw(dx, dz);
        double targetPitch = calculateTargetPitch(dx, dy, dz);

        double currentYaw = contraptionEntity.yaw;
        double currentPitch = contraptionEntity.pitch;

        double yawDiff = normalizeDifference(targetYaw - currentYaw);
        double pitchDiff = normalizeDifference(targetPitch - currentPitch);

        double newYaw = adjustYaw(currentYaw, yawDiff);
        double newPitch = adjustPitch(currentPitch, pitchDiff);

        contraptionEntity.yaw = (float) newYaw;
        contraptionEntity.pitch = (float) newPitch;
        turret.setYaw((float) newYaw);
        turret.setPitch((float) newPitch);
        turret.notifyUpdate();

    }

    private double calculateTargetYaw(double dx, double dz) {
        double targetYaw = Math.atan2(dz, dx) - Math.PI / 2;
        targetYaw = Math.toDegrees(targetYaw);
        return targetYaw < 0 ? targetYaw + 360 : targetYaw;
    }

    private double calculateTargetPitch(double dx, double dy, double dz) {
        double distance = Math.sqrt(dx * dx + dz * dz);
        double targetPitch = Math.atan2(dy, distance);
        targetPitch = Math.toDegrees(targetPitch);
        return targetPitch < 0 ? targetPitch + 360 : targetPitch;
    }

    private double normalizeDifference(double diff) {
        return ((diff + 180) % 360) - 180;
    }

    private double adjustYaw(double currentYaw, double diff) {
        double tolerance = 2.0; // Tolerance level, adjust as needed
        if (Math.abs(diff) <= tolerance) {
            return currentYaw; // Do not adjust if within tolerance
        }

        double yawAdjustmentRate = 0.5; // Adjust this value to control the smoothness
        if (Math.abs(diff) < yawAdjustmentRate) {
            return currentYaw + diff; // If the difference is smaller than the rate, adjust directly
        }
        currentYaw += diff < 0 ? -yawAdjustmentRate : yawAdjustmentRate;
        // Normalize the yaw to keep it within 0 - 360 degrees
        if (currentYaw < 0) {
            currentYaw += 360;
        } else if (currentYaw >= 360) {
            currentYaw -= 360;
        }
        return currentYaw;
    }

    private double adjustPitch(double currentPitch, double diff) {
        double tolerance = 2.0; // Tolerance level, adjust as needed
        if (Math.abs(diff) <= tolerance) {
            return currentPitch; // Do not adjust if within tolerance
        }

        double pitchAdjustmentRate = 0.5; // Adjust this value to control the smoothness
        if (Math.abs(diff) < pitchAdjustmentRate) {
            return currentPitch + diff; // If the difference is smaller than the rate, adjust directly
        }
        currentPitch += diff < 0 ? -pitchAdjustmentRate : pitchAdjustmentRate;
        // Normalize the pitch to keep it within 0 - 360 degrees
        if (currentPitch < 0) {
            currentPitch += 360;
        } else if (currentPitch >= 360) {
            currentPitch -= 360;
        }
        return currentPitch;
    }
}
