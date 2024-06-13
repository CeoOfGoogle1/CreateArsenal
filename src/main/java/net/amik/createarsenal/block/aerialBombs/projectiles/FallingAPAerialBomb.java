package net.amik.createarsenal.block.aerialBombs.projectiles;

import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModProjectiles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class FallingAPAerialBomb extends FallingAerialBomb {

    int armorPiercingLevel = 10;


    public FallingAPAerialBomb(EntityType<? extends FallingAerialBomb> entityType, Level level) {
        super(entityType, level);
        this.setBlockState(ModBlocks.ARMOR_PIERCING_BOMB.getDefaultState());
    }

    public FallingAPAerialBomb(Level level) {
        super(ModProjectiles.FALLING_AP_AERIAL_BOMB.get(), level);
        this.armorPiercingLevel = 0;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("armorPiercingLevel"))
            this.armorPiercingLevel = compound.getInt("armorPiercingLevel");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("armorPiercingLevel", armorPiercingLevel);
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
    }

    @Override
    protected void triggerOnHitEffects() {
        if (armorPiercingLevel > 0)
            return;
        super.triggerOnHitEffects();
    }

    public static void APbuild(EntityType.Builder<FallingAPAerialBomb> fallingBombBuilder) {
        fallingBombBuilder.sized(0.98F, 0.98F);
    }
}
