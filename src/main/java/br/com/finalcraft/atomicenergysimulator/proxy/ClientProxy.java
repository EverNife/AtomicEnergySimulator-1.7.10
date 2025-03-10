package br.com.finalcraft.atomicenergysimulator.proxy;

import br.com.finalcraft.atomicenergysimulator.common.tiles.FCTileEntity;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        FCTileEntity.registerRenders();
        //registerEventHandler(new ClientPlayerListener());
        //KeyBindings.registerIt();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

}
