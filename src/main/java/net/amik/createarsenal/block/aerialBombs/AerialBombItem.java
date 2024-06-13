package net.amik.createarsenal.block.aerialBombs;

import com.simibubi.create.foundation.gui.ScreenOpener;
import net.amik.createarsenal.block.seaMine.SeaMineBlockEntity;
import net.amik.createarsenal.screen.ProximityFuseScreen;
import net.amik.createarsenal.util.IAdditionalCreativeItems;
import net.minecraft.client.player.LocalPlayer;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (usedHand == InteractionHand.MAIN_HAND && hasFuse(stack))
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> displayScreen(stack, player));

        return super.use(level, player, usedHand);
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        InteractionResult result = super.place(pContext);
        if (pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof AerialBombBlockEntity be) {
            be.proximityRange = getDetonationRadius(pContext.getItemInHand());
        }
        return result;
    }

    @OnlyIn(value = Dist.CLIENT)
    protected void displayScreen(ItemStack stack, Player player) {
        if (player instanceof LocalPlayer)
            ScreenOpener
                    .open(new ProximityFuseScreen(getDetonationRadius(stack)));
    }

    public int getDetonationRadius(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("proximityRange"))
            return tag.getInt("proximityRange");
        return 0;
    }

    public boolean hasFuse(ItemStack stack) {
        return stack.getOrCreateTag().contains("proximityRange");
    }

}
