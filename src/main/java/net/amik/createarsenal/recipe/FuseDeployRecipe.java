package net.amik.createarsenal.recipe;

import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class FuseDeployRecipe extends DeployerApplicationRecipe {

    public FuseDeployRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(params);
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        if (!super.matches(inv, level)) return false;
        ItemStack stack = inv.getItem(0).copy();
        ItemStack fuse = inv.getItem(1).copy();
        if (!fuse.hasTag())
            return false;
        stack.setTag(fuse.getTag().copy());
        enforceNextResult(() -> stack);
        return true;
    }


}