package br.com.finalcraft.atomicenergysimulator.common.gui;

import br.com.finalcraft.atomicenergysimulator.AtomicEnergySimulator;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class FCGuiHandler implements IGuiHandler {

    public static final Map<Integer, IGuiHandler> guiHandlers = new HashMap<Integer, IGuiHandler>();

    public static void registerGuiHandler(int id, IGuiHandler handler) {
        guiHandlers.put(id, handler);
    }

    public static void openGuiFor(EntityPlayer entityPlayer, GuiIdentifiers guiIdentifier, World world, int x, int y, int z){
        entityPlayer.closeScreen();
        entityPlayer.openGui(AtomicEnergySimulator.instance, guiIdentifier.getID(), world, x,y,z);
    }

    public static void openGuiFor(EntityPlayer entityPlayer, GuiIdentifiers guiIdentifier){
        entityPlayer.closeScreen();
        entityPlayer.openGui(AtomicEnergySimulator.instance, guiIdentifier.getID(), entityPlayer.worldObj, (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        IGuiHandler handler = guiHandlers.get(ID);
        if (handler != null) {
            return handler.getServerGuiElement(ID, player, world, x, y, z);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        IGuiHandler handler = guiHandlers.get(ID);
        if (handler != null) {
            return handler.getClientGuiElement(ID, player, world, x, y, z);
        }
        return null;
    }

    public static enum GuiIdentifiers{
        SOLAR_ARRAY(1),
        QUAD_SOLAR_ARRAY(2),
        ;

        private final int ID;
        GuiIdentifiers(int ID) {
            this.ID = ID;
        }

        public int getID() {
            return ID;
        }

        public static GuiIdentifiers getByID(int ID){
            return GuiIdentifiers.values()[ID - 1];
        }

    }
}
