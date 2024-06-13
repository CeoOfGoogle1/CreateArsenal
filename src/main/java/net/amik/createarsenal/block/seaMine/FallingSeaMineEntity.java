package net.amik.createarsenal.block.seaMine;

import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModProjectiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class FallingSeaMineEntity extends Entity {

    private int time = 0;
    BlockPos targetPos = BlockPos.ZERO;
    int detonationRadius;

    public FallingSeaMineEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }


    public FallingSeaMineEntity(Level level, BlockPos floatLevel, int detonationRadius) {
        super(ModProjectiles.FALLING_SEA_MINE.get(), level);
        this.targetPos = floatLevel;
        this.detonationRadius = detonationRadius;
    }


    @Override
    public void setDeltaMovement(Vec3 deltaMovement) {
        super.setDeltaMovement(new Vec3(0, deltaMovement.y, 0));
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        time = compound.getInt("time");

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("time", time);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.time > 200 || this.onGround()) {
            if (level() instanceof ServerLevel level) {
                level.sendParticles(ParticleTypes.POOF, this.getX(), this.getY() + .4, this.getZ(), 4, 0.5, 0.5, 0.5, 0.1);
            }
            this.remove(RemovalReason.DISCARDED);
        }
        ++this.time;
        this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));

        if (isAtTargetPosition() && isTargetPositionWater()) {
            placeSeaMine();
            this.kill();
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    private boolean isAtTargetPosition() {
        return this.getY() < targetPos.getY() + .5 && this.getY() > targetPos.getY() - .5;
    }

    private boolean isTargetPositionWater() {
        return level().getBlockState(targetPos).is(Blocks.WATER);
    }

    private void placeSeaMine() {
        level().setBlock(targetPos, ModBlocks.SEA_MINE.get().defaultBlockState(), 3);
        if (level().getBlockEntity(targetPos) instanceof SeaMineBlockEntity seamine) {
            seamine.armed = true;
            seamine.detonationRadius = detonationRadius;
        }
    }

    @Override
    public boolean isAttackable() {
        return false;
    }


    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    public static void build(EntityType.Builder<FallingSeaMineEntity> fallingSeaMineEntityBuilder) {
        fallingSeaMineEntityBuilder.sized(0.98F, 0.98F);
    }
}
