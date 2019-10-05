package mod.fbd.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import mod.fbd.inventory.ContainerCartridge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiCartridge extends ContainerScreen<ContainerCartridge> {
	private static final ResourceLocation tex = new ResourceLocation("fbd", "textures/gui/cartridge.png");

	private IInventory cartridge;
	private PlayerInventory player;

	public GuiCartridge(ContainerCartridge container, PlayerInventory inv, ITextComponent titleIn){
		super(container, inv, titleIn);
		cartridge = container.gunInventory();
		player = inv;
		this.xSize =176;
		this.ySize = 192;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j){
		this.font.drawString(net.minecraft.client.resources.I18n.format("gui.cartridge.title"),8,4,4210752);
		this.font.drawString("Inventory", 8, this.ySize - 96 + 5, 4210752);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks){
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y){
		// 背景
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bindTexture(tex);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
	}
}
