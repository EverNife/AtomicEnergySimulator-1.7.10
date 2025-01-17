package br.com.finalcraft.atomicenergysimulator.common.container;

import cofh.lib.gui.slot.SlotFalseCopy;
import cofh.lib.util.helpers.InventoryHelper;
import cofh.lib.util.helpers.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerBase extends Container {

    public ContainerBase() {
    }

    protected abstract int getPlayerInventoryVerticalOffset();

    protected int getPlayerInventoryHorizontalOffset() {
        return 8;
    }

    protected void bindPlayerInventory(InventoryPlayer var1) {
        int var2 = this.getPlayerInventoryVerticalOffset();
        int var3 = this.getPlayerInventoryHorizontalOffset();

        int var4;
        for(var4 = 0; var4 < 3; ++var4) {
            for(int var5 = 0; var5 < 9; ++var5) {
                this.addSlotToContainer(new Slot(var1, var5 + var4 * 9 + 9, var3 + var5 * 18, var2 + var4 * 18));
            }
        }

        for(var4 = 0; var4 < 9; ++var4) {
            this.addSlotToContainer(new Slot(var1, var4, var3 + var4 * 18, var2 + 58));
        }
    }

    protected abstract int getSizeInventory();

    protected boolean supportsShiftClick(EntityPlayer var1, int var2) {
        return this.supportsShiftClick(var2);
    }

    protected boolean supportsShiftClick(int var1) {
        return true;
    }

    protected boolean performMerge(EntityPlayer var1, int var2, ItemStack var3) {
        return this.performMerge(var2, var3);
    }

    protected boolean performMerge(int var1, ItemStack var2) {
        int var3 = this.getSizeInventory();
        int var4 = super.inventorySlots.size();
        return var1 < var3 ? this.mergeItemStack(var2, var3, var4, true) : this.mergeItemStack(var2, 0, var3, false);
    }

    public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
        if (!this.supportsShiftClick(var1, var2)) {
            return null;
        } else {
            ItemStack var3 = null;
            Slot var4 = (Slot)super.inventorySlots.get(var2);
            if (var4 != null && var4.getHasStack()) {
                ItemStack var5 = var4.getStack();
                var3 = var5.copy();
                if (!this.performMerge(var1, var2, var5)) {
                    return null;
                }

                var4.onSlotChange(var5, var3);
                if (var5.stackSize <= 0) {
                    var4.putStack((ItemStack)null);
                } else {
                    var4.putStack(var5);
                }

                if (var5.stackSize == var3.stackSize) {
                    return null;
                }

                var4.onPickupFromSlot(var1, var5);
            }

            return var3;
        }
    }

    protected void sendSlots(int var1, int var2) {
        var1 = MathHelper.clamp(var1, 0, super.inventorySlots.size());

        for(var2 = MathHelper.clamp(var2, 0, super.inventorySlots.size()); var1 < var2; ++var1) {
            ItemStack var3 = ((Slot)super.inventorySlots.get(var1)).getStack();
            ItemStack var4 = var3 == null ? null : var3.copy();
            super.inventoryItemStacks.set(var1, var4);

            for(int var5 = 0; var5 < super.crafters.size(); ++var5) {
                ((ICrafting)super.crafters.get(var5)).sendSlotContents(this, var1, var4);
            }
        }

    }

    @SideOnly(Side.CLIENT)
    public void putStacksInSlots(ItemStack[] var1) {
        for(int var2 = 0; var2 < var1.length; ++var2) {
            this.putStackInSlot(var2, var1[var2]);
        }
    }

    public ItemStack slotClick(int var1, int var2, int var3, EntityPlayer var4) {
        Slot var5 = var1 < 0 ? null : (Slot)super.inventorySlots.get(var1);
        if (var5 instanceof SlotFalseCopy) {
            if (var2 == 2) {
                var5.putStack((ItemStack)null);
            } else {
                var5.putStack(var4.inventory.getItemStack() == null ? null : var4.inventory.getItemStack().copy());
            }

            return var4.inventory.getItemStack();
        } else {
            return super.slotClick(var1, var2, var3, var4);
        }
    }

    protected boolean mergeItemStack(ItemStack var1, int var2, int var3, boolean var4) {
        return InventoryHelper.mergeItemStack(super.inventorySlots, var1, var2, var3, var4);
    }
}
