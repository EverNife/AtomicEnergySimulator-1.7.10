package br.com.finalcraft.atomicenergysimulator.common.util;

import br.com.finalcraft.atomicenergysimulator.AtomicEnergySimulator;
import br.com.finalcraft.atomicenergysimulator.refer.MainStrings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCreatorHelper {

    public static String buildArmorName(String name, int type){
        String theType="helmet";
        if (type == 1) theType = "chestplate";
        else  if (type == 2) theType = "leggings";
        else  if (type == 3) theType = "boots";
        name = name + "_" + theType;
        return name;
    }

    public static void registerItem(Item item, String name){
        GameRegistry.registerItem(item, name);
    }

    public static void registerBlock(Block block, String name){
        GameRegistry.registerBlock(block,name);
    }

    public static void registerBlockWithMetaItem(Block block, String name, Class<? extends ItemBlock> itemclass){
        GameRegistry.registerBlock(block, itemclass, name);
    }

    public static Item createDefaultItem(String name){
        Item item = new Item().setUnlocalizedName(name).setCreativeTab(AtomicEnergySimulator.tabBlocksItems).setTextureName(MainStrings.MOD_ID + ":" + name);
        registerItem(item,name);
        return item;
    }

    public static void addFurnaceRecipe(Block input, ItemStack output, float xp){
        GameRegistry.addSmelting(input, output, xp);
    }


}
