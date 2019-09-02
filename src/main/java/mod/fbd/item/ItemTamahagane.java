package mod.fbd.item;

import net.minecraft.item.Item;

public class ItemTamahagane extends Item {

	private float repairValue;
	public ItemTamahagane(Item.Properties property, float repair) {
		super(property);
		repairValue = repair;
	}

	public float getRepair() {
		return repairValue;
	}
}
