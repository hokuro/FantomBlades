package mod.fbd.gui;

import mod.fbd.inventory.ContainerAutomatic;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiAutomatic extends GuiContainer {
	private static final ResourceLocation tex = new ResourceLocation("fbd", "textures/gui/automatic.png");

	private IInventory automatic;
	private EntityPlayer player;

	public GuiAutomatic(EntityPlayer playerIn, IInventory gun){
		super(new ContainerAutomatic(playerIn, gun));
		automatic = gun;
		player = playerIn;
		this.xSize =176;
		this.ySize = 192;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j){
		fontRenderer.drawString(net.minecraft.client.resources.I18n.format("gui.automatic.title"),8,4,4210752);
        fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 5, 4210752);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks){
        this.drawDefaultBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y){
		// 背景
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(tex);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}
}
