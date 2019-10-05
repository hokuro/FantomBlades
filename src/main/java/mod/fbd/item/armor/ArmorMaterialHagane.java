package mod.fbd.item.armor;

import mod.fbd.item.ItemCore;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArmorMaterialHagane implements IArmorMaterial {

	public static final ArmorMaterialHagane HAGANE = new ArmorMaterialHagane("fbd:hagane", 28, new int[]{2, 5, 7, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.8F, Ingredient.fromItems(ItemCore.item_steel));
	public static final ArmorMaterialHagane BYAKO = new ArmorMaterialHagane("fbd:byako", 35, new int[]{4, 7, 9, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, Ingredient.fromItems(ItemCore.item_steel));
	public static final ArmorMaterialHagane SUZAKU = new ArmorMaterialHagane("fbd:suzaku", 35, new int[]{4, 7, 9, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, Ingredient.fromItems(ItemCore.item_steel));
	public static final ArmorMaterialHagane SEIRYU = new ArmorMaterialHagane("fbd:seiryu", 35, new int[]{4, 7, 9, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, Ingredient.fromItems(ItemCore.item_steel));
	public static final ArmorMaterialHagane GENBU = new ArmorMaterialHagane("fbd:genbu", 35, new int[]{4, 7, 9, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, Ingredient.fromItems(ItemCore.item_steel));
	public static final ArmorMaterialHagane KIRIN = new ArmorMaterialHagane("fbd:kirin", 35, new int[]{4, 7, 9, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, Ingredient.fromItems(ItemCore.item_steel));
	public static final ArmorMaterialHagane NIJI = new ArmorMaterialHagane("fbd:niji", 50, new int[]{10, 10, 10, 10}, 40, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 10.0F, Ingredient.fromItems(ItemCore.item_steel));

	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final LazyLoadBase<Ingredient> repairMaterial;

	public ArmorMaterialHagane(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float p_i48533_8_, Ingredient repairMaterialSupplier) {
		this.name = nameIn;
		this.maxDamageFactor = maxDamageFactorIn;
		this.damageReductionAmountArray = damageReductionAmountsIn;
		this.enchantability = enchantabilityIn;
		this.soundEvent = equipSoundIn;
		this.toughness = p_i48533_8_;
		this.repairMaterial = new LazyLoadBase<>(()->{return repairMaterialSupplier;});
	}

	@Override
	public int getDurability(EquipmentSlotType slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public SoundEvent getSoundEvent() {
		return this.soundEvent;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}
}
