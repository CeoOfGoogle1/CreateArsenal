package net.amik.createarsenal.registrate;

import com.tterrag.registrate.util.entry.EntityEntry;
import net.amik.createarsenal.BulletRenderer;
import net.amik.createarsenal.shell.ShellEntity;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

public class ModProjectiles {

    public static final EntityEntry<ShellEntity> SHELL_ENTITY = REGISTRATE
            .entity("bullet",(EntityType.EntityFactory<ShellEntity>)ShellEntity::new, MobCategory.MISC)
            .renderer(() -> BulletRenderer::new)
            .register();


    public static void register() {}

}
