package br.com.finalcraft.atomicenergysimulator.common.container;

import br.com.finalcraft.atomicenergysimulator.common.tiles.TileSolarQuadArray;
import crazypants.enderio.machine.gui.AbstractMachineContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerQuadSolarArray extends AbstractMachineContainer<TileSolarQuadArray> {

    private final Slot[] solarSlots = new Slot[5];

    public ContainerQuadSolarArray(final InventoryPlayer playerInv, final TileSolarQuadArray sunSimulator) {
        super(playerInv, sunSimulator);

        this.addSlotToContainer(solarSlots[0] = getSolarOnlySlot(getInv(), 0, 71, 7));
        this.addSlotToContainer(solarSlots[1] = getSolarOnlySlot(getInv(), 1, 41, 35));
        this.addSlotToContainer(solarSlots[2] = getSolarOnlySlot(getInv(), 2, 100, 35));
        this.addSlotToContainer(solarSlots[3] = getSolarOnlySlot(getInv(), 3, 71, 63));
        this.addSlotToContainer(solarSlots[4] = getSolarOnlySlot(getInv(), 4, 12, 60));
    }

    public void refreshOutput(){

    }

    @Override
    protected void addMachineSlots(InventoryPlayer playerInv) {
        //Solar Slots
    }

    private Slot getSolarOnlySlot(IInventory inventory, int id, int xCoord, int yCoord){
        return new Slot(inventory,id,xCoord,yCoord){
            @Override
            public int getSlotStackLimit() {
                return 64;
            }

            @Override
            public boolean isItemValid(ItemStack stack) {
                return getInv().isItemValidForSlot(id, stack);
            }

            @Override
            public void onSlotChanged() {
                super.onSlotChanged();
                refreshOutput();
            }
        };
    }

}
