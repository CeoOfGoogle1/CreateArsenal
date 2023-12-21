package net.amik.createarsenal.block.monitor;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.ControlledContraptionEntity;
import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTarget;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import net.amik.createarsenal.block.radar.AbstractRadarFrame;
import net.amik.createarsenal.block.radar.base.RadarBaseBlockTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;
import java.util.Map;

public class MonitorDisplayBehavior extends DisplayTarget {
    @Override
    public void acceptText(int line, List<MutableComponent> text, DisplayLinkContext context) {
        if (!(context.getSourceBlockEntity() instanceof RadarBaseBlockTileEntity radar))
            return;

        if (!radar.isRunning())
            return;

        ControlledContraptionEntity contraptionEntity = radar.getMovedContraption();
        if (contraptionEntity == null)
            return;

        Contraption contraption = contraptionEntity.getContraption();
        if (contraption == null)
            return;

        int width = 0;

        for (Map.Entry<BlockPos, StructureTemplate.StructureBlockInfo> block : contraption.getBlocks().entrySet()) {
            StructureTemplate.StructureBlockInfo info = block.getValue();

            if (info.state.getBlock() instanceof AbstractRadarFrame)
                width += 10;
        }


        MonitorBlockEntity monitor = (MonitorBlockEntity) context.getTargetBlockEntity();

        monitor.tickSinceLastWork = 120;
        monitor.widthRange = width;
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
