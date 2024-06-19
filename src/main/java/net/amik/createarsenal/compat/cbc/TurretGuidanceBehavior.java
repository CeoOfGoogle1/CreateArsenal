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
        MonitorBlockEntity monitor = (MonitorBlockEntity) context.getTargetBlockEntity();
        if (!monitor.hasTarget())
            return;

        BlockPos turretPos = turret.getBlockPos();
        BlockPos targetPos = monitor.getSelectedTarget();

        // Calculate the difference in each axis
        double dx = targetPos.getX() - turretPos.getX();
        double dy = targetPos.getY() - turretPos.getY();
        double dz = targetPos.getZ() - turretPos.getZ();

        // Calculate the target yaw and pitch angles
        double targetYaw = calculateTargetYaw(dx, dz);
        double targetPitch = calculateTargetPitch(dx, dy, dz);

        // Adjust yaw
        double currentYaw = contraptionEntity.yaw;
        double yawDiff = normalizeDifference(targetYaw - currentYaw);
        if (Math.abs(yawDiff) > 1) {
            currentYaw = adjustYaw(currentYaw, yawDiff);
        }

        // Adjust pitch
        double currentPitch = contraptionEntity.pitch;
        double pitchDiff = normalizeDifference(targetPitch - currentPitch);
        if (Math.abs(pitchDiff) > 1) {
            currentPitch = adjustPitch(currentPitch, pitchDiff);
        }

        turret.setYaw((float) currentYaw);
        turret.setPitch((float) currentPitch);
        contraptionEntity.yaw = (float) currentYaw;
        contraptionEntity.pitch = (float) currentPitch;

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
        currentYaw += diff < 0 ? -1 : 1;
        return currentYaw < 0 ? currentYaw + 360 : currentYaw >= 360 ? currentYaw - 360 : currentYaw;
    }

    private double adjustPitch(double currentPitch, double diff) {
        currentPitch += diff < 0 ? -1 : 1;
        return currentPitch < 0 ? currentPitch + 360 : currentPitch >= 360 ? currentPitch - 360 : currentPitch;
    }
}
