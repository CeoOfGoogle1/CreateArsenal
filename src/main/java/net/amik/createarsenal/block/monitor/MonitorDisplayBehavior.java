package net.amik.createarsenal.block.monitor;

import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTarget;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import net.amik.createarsenal.block.radar.base.RadarBaseBlockTileEntity;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

import java.util.List;

public class MonitorDisplayBehavior extends DisplayTarget {
    @Override
    public void acceptText(int line, List<MutableComponent> text, DisplayLinkContext context) {
        if (!(context.getSourceBlockEntity() instanceof RadarBaseBlockTileEntity radar))
            return;

        if (!radar.isRunning())
            return;

        MonitorBlockEntity monitor = (MonitorBlockEntity) context.getTargetBlockEntity();

        monitor.tickSinceLastWork = 120;
        monitor.notifyUpdate();
    }

    @Override
    public DisplayTargetStats provideStats(DisplayLinkContext context) {
        return new DisplayTargetStats(1, 1, this);
    }

    public static class RadarSource extends DisplaySource {

        @Override
        public List<MutableComponent> provideText(DisplayLinkContext context, DisplayTargetStats stats) {
            return List.of(new TextComponent(" "));
        }
    }
}
