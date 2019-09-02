package mod.fbd.gui;

import mod.fbd.block.BlockBladeforge;
import mod.fbd.inventory.ContainerBladeforge;
import mod.fbd.tileentity.TileEntityBladeforge;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiBladeforge extends GuiContainer {
	private static final ResourceLocation tex = new ResourceLocation("fbd", "textures/gui/bladeforge.png");

	private TileEntityBladeforge entity;

	public GuiBladeforge(EntityPlayer player, IInventory tileEntity){
		super(new ContainerBladeforge(player.inventory, tileEntity));
		entity = (TileEntityBladeforge)tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j){
		fontRenderer.drawString(net.minecraft.client.resources.I18n.format("gui.bladeforge.title"),8,4,4210752);
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

        this.drawTexturedModalRect(i+46, j+17, 176, 0, 8, getIronBar());
        this.drawTexturedModalRect(i+121, j+17, 176, 0, 8, getCoalBar());

        if (entity.getField(TileEntityBladeforge.FIELD_STATE) == BlockBladeforge.STAGE_2){
            this.drawTexturedModalRect(i+55, j+16, 184, 0, 66, 59);
        }
	}

	protected int getIronBar(){
		return (int)(((float)(TileEntityBladeforge.MAX_IRON - entity.getField(TileEntityBladeforge.FIELD_IRON))) / ((float)TileEntityBladeforge.MAX_IRON) * 56.0);
	}

	protected int getCoalBar(){
		return (int)(((float)(TileEntityBladeforge.MAX_COAL - entity.getField(TileEntityBladeforge.FIELD_COAL))) / ((float)TileEntityBladeforge.MAX_COAL) * 56.0);
	}
}
