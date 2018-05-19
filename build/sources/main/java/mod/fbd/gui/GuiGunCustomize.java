package mod.fbd.gui;

import mod.fbd.core.ModCommon;
import mod.fbd.inventory.ContainerGunCustomizer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiGunCustomize extends GuiContainer implements IContainerListener{
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/gui/guncustomaizer.png");
    private final ContainerGunCustomizer inventory;
    private GuiTextField nameField;
    private final EntityPlayer player;

	public GuiGunCustomize(EntityPlayer playerIn, World worldIn){
        super(new ContainerGunCustomizer(playerIn,worldIn));
        this.player = playerIn;
        this.inventory = (ContainerGunCustomizer)this.inventorySlots;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.inventorySlots.removeListener(this);
        this.inventorySlots.addListener(this);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();
        this.inventorySlots.removeListener(this);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(I18n.format("container.guncustomizer"), 60, 6, 4210752);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    /**
     * update the crafting window inventory with the items in the list
     */
    public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList)
    {
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

	@Override
	public void sendAllWindowProperties(Container containerIn, IInventory inventory) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
