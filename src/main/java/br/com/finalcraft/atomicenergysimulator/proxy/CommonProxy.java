package br.com.finalcraft.atomicenergysimulator.proxy;

import br.com.finalcraft.atomicenergysimulator.AtomicEnergySimulator;
import br.com.finalcraft.atomicenergysimulator.common.COREBlocks;
import br.com.finalcraft.atomicenergysimulator.common.COREItems;
import br.com.finalcraft.atomicenergysimulator.common.gui.FCGuiHandler;
import br.com.finalcraft.atomicenergysimulator.common.integration.IntegrationHelper;
import br.com.finalcraft.atomicenergysimulator.common.tiles.FCTileEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    protected SimpleNetworkWrapper network;

    protected static void registerEventHandler(Object object){
        MinecraftForge.EVENT_BUS.register(object);
        FMLCommonHandler.instance().bus().register(object);
    }

    public void preInit(FMLPreInitializationEvent e) {
        IntegrationHelper.onPreInit();

        NetworkRegistry.INSTANCE.registerGuiHandler(AtomicEnergySimulator.instance, new FCGuiHandler());
        COREItems.init();
        COREBlocks.init();

        FCTileEntity.registerTileEntities();

        network = NetworkRegistry.INSTANCE.newSimpleChannel("AtomicEnergySimulator");

    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {

    }

}
