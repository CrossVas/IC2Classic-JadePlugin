package ic2.jadeplugin;

import ic2.jadeplugin.base.JadeCommonHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import snownee.jade.impl.ObjectDataCenter;

@Mod(IC2JadePlugin.ID)
public class IC2JadePlugin {

    public static final String ID = "ic2jadeplugin";
    public static final String ID_IC2 = "ic2";

    public IC2JadePlugin() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Ic2JadeConfig.SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        JadeCommonHandler.THIS.init();
    }

    @SubscribeEvent
    public void onConfigLoad(ModConfigEvent.Loading e) {
        if (e.getConfig().getSpec() == Ic2JadeConfig.SPEC) {
            applyConfig();
        }
    }

    @SubscribeEvent
    public void onConfigReload(ModConfigEvent.Reloading e) {
        if (e.getConfig().getSpec() == Ic2JadeConfig.SPEC) {
            applyConfig();
        }
    }

    private static void applyConfig() {
        ObjectDataCenter.rateLimiter = Ic2JadeConfig.updateRate.get();
        ObjectDataCenter.requestServerData();
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(ID_IC2, path);
    }
}
