package mod.fbd.gui;

import mod.fbd.entity.mob.EntityArmorSmith;
import mod.fbd.inventory.ContainerArmorSmith;
import mod.fbd.inventory.InventoryArmorSmith;
import mod.fbd.network.MessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiArmorSmith extends GuiContainer{
	private static final ResourceLocation tex = new ResourceLocation("fbd", "textures/gui/armorsmith.png");

	private EntityArmorSmith entity;
	private GuiButton button_create;
	private GuiButton button_repair;

	public GuiArmorSmith(EntityPlayer player, EntityArmorSmith entity){
		super(new ContainerArmorSmith(player.inventory, entity.getSmithInventory(),Minecraft.getInstance().world));
		this.entity = entity;
		this.ySize = 192;
	}

	public void initGui(){
		super.initGui();
    	int x=(this.width - this.xSize) / 2;
    	int y=(this.height - this.ySize) / 2;
    	this.buttons.clear();
    	button_create = new GuiButton(101,x+84,y+88,40,20,"CREATE") {
    		@Override
    		public void onClick(double mouseX, double mouseY) {
    			actionPerformed(this);
    		}
    	};
    	this.buttons.add(button_create);
    	button_repair=new GuiButton(102,x+129,y+88,40,20,"REPAIR") {
    		@Override
    		public void onClick(double mouseX, double mouseY) {
    			actionPerformed(this);
    		}
    	};
    	button_repair.enabled = false;
    	this.buttons.add(button_repair);
    	this.children.addAll(buttons);
	}

    protected void actionPerformed(GuiButton button)
    {
    	boolean send = false;
    	int size;
    	switch(button.id){
    	case 101:
    		// 刀作成開始
    		MessageHandler.Send_MessageCreateAromor(this.entity);
    		//Mod_FantomBlade.Net_Instance.sendToServer(new MessageCreateArmor(this.entity));
			mc.displayGuiScreen(null);
	    break;
    	case 102:
    		// 刀修復
    		MessageHandler.SendMessageRepairArmor(this.entity);
    		//Mod_FantomBlade.Net_Instance.sendToServer(new MessageRepairArmor(this.entity));
	    break;
    	}
    }

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j){
		fontRenderer.drawString(entity.getSmithInventory().getName().getFormattedText(),8,4,4210752);
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

        ItemStack stack = entity.getSmithInventory().getStackInSlot(2);
        if ( !stack.isEmpty()){
        	if (stack.isDamageable()){
            	fontRenderer.drawString("cost:" + ((InventoryArmorSmith)entity.getSmithInventory()).getRepaierCost(), i+120, j+46,
            			((InventoryArmorSmith)entity.getSmithInventory()).canRepaier()?3465813:12333361);
            	if (((InventoryArmorSmith)entity.getSmithInventory()).canRepaier()){
            		button_repair.enabled = true;
            	}else{
            		button_repair.enabled = false;
            	}
            }
        	button_create.enabled = false;
        }else{
        	button_create.enabled = true;
    		button_repair.enabled = false;
        }
	}

    public void onGuiClosed()
    {
        if (this.mc.player != null)
        {
            this.inventorySlots.onContainerClosed(this.mc.player);
        }
    }

}
