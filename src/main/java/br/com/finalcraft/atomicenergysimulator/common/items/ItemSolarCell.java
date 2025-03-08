package br.com.finalcraft.atomicenergysimulator.common.items;

import br.com.finalcraft.atomicenergysimulator.AtomicEnergySimulator;
import br.com.finalcraft.atomicenergysimulator.common.COREItems;
import br.com.finalcraft.atomicenergysimulator.common.util.ItemCreatorHelper;
import br.com.finalcraft.atomicenergysimulator.refer.MainStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.Getter;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemSolarCell extends Item {

    String unlocalizedname;

    public ItemSolarCell(String unlocalizedname) {
        super();
        this.unlocalizedname = unlocalizedname;
        this.setHasSubtypes(true);
        this.setUnlocalizedName(unlocalizedname);
        this.setCreativeTab(AtomicEnergySimulator.tabBlocksItems);
        ItemCreatorHelper.registerItem(this,unlocalizedname);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        for (SolarCellTypeEnum solarCellTypeEnum : SolarCellTypeEnum.values()) {
            solarCellTypeEnum.regiterIcon(reg);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        if (meta >= SolarCellTypeEnum.values().length)
            meta = 0;
        return SolarCellTypeEnum.getByMeta(meta).icon;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (SolarCellTypeEnum solarCellTypeEnum : SolarCellTypeEnum.values()) {
            list.add(solarCellTypeEnum.createItemStack(1));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "." + SolarCellTypeEnum.getByMeta(stack.getItemDamage()).name().toLowerCase();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer entityPlayer, List list, boolean bool) {
        SolarCellTypeEnum typeEnum = SolarCellTypeEnum.getByMeta(stack.getItemDamage());
        list.add(String.format("§7Produz: §a%s RF/t", typeEnum.getProduction()));
//        list.add(String.format("§7Bateria Interna: §a%s RF", typeEnum.getStorage()));
    }

    @Getter
    public static enum SolarCellTypeEnum {
        X1(0,32),
        X8(1,   32 * 8),
        X64(2,  32 * 8 * 8),
        X4096(3,32 * 8 * 8 * 8),
        ;

        private final int meta;
        private final int production;
        private IIcon icon;

        SolarCellTypeEnum(int meta, int production) {
            this.meta = meta;
            this.production = production;
        }

        public ItemStack createItemStack(int amount){
            return new ItemStack(COREItems.solar_cell,amount,meta);
        }

        private void regiterIcon(IIconRegister reg){
            this.icon = reg.registerIcon(MainStrings.MOD_ID + ":solar_cell/solar_cell_" + this.name().toLowerCase());
        }

        public static SolarCellTypeEnum getByMeta(int meta){
            return SolarCellTypeEnum.values()[meta];
        }
    }
}
