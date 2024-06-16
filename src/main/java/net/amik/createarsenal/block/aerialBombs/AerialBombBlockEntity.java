package net.amik.createarsenal.block.aerialBombs;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.amik.createarsenal.block.aerialBombs.projectiles.FallingAerialBomb;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class AerialBombBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

    int count = 1;
    int maxCount = 1;
    int proximityRange = 0;
    int explosionRadius = 0;
    int fireRadius = 0;
    int timeRequired = 20;
    int armorPiercingLevel = 0;
    int clusterBombletCount = 0;
    int detonationAltitude = 32;
    int shrapnelCount = 0;
    int delay = 0;
    int landmineCount = 0;

    public AerialBombBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    public void tick() {
        super.tick();
        if (!hasRedstone())
            delay = 0;
        if (delay > 0) {
            delay--;
            return;
        }
        if (canDropBomb()) {
            dropBomb();
            count--;
            delay = 40;
        }
        if (count <= 0) {
            level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
        }
    }

    public boolean canDropBomb() {
        return hasRedstone() && level.getBlockState(getBlockPos().below()).canBeReplaced() && count > 0;
    }


    public void dropBomb() {
        FallingAerialBomb bomb = new FallingAerialBomb(level);
        loadBomb(bomb);
        bomb.setPos(getBlockPos().getX() + .5, getBlockPos().getY() - .5, getBlockPos().getZ() + .5);
        level.addFreshEntity(bomb);
        level.playSound(null, bomb, ModSoundEvents.BOMB_WHISTLE.getMainEvent(), SoundSource.HOSTILE, 1, 1);
    }


    private void loadBomb(FallingAerialBomb bomb) {
        bomb.setBlockState(this.getBlockState());
        bomb.setProximityRadius(proximityRange);
        bomb.setExplosionRadius(explosionRadius);
        bomb.setFireRadius(fireRadius);
        bomb.setTimeRequired(timeRequired);
        bomb.setArmorPiercingLevel(armorPiercingLevel);
        bomb.setClusterBombletCount(clusterBombletCount);
        bomb.setDetonationAltitude(detonationAltitude);
        bomb.setShrapnelCount(shrapnelCount);
        bomb.setLandmineCount(landmineCount);
    }


    private boolean hasRedstone() {
        if (level.hasNeighborSignal(worldPosition))
            return true;
        for (Direction direction : Direction.values()) {
            if (level.hasSignal(worldPosition, direction))
                return true;
        }
        return false;
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        proximityRange = tag.getInt("proximityRange");
        explosionRadius = tag.getInt("explosionRadius");
        fireRadius = tag.getInt("fireRadius");
        timeRequired = tag.getInt("timeRequired");
        armorPiercingLevel = tag.getInt("armorPiercingLevel");
        clusterBombletCount = tag.getInt("clusterBombletCount");
        detonationAltitude = tag.getInt("detonationAltitude");
        shrapnelCount = tag.getInt("shrapnelCount");
        count = tag.getInt("count");
        maxCount = tag.getInt("maxCount");
        landmineCount = tag.getInt("landmineCount");

    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putInt("proximityRange", proximityRange);
        tag.putInt("explosionRadius", explosionRadius);
        tag.putInt("fireRadius", fireRadius);
        tag.putInt("timeRequired", timeRequired);
        tag.putInt("armorPiercingLevel", armorPiercingLevel);
        tag.putInt("clusterBombletCount", clusterBombletCount);
        tag.putInt("detonationAltitude", detonationAltitude);
        tag.putInt("shrapnelCount", shrapnelCount);
        tag.putInt("count", count);
        tag.putInt("maxCount", maxCount);
        tag.putInt("landmineCount", landmineCount);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        tooltip.add(Component.literal(""));
        if (count > 0)
            tooltip.add(Component.literal("Count: " + count));
        if (proximityRange > 0)
            tooltip.add(Component.translatable("proximityfuse.range").append(": " + proximityRange));
        if (explosionRadius > 0)
            tooltip.add(Component.literal("Explosion Radius: " + explosionRadius));
        if (fireRadius > 0)
            tooltip.add(Component.literal("Fire Radius: " + fireRadius));
        if (timeRequired != 20)
            tooltip.add(Component.literal("Time Required: " + timeRequired));
        if (armorPiercingLevel > 0)
            tooltip.add(Component.literal("Armor Piercing Level: " + armorPiercingLevel));
        if (clusterBombletCount > 0)
            tooltip.add(Component.literal("Cluster Bomblet Count: " + clusterBombletCount));
        if (clusterBombletCount > 0)
            tooltip.add(Component.literal("Detonation Altitude: " + detonationAltitude));
        if (shrapnelCount > 0)
            tooltip.add(Component.literal("Shrapnel Count: " + shrapnelCount));
        if (landmineCount > 0)
            tooltip.add(Component.literal("Landmine Count: " + landmineCount));
        return true;
    }

    public void loadCustomBombData(CompoundTag tag) {
        if (tag.contains("proximityRange"))
            proximityRange = tag.getInt("proximityRange");
        if (tag.contains("explosionRadius"))
            explosionRadius = tag.getInt("explosionRadius");
        if (tag.contains("fireRadius"))
            fireRadius = tag.getInt("fireRadius");
        if (tag.contains("timeRequired"))
            timeRequired = tag.getInt("timeRequired");
        if (tag.contains("armorPiercingLevel"))
            armorPiercingLevel = tag.getInt("armorPiercingLevel");
        if (tag.contains("clusterBombletCount"))
            clusterBombletCount = tag.getInt("clusterBombletCount");
        if (tag.contains("detonationAltitude"))
            detonationAltitude = tag.getInt("detonationAltitude");
        if (tag.contains("shrapnelCount"))
            shrapnelCount = tag.getInt("shrapnelCount");
        if (tag.contains("count"))
            count = tag.getInt("count");
        if (tag.contains("landmineCount"))
            landmineCount = tag.getInt("landmineCount");
    }

    public void loadDefaultBombData() {
        if (ModBlocks.BIG_BOMB.has(this.getBlockState())) {
            explosionRadius = 8;
        }
        if (ModBlocks.MEDIUM_BOMB.has(this.getBlockState())) {
            explosionRadius = 4;
            maxCount = 4;
        }
        if (ModBlocks.SMALL_BOMB.has(this.getBlockState())) {
            explosionRadius = 2;
            maxCount = 9;
        }
        if (ModBlocks.INCENDIARY_BIG_BOMB.has(this.getBlockState())) {
            explosionRadius = 4;
            fireRadius = 8;
        }
        if (ModBlocks.INCENDIARY_MEDIUM_BOMB.has(this.getBlockState())) {
            explosionRadius = 2;
            fireRadius = 4;
            maxCount = 4;
        }
        if (ModBlocks.INCENDIARY_SMALL_BOMB.has(this.getBlockState())) {
            explosionRadius = 1;
            fireRadius = 2;
            maxCount = 9;
        }
        if (ModBlocks.ARMOR_PIERCING_BIG_BOMB.has(this.getBlockState())) {
            explosionRadius = 5;
            armorPiercingLevel = 10;
        }
        if (ModBlocks.ARMOR_PIERCING_MEDIUM_BOMB.has(this.getBlockState())) {
            explosionRadius = 3;
            armorPiercingLevel = 5;
            maxCount = 4;
        }
        if (ModBlocks.ARMOR_PIERCING_SMALL_BOMB.has(this.getBlockState())) {
            explosionRadius = 1;
            armorPiercingLevel = 2;
            maxCount = 9;
        }
        if (ModBlocks.CLUSTER_BIG_BOMB.has(this.getBlockState())) {
            explosionRadius = 4;
            clusterBombletCount = 16;
        }
        if (ModBlocks.CLUSTER_MEDIUM_BOMB.has(this.getBlockState())) {
            explosionRadius = 2;
            clusterBombletCount = 8;
            maxCount = 4;
        }
        if (ModBlocks.CLUSTER_SMALL_BOMB.has(this.getBlockState())) {
            explosionRadius = 1;
            clusterBombletCount = 4;
            maxCount = 9;
        }
        if (ModBlocks.SHRAPNEL_BIG_BOMB.has(this.getBlockState())) {
            explosionRadius = 3;
            shrapnelCount = 32;
        }
        if (ModBlocks.SHRAPNEL_MEDIUM_BOMB.has(this.getBlockState())) {
            explosionRadius = 2;
            shrapnelCount = 16;
            maxCount = 4;
        }
        if (ModBlocks.SHRAPNEL_SMALL_BOMB.has(this.getBlockState())) {
            explosionRadius = 1;
            shrapnelCount = 8;
            maxCount = 9;
        }
        if (ModBlocks.CREATIVE_BOMB.has(this.getBlockState())) {
            count = 64;
        }
    }

    public boolean consumeItem(ItemStack itemstack) {
        if (!itemstack.is(getBlockState().getBlock().asItem()))
            return false;
        if (count >= maxCount)
            return false;
        count++;
        notifyUpdate();
        return true;
    }
}
