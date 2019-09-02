package mod.fbd.intaractionobject;

import mod.fbd.core.ModCommon;
import mod.fbd.inventory.ContainerBladeAlter;
import mod.fbd.tileentity.TileEntityBladeAlter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;

public class IntaractionObjectBladealter implements IInteractionObject {
	private BlockPos entPos;

	public IntaractionObjectBladealter(BlockPos blockPos) {
		// TODO 自動生成されたコンストラクター・スタブ
		entPos = blockPos;
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
		TileEntity ent = playerIn.world.getTileEntity(entPos);
		if (ent instanceof TileEntityBladeAlter){
			return new ContainerBladeAlter(playerIn, (IInventory)ent);
		}
		return null;
	}

	@Override
	public String getGuiID() {
		// TODO 自動生成されたメソッド・スタブ
		return ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_BLADEALTER;
	}

}
