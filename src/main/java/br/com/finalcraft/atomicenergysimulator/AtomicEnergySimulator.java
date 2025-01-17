package br.com.finalcraft.atomicenergysimulator;

import br.com.finalcraft.atomicenergysimulator.client.creativetab.CustomCreativeTab;
import br.com.finalcraft.atomicenergysimulator.proxy.CommonProxy;
import br.com.finalcraft.atomicenergysimulator.refer.MainStrings;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.Logger;

@Mod(modid = MainStrings.MOD_ID,
        name = MainStrings.MODNAME,
        version = MainStrings.MODVERSION,
        dependencies = "after:AWWayofTime;after:DraconicEvolution;after:TConstruct;after:EventHelper")

public class AtomicEnergySimulator {

    public static CreativeTabs tabBlocksItems = new CustomCreativeTab(CreativeTabs.getNextID(), MainStrings.MOD_ID, "blocksAndItems", 1);
    public static CreativeTabs tabToolsArmors = new CustomCreativeTab(CreativeTabs.getNextID(), MainStrings.MOD_ID, "toolsAndArmors", 2);

    public static Logger logger;
    @Instance
    public static AtomicEnergySimulator instance = new AtomicEnergySimulator();

    @SidedProxy(
        clientSide="br.com.finalcraft.atomicenergysimulator.proxy.ClientProxy",
        serverSide="br.com.finalcraft.atomicenergysimulator.proxy.ServerProxy")
    public static CommonProxy proxy;


    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
