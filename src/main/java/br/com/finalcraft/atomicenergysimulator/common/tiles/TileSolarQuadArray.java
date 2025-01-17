package br.com.finalcraft.atomicenergysimulator.common.tiles;

import advsolar.common.AdvancedSolarPanel;
import advsolar.common.items.ItemAdvSolarPanel;
import br.com.finalcraft.atomicenergysimulator.common.COREBlocks;
import com.enderio.core.api.common.util.IProgressTile;
import com.enderio.core.common.util.BlockCoord;
import cpw.mods.fml.common.FMLLog;
import crazypants.enderio.machine.SlotDefinition;
import crazypants.enderio.machine.generator.AbstractGeneratorEntity;
import crazypants.enderio.power.BasicCapacitor;
import crazypants.enderio.power.ICapacitor;
import crazypants.enderio.power.PowerDistributor;
import net.minecraft.block.Block;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileSolarQuadArray extends AbstractGeneratorEntity implements ISidedInventory, IProgressTile, FCTileEntity {

    private PowerDistributor powerDis;

    public TileSolarQuadArray() {
        super(new SlotDefinition(4,0));
    }

    @Override
    public String getTileRegistryName() {
        FMLLog.info("[TileSolarQuadArray] TileSolarQuadArray == " + Block.blockRegistry.getNameForObject(COREBlocks.quadSolarArray));
        return Block.blockRegistry.getNameForObject(COREBlocks.quadSolarArray);
    }

//    @SideOnly(Side.CLIENT)
//    public static void registerSpecialRender() {
//        ClientRegistry.bindTileEntitySpecialRenderer(TileSolarQuadArray.class,new RenderSunSimulator());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(COREBlocks.astralAnvil),
//            new GenericBlockItemRenderer(
//                new TileSolarQuadArray(), new RenderSunSimulator()
//            )
//        );
//    }

    private final ICapacitor INTERNAL_CAPACITOR = new BasicCapacitor(
        1,
        0,
        999_999_999,
        4_194_304
    );

    @Override
    public ICapacitor getCapacitor() {
        return INTERNAL_CAPACITOR;
    }

    @Override
    public int getMaxEnergyStored() {
        return super.getCapacitor().getMaxEnergyStored() * 4 + 1_000_000;
    }

    @Override
    public float getProgress() {
        return 0;
    }

    @Override
    public void setProgress(float progress) {

    }

    @Override
    public TileEntity getTileEntity() {
        return this;
    }

    @Override
    protected boolean isMachineItemValidForSlot(int i, ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() instanceof ItemAdvSolarPanel){
            return true;
        }
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public String getMachineName() {
        return COREBlocks.quadSolarArray.getUnlocalizedName();
    }

    @Override
    public void onNeighborBlockChange(Block blockId) {
        super.onNeighborBlockChange(blockId);
        if (powerDis != null) {
            powerDis.neighboursChanged();
        }
    }

    private int calculateEnergyFromStack(ItemStack itemStack){
        if (itemStack == null || itemStack.getItem() instanceof ItemAdvSolarPanel == false){
            return 0;
        }

        switch (itemStack.getItemDamage()){
            case 0:
                return AdvancedSolarPanel.advGenDay * itemStack.stackSize * 4;
            case 1:
                return AdvancedSolarPanel.hGenDay * itemStack.stackSize * 4;
            case 2:
                return AdvancedSolarPanel.uhGenDay * itemStack.stackSize * 4;
            case 3:
                return AdvancedSolarPanel.qpGenDay * itemStack.stackSize * 4;
        }

        return 0;
    }


    long lastCalculationTime = 0;
    int lastCalculationEnergy = 0;
    public int calculateEnergyGenereatedPerTick(){

        if (worldObj.getTotalWorldTime() - lastCalculationTime < 20){
            return lastCalculationEnergy;
        }

        lastCalculationTime = worldObj.getTotalWorldTime();
        lastCalculationEnergy = 0;
        for (int i = 0; i < 4; i++) {
            lastCalculationEnergy += calculateEnergyFromStack(inventory[i]);
        }
        return lastCalculationEnergy;
    }

    public int countSolars(){
        int count = 0;
        for (int i = 0; i < 4; i++) {
            ItemStack itemStack = inventory[i];
            if (itemStack != null){
                count += itemStack.stackSize;
            }
        }
        return count;
    }

    @Override
    protected boolean processTasks(boolean redstoneCheckPassed) {
        boolean needsUpdate = false;

//        if (shouldDoWorkThisTick(10)){
            if (getEnergyStored() < getMaxEnergyStored()) {
                int energyIncrease = calculateEnergyGenereatedPerTick();
                if (energyIncrease > 0){
                    this.setEnergyStored(this.getEnergyStored() + energyIncrease);
                }
            }
//        }

        transmitEnergy();

        return needsUpdate;
    }

    // private PowerDistributor powerDis;
    private boolean transmitEnergy() {
        if (powerDis == null) {
            powerDis = new PowerDistributor(new BlockCoord(this));
        }
        int canTransmit = Math.min(getEnergyStored(), getCapacitor().getMaxEnergyExtracted());
        if (canTransmit <= 0) {
            return false;
        }
        int transmitted = powerDis.transmitEnergy(worldObj, canTransmit);
        setEnergyStored(getEnergyStored() - transmitted);
        return transmitted > 0;
    }

}
