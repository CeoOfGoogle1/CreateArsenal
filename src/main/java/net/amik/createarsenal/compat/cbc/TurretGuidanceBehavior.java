package net.amik.createarsenal.compat.cbc;

import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTarget;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import net.amik.createarsenal.block.radar.monitor.MonitorBlockEntity;
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

        monitor.getRadar();
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
