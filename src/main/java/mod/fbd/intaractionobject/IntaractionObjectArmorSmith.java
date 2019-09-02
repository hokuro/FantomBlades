package mod.fbd.intaractionobject;

import mod.fbd.core.ModCommon;
import mod.fbd.core.log.ModLog;
import mod.fbd.entity.mob.EntityArmorSmith;
import mod.fbd.inventory.ContainerArmorSmith;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;

public class IntaractionObjectArmorSmith implements IInteractionObject {
	private int entityId;

	public IntaractionObjectArmorSmith(int id) {
		entityId = id;
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
		ModLog.log().debug("server gui entity id:"+this.entityId);
		Entity entity = playerIn.world.getEntityByID(entityId);
		if (entity instanceof EntityArmorSmith){
			return new ContainerArmorSmith(playerIn.inventory, ((EntityArmorSmith)entity).getSmithInventory(), playerIn.world);
		}
		return null;
	}

	@Override
	public String getGuiID() {
		// TODO 自動生成されたメソッド・スタブ
		return ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_ARMORSMITH;
	}

}
