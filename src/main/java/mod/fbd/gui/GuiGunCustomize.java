package mod.fbd.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import mod.fbd.core.ModCommon;
import mod.fbd.inventory.ContainerGunCustomizer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiGunCustomize extends ContainerScreen<ContainerGunCustomizer> implements IContainerListener {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/gui/guncustomaizer.png");

	public GuiGunCustomize(ContainerGunCustomizer container, PlayerInventory inv, ITextComponent titleIn){
		super(container, inv, titleIn);
    }

    @Override
    public void init() {
        super.init();
        this.container.removeListener(this);
        this.container.addListener(this);
    }


    @Override
    public void onClose() {
        super.onClose();
        this.container.removeListener(this);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(I18n.format("gui.guncustomizer.title"), 60, 6, 4210752);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {
        this.sendSlotContents(containerToSend, 0, containerToSend.getSlot(0).getStack());
    }

	@Override
	public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
