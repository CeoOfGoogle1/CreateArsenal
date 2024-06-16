package net.amik.createarsenal.block.aerialBombs.projectiles;

import net.amik.createarsenal.block.landmine.FallingLandMine;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;
import rbasamoyai.ritchiesprojectilelib.PreciseProjectile;

public class FallingAerialBomb extends Projectile implements PreciseProjectile {

    int proximityRadius = 0;
    int explosionRadius = 0;
    int fireRadius = 0;
    int armorPiercingLevel = 0;
    int clusterBombletCount = 0;
    int landmineCount = 0;
    int detonationAltitude = 0;
    int shrapnelCount = 0;


    protected static final EntityDataAccessor<Integer> TIME_REQUIRED = SynchedEntityData.defineId(FallingAerialBomb.class, EntityDataSerializers.INT);
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
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(TIME, 0);
        this.entityData.define(TIME_REQUIRED, 20);
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
        this.entityData.set(TIME_REQUIRED, compound.getInt("timeRequired"));
        this.armorPiercingLevel = compound.getInt("armorPiercingLevel");
        this.clusterBombletCount = compound.getInt("clusterBombletCount");
        this.detonationAltitude = compound.getInt("detonationAltitude");
        this.shrapnelCount = compound.getInt("shrapnelCount");
        this.landmineCount = compound.getInt("landmineCount");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("time", getTime());
        compound.putInt("timeRequired", this.entityData.get(TIME_REQUIRED));
        compound.putInt("proximityRadius", proximityRadius);
        compound.putInt("explosionRadius", explosionRadius);
        compound.putInt("fireRadius", fireRadius);
        compound.put("blockstate", NbtUtils.writeBlockState(this.getBombBlockState()));
        compound.putInt("armorPiercingLevel", armorPiercingLevel);
        compound.putInt("clusterBombletCount", clusterBombletCount);
        compound.putInt("detonationAltitude", detonationAltitude);
        compound.putInt("shrapnelCount", shrapnelCount);
        compound.putInt("landmineCount", landmineCount);
    }

    @Override
    public void tick() {
        super.tick();
        if (isInWater()) {
            triggerExplosion();
            this.remove(RemovalReason.DISCARDED);
        }
        if (proximityRadius > 0 && inProximityRange()) {
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
        if (atClusterAltitude() && !level().isClientSide() && (clusterBombletCount > 0 || landmineCount > 0)) {
            if (landmineCount > 0) {
                triggerLandmineExplosion();
            }
            if (clusterBombletCount > 0)
                triggerClusterExplosion();
            this.remove(RemovalReason.DISCARDED);
        }

        if (this.getTime() > 200) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    private boolean atClusterAltitude() {
        if (getTime() < entityData.get(TIME_REQUIRED))
            return false;
        for (int i = 0; i < this.detonationAltitude; ++i) {
            if (!this.level().getBlockState(this.getOnPos().below(i)).isAir()) {
                return true;
            }
        }

        return false;
    }

    private void triggerClusterExplosion() {
        if (this.clusterBombletCount > 0) {
            for (int i = 0; i < this.clusterBombletCount; ++i) {
                ClusterBomblet bomb = new ClusterBomblet(this.level());
                bomb.setPos(this.getX(), this.getY(), this.getZ());
                bomb.setDeltaMovement(new Vec3(this.random.nextFloat() - .5f * .7f, -Math.abs(this.random.nextFloat()), this.random.nextFloat() - .5f * .7f));
                this.level().addFreshEntity(bomb);
            }
        }

    }

    private void triggerLandmineExplosion() {
        if (this.landmineCount > 0) {
            for (int i = 0; i < this.landmineCount; ++i) {
                FallingLandMine bomb = new FallingLandMine(this.level());
                bomb.setPos(this.getX(), this.getY(), this.getZ());
                bomb.setDeltaMovement(new Vec3(this.random.nextFloat() - .5f * .7f, -Math.abs(this.random.nextFloat()), this.random.nextFloat() - .5f * .7f));
                this.level().addFreshEntity(bomb);
            }
        }

    }

    private boolean inProximityRange() {
        return !level().getEntities(this, getBoundingBox().inflate(proximityRadius), this::isNotItemEntity).isEmpty() && getTime() > entityData.get(TIME_REQUIRED);
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
        if (armorPiercingLevel > 0)
            return;
        if (getTime() > entityData.get(TIME_REQUIRED)) {
            triggerExplosion();
            if (shrapnelCount > 0)
                triggerFragExplosion();
        } else if (level() instanceof ServerLevel server)
            server.sendParticles(ParticleTypes.POOF, getX(), getY(), getZ(), 10, 0.1, 0.1, 0.1, 0.1);
        this.remove(RemovalReason.DISCARDED);
    }

    private void triggerFragExplosion() {
        if (this.shrapnelCount > 0) {
            for (int i = 0; i < this.shrapnelCount; ++i) {
                ShrapnelProjectile bomb = new ShrapnelProjectile(this.level());
                bomb.setPos(this.getX(), this.getY(), this.getZ());
                double r = 1; // radius
                double theta = 2 * Math.PI * random.nextDouble(); // azimuthal angle
                double phi = Math.acos(1 - 2 * random.nextDouble()); // polar angle
                double x = r * Math.sin(phi) * Math.cos(theta);
                double y = r * Math.sin(phi) * Math.sin(theta);
                double z = r * Math.cos(phi);
                bomb.setDeltaMovement(new Vec3(x, y * .5f + .1f, z));
                this.level().addFreshEntity(bomb);
            }
        }
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


    public int getArmorPiercingLevel() {
        return armorPiercingLevel;
    }

    public void setArmorPiercingLevel(int armorPiercingLevel) {
        this.armorPiercingLevel = armorPiercingLevel;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (armorPiercingLevel > 0) {
            this.armorPiercingLevel--;
            level().destroyBlock(result.getBlockPos(), false);
        }
        if (armorPiercingLevel > 0 && !level().getBlockState(result.getBlockPos().below()).isAir()) {
            this.armorPiercingLevel--;
            level().destroyBlock(result.getBlockPos().below(), false);
        }
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
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

    public int getTimeRequired() {
        return this.entityData.get(TIME_REQUIRED);
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

    public void setTimeRequired(int timeRequired) {
        this.entityData.set(TIME_REQUIRED, timeRequired);
    }

    public void setClusterBombletCount(int clusterBombletCount) {
        this.clusterBombletCount = clusterBombletCount;
    }

    public void setDetonationAltitude(int detonationAltitude) {
        this.detonationAltitude = detonationAltitude;
    }

    public void setShrapnelCount(int shrapnelCount) {
        this.shrapnelCount = shrapnelCount;
    }

    public void setLandmineCount(int landmineCount) {
        this.landmineCount = landmineCount;
    }
}