package br.com.finalcraft.atomicenergysimulator.common.container;

import br.com.finalcraft.atomicenergysimulator.common.tiles.TileSolarArray;
import crazypants.enderio.machine.gui.AbstractMachineContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSolarArray extends AbstractMachineContainer<TileSolarArray> {

    private final Slot[] solarSlots = new Slot[2];

    public ContainerSolarArray(final InventoryPlayer playerInv, final TileSolarArray sunSimulator) {
        super(playerInv, sunSimulator);

        this.addSlotToContainer(solarSlots[0] = getSolarOnlySlot(getInv(), 0, 71, 63));
        this.addSlotToContainer(solarSlots[1] = getSolarOnlySlot(getInv(), 1, 12, 60)); //Capacitor
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
