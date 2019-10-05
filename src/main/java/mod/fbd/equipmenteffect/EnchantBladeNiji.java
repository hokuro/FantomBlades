package mod.fbd.equipmenteffect;

import mod.fbd.item.katana.ItemKatanaMugen;
import mod.fbd.item.katana.ItemKatanaNiji;
import mod.fbd.util.ModUtil;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class EnchantBladeNiji  extends Enchantment {

    /** None */
    private static final String[] DAMAGE_NAMES = new String[] {"all", "undead", "arthropods"};
    /** Holds the base factor of enchantability needed to be able to use the enchant. */
    private static final int[] MIN_COST = new int[] {1, 5, 5};
    /** None */
    private static final int[] LEVEL_COST = new int[] {11, 8, 8};
    /** None */
    private static final int[] LEVEL_COST_SPAN = new int[] {20, 20, 20};
    /** Defines the type of damage of the enchantment, 0 = all, 1 = undead, 3 = arthropods */

    public EnchantBladeNiji()
    {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentType.WEAPON, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 9999;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 9999;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    @Override
    public int getMaxLevel()
    {
        return 99;
    }

    /**
     * Calculates the additional damage that will be dealt by an item with this enchantment. This alternative to
     * calcModifierDamage is sensitive to the targets EnumCreatureAttribute.
     */
    @Override
    public float calcDamageByCreature(int level, CreatureAttribute creatureType)
    {
    	// i70%の確率で追加ダメージ
    	float ret = 0.0F;
    	if (ModUtil.randomF() <= 0.7) {
    		ret += (1.0F + (0.5 * level));
    	}
    	return ret;
    }

    /**
     * Return the name of key in translation table of this enchantment.
     */
    @Override
    public String getName()
    {
        return "enchantment.damage.critical";
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
        return !(ench instanceof DamageEnchantment);
    }

    /**
     * Determines if this enchantment can be applied to a specific ItemStack.
     */
    @Override
    public boolean canApply(ItemStack stack)
    {
        return (stack.getItem() instanceof ItemKatanaNiji || stack.getItem() instanceof ItemKatanaMugen);
    }

    /**
     * Called whenever a mob is damaged with an item that has this enchantment on it.
     */
    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level){
    }
}