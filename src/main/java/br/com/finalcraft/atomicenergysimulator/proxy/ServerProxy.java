package br.com.finalcraft.atomicenergysimulator.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class ServerProxy extends CommonProxy{

    private static ServerProxy instance;

    public static SimpleNetworkWrapper getNetwork(){
        return instance.network;
    }

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        instance = this;
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
