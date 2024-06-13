package net.amik.createarsenal.registrate;

import com.simibubi.create.foundation.data.CreateEntityBuilder;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.block.aerialBombs.projectiles.*;
import net.amik.createarsenal.block.seaMine.FallingSeaMineEntity;
import net.amik.createarsenal.block.seaMine.FallingSeaMineRenderer;
import net.amik.createarsenal.shell.BulletEntity;
import net.amik.createarsenal.shell.DynamicBulletRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModProjectiles {


    public static final EntityEntry<BulletEntity> BULLET_ENTITY =
            register("bullet", BulletEntity::new, () -> DynamicBulletRenderer::new,
                    MobCategory.MISC, 64, 2, true, false, BulletEntity::build).register();

    public static final EntityEntry<FallingSeaMineEntity> FALLING_SEA_MINE =
            register("falling_sea_mine", FallingSeaMineEntity::new, () -> FallingSeaMineRenderer::new,
                    MobCategory.MISC, 64, 1, true, false, FallingSeaMineEntity::build).register();


    public static final EntityEntry<FallingAerialBomb> FALLING_AERIAL_BOMB =
            register("falling_aerial_bomb", FallingAerialBomb::new, () -> FallingAerialBombRenderer::new,
                    MobCategory.MISC, 64, 1, true, false, FallingAerialBomb::build).register();

    public static final EntityEntry<FallingAPAerialBomb> FALLING_AP_AERIAL_BOMB =
            register("falling_ap_aerial_bomb", FallingAPAerialBomb::new, () -> FallingAerialBombRenderer::new,
                    MobCategory.MISC, 64, 1, true, false, FallingAPAerialBomb::APbuild).register();

    public static final EntityEntry<FallingClusterAerialBomb> FALLING_CLUSTER_AERIAL_BOMB =
            register("falling_cluster_aerial_bomb", FallingClusterAerialBomb::new, () -> FallingAerialBombRenderer::new,
                    MobCategory.MISC, 64, 1, true, false, FallingClusterAerialBomb::clusterbuild).register();

    public static final EntityEntry<FallingShrapnelAerialBomb> FALLING_SHRAPNEL_AERIAL_BOMB =
            register("falling_shrapnel_aerial_bomb", FallingShrapnelAerialBomb::new, () -> FallingAerialBombRenderer::new,
                    MobCategory.MISC, 64, 1, true, false, FallingShrapnelAerialBomb::fragbuild).register();

    public static final EntityEntry<ClusterBomblet> CLUSTER_BOMBLET =
            register("cluster_bomblet", ClusterBomblet::new, () -> ClusterBombletRenderer::new,
                    MobCategory.MISC, 64, 1, true, false, ClusterBomblet::build).register();

    public static final EntityEntry<ShrapnelProjectile> SHRAPNEL =
            register("shrapnel", ShrapnelProjectile::new, () -> ShrapnelRenderer::new,
                    MobCategory.MISC, 64, 1, true, false, ShrapnelProjectile::build).register();


    public static void register() {}

    private static <T extends Entity> CreateEntityBuilder<T, ?> register(String name, EntityType.EntityFactory<T> factory,
                                                                         NonNullSupplier<NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>>> renderer,
                                                                         MobCategory group, int range, int updateFrequency, boolean sendVelocity, boolean immuneToFire,
                                                                         NonNullConsumer<EntityType.Builder<T>> propertyBuilder) {
        String id = Lang.asId(name);
        return (CreateEntityBuilder<T, ?>) CreateArsenal.REGISTRATE
                .entity(id, factory, group)
                .properties(b -> b.setTrackingRange(range)
                        .setUpdateInterval(updateFrequency)
                        .setShouldReceiveVelocityUpdates(sendVelocity))
                .properties(propertyBuilder)
                .properties(b -> {
                    if (immuneToFire)
                        b.fireImmune();
                })
                .renderer(renderer);
    }
}
