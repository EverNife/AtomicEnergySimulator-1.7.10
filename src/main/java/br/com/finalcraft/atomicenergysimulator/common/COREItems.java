package br.com.finalcraft.atomicenergysimulator.common;

import br.com.finalcraft.atomicenergysimulator.common.items.ItemMaterial;
import br.com.finalcraft.atomicenergysimulator.common.items.ItemSolarCell;
import net.minecraft.item.Item;

public final class COREItems {

    public static Item material;
    public static Item solar_cell;

    public static final void init() {
        material = new ItemMaterial("material");
        solar_cell = new ItemSolarCell("solar_cell");
    }

}
