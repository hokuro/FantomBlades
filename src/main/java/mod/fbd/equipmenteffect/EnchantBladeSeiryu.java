package mod.fbd.equipmenteffect;

import mod.fbd.item.katana.ItemKatanaSeiryu;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;

public class EnchantBladeSeiryu extends Enchantment {

    /** None */
    private static final String[] DAMAGE_NAMES = new String[] {"all", "undead", "arthropods"};
    /** Holds the base factor of enchantability needed to be able to use the enchant. */
    private static final int[] MIN_COST = new int[] {1, 5, 5};
    /** None */
    private static final int[] LEVEL_COST = new int[] {11, 8, 8};
    /** None */
    private static final int[] LEVEL_COST_SPAN = new int[] {20, 20, 20};
    /** Defines the type of damage of the enchantment, 0 = all, 1 = undead, 3 = arthropods */

    public EnchantBladeSeiryu()
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
        return 10;
    }

    /**
     * Calculates the additional damage that will be dealt by an item with this enchantment. This alternative to
     * calcModifierDamage is sensitive to the targets EnumCreatureAttribute.
     */
    @Override
    public float calcDamageByCreature(int level, CreatureAttribute creatureType)
    {
    	/*
    	 * アンデットへのダメージ上昇
    	 */
    	if (creatureType == CreatureAttribute.UNDEAD) {
    		return 5.0F * level;
    	}
    	return super.calcDamageByCreature(level, creatureType);
    }

    /**
     * Return the name of key in translation table of this enchantment.
     */
    @Override
    public String getName()
    {
        return "enchantment.damage.seiryu";
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
        return stack.getItem() instanceof ItemKatanaSeiryu;
    }

    /**
     * Called whenever a mob is damaged with an item that has this enchantment on it.
     */
    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level){
    	ItemStack stack = user.getHeldItem(Hand.MAIN_HAND);
    	/*
    	 * 生物のみ処理
    	 */
    	if (!(target instanceof LivingEntity)) {
    		return;
    	}
    	double damage = user.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
    	LivingEntity living = (LivingEntity)target;
    	if (stack.getItem() instanceof ItemKatanaSeiryu) {
    		/*
    		 * 炎上ダメージを受けないモブに回復ポーション効果
    		 */
			int lv = level/2 + Math.max((int)(damage/10),0);
    		if ((living.getType().isImmuneToFire() && living.getCreatureAttribute() != CreatureAttribute.UNDEAD)) {
    			Effects.INSTANT_HEALTH.affectEntity(null, null, living, lv, 1);
    		}else if ((living.getType().isImmuneToFire() && living.getCreatureAttribute() == CreatureAttribute.UNDEAD)) {
    			// iアンデッドなので即時ダメージで回復させる
    			Effects.INSTANT_DAMAGE.affectEntity(null, null, living, lv, 1);
    		}
    		/*
    		 * 暴漢系のモンスターに追加ダメージ
    		 */
    		if (living instanceof AbstractIllagerEntity) {
       			if (user instanceof PlayerEntity) {
    				target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity)user), (float)(damage*3.0D));
    			}else {
    				target.attackEntityFrom(DamageSource.causeMobDamage(user), (float)(damage*3.0D));
    			}
    		}
    	}
    }
}