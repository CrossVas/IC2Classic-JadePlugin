package dev.crossvas.jadexic2c.providers;

import dev.crossvas.jadexic2c.base.interfaces.IInfoProvider;
import dev.crossvas.jadexic2c.base.interfaces.IJadeHelper;
import dev.crossvas.jadexic2c.utils.TextFormatter;
import ic2.api.energy.EnergyNet;
import ic2.core.block.wiring.tile.TileEntityBatteryStation;
import ic2.core.inventory.gui.components.special.BatteryStationComp;
import ic2.core.platform.lang.storage.Ic2GuiLang;
import ic2.core.util.math.IntCounter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BatteryStationInfo implements IInfoProvider {

    public static final BatteryStationInfo THIS = new BatteryStationInfo();
    public static final BatteryStationComp.NumberComparator counter = new BatteryStationComp.NumberComparator();

    @Override
    public void addInfo(IJadeHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityBatteryStation) {
            TileEntityBatteryStation station = (TileEntityBatteryStation) blockEntity;
            text(helper, station.getStationMode().createComponent());
            text(helper, station.getSendingMode().createComponent());

            if (station.stationMode != 0) {
                if (station.sendingMode != 2) {
                    text(helper, translatable("probe.energy.output.max", (int) EnergyNet.instance.getPowerFromTier(station.slots.getTier())));
                } else {
                    Map<Integer, IntCounter> map = station.slots.getSlotInfo();
                    if (!map.isEmpty()) {
                        if (map.size() == 1) {
                            Map.Entry<Integer, IntCounter> entry = map.entrySet().iterator().next();
                            text(helper, TextFormatter.EMPTY.literal(Ic2GuiLang.packetCountPerTick.getLocalizedFormatted((entry.getValue()).getValue(), entry.getKey())));
                        } else {
                            List<Map.Entry<Integer, IntCounter>> keys = new ArrayList<>(map.entrySet());
                            keys.sort(counter);
                            String text = "";

                            for(Map.Entry<Integer, IntCounter> entry : keys) {
                                text = text + " " + Ic2GuiLang.packetCountPerTick.getLocalizedFormatted(entry.getValue().getValue(), entry.getKey());
                            }
                            text(helper, TextFormatter.EMPTY.literal(text));
                        }
                    }
                }
            }
        }
    }
}
