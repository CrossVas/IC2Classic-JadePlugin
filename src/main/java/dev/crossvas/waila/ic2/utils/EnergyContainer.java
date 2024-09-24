package dev.crossvas.waila.ic2.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.NodeStats;
import net.minecraft.tileentity.TileEntity;

import java.util.concurrent.TimeUnit;

/**
 * Straight copy-paste of CableProvider.EnergyContainer
 * © IC2Classic Dev
 */

public class EnergyContainer {

    static final Cache<BlockPos, EnergyContainer> CACHE;

    static {
        CACHE = CacheBuilder.newBuilder().expireAfterAccess(1L, TimeUnit.SECONDS).maximumSize(128L).build();
    }

    LongAverager energyIn = new LongAverager(5);
    LongAverager energyOut = new LongAverager(5);
    long lastTime;
    long lastIn;
    long lastOut;


    public EnergyContainer() {}

    public static EnergyContainer getContainer(TileEntity tile) {
        BlockPos pos = new BlockPos(tile.xCoord, tile.yCoord, tile.zCoord);
        EnergyContainer result = CACHE.getIfPresent(pos);
        if (result == null) {
            result = new EnergyContainer();
            CACHE.put(pos, result);
        }

        result.tick(tile.getWorldObj().getTotalWorldTime(), EnergyNet.instance.getNodeStats(tile));
        return result;
    }

    public void tick(long time, NodeStats stats) {
        if (this.lastTime == 0L) {
            this.lastTime = time;
            this.lastIn = (long) stats.getEnergyIn();
            this.lastOut = (long) stats.getEnergyOut();
        } else {
            long diff = time - this.lastTime;
            if (diff <= 0L) {
                return;
            }
            this.energyIn.addEntry((long) ((stats.getEnergyIn() - this.lastIn) / diff));
            this.energyOut.addEntry((long) ((stats.getEnergyOut() - this.lastOut) / diff));
            this.lastTime = time;
            this.lastIn = (long) stats.getEnergyIn();
            this.lastOut = (long) stats.getEnergyOut();

        }
    }

    public int getAverageIn() {
        return (int) this.energyIn.getAverage();
    }

    public int getAverageOut() {
        return (int) this.energyOut.getAverage();
    }
}
