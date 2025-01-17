package br.com.finalcraft.atomicenergysimulator.client.creativetab;

import br.com.finalcraft.atomicenergysimulator.common.COREBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomCreativeTab extends CreativeTabs {

    private String label;
    private int tab;

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(COREBlocks.quadSolarArray);
    }

    public CustomCreativeTab(int id, String modid, String label, int tab) {
        super(id, modid);
        this.label = label;
        this.tab = tab;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        return new ItemStack(Item.getItemFromBlock(COREBlocks.quadSolarArray));
    }

    @Override
    public String getTabLabel() {
        return this.label;
    }
}
