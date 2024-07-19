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

        if (!(context.getTargetBlockEntity() instanceof MonitorBlockEntity))
            return;

        MonitorBlockEntity monitor = ((MonitorBlockEntity) context.getTargetBlockEntity()).getController();
        BlockPos targetPos = monitor.getTargetPos();
        if (targetPos == null)
            return;

        BlockPos turretPos = turret.getBlockPos();

        double dx = targetPos.getX() - turretPos.getX();
        double dy = targetPos.getY() - turretPos.getY();
        double dz = targetPos.getZ() - turretPos.getZ();


        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
        double newYaw = Math.toDegrees(Math.atan2(dz, dx)) - 90; // Subtracting 90 to align with the game's coordinate system
        double newPitch = Math.toDegrees(Math.atan2(dy, horizontalDistance));


        // Ensure pitch is within -90 to 90 degrees
        if (newPitch < -90) {
            newPitch = -90;
        } else if (newPitch > 90) {
            newPitch = 90;
        }


        contraptionEntity.yaw = (float) newYaw;
        contraptionEntity.pitch = (float) newPitch;
        turret.setYaw((float) newYaw);
        turret.setPitch((float) newPitch);
        turret.notifyUpdate();

    }


}
