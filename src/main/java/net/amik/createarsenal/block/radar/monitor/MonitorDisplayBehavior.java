package net.amik.createarsenal.block.radar.monitor;

import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTarget;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import com.simibubi.create.foundation.gui.ModularGuiLineBuilder;
import net.amik.createarsenal.block.radar.base.RadarBaseBlockTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class MonitorDisplayBehavior extends DisplayTarget {
    @Override
    public void acceptText(int line, List<MutableComponent> text, DisplayLinkContext context) {
        if (!(context.getSourceBlockEntity() instanceof RadarBaseBlockTileEntity radar))
            return;

        if (!radar.isRunning())
            return;

        if (!radar.hasReceiver())
            return;


        MonitorBlockEntity monitor = (MonitorBlockEntity) context.getTargetBlockEntity();
        monitor = monitor.getController();
        monitor.setRadarPos(radar.getBlockPos());
        monitor.setFilter(MonitorFilter.values()[context.blockEntity().getSourceConfig().getInt("Filter")]);
        monitor.setActive();
        monitor.notifyUpdate();

    }

    @Override
    public AABB getMultiblockBounds(LevelAccessor level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof MonitorBlockEntity monitor && monitor.getController() != null) {
            return monitor.getController().getMultiblockBounds(level, pos);
        }
        return super.getMultiblockBounds(level, pos);
    }

    @Override
    public DisplayTargetStats provideStats(DisplayLinkContext context) {
        return new DisplayTargetStats(1, 1, this);
    }

    public static class RadarSource extends DisplaySource {

        @Override
        public List<MutableComponent> provideText(DisplayLinkContext context, DisplayTargetStats stats) {
            return List.of(Component.literal(""));
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void initConfigurationWidgets(DisplayLinkContext context, ModularGuiLineBuilder builder, boolean isFirstLine) {
            if (isFirstLine)
                addFilterConfig(builder);
        }

        @OnlyIn(Dist.CLIENT)
        protected void addFilterConfig(ModularGuiLineBuilder builder) {
            builder.addSelectionScrollInput(0, 100,
                    (si, l) -> si.forOptions(List.of(
                                    Component.literal("All Entities"),
                                    Component.literal("No Mobs"),
                                    Component.literal("Players Only"),
                                    Component.literal("Projectiles Only"),
                                    Component.literal("VS2 Only"),
                                    Component.literal("Mob Bosses Only")))
                            .titled(Component.literal("Show")),
                    "Filter");
        }
    }
}
