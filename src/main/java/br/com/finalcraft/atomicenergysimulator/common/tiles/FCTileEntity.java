package br.com.finalcraft.atomicenergysimulator.common.tiles;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public interface FCTileEntity {

    public static List<FCTileEntity> allFcTileEntities = new ArrayList<>();

    public static void registerTileEntities(){
        allFcTileEntities.add(new TileSolarArray());
        allFcTileEntities.add(new TileSolarQuadArray());

        FCTileEntity.allFcTileEntities.forEach(fcTileEntity -> {
            Class theClass = fcTileEntity.getClass();
            String tileEntityID = fcTileEntity.getTileRegistryName();
            GameRegistry.registerTileEntity(theClass, tileEntityID);
        });
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders(){
//        TileSolarQuadArray.registerSpecialRender();
    }

    public String getTileRegistryName();

    public default void scheduleRegisterOfThisTileEntity(){
        allFcTileEntities.add(this);
    }
}
