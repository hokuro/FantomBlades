package mod.fbd.creative;

import mod.fbd.item.ItemCore;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CreativeTabElmWepon  extends ItemGroup {
	public CreativeTabElmWepon(String label){
		super(label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ItemCore.item_katana);
	}
}