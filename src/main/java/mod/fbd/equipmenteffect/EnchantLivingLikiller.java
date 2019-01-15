package mod.fbd.equipmenteffect;

import mod.fbd.item.ItemKatanaGenbu;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EnchantLivingLikiller  extends Enchantment {

    /** None */
    private static final String[] DAMAGE_NAMES = new String[] {"all", "undead", "arthropods"};
    /** Holds the base factor of enchantability needed to be able to use the enchant. */
    private static final int[] MIN_COST = new int[] {1, 5, 5};
    /** None */
    private static final int[] LEVEL_COST = new int[] {11, 8, 8};
    /** None */
    private static final int[] LEVEL_COST_SPAN = new int[] {20, 20, 20};
    /** Defines the type of damage of the enchantment, 0 = all, 1 = undead, 3 = arthropods */

    public EnchantLivingLikiller()
    {
        super(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 9999;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 9999;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 1;
    }

    /**
     * Calculates the additional damage that will be dealt by an item with this enchantment. This alternative to
     * calcModifierDamage is sensitive to the targets EnumCreatureAttribute.
     */
    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType)
    {
    	return super.calcDamageByCreature(level, creatureType);
    }

    /**
     * Return the name of key in translation table of this enchantment.
     */
    public String getName()
    {
        return "enchantment.damage.living";
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment ench)
    {
        return !(ench instanceof EnchantmentDamage);
    }

    /**
     * Determines if this enchantment can be applied to a specific ItemStack.
     */
    public boolean canApply(ItemStack stack)
    {
        return stack.getItem() instanceof ItemKatanaGenbu;
    }

    /**
     * Called whenever a mob is damaged with an item that has this enchantment on it.
     */
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
    {
    	// 生きているモブ相手に追撃3*レベルダメージ
        if ( target instanceof EntityLiving && !((EntityLiving)target).isEntityUndead())
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)target;
            entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(user), 3.0F*level);
        }
    }
}