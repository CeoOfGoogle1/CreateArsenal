package net.amik.createarsenal.compat.cbc.block;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.cannon_control.cannon_mount.CannonMountBlockEntity;

import java.util.Optional;

public class CannonControllerBlockEntity extends KineticBlockEntity {

    BlockPos target;

    public CannonControllerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        getCannon().ifPresent(this::aimCannonAtTarget);
    }

    private void aimCannonAtTarget(CannonMountBlockEntity cannon) {
        if (target != null && target != BlockPos.ZERO && cannon.getContraption() != null) {
            double dx = getBlockPos().getX() - target.getX();
            double dy = getBlockPos().getY() - target.getY();
            double dz = getBlockPos().getZ() - target.getZ();
            double horizontalDistance = Math.sqrt(dx * dx + dz * dz);

            double newYaw = Math.toDegrees(Math.atan2(dz, dx)) + 90;
            double newPitch = Math.toDegrees(Math.atan2(dy, horizontalDistance));

            // Normalize yaw to 0-360 degrees
            if (newYaw < 0) {
                newYaw += 360;
            }

            // Ensure pitch is within -90 to 90 degrees
            if (newPitch < -90) {
                newPitch = -90;
            } else if (newPitch > 90) {
                newPitch = 90;
            }

            System.out.println("CannonControllerBlockEntity: " + target.getX() + ", " + target.getY() + ", " + target.getZ());
            System.out.println("Yaw: " + newYaw);
            System.out.println("Pitch: " + newPitch);

            cannon.getContraption().yaw = (float) newYaw;
            cannon.getContraption().pitch = (float) newPitch;
            cannon.notifyUpdate();
        }
    }

    private Optional<CannonMountBlockEntity> getCannon() {
        BlockEntity be = level.getBlockEntity(getBlockPos().above());
        if (be == null)
            return Optional.empty();
        if (be instanceof CannonMountBlockEntity)
            return Optional.of((CannonMountBlockEntity) be);
        return Optional.empty();
    }

    public void setTarget(BlockPos pos) {
        this.target = pos;
        notifyUpdate();
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        target = BlockPos.of(compound.getLong("target"));
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        if (target != null)
            compound.putLong("target", target.asLong());
    }
}
