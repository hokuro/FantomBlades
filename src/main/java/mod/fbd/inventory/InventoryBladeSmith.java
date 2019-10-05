package mod.fbd.inventory;

import mod.fbd.entity.mob.EntityBladeSmith;
import mod.fbd.item.ItemCore;
import mod.fbd.item.katana.AbstractItemKatana;
import mod.fbd.item.piece.ItemBladePiece;
import mod.fbd.item.piece.ItemTamahagane;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;

public class InventoryBladeSmith extends InventorySmithBase {

	public InventoryBladeSmith(EntityBladeSmith smith) {
		super(smith);
	}

    public boolean allowItem(int index ,ItemStack stack){
    	if (index == INV_05_RESULT) {
    		return (stack.getItem() instanceof AbstractItemKatana && stack.getDamage() > 0);
    	} else if (index == INV_06_TOOL_BIGHAMMER) {
    		return (stack.getItem() == ItemCore.item_steel_bighammer);
    	} else if (index == INV_07_TOOL_SMALLHAMMER) {
    		return (stack.getItem() == ItemCore.item_steel_smallhammer);
    	} else if (index == INV_08_TOOL_SHEARS) {
    		return (stack.getItem() == Items.SHEARS);
    	} else if (index == INV_09_TOOL_COAL) {
    		return (stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL);
    	} else if (index <= INV_04_POTION){
    		return ((stack.getItem() instanceof ItemTamahagane) ||
    				(stack.getItem() instanceof ItemBladePiece) ||
    				(stack.getItem() instanceof EnchantedBookItem) ||
    				(stack.getItem() instanceof PotionItem));
    	}
    	return false;
    }

    @Override
	public void repair() {
		super.repair();
    	AbstractItemKatana.setRustValue(stacks.get(INV_05_RESULT), 0);
	}
}
