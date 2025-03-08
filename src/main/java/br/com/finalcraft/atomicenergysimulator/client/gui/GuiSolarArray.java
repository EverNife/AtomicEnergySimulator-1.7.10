package br.com.finalcraft.atomicenergysimulator.client.gui;

import br.com.finalcraft.atomicenergysimulator.client.render.meta.FCTexture;
import br.com.finalcraft.atomicenergysimulator.common.container.ContainerSolarArray;
import br.com.finalcraft.atomicenergysimulator.common.tiles.TileSolarArray;
import br.com.finalcraft.atomicenergysimulator.refer.MainStrings;
import com.enderio.core.client.gui.widget.GuiToolTip;
import com.enderio.core.client.render.ColorUtil;
import crazypants.enderio.machine.gui.GuiPoweredMachineBase;
import crazypants.enderio.machine.power.PowerDisplayUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiSolarArray extends GuiPoweredMachineBase<TileSolarArray> {

    private static final FCTexture BACKGROUND = new FCTexture(
        new ResourceLocation(MainStrings.MOD_ID, "textures/gui/solar_array.png"),
        704,
        664,
        0,
        0,
        scaledResolution -> {
            return 4d;
        }
    );

    private static final FCTexture DARK_LIGHT = new FCTexture(
        new ResourceLocation(MainStrings.MOD_ID, "textures/gui/solar_quad_array.png"),
        8,
        8,
        704,
        0,
        scaledResolution -> {
            return 4d;
        }
    );

    private static final FCTexture GREEN_WEAK = new FCTexture(
        new ResourceLocation(MainStrings.MOD_ID, "textures/gui/solar_quad_array.png"),
        8,
        8,
        712,
        0,
        scaledResolution -> {
            return 4d;
        }
    );

    private static final FCTexture GREEN_STRONG = new FCTexture(
        new ResourceLocation(MainStrings.MOD_ID, "textures/gui/solar_quad_array.png"),
        8,
        8,
        728,
        0,
        scaledResolution -> {
            return 4d;
        }
    );

    private ContainerSolarArray c;

    public GuiSolarArray(InventoryPlayer inventoryPlayer, TileSolarArray machine) {
        super(machine, new ContainerSolarArray(inventoryPlayer, machine));
        this.c = (ContainerSolarArray) inventorySlots;

        Rectangle r = new Rectangle(c.getUpgradeOffset(), new Dimension(16, 16));
        ttMan.addToolTip(
            new GuiToolTip(
                new Rectangle(  c.getUpgradeOffset(), new Dimension(16, 16)),
                "Melhorar Bateria Interna",
                "§7Quanto maior for o capacitor, maior será bateria!"
            ) {
                @Override
                public boolean shouldDraw() {
                    return !c.getUpgradeSlot().getHasStack() && super.shouldDraw();
                }
            }
        );

        ttMan.addToolTip(
            new GuiToolTip(
                new Rectangle(new Point(124, 43), new Dimension(44, 34))
            ) {
                @Override
                protected void updateText() {
                    int generation = getTileEntity().calculateEnergyGenereatedPerTick();
                    setToolTipText(
                        "§2Gerando: " + "§a" + PowerDisplayUtil.formatPower(generation) + "§7 " + PowerDisplayUtil.abrevation() + PowerDisplayUtil.perTickStr(),
                        "§7Solar Cells: " + getTileEntity().countSolars() +  "/256"
                    );
                }
            }
        );
    }

    @Override
    protected boolean showRecipeButton() {
        return false;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        BACKGROUND.renderAtCenter(this);

        super.drawGuiContainerBackgroundLayer(par1, par2, par3);

        int generation = getTileEntity().calculateEnergyGenereatedPerTick();
//        if (getTileEntity().isActive()) {
//            generation = c.getInv().calculateEnergyGenereatedPerTick();
//        }

        FontRenderer fr = getFontRenderer();
        String txt = "§2Gen: §a" + this.formatPower(generation);
        fr.drawStringWithShadow(txt, guiLeft + 127, guiTop + 51, ColorUtil.getRGB(Color.WHITE));

        txt = "§7" + PowerDisplayUtil.abrevation() + PowerDisplayUtil.perTickStr();
        fr.drawStringWithShadow(txt, guiLeft + 127, guiTop + 61, ColorUtil.getRGB(Color.WHITE));
    }

    private String formatPower(int power){
        if (power > 1_000_000){
            return (power / 1_000_000) + "M";
        }
        if (power > 1_000){
            return (power / 1_000) + "K";
        }
        return String.valueOf(power);
    }

}
