package mod.fbd.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import mod.fbd.inventory.ContainerBladeSmith;
import mod.fbd.inventory.InventoryBladeSmith;
import mod.fbd.inventory.InventorySmithBase;
import mod.fbd.network.MessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiBladeSmith extends ContainerScreen<ContainerBladeSmith> {
	private static final ResourceLocation tex = new ResourceLocation("fbd", "textures/gui/bladesmith.png");
	private InventoryBladeSmith smithInv;
	private Button button_create;
	private Button button_repair;

	public GuiBladeSmith(ContainerBladeSmith container, PlayerInventory playerInv, ITextComponent title) {
		super(container, playerInv, title);
		smithInv = (InventoryBladeSmith)container.getSmithInventory();
		this.ySize = 192;
	}

	@Override
	public void init(){
		super.init();
    	int x=(this.width - this.xSize) / 2;
    	int y=(this.height - this.ySize) / 2;
    	this.buttons.clear();
    	button_create = new Button(x+84,y+88,40,20,"CREATE", (bt) ->{actionPerformed(101,bt);});
    	this.addButton(button_create);

    	button_repair = new Button(x+129,y+88,40,20,"REPAIR", (bt) ->{actionPerformed(102,bt);});
    	button_repair.active = false;
    	this.addButton(button_repair);
	}

    protected void actionPerformed(int id, Button button) {
    	boolean send = false;
    	int size;
    	switch(id){
    	case 101:
    		// 作成開始
    		MessageHandler.Send_MessageCreateEquipment(container.getSmithId());
    		Minecraft.getInstance().displayGuiScreen(null);
			break;
    	case 102:
    		// 修復
    		MessageHandler.Send_MessageRepairEquipment(container.getSmithId());
    		break;
    	}
    }

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j){
		font.drawString("gui.bladesmith",8,4,4210752);
		font.drawString("Inventory", 8, this.ySize - 96 + 5, 4210752);
		font.drawString("Level: " + smithInv.getSmith().getLevel(), 95, 74, 4210752);
		font.drawString("next", 150, 64, 4210752);
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

        this.blit(i+150, j+74, 176, 18, (int)(20 *((float)smithInv.getSmith().getExpParcent()/100.0F)), 8);


        ItemStack stack = smithInv.getStackInSlot(InventorySmithBase.INV_05_RESULT);
        if ( !stack.isEmpty()){
        	if (stack.isDamageable()){
        		font.drawString("cost:" + smithInv.getRepaierCost(), i+120, j+46,
            			smithInv.canRepaier()?3465813:12333361);
            	if (smithInv.canRepaier()){
            		button_repair.active = true;
            	}else{
            		button_repair.active = false;
            	}
            }
        	button_create.active = false;
        }else{
        	button_create.active = true;
    		button_repair.active = false;
        }
	}

    public void onGuiClosed() {
        if (this.playerInventory.player != null){
            this.container.onContainerClosed(Minecraft.getInstance().player);
        }
    }
}
