package br.com.finalcraft.atomicenergysimulator.common;

import br.com.finalcraft.atomicenergysimulator.common.blocks.BlockSolarQuadArray;
import net.minecraft.block.Block;

public final class COREBlocks {

    public static Block quadSolarArray;

    public static final void init() {
        quadSolarArray = new BlockSolarQuadArray("quad_solar_array");
    }

}
