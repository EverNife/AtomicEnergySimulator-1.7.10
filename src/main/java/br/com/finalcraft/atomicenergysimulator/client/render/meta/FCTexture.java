package br.com.finalcraft.atomicenergysimulator.client.render.meta;

import br.com.finalcraft.atomicenergysimulator.common.util.FCMathUtil;
import lombok.Data;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

@Data
public class FCTexture {

    private static GuiScreen guiScreen = new GuiScreen();

    private ResourceLocation TEXTURE;
    private int imageWidth;
    private int imageHeight;
    private int imageStartX;
    private int imageStartY;

    private final Function<ScaledResolution, Double> scaleFactorFunction;

    public FCTexture(ResourceLocation TEXTURE, int imageWidth, int imageHeight, int imageStartX, int imageStartY, Function<ScaledResolution, Double> scaleFactorFunction) {
        this.TEXTURE = TEXTURE;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imageStartX = imageStartX;
        this.imageStartY = imageStartY;
        this.scaleFactorFunction = scaleFactorFunction;
    }

    public double getScaleFactor() {
        return this.scaleFactorFunction.apply(new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight));
    }

    /**
     * Calculates the necesssary dislocation between a point on the image
     * to its center.
     * @param xImageStart
     * @return xDislocation
     */
    public int getDislocationFromCenterX(int xImageStart) {
        return FCMathUtil.floor(this.getScaleFactor() * (xImageStart - this.getCenterX()));
    }

    /**
     * Calculates the necesssary dislocation between a point on the image
     * to its center.
     * @param yImageStart
     * @return yDislocation
     */
    public int getDislocationFromCenterY(int yImageStart) {
        return FCMathUtil.floor(this.getScaleFactor() * (yImageStart - this.getCenterY()));
    }

    public int getCenterX(){
        return imageWidth / 2;
    }

    public int getCenterY(){
        return imageHeight / 2;
    }

    public int getEffectiveWidth(){
        return FCMathUtil.floor(imageWidth / getScaleFactor());
    }

    public int getEffectiveHeight(){
        return FCMathUtil.floor(imageHeight / getScaleFactor());
    }

    public int getEdgeSpacingX(GuiScreen screen, int dislocationPixelsX){
        return getEdgeSpacingX(screen.width, dislocationPixelsX);
    }

    public int getEdgeSpacingX(int width, int dislocationPixelsX){
        return (width - this.getEffectiveWidth() + FCMathUtil.floor(dislocationPixelsX / this.getScaleFactor())) / 2;
    }

    public int getEdgeSpacingY(GuiScreen screen, int dislocationPixelsY){
        return getEdgeSpacingY(screen.height, dislocationPixelsY);
    }

    public int getEdgeSpacingY(int height, int dislocationPixelsY){
        return (height - this.getEffectiveHeight() + FCMathUtil.floor(dislocationPixelsY / this.getScaleFactor())) / 2;
    }

    public void renderAtCenter(GuiScreen screen) {
        renderAtCenter(screen, 0,0);
    }

    public void renderAtCenter(GuiScreen screen, int dislocationPixelsX, int dislocationPixelsY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTEXTURE());
        guiScreen.drawTexturedModalRect(
            this.getEdgeSpacingX(screen, dislocationPixelsX),
            this.getEdgeSpacingY(screen, dislocationPixelsY),
            this.getImageStartX(),
            this.getImageStartY(),
            (int) (this.getImageWidth() / getScaleFactor()),
            (int) (this.getImageHeight() / getScaleFactor())
        );
    }

    public void renderAt(int x, int y) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTEXTURE());
        guiScreen.drawTexturedModalRect(
            x,
            y,
            this.getImageStartX(),
            this.getImageStartY(),
            (int) (this.getImageWidth() / getScaleFactor()),
            (int) (this.getImageHeight() / getScaleFactor())
        );
    }

    public void renderCenteredAt(int x, int y) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTEXTURE());
        guiScreen.drawTexturedModalRect(
            x - (this.getEffectiveWidth() / 2),
            y - (this.getEffectiveHeight() / 2),
            this.getImageStartX(),
            this.getImageStartY(),
            (int) (this.getImageWidth() / getScaleFactor()),
            (int) (this.getImageHeight() / getScaleFactor())
        );
    }

}
