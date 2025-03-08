package br.com.finalcraft.atomicenergysimulator.common;

import br.com.finalcraft.atomicenergysimulator.common.blocks.BlockSolarArray;
import br.com.finalcraft.atomicenergysimulator.common.blocks.BlockSolarQuadArray;
import net.minecraft.block.Block;

public final class COREBlocks {

    public static Block solarArray;
    public static Block quadSolarArray;

    public static final void init() {
        solarArray = new BlockSolarArray("solar_array");
        quadSolarArray = new BlockSolarQuadArray("quad_solar_array");
    }

}
