package br.com.finalcraft.atomicenergysimulator.common.items;

import br.com.finalcraft.atomicenergysimulator.AtomicEnergySimulator;
import br.com.finalcraft.atomicenergysimulator.common.COREItems;
import br.com.finalcraft.atomicenergysimulator.common.util.ItemCreatorHelper;
import br.com.finalcraft.atomicenergysimulator.refer.MainStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemMaterial extends Item {

    String unlocalizedname;

    public ItemMaterial(String unlocalizedname) {
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
        for (MaterialTypeEnum materialTypeEnum : MaterialTypeEnum.values()) {
            materialTypeEnum.regiterIcon(reg);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        if (meta >= MaterialTypeEnum.values().length)
            meta = 0;
        return MaterialTypeEnum.getByMeta(meta).icon;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (MaterialTypeEnum materialTypeEnum : MaterialTypeEnum.values()) {
            list.add(materialTypeEnum.createItemStack(1));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "." + MaterialTypeEnum.getByMeta(stack.getItemDamage()).name().toLowerCase();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer entityPlayer, List list, boolean bool) {
//        list.add("§7Produz: ");
//        list.add("");
//        list.add("");
//        list.add("§d▌§m---------§d< Astral Item >§d§m---------§d▌");
//        list.add("");
    }

    public static enum MaterialTypeEnum {
        GOLDEN_IRIDIUM_PLATE(0),
        REINFORCED_IRRADIATOR(1),
        META_ENDER_CORE(2),
        ;

        private final int meta;
        private IIcon icon;

        MaterialTypeEnum(int meta) {
            this.meta = meta;
        }

        public ItemStack createItemStack(int amount){
            return new ItemStack(COREItems.material,amount,meta);
        }

        private void regiterIcon(IIconRegister reg){
            this.icon = reg.registerIcon(MainStrings.MOD_ID + ":material/" + this.name().toLowerCase());
        }

        public int getMeta() {
            return meta;
        }

        public static MaterialTypeEnum getByMeta(int meta){
            return MaterialTypeEnum.values()[meta];
        }
    }
}
