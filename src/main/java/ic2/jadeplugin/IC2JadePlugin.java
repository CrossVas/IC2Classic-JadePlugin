package ic2.jadeplugin;

import ic2.jadeplugin.base.JadeCommonHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod(IC2JadePlugin.ID)
public class IC2JadePlugin {

    public static final String ID = "ic2jadeplugin";

    public static final String ID_IC2 = "ic2";

    public IC2JadePlugin() {
        JadeCommonHandler.THIS.init();
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(ID_IC2, path);
    }
}
