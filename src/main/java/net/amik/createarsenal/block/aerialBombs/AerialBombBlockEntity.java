package net.amik.createarsenal.block.aerialBombs;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.amik.createarsenal.block.aerialBombs.projectiles.FallingAPAerialBomb;
import net.amik.createarsenal.block.aerialBombs.projectiles.FallingAerialBomb;
import net.amik.createarsenal.block.aerialBombs.projectiles.FallingClusterAerialBomb;
import net.amik.createarsenal.block.aerialBombs.projectiles.FallingShrapnelAerialBomb;
import net.amik.createarsenal.registrate.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class AerialBombBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

    int proximityRange = 0;


    public AerialBombBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    public void tick() {
        super.tick();
        if (hasRedstone() && level.getBlockState(getBlockPos().below()).canBeReplaced())
            dropBomb();
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

    private void dropBomb() {
        BlockState blockState = getBlockState();
        if (ModBlocks.BIG_BOMB.has(blockState))
            dropBigBomb(6);
        else if (ModBlocks.INCENDIARY_BOMB.has(blockState))
            dropFireBomb(3, 5);
        else if (ModBlocks.ARMOR_PIERCING_BOMB.has(blockState))
            dropAPBomb(3, 10);
        else if (ModBlocks.CLUSTER_BOMB.has(blockState))
            dropClusterBomb(3, 16, 10);
        else if (ModBlocks.SHRAPNEL_BOMB.has(blockState))
            dropShrapnelBomb(32);
    }

    private void dropShrapnelBomb(int shrapnelCount) {
        FallingShrapnelAerialBomb bomb = new FallingShrapnelAerialBomb(level);
        bomb.setProximityRadius(proximityRange);
        bomb.setShrapnelCount(shrapnelCount);
        bomb.setExplosionRadius(2);
        bomb.setPos(getBlockPos().getX() + .5, getBlockPos().getY() + .25, getBlockPos().getZ() + .5);
        bomb.setBlockState(this.getBlockState());
        level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
        level.addFreshEntity(bomb);
    }

    public void dropBigBomb(int explosionRadius) {
        FallingAerialBomb bomb = new FallingAerialBomb(level);
        bomb.setProximityRadius(proximityRange);
        bomb.setExplosionRadius(explosionRadius);
        bomb.setPos(getBlockPos().getX() + .5, getBlockPos().getY() + .25, getBlockPos().getZ() + .5);
        bomb.setBlockState(this.getBlockState());
        level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
        level.addFreshEntity(bomb);
    }

    public void dropFireBomb(int explosionRadius, int fireRadius) {
        FallingAerialBomb bomb = new FallingAerialBomb(level);
        bomb.setProximityRadius(proximityRange);
        bomb.setExplosionRadius(explosionRadius);
        bomb.setFireRadius(fireRadius);
        bomb.setPos(getBlockPos().getX() + .5, getBlockPos().getY() + .25, getBlockPos().getZ() + .5);
        bomb.setBlockState(this.getBlockState());
        level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
        level.addFreshEntity(bomb);
    }

    public void dropAPBomb(int explosion, int armorPiercingLevel) {
        FallingAPAerialBomb bomb = new FallingAPAerialBomb(level);
        bomb.setExplosionRadius(explosion);
        bomb.setArmorPiercingLevel(armorPiercingLevel);
        bomb.setPos(getBlockPos().getX() + .5, getBlockPos().getY() + .25, getBlockPos().getZ() + .5);
        bomb.setBlockState(this.getBlockState());
        level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
        level.addFreshEntity(bomb);
    }

    public void dropClusterBomb(int explosion, int clusterCount, int altitude) {
        FallingClusterAerialBomb bomb = new FallingClusterAerialBomb(level);
        bomb.setExplosionRadius(explosion);
        bomb.setClusterBombletCount(clusterCount);
        bomb.setDetonationAltitude(altitude);
        bomb.setPos(getBlockPos().getX() + .5, getBlockPos().getY() + .25, getBlockPos().getZ() + .5);
        bomb.setBlockState(this.getBlockState());
        level.setBlockAndUpdate(getBlockPos(), Blocks.AIR.defaultBlockState());
        level.addFreshEntity(bomb);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if (proximityRange == 0)
            return false;
        tooltip.add(Component.literal(""));
        tooltip.add(Component.translatable("proximityfuse.range").append(": " + proximityRange));
        return true;
    }
}
