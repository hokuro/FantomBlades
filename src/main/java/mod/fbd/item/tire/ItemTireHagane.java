package mod.fbd.item.tire;

import java.util.function.Supplier;

import mod.fbd.item.ItemCore;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;

public class ItemTireHagane implements IItemTier {
	private int harvestLevel;
	private int maxUses;
	private float efficiency;
	private float attackDamage;
	private int enchantability;
	private LazyLoadBase<Ingredient> repairMaterial;

	public ItemTireHagane() {
		this.harvestLevel = 3;
		this.maxUses = 750;
		this.efficiency = 6.0F;
		this.attackDamage = 2.5F;
		this.enchantability = 10;
		Supplier<Ingredient> wk = ()->{return Ingredient.fromItems(ItemCore.item_steel);};
		this.repairMaterial = new LazyLoadBase<>(wk);
	}

	@Override
	public int getMaxUses() {
		// TODO 自動生成されたメソッド・スタブ
		return maxUses;
	}

	@Override
	public float getEfficiency() {
		// TODO 自動生成されたメソッド・スタブ
		return efficiency;
	}

	@Override
	public float getAttackDamage() {
		// TODO 自動生成されたメソッド・スタブ
		return attackDamage;
	}

	@Override
	public int getHarvestLevel() {
		// TODO 自動生成されたメソッド・スタブ
		return harvestLevel;
	}

	@Override
	public int getEnchantability() {
		// TODO 自動生成されたメソッド・スタブ
		return enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		// TODO 自動生成されたメソッド・スタブ
		return repairMaterial.getValue();
	}

}
