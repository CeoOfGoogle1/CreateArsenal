package net.amik.createarsenal.block.aerialBombs;

import com.simibubi.create.foundation.gui.ScreenOpener;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.screen.ClusterBombScreen;
import net.amik.createarsenal.screen.CreativeBombScreen;
import net.amik.createarsenal.screen.ProximityFuseScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AerialBombItem extends BlockItem {

    public AerialBombItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if (hasFuse(stack)) {
            tooltipComponents.add(Component.translatable("proximityfuse.range").append(": " + getDetonationRadius(stack)));
            tooltipComponents.add(Component.literal("Right click to change the detonation radius"));
        }
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getHand() != InteractionHand.MAIN_HAND)
            return super.useOn(context);
        if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof AerialBombBlockEntity be) {
            Player player = context.getPlayer();
            ItemStack itemstack = context.getItemInHand();
            if (be.consumeItem(itemstack)) {
                if (player == null || !player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (usedHand != InteractionHand.MAIN_HAND || !level.isClientSide())
            return super.use(level, player, usedHand);

        ItemStack stack = player.getItemInHand(usedHand);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> displayScreen(stack));
        return super.use(level, player, usedHand);
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        InteractionResult result = super.place(pContext);
        if (pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof AerialBombBlockEntity be) {
            be.loadDefaultBombData();
            be.loadCustomBombData(pContext.getItemInHand().getOrCreateTag());
        }
        return result;
    }

    @OnlyIn(value = Dist.CLIENT)
    protected void displayScreen(ItemStack stack) {
        if (isCreativeBomb()) {
            ScreenOpener.open(new CreativeBombScreen(stack.getOrCreateTag()));
            return;
        }
        if (isClusterBomb()) {
            ScreenOpener.open(new ClusterBombScreen(getAltitude(stack)));
            return;
        }
        if (hasFuse(stack)) {
            ScreenOpener.open(new ProximityFuseScreen(getDetonationRadius(stack)));
        }
    }

    private boolean isClusterBomb() {
        return this.getBlock() == ModBlocks.CLUSTER_BIG_BOMB.get() ||
                this.getBlock() == ModBlocks.CLUSTER_MEDIUM_BOMB.get() ||
                this.getBlock() == ModBlocks.CLUSTER_SMALL_BOMB.get();
    }

    private boolean isCreativeBomb() {
        return this.getBlock() == ModBlocks.CREATIVE_BOMB.get();
    }


    public int getDetonationRadius(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("proximityRange"))
            return tag.getInt("proximityRange");
        return 0;
    }

    public int getAltitude(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("detonationAltitude"))
            return tag.getInt("detonationAltitude");
        return 32;
    }

    public boolean hasFuse(ItemStack stack) {
        return stack.getOrCreateTag().contains("proximityRange");
    }

}
