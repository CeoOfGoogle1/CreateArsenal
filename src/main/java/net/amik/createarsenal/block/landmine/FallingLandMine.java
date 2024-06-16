package net.amik.createarsenal.block.landmine;

import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModProjectiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;
import rbasamoyai.ritchiesprojectilelib.PreciseProjectile;

public class FallingLandMine extends Projectile implements PreciseProjectile {

    public FallingLandMine(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }


    public FallingLandMine(Level level) {
        super(ModProjectiles.FALLING_LANDMINE.get(), level);
    }


    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        if (isInWater()) {
            this.remove(RemovalReason.DISCARDED);
        }
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }
        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;
        if (!this.isNoGravity()) {
            Vec3 vec31 = this.getDeltaMovement();
            this.setDeltaMovement(vec31.x, vec31.y - (double) this.getGravity(), vec31.z);
        }
        this.setPos(d2, d0, d1);
    }


    protected float getGravity() {
        return 0.03F;
    }


    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
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

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos target = result.getBlockPos().above();
        if (level().getBlockState(target).isAir() && level().getBlockState(result.getBlockPos()).isFaceSturdy(level(), result.getBlockPos(), Direction.UP)) {
            level().setBlockAndUpdate(target, ModBlocks.LANDMINE.get().defaultBlockState().setValue(LandMineBlock.BURIED, true));
        }
        if (level().getBlockState(result.getBlockPos()).canBeReplaced() && level().getBlockState(result.getBlockPos().below()).isFaceSturdy(level(), result.getBlockPos().below(), Direction.UP)) {
            level().setBlockAndUpdate(result.getBlockPos(), ModBlocks.LANDMINE.get().defaultBlockState().setValue(LandMineBlock.BURIED, true));
        }
        this.remove(RemovalReason.DISCARDED);

    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    public static void build(EntityType.Builder<FallingLandMine> fallingBombBuilder) {
        fallingBombBuilder.sized(0.75F, .25f);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        double d0 = packet.getXa();
        double d1 = packet.getYa();
        double d2 = packet.getZa();
        this.setDeltaMovement(d0, d1, d2);
    }

}