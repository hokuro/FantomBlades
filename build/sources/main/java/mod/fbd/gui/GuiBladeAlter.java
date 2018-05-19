package mod.fbd.gui;

import java.io.IOException;

import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.inventory.ContainerBladeAlter;
import mod.fbd.item.ItemCore;
import mod.fbd.item.ItemKatana;
import mod.fbd.item.ItemKatanaNiji;
import mod.fbd.network.Message_BladeLevelUpdate;
import mod.fbd.network.Message_BladeLevelUpdate.EnumLevelKind;
import mod.fbd.tileentity.TileEntityBladeAlter;
import mod.fbd.util.Enchant;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class GuiBladeAlter extends GuiContainer {
	private static final ResourceLocation tex = new ResourceLocation("fbd", "textures/gui/bladealter.png");

	private static final int[] BUTTON1_X = {0,0,0};
	private static final int[] BUTTON1_Y = {208,223,238};
	private static final int[] BUTTON2_X = {30,30,30};
	private static final int[] BUTTON2_Y = {208,223,238};
	private static final int[] BUTTON3_X = {52,52,52};
	private static final int[] BUTTON3_Y = {208,223,238};

	private static final int[] BUTTON_LVUP = {7,54};
	private static final int[] BUTTON_MUGEN = {7,80};

	private static final int[] BUTTON_ENC_UP = {78,34};
	private static final int[] BUTTON_ENC_DOWN = {109,34};
	private static final int[] BUTTON_ENC_DEL = {141,34};

	private static final int[] BUTTON_POT_UP = {78,82};
	private static final int[] BUTTON_POT_DOWN = {78,98};
	private static final int[] BUTTON_POT_TUP = {109,82};
	private static final int[] BUTTON_POT_TDOWN = {109,98};
	private static final int[] BUTTON_POT_DEL = {141,88};

	private TileEntityBladeAlter entity;
	private String str_enchant = "";
	private String str_potion = "";
	private int idx_enchant = 0;
	private int idx_potion = 0;

	public GuiBladeAlter(EntityPlayer player, IInventory tileEntity){
		super(new ContainerBladeAlter(player, tileEntity));
		entity = (TileEntityBladeAlter)tileEntity;
		this.xSize =199;
		this.ySize = 207;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j){
		fontRenderer.drawString(I18n.translateToFallback("gui.bladealter.title"),8,4,4210752);
        fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 5, 4210752);

        fontRenderer.drawString("Lv+", 11, 58, 0xFFFFFFFF);

        fontRenderer.drawString("Lv+", 83, 38, 0xFFFFFFFF);
        fontRenderer.drawString("Lv-", 115, 38, 0xFFFFFFFF);
        fontRenderer.drawString("del", 143, 38, 0xFFFFFFFF);


        fontRenderer.drawString("Lv+", 83, 86, 0xFFFFFFFF);
        fontRenderer.drawString("Lv-", 83, 102, 0xFFFFFFFF);

        fontRenderer.drawString("+30", 115, 86, 0xFFFFFFFF);
        fontRenderer.drawString("-30", 115, 102, 0xFFFFFFFF);

        fontRenderer.drawString("del", 143, 92, 0xFFFFFFFF);

        ItemStack katana = entity.getKatana();
        if (katana != ItemStack.EMPTY){
        	ItemKatana k = ((ItemKatana)katana.getItem());
            int lvCost = k.getLevelUpExp();
            int encCost = k.getEnchantLevelUpExp();
            int potCost = k.getPotionEffectUpExp();
            int exp = ItemKatana.getExp(katana);
	        fontRenderer.drawString("Lv:"+((ItemKatana)katana.getItem()).getLevel(katana), 7,  35, 0x00FFFFFF);
	        fontRenderer.drawString(ItemKatana.getExp(katana)+"Exp", 7,  44, 0x00FFFFFF);
	        fontRenderer.drawString("cost:"+lvCost, 7,  70, (lvCost < exp?6684463:16711680));
	        if (!katana.isEmpty() && katana.getItem() != ItemCore.item_katana){
	        	fontRenderer.drawString("cost:"+encCost, 88, 51, (encCost < exp?6684463:16711680));
	        }
	        if (katana.getItem() == ItemCore.item_katana_niji || katana.getItem() == ItemCore.item_katana_mugen){
	        	fontRenderer.drawString("cost:"+potCost, 89, 114, (potCost < exp?6684463:16711680));
	        }

	        if (katana.getItem() == ItemCore.item_katana_niji && ((ItemKatanaNiji)katana.getItem()).getKillCount(katana) >= 1000){
	        	fontRenderer.drawString("YUME", 10, 84, 0x00FFFFFF);
	        }
	        String[] encStr = entity.getStrEnchant().split("/");
	        fontRenderer.drawString(encStr[0], 81, 16, 0x00FFFFFF);
	        if (encStr.length == 2){
		        fontRenderer.drawString(encStr[1], 87, 24, 0x00FFFFFF);
	        }

	        String[] potStr = entity.getStrPotion().split("/");
	        fontRenderer.drawString(potStr[0], 81, 64, 0x00FFFFFF);
	        if (potStr.length == 2){
		        fontRenderer.drawString(potStr[1], 87, 72, 0x00FFFFFF);
	        }
        }
	}

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;;

        int j2 = mouseX - i;
        int k2 = mouseY - j;
        ItemStack katana = entity.getKatana();
        int lvCost = 0;
        int encCost = 0;
        int potCost =0;
        int exp = -1;
        if (katana.getItem() instanceof ItemKatana){
            lvCost = ((ItemKatana)katana.getItem()).getLevelUpExp();
            encCost = ((ItemKatana)katana.getItem()).getEnchantLevelUpExp();
            potCost = ((ItemKatana)katana.getItem()).getPotionEffectUpExp();
            exp = ItemKatana.getExp(katana);
        }
        // level Up
        if (this.canLevelUp()){
            if ((j2-BUTTON_LVUP[0]-1) >= 0 && (k2-BUTTON_LVUP[1]-1) >= 0 && (j2-BUTTON_LVUP[0]-1) < 27 && (k2-BUTTON_LVUP[1]-1) < 13)
            {
                // 刀レベルアップ
            	Mod_FantomBlade.Net_Instance.sendToServer(new Message_BladeLevelUpdate(EnumLevelKind.LEVEL_BLADE, entity.getPos()));
            }
        }

        // upglade
        if (this.classUpMugen()){
            if ((j2-BUTTON_MUGEN[0]-1) >= 0 && (k2-BUTTON_MUGEN[1]-1) >= 0 && (j2-BUTTON_MUGEN[0]-1) < 27 && (k2-BUTTON_MUGEN[1]-1) < 13)
            {
                // 虹ランクアップ
            	Mod_FantomBlade.Net_Instance.sendToServer(new Message_BladeLevelUpdate(EnumLevelKind.NIJI, entity.getPos()));
            }
        }

        // Lv+
        if (this.canLevelUpEnchant()){
        	if ((j2-BUTTON_ENC_UP[0]-1) >= 0 && (k2-BUTTON_ENC_UP[1]-1) >= 0 && (j2-BUTTON_ENC_UP[0]-1) < 27 && (k2-BUTTON_ENC_UP[1]-1) < 13)
            {
                // エンチャントレベルアップ
            	Mod_FantomBlade.Net_Instance.sendToServer(new Message_BladeLevelUpdate(EnumLevelKind.LEVEL_ENCHANT, entity.getPos(),entity.getEnchantIndex(),1));
            }
        }

        // Lv-
    	if (this.canLevelDownEnchant()){
            if ((j2-BUTTON_ENC_DOWN[0]-1) >= 0 && (k2-BUTTON_ENC_DOWN[1]-1) >= 0 && (j2-BUTTON_ENC_DOWN[0]-1) < 27 && (k2-BUTTON_ENC_DOWN[1]-1) < 13)
            {
                // エンチャントレベルダウン
            	Mod_FantomBlade.Net_Instance.sendToServer(new Message_BladeLevelUpdate(EnumLevelKind.LEVEL_ENCHANT, entity.getPos(),entity.getEnchantIndex(),-1));
            }
    	}

        // del
    	if (this.canRemoveEnchant()){
            if ((j2-BUTTON_ENC_DEL[0]-1) >= 0 && (k2-BUTTON_ENC_DEL[1]-1) >= 0 && (j2-BUTTON_ENC_DEL[0]-1) < 27 && (k2-BUTTON_ENC_DEL[1]-1) < 13)
            {
                // エンチャント削除
            	Mod_FantomBlade.Net_Instance.sendToServer(new Message_BladeLevelUpdate(EnumLevelKind.LEVEL_ENCHANT, entity.getPos(),entity.getEnchantIndex(),0));
            }
        }

        	  // lv+
    	if (this.canLevelUpPotion()){
            if ((j2-BUTTON_POT_UP[0]-1) >= 0 && (k2-BUTTON_POT_UP[1]-1) >= 0 && (j2-BUTTON_POT_UP[0]-1) < 27 && (k2-BUTTON_POT_UP[1]-1) < 13)
            {
            	// ポーション効果レベルアップ
            	Mod_FantomBlade.Net_Instance.sendToServer(new Message_BladeLevelUpdate(EnumLevelKind.LEVEL_POTION, entity.getPos(),entity.getPotionIndex(),1));
            }
    	}

            // lv-
    	if (this.canLevelDownPotion()){
            if ((j2-BUTTON_POT_DOWN[0]-1) >= 0 && (k2-BUTTON_POT_DOWN[1]-1) >= 0 && (j2-BUTTON_POT_DOWN[0]-1) < 27 && (k2-BUTTON_POT_DOWN[1]-1) < 13)
            {
            	// ポーション効果レベルダウン
            	Mod_FantomBlade.Net_Instance.sendToServer(new Message_BladeLevelUpdate(EnumLevelKind.LEVEL_POTION, entity.getPos(),entity.getPotionIndex(),-1));
            }
    	}

            // 30+
    	if (this.canLevelUpPotionDuration()){
            if ((j2-BUTTON_POT_TUP[0]-1) >= 0 && (k2-BUTTON_POT_TUP[1]-1) >= 0 && (j2-BUTTON_POT_TUP[0]-1) < 27 && (k2-BUTTON_POT_TUP[1]-1) < 13)
            {
                // ポーション効果時間延長
            	Mod_FantomBlade.Net_Instance.sendToServer(new Message_BladeLevelUpdate(EnumLevelKind.LEVEL_POTION_DURATION, entity.getPos(),entity.getPotionIndex(),1));
            }
    	}

            // 30-
    	if (this.canLevelDownPotionDuration()){
            if ((j2-BUTTON_POT_TDOWN[0]-1) >= 0 && (k2-BUTTON_POT_TDOWN[1]-1) >= 0 && (j2-BUTTON_POT_TDOWN[0]-1) < 27 && (k2-BUTTON_POT_TDOWN[1]-1) < 13)
            {
               // ポーション効果時間短縮
            	Mod_FantomBlade.Net_Instance.sendToServer(new Message_BladeLevelUpdate(EnumLevelKind.LEVEL_POTION_DURATION, entity.getPos(),entity.getPotionIndex(),-1));
            }
    	}

            // del
    	if(this.canRemovePotion()){
            if ((j2-BUTTON_POT_DEL[0]-1) >= 0 && (k2-BUTTON_POT_DEL[1]-1) >= 0 && (j2-BUTTON_POT_DEL[0]-1) < 27 && (k2-BUTTON_POT_DEL[1]-1) < 13)
            {
                // ポーション効果削除
            	Mod_FantomBlade.Net_Instance.sendToServer(new Message_BladeLevelUpdate(EnumLevelKind.LEVEL_POTION, entity.getPos(),entity.getPotionIndex(),0));
            }
        }

        if (!katana.isEmpty() && katana.getItem() != ItemCore.item_katana){
            // 1
            if ((j2-66) >= 0 && (k2-14) >= 0 && (j2-66) < 11 && (k2-14) < 18)
            {
               // エンチャント変更
            	entity.addEnchantIndex(-1);
            }
            // 2
            if ((j2-185) >= 0 && (k2-14) >= 0 && (j2-185) < 11 && (k2-14) < 18)
            {
               // エンチャント変更
            	entity.addEnchantIndex(1);
            }
        }

        if (katana.getItem() == ItemCore.item_katana_niji || katana.getItem() == ItemCore.item_katana_mugen){
            // 3
            if ((j2-66) >= 0 && (k2-62) >= 0 && (j2-66) < 11 && (k2-62) < 18)
            {
                // ポーション効果変更
            	entity.addPotionIndex(-1);
            }
            // 4
            if ((j2-185) >= 0 && (k2-62) >= 0 && (j2-185) < 11 && (k2-62) < 18)
            {
                // ポーション効果変更
            	entity.addPotionIndex(1);
            }
        }
    }


	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y){
		// 背景
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(tex);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int j2 = x - i;
        int k2 = y - j;

        ItemStack katana = entity.getKatana();
        int lvCost = 0;
        int encCost = 0;
        int potCost =0;
        int exp = -1;
        if (katana.getItem() instanceof ItemKatana){
            lvCost = ((ItemKatana)katana.getItem()).getLevelUpExp();
            encCost = ((ItemKatana)katana.getItem()).getEnchantLevelUpExp();
            potCost = ((ItemKatana)katana.getItem()).getPotionEffectUpExp();
            exp = ItemKatana.getExp(katana);
        }

        // level Up
        if (this.canLevelUp()){
            if ((j2-BUTTON_LVUP[0]-1) >= 0 && (k2-BUTTON_LVUP[1]-1) >= 0 && (j2-BUTTON_LVUP[0]-1) < 27 && (k2-BUTTON_LVUP[1]-1) < 13)
            {
                this.drawTexturedModalRect(i+BUTTON_LVUP[0], j+BUTTON_LVUP[1], BUTTON1_X[2], BUTTON1_Y[2], 29, 15);
            }
            else
            {
                this.drawTexturedModalRect(i+BUTTON_LVUP[0], j+BUTTON_LVUP[1], BUTTON1_X[1], BUTTON1_Y[1], 29, 15);
            }
        }else{
        	this.drawTexturedModalRect(i+BUTTON_LVUP[0], j+BUTTON_LVUP[1], BUTTON1_X[0], BUTTON1_Y[0], 29, 15);
        }

        // upglade
        if (this.classUpMugen()){
            if ((j2-BUTTON_MUGEN[0]-1) >= 0 && (k2-BUTTON_MUGEN[1]-1) >= 0 && (j2-BUTTON_MUGEN[0]-1) < 27 && (k2-BUTTON_MUGEN[1]-1) < 13)
            {
                this.drawTexturedModalRect(i+BUTTON_MUGEN[0], j+BUTTON_MUGEN[1], BUTTON1_X[2], BUTTON1_Y[2], 29, 15);
            }
            else
            {
                this.drawTexturedModalRect(i+BUTTON_MUGEN[0], j+BUTTON_MUGEN[1], BUTTON1_X[1], BUTTON1_Y[1], 29, 15);
            }
        }

        // Lv+
        if (this.canLevelUpEnchant()){
        	if ((j2-BUTTON_ENC_UP[0]-1) >= 0 && (k2-BUTTON_ENC_UP[1]-1) >= 0 && (j2-BUTTON_ENC_UP[0]-1) < 27 && (k2-BUTTON_ENC_UP[1]-1) < 13)
            {
                this.drawTexturedModalRect(i+BUTTON_ENC_UP[0], j+BUTTON_ENC_UP[1], BUTTON1_X[2], BUTTON1_Y[2], 29, 15);
            }
            else
            {
                this.drawTexturedModalRect(i+BUTTON_ENC_UP[0], j+BUTTON_ENC_UP[1], BUTTON1_X[1], BUTTON1_Y[1], 29, 15);
            }
        }else{
            this.drawTexturedModalRect(i+BUTTON_ENC_UP[0], j+BUTTON_ENC_UP[1], BUTTON1_X[0], BUTTON1_Y[0], 29, 15);
        }

        if (this.canLevelDownEnchant()){
            // Lv-
            if ((j2-BUTTON_ENC_DOWN[0]-1) >= 0 && (k2-BUTTON_ENC_DOWN[1]-1) >= 0 && (j2-BUTTON_ENC_DOWN[0]-1) < 27 && (k2-BUTTON_ENC_DOWN[1]-1) < 13)
            {
                this.drawTexturedModalRect(i+BUTTON_ENC_DOWN[0], j+BUTTON_ENC_DOWN[1], BUTTON1_X[2], BUTTON1_Y[2], 29, 15);
            }
            else
            {
                this.drawTexturedModalRect(i+BUTTON_ENC_DOWN[0], j+BUTTON_ENC_DOWN[1], BUTTON1_X[1], BUTTON1_Y[1], 29, 15);
            }
        }else{
            this.drawTexturedModalRect(i+BUTTON_ENC_DOWN[0], j+BUTTON_ENC_DOWN[1], BUTTON1_X[0], BUTTON1_Y[0], 29, 15);
        }

        if (this.canRemoveEnchant()){
            // del
            if ((j2-BUTTON_ENC_DEL[0]-1) >= 0 && (k2-BUTTON_ENC_DEL[1]-1) >= 0 && (j2-BUTTON_ENC_DEL[0]-1) < 27 && (k2-BUTTON_ENC_DEL[1]-1) < 13)
            {
                this.drawTexturedModalRect(i+BUTTON_ENC_DEL[0], j+BUTTON_ENC_DEL[1], BUTTON3_X[2], BUTTON3_Y[2], 18, 15);
            }
            else
            {
                this.drawTexturedModalRect(i+BUTTON_ENC_DEL[0], j+BUTTON_ENC_DEL[1], BUTTON3_X[1], BUTTON3_Y[1], 18, 15);
            }
        }else{
            this.drawTexturedModalRect(i+BUTTON_ENC_DEL[0], j+BUTTON_ENC_DEL[1], BUTTON3_X[0], BUTTON3_Y[0], 18, 15);
        }

        	  // lv+
        if (this.canLevelUpPotion()){
            if ((j2-BUTTON_POT_UP[0]-1) >= 0 && (k2-BUTTON_POT_UP[1]-1) >= 0 && (j2-BUTTON_POT_UP[0]-1) < 27 && (k2-BUTTON_POT_UP[1]-1) < 13)
            {
                this.drawTexturedModalRect(i+BUTTON_POT_UP[0], j+BUTTON_POT_UP[1], BUTTON1_X[2], BUTTON1_Y[2], 29, 15);
            }
            else
            {
                this.drawTexturedModalRect(i+BUTTON_POT_UP[0], j+BUTTON_POT_UP[1], BUTTON1_X[1], BUTTON1_Y[1], 29, 15);
            }
        }else{
            this.drawTexturedModalRect(i+BUTTON_POT_UP[0], j+BUTTON_POT_UP[1], BUTTON1_X[0], BUTTON1_Y[0], 29, 15);
        }

            // lv-
        if (this.canLevelDownPotion()){
            if ((j2-BUTTON_POT_DOWN[0]-1) >= 0 && (k2-BUTTON_POT_DOWN[1]-1) >= 0 && (j2-BUTTON_POT_DOWN[0]-1) < 27 && (k2-BUTTON_POT_DOWN[1]-1) < 13)
            {
                this.drawTexturedModalRect(i+BUTTON_POT_DOWN[0], j+BUTTON_POT_DOWN[1], BUTTON1_X[2], BUTTON1_Y[2], 29, 15);
            }
            else
            {
                this.drawTexturedModalRect(i+BUTTON_POT_DOWN[0], j+BUTTON_POT_DOWN[1], BUTTON1_X[1], BUTTON1_Y[1], 29, 15);
            }
        }else{
            this.drawTexturedModalRect(i+BUTTON_POT_DOWN[0], j+BUTTON_POT_DOWN[1], BUTTON1_X[0], BUTTON1_Y[0], 29, 15);
        }
            // 30+
        if (this.canLevelUpPotionDuration()){
            if ((j2-BUTTON_POT_TUP[0]-1) >= 0 && (k2-BUTTON_POT_TUP[1]-1) >= 0 && (j2-BUTTON_POT_TUP[0]-1) < 27 && (k2-BUTTON_POT_TUP[1]-1) < 13)
            {
                this.drawTexturedModalRect(i+BUTTON_POT_TUP[0], j+BUTTON_POT_TUP[1], BUTTON1_X[2], BUTTON1_Y[2], 29, 15);
            }
            else
            {
                this.drawTexturedModalRect(i+BUTTON_POT_TUP[0], j+BUTTON_POT_TUP[1], BUTTON1_X[1], BUTTON1_Y[1], 29, 15);
            }
        }else{
            this.drawTexturedModalRect(i+BUTTON_POT_TUP[0], j+BUTTON_POT_TUP[1], BUTTON1_X[0], BUTTON1_Y[0], 29, 15);
        }

            // 30-
        if (this.canLevelDownPotionDuration()){
            if ((j2-BUTTON_POT_TDOWN[0]-1) >= 0 && (k2-BUTTON_POT_TDOWN[1]-1) >= 0 && (j2-BUTTON_POT_TDOWN[0]-1) < 27 && (k2-BUTTON_POT_TDOWN[1]-1) < 13)
            {
                this.drawTexturedModalRect(i+BUTTON_POT_TDOWN[0], j+BUTTON_POT_TDOWN[1], BUTTON1_X[2], BUTTON1_Y[2], 29, 15);
            }
            else
            {
                this.drawTexturedModalRect(i+BUTTON_POT_TDOWN[0], j+BUTTON_POT_TDOWN[1], BUTTON1_X[1], BUTTON1_Y[1], 29, 15);
            }
        }else{
        	this.drawTexturedModalRect(i+BUTTON_POT_TDOWN[0], j+BUTTON_POT_TDOWN[1], BUTTON1_X[0], BUTTON1_Y[0], 29, 15);
        }

            // del
        if (this.canRemovePotion()){
            if ((j2-BUTTON_POT_DEL[0]-1) >= 0 && (k2-BUTTON_POT_DEL[1]-1) >= 0 && (j2-BUTTON_POT_DEL[0]-1) < 27 && (k2-BUTTON_POT_DEL[1]-1) < 13)
            {
                this.drawTexturedModalRect(i+BUTTON_POT_DEL[0], j+BUTTON_POT_DEL[1], BUTTON3_X[2], BUTTON3_Y[2], 18, 15);
            }
            else
            {
                this.drawTexturedModalRect(i+BUTTON_POT_DEL[0], j+BUTTON_POT_DEL[1], BUTTON3_X[1], BUTTON3_Y[1], 18, 15);
            }
        }else{
        	this.drawTexturedModalRect(i+BUTTON_POT_DEL[0], j+BUTTON_POT_DEL[1], BUTTON3_X[0], BUTTON3_Y[0], 18, 15);
        }

        if (!katana.isEmpty() && katana.getItem() != ItemCore.item_katana){
            // 1
            if ((j2-66) >= 0 && (k2-14) >= 0 && (j2-66) < 11 && (k2-14) < 18)
            {
                this.drawTexturedModalRect(i+66, j+14, 211, 0, 11, 18);
            }
            else
            {
                this.drawTexturedModalRect(i+66, j+14, 211, 19, 11, 18);
            }
            // 2
            if ((j2-185) >= 0 && (k2-14) >= 0 && (j2-185) < 11 && (k2-14) < 18)
            {
                this.drawTexturedModalRect(i+185, j+14, 200, 0, 11, 18);
            }
            else
            {
                this.drawTexturedModalRect(i+185, j+14, 200, 19, 11, 18);
            }
        }

        if (katana.getItem() == ItemCore.item_katana_niji || katana.getItem() == ItemCore.item_katana_mugen){
            // 3
            if ((j2-66) >= 0 && (k2-62) >= 0 && (j2-66) < 11 && (k2-62) < 18)
            {
                this.drawTexturedModalRect(i+66, j+62, 211, 0, 11, 18);
            }
            else
            {
                this.drawTexturedModalRect(i+66, j+62, 211, 19, 11, 18);
            }
            // 4
            if ((j2-185) >= 0 && (k2-62) >= 0 && (j2-185) < 11 && (k2-62) < 18)
            {
                this.drawTexturedModalRect(i+185, j+62, 200, 0, 11, 18);
            }
            else
            {
                this.drawTexturedModalRect(i+185, j+62, 200, 19, 11, 18);
            }
        }
	}


	private boolean canLevelUp(){
		ItemStack stack = entity.getKatana();
		if (!stack.isEmpty() && (ItemKatana.getExp(stack) >= ((ItemKatana)stack.getItem()).getLevelUpExp())){
			return true;
		}
		return false;
	}

	private boolean classUpMugen(){
		ItemStack stack = entity.getKatana();
		if (stack.getItem() instanceof ItemKatanaNiji){
			if (ItemKatanaNiji.getKillCount(stack) >= 1000){
				return true;
			}
		}
		return false;
	}

	private boolean canLevelUpEnchant(){
		ItemStack stack = entity.getKatana();
		if (!stack.isEmpty()){
			if (stack.getItem() != ItemCore.item_katana){
				Enchant ent = entity.getEnchant();
				if (ent != null && (ItemKatana.getExp(stack) >= ((ItemKatana)stack.getItem()).getEnchantLevelUpExp())){
					int lv = ((ItemKatana)stack.getItem()).getLevel(stack);
					if (lv <= 10 && ent.Level() < lv){
						// 10レベルまでは刀のレベルまで上げられる
						return true;
					}else if (lv > 10 && lv <= 100 && ent.Level() < lv/10+10){
						// 100レベルまでは20レベルまで上げられる
						return true;
					}else if (lv > 100 && ent.Level() < lv/20 + 20){
						// 100レベル以上は25まで上げられる
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean canLevelDownEnchant(){
		ItemStack stack = entity.getKatana();
		if (!stack.isEmpty()){
			if (stack.getItem() != ItemCore.item_katana){
				Enchant ent = entity.getEnchant();
				if (ent != null && (ent.Level() > 1 && ItemKatana.getExp(stack) >= ((ItemKatana)stack.getItem()).getEnchantLevelUpExp())){
					return true;
				}
			}
		}
		return false;
	}


	private boolean canRemoveEnchant(){
		ItemStack stack = entity.getKatana();
		if (!stack.isEmpty()){
			if (stack.getItem() != ItemCore.item_katana){
				Enchant ent = entity.getEnchant();
				if (ent!=null && ItemKatana.getExp(stack) >= ((ItemKatana)stack.getItem()).getPotionEffectUpExp()){
					if (ent.Level() > 0){
						return true;
					}
				}
			}
		}
		return false;
	}


	private boolean canLevelUpPotion(){
		ItemStack stack = entity.getKatana();
		if (!stack.isEmpty()){
			PotionEffect ent = entity.getPotion();
			if (ent == null ){return false;}
			if (stack.getItem() == ItemCore.item_katana_niji){
				if (ent != null && ItemKatana.getExp(stack) >= ((ItemKatana)stack.getItem()).getPotionEffectUpExp()){
					int lv = ((ItemKatana)stack.getItem()).getLevel(stack);
					if (lv <= 10 && ent.getAmplifier() < lv){
						// 10レベルまでは刀のレベルまで上げられる
						return true;
					}else if (lv > 10 && lv <= 100 && ent.getAmplifier() < lv/10+10){
						// 100レベルまでは20レベルまで上げられる
						return true;
					}else if (lv > 100 && ent.getAmplifier() < lv/20 + 20){
						// 100レベル以上は25まで上げられる
						return true;
					}
				}
			}else if (stack.getItem() == ItemCore.item_katana_mugen){
				if (ent != null && ItemKatana.getExp(stack) >= ((ItemKatana)stack.getItem()).getPotionEffectUpExp()){
					int lv = ((ItemKatana)stack.getItem()).getLevel(stack);
					if (ent.getAmplifier() < lv){
						// 刀のレベルまで上げられる
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean canLevelDownPotion(){
		ItemStack stack = entity.getKatana();
		if (!stack.isEmpty()){
			if (stack.getItem() == ItemCore.item_katana_niji || stack.getItem() == ItemCore.item_katana_mugen){
				PotionEffect ent = entity.getPotion();
				if (ent != null && ent.getAmplifier() > 0 && ItemKatana.getExp(stack) >= ((ItemKatana)stack.getItem()).getPotionEffectUpExp()){
					return true;
				}
			}
		}
		return false;
	}

	private boolean canLevelUpPotionDuration(){
		ItemStack stack = entity.getKatana();
		if (!stack.isEmpty()){
			PotionEffect ent = entity.getPotion();
			if (ent == null){return false;}
			if (ent.getPotion().isInstant()){return false;}
			if (stack.getItem() == ItemCore.item_katana_niji){
				if (ent != null && ItemKatana.getExp(stack) >= ((ItemKatana)stack.getItem()).getPotionEffectUpExp() && ent.getAmplifier() >= 0){
					int lv = ((ItemKatana)stack.getItem()).getLevel(stack);
					int duration = (ent.getDuration()/600);
					if (lv <= 10 && duration < lv){
						// 10レベルまでは刀のレベルまで上げられる
						return true;
					}else if (lv <= 100 && duration < lv/10+10){
						// 100レベルまでは20レベルまで上げられる
						return true;
					}else if (duration < lv/20 + 20){
						// 100レベル以上は25まで上げられる
						return true;
					}
				}
			}else if (stack.getItem() == ItemCore.item_katana_mugen){
				if (ent != null && ItemKatana.getExp(stack) >= ((ItemKatana)stack.getItem()).getPotionEffectUpExp()){
					int lv = ((ItemKatana)stack.getItem()).getLevel(stack);
					int duration = (ent.getDuration()/600);
					if (duration < lv){
						// 刀のレベルまで上げられる
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean canLevelDownPotionDuration(){
		ItemStack stack = entity.getKatana();
		if (!stack.isEmpty()){
			if (stack.getItem() == ItemCore.item_katana_niji || stack.getItem() == ItemCore.item_katana_mugen){
				PotionEffect ent = entity.getPotion();
				if (ent != null && ent.getDuration() > 600 && ItemKatana.getExp(stack) >= ((ItemKatana)stack.getItem()).getPotionEffectUpExp()){
					return true;
				}
			}
		}
		return false;
	}


	private boolean canRemovePotion(){
		ItemStack stack = entity.getKatana();
		if (!stack.isEmpty()){
			if (stack.getItem() == ItemCore.item_katana_niji || stack.getItem() == ItemCore.item_katana_mugen){
				PotionEffect ent = entity.getPotion();
				if (ent != null && ent.getAmplifier() >= 0 && ItemKatana.getExp(stack) >= ((ItemKatana)stack.getItem()).getPotionEffectUpExp()){
					return true;
				}
			}
		}
		return false;
	}
}
