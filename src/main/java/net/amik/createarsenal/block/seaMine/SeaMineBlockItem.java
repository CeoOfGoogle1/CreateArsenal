package net.amik.createarsenal.block.seaMine;

import com.simibubi.create.foundation.gui.ScreenOpener;
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

public class SeaMineBlockItem extends BlockItem implements IAdditionalCreativeItems {
    public SeaMineBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (usedHand == InteractionHand.MAIN_HAND)
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> displayScreen(stack, player));

        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.translatable("sea_mine.float_level").append(": " + getFloatLevel(stack)));
        tooltipComponents.add(Component.translatable("sea_mine.range").append(": " + getDetonationRadius(stack)));
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        InteractionResult result = super.place(pContext);
        if (pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof SeaMineBlockEntity be) {
            be.setFloatLevel(getFloatLevel(pContext.getItemInHand()));
            be.setRange(getDetonationRadius(pContext.getItemInHand()));
        }
        return result;
    }

    @OnlyIn(value = Dist.CLIENT)
    protected void displayScreen(ItemStack stack, Player player) {
        if (player instanceof LocalPlayer)
            ScreenOpener
                    .open(new SeaMineScreen(getFloatLevel(stack), getDetonationRadius(stack)));
    }

    public int getFloatLevel(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("floatLevel"))
            return tag.getInt("floatLevel");
        return 0;
    }

    public int getDetonationRadius(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("detonationRadius"))
            return tag.getInt("detonationRadius");
        return 1;
    }

    @Override
    public void addCreativeItems(List<ItemStack> pItems) {
        ItemStack stack = new ItemStack(this);
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("floatLevel", 0);
        tag.putInt("detonationRadius", 1);
        pItems.add(stack);
    }
}
