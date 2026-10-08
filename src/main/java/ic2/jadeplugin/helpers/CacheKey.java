package ic2.jadeplugin.helpers;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record CacheKey(ResourceKey<Level> dimension, long position) {}
