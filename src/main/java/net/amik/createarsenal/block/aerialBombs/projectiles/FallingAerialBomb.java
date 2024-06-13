package net.amik.createarsenal.block.aerialBombs.projectiles;

import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModProjectiles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;

public class FallingAerialBomb extends Projectile {

    int proximityRadius = 0;
    int explosionRadius = 4;
    int fireRadius = 0;
    int timeRequired = 20;


    protected static final EntityDataAccessor<Integer> TIME = SynchedEntityData.defineId(FallingAerialBomb.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<BlockState> STATE = SynchedEntityData.defineId(FallingAerialBomb.class, EntityDataSerializers.BLOCK_STATE);


    public FallingAerialBomb(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public int getTime() {
        return this.entityData.get(TIME);
    }


    public FallingAerialBomb(Level level) {
        super(ModProjectiles.FALLING_AERIAL_BOMB.get(), level);
        this.explosionRadius = 0;
        this.fireRadius = 0;
        this.proximityRadius = 0;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(TIME, 0);
        this.entityData.define(STATE, ModBlocks.BIG_BOMB.get().defaultBlockState());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (!compound.contains("time"))
            return;
        this.proximityRadius = compound.getInt("proximityRadius");
        this.explosionRadius = compound.getInt("explosionRadius");
        this.fireRadius = compound.getInt("fireRadius");
        this.entityData.set(TIME, compound.getInt("time"));
        this.entityData.set(STATE, NbtUtils.readBlockState(level().holderLookup(Registries.BLOCK), compound.getCompound("blockstate")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("time", getTime());
        compound.putInt("proximityRadius", proximityRadius);
        compound.putInt("explosionRadius", explosionRadius);
        compound.putInt("fireRadius", fireRadius);
        compound.put("blockstate", NbtUtils.writeBlockState(this.getBombBlockState()));
    }

    @Override
    public void tick() {
        super.tick();
        if (isInWater()) {
            triggerExplosion();
            this.remove(RemovalReason.DISCARDED);
        }
        if (inProximityRange()) {
            triggerOnHitEffects();
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
        if (!level().isClientSide()) {
            this.entityData.set(TIME, this.getTime() + 1);
        }
        if (this.getTime() > 200) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    private boolean inProximityRange() {
        return !level().getEntities(this, getBoundingBox().inflate(proximityRadius), this::isNotItemEntity).isEmpty() && getTime() > timeRequired;
    }

    private boolean isNotItemEntity(Entity entity) {
        return !(entity instanceof ItemEntity);
    }


    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        triggerOnHitEffects();
    }

    protected void triggerOnHitEffects() {
        if (getTime() > timeRequired)
            triggerExplosion();
        else if (level() instanceof ServerLevel server)
            server.sendParticles(ParticleTypes.POOF, getX(), getY(), getZ(), 10, 0.1, 0.1, 0.1, 0.1);
        this.remove(RemovalReason.DISCARDED);
    }

    protected float getGravity() {
        return 0.03F;
    }

    private void triggerExplosion() {
        if (explosionRadius > 0)
            level().explode(this, this.getX(), this.getY(), this.getZ(), explosionRadius, false, Level.ExplosionInteraction.BLOCK);
        if (fireRadius > 0)
            level().explode(this, this.getX(), this.getY(), this.getZ(), fireRadius, true, Level.ExplosionInteraction.NONE);
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

    public static void build(EntityType.Builder<FallingAerialBomb> fallingBombBuilder) {
        fallingBombBuilder.sized(0.98F, 0.98F);
    }

    public int getProximityRadius() {
        return proximityRadius;
    }

    public void setProximityRadius(int proximityRadius) {
        this.proximityRadius = proximityRadius;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }

    public void setExplosionRadius(int explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    public int getFireRadius() {
        return fireRadius;
    }

    public void setFireRadius(int fireRadius) {
        this.fireRadius = fireRadius;
    }


    public BlockState getBombBlockState() {
        return this.entityData.get(STATE);
    }

    public void setBlockState(BlockState bombBlockState) {
        this.entityData.set(STATE, bombBlockState);
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