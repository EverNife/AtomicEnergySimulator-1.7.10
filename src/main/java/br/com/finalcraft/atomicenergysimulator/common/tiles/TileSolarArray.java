package br.com.finalcraft.atomicenergysimulator.common.tiles;

import br.com.finalcraft.atomicenergysimulator.common.COREBlocks;
import br.com.finalcraft.atomicenergysimulator.common.items.ItemSolarCell;
import com.enderio.core.api.common.util.IProgressTile;
import com.enderio.core.common.util.BlockCoord;
import crazypants.enderio.machine.SlotDefinition;
import crazypants.enderio.machine.generator.AbstractGeneratorEntity;
import crazypants.enderio.power.BasicCapacitor;
import crazypants.enderio.power.ICapacitor;
import crazypants.enderio.power.PowerDistributor;
import net.minecraft.block.Block;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileSolarArray extends AbstractGeneratorEntity implements ISidedInventory, IProgressTile, FCTileEntity {

    private PowerDistributor powerDis;
    private static final int SOLAR_COUNT = 1;

    public TileSolarArray() {
        super(new SlotDefinition(SOLAR_COUNT,0));
    }

    @Override
    public String getTileRegistryName() {
        return Block.blockRegistry.getNameForObject(COREBlocks.solarArray);
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
        return super.getCapacitor().getMaxEnergyStored() * 4 + 1_000_000 + (calculateEnergyGenereatedPerTick() * 4);
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
    protected boolean isMachineItemValidForSlot(int i, ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() instanceof ItemSolarCell){
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
        if (itemStack == null || itemStack.getItem() instanceof ItemSolarCell == false){
            return 0;
        }

        ItemSolarCell.SolarCellTypeEnum typeEnum = ItemSolarCell.SolarCellTypeEnum.getByMeta(itemStack.getItemDamage());

        return typeEnum.getProduction() * itemStack.stackSize;
    }


    long lastCalculationEnergyTime = 0;
    int lastCalculationEnergy = 0;
    public int calculateEnergyGenereatedPerTick(){

        if (worldObj.getTotalWorldTime() - lastCalculationEnergyTime < 20){
            return lastCalculationEnergy;
        }

        lastCalculationEnergyTime = worldObj.getTotalWorldTime();
        lastCalculationEnergy = 0;
        for (int i = 0; i < SOLAR_COUNT; i++) {
            lastCalculationEnergy += calculateEnergyFromStack(inventory[i]);
        }
        return lastCalculationEnergy;
    }

    public int countSolars(){
        int count = 0;
        for (int i = 0; i < SOLAR_COUNT; i++) {
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
