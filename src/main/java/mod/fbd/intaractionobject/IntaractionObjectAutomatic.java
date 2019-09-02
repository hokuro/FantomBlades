package mod.fbd.intaractionobject;

import mod.fbd.core.ModCommon;
import mod.fbd.inventory.ContainerRevolver;
import mod.fbd.item.ItemRevolver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;

public class IntaractionObjectAutomatic implements IInteractionObject {
	private EnumHand have;

	public IntaractionObjectAutomatic(EnumHand hand) {
		have = hand;
	}

	@Override
	public ITextComponent getName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public ITextComponent getCustomName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		ItemStack gunStack;
		if (have == EnumHand.OFF_HAND) {
			gunStack = playerIn.getHeldItemOffhand();
		}else{
			gunStack = playerIn.getHeldItemMainhand();
		}
		if(!gunStack.isEmpty() && gunStack.getItem() instanceof ItemRevolver){
			return new ContainerRevolver(playerIn, ((ItemRevolver)gunStack.getItem()).getInventory(gunStack));
		}
		return null;
	}

	@Override
	public String getGuiID() {
		// TODO 自動生成されたメソッド・スタブ
		return ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_AUTOMATIC;
	}


}
