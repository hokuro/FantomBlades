package mod.fbd.inventory;

import mod.fbd.core.Mod_FantomBlade;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerArmorSmith extends ContainerSmithBase {
	public ContainerArmorSmith(int id,  PlayerInventory playerIn,  InventorySmithBase smith){
    	super(Mod_FantomBlade.CONTAINER_ARMORSMITH, id, playerIn, smith);
	}
}
