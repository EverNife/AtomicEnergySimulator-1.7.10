package br.com.finalcraft.atomicenergysimulator.common.blocks;

import br.com.finalcraft.atomicenergysimulator.AtomicEnergySimulator;
import br.com.finalcraft.atomicenergysimulator.client.gui.GuiSolarArray;
import br.com.finalcraft.atomicenergysimulator.common.container.ContainerSolarArray;
import br.com.finalcraft.atomicenergysimulator.common.gui.FCGuiHandler;
import br.com.finalcraft.atomicenergysimulator.common.tiles.TileSolarArray;
import br.com.finalcraft.atomicenergysimulator.common.util.ItemCreatorHelper;
import br.com.finalcraft.atomicenergysimulator.refer.MainStrings;
import crazypants.enderio.ModObject;
import crazypants.enderio.machine.AbstractMachineBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class BlockSolarArray extends AbstractMachineBlock<TileSolarArray> {

    public static final ModObject ModObject_solarArray = EnumHelper
        .addEnum(ModObject.class, "solarArray", new Class<?>[0], new Object[0]);

    public BlockSolarArray(String unlocalizedName) {
        super(ModObject_solarArray, TileSolarArray.class);

        this.setBlockName(unlocalizedName);
        this.setCreativeTab(AtomicEnergySimulator.tabBlocksItems);

        ItemCreatorHelper.registerBlock(this, unlocalizedName);
        FCGuiHandler.registerGuiHandler(getGuiId(), this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerSolarArray(
            player.inventory,
            (TileSolarArray) world.getTileEntity(x, y, z)
        );
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GuiSolarArray(player.inventory, (TileSolarArray) world.getTileEntity(x, y, z));
    }

    @Override
    public boolean openGui(World world, int x, int y, int z, EntityPlayer entityPlayer, int side) {
        if (!world.isRemote) {
            entityPlayer.openGui(AtomicEnergySimulator.instance, getGuiId(), world, x, y, z);
        }
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //  Render
    // -----------------------------------------------------------------------------------------------------------------


    @Override
    protected String getMachineFrontIconKey(boolean active) {
        return MainStrings.MOD_ID + ":solar_array/solar_array_side";
    }

    @Override
    protected String getSideIconKey(boolean active) {
        return MainStrings.MOD_ID + ":solar_array/solar_array_side";
    }

    @Override
    protected String getBottomIconKey(boolean active) {
        return MainStrings.MOD_ID + ":solar_array/solar_array_bottom";
    }

    @Override
    protected String getTopIconKey(boolean active) {
        return MainStrings.MOD_ID + ":solar_array/solar_array_top";
    }

    @Override
    protected String getBackIconKey(boolean active) {
        return MainStrings.MOD_ID + ":solar_array/solar_array_side";
    }

    //    @Override
//    public boolean renderAsNormalBlock() {
//        return false;
//    }
//
//    @Override
//    public int getRenderType() {
//        return -1;
//    }

    @Override
    protected int getGuiId() {
        return FCGuiHandler.GuiIdentifiers.SOLAR_ARRAY.getID();
    }

//    @Override
//    public boolean isOpaqueCube() {
//        return false;
//    }

}
