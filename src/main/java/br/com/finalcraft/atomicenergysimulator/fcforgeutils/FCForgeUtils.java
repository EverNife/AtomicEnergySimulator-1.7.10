package br.com.finalcraft.atomicenergysimulator.fcforgeutils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.UUID;

public class FCForgeUtils {

    private static Side side = null;
    public static Side getSide(){
        return side != null ? side : (side = FMLCommonHandler.instance().getEffectiveSide());
    }

    public static EntityPlayerMP getPlayer(String playerName){
        playerName = playerName.toLowerCase();
        for (EntityPlayerMP entityPlayer : (List<EntityPlayerMP>)MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
            if (entityPlayer.getGameProfile().getName().toLowerCase().equals(playerName)){
                return entityPlayer;
            }
        }
        return null;
    }

    public static EntityPlayerMP getPlayer(UUID playerUUID){
        for (EntityPlayerMP entityPlayer : (List<EntityPlayerMP>)MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
            if (entityPlayer.getGameProfile().getId().equals(playerUUID)){
                return entityPlayer;
            }
        }
        return null;
    }

}
