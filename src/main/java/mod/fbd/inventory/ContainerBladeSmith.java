package mod.fbd.inventory;

import mod.fbd.core.Mod_FantomBlade;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerBladeSmith extends ContainerSmithBase {
	public ContainerBladeSmith(int id,  PlayerInventory playerIn,  InventorySmithBase smith){
    	super(Mod_FantomBlade.CONTAINER_BLADESMITH, id, playerIn, smith);
	}

}
