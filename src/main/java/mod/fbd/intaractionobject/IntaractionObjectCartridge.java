package mod.fbd.intaractionobject;

import mod.fbd.core.ModCommon;
import mod.fbd.inventory.ContainerCartridge;
import mod.fbd.item.ItemCartridge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;

public class IntaractionObjectCartridge implements IInteractionObject {

	private EnumHand have;
	public IntaractionObjectCartridge(EnumHand hand) {
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
		if (have == EnumHand.MAIN_HAND) {
			gunStack = playerIn.getHeldItemMainhand();
		}else {
			gunStack = playerIn.getHeldItemOffhand();
		}
		if(!gunStack.isEmpty() && gunStack.getItem() instanceof ItemCartridge){
			return new ContainerCartridge(playerIn, ((ItemCartridge)gunStack.getItem()).getInventory(gunStack));
		}
		return null;
	}

	@Override
	public String getGuiID() {
		// TODO 自動生成されたメソッド・スタブ
		return ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_CARTRIDGE;
	}

}
