package dev.crossvas.jadexic2c;

import dev.crossvas.ic2classicjade.Tags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.File;

@Mod(modid = Tags.MOD_ID, version = Tags.VERSION, name = Tags.MOD_NAME, acceptedMinecraftVersions = "[1.12.2]")
public class JadeIC2Classic {

    public Configuration CONFIG;
    public static int DEFAULT_BAR_WIDTH;

    public static final String ID_IC2 = "ic2";

    public JadeIC2Classic() {}

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(ID_IC2, path);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        CONFIG = new Configuration(new File((File) FMLInjectionData.data()[6], "config/JadeAddonsIC2.cfg"));
        CONFIG.load();
        DEFAULT_BAR_WIDTH = CONFIG.getInt("defaultBarWidth", "general", 120, 120, Integer.MAX_VALUE, "Set Max Width for bar element (useful when using localizations that are longer than English)");
        if (CONFIG.hasChanged()) CONFIG.save();
    }
}
