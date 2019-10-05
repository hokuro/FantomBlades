package mod.fbd.event;

import mod.fbd.item.ItemCore;
import mod.fbd.item.katana.AbstractItemKatana;
import mod.fbd.util.ModUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {
	@SubscribeEvent
	public void AttackEntity(AttackEntityEvent event) {
//		if (event.getTarget() instanceof LivingEntity) {
//			LivingEntity target = (LivingEntity)event.getTarget();
//			PlayerEntity player = event.getPlayer();
//			ItemStack stack =  player.getHeldItemMainhand();
//			if (stack.getItem() instanceof AbstractItemKatana) {
//				AbstractItemKatana katana = (AbstractItemKatana)stack.getItem();
//				event.setCanceled(katana.beforeProc(player, target, stack));
//			}
//		}
	}

	@SubscribeEvent
	public void LivingAttack(LivingAttackEvent event) {
		LivingEntity living = event.getEntityLiving();
		Entity source = event.getSource().getTrueSource();

		// iダメージソースがプレイヤー
		if (source instanceof PlayerEntity) {
			ItemStack stack = ((PlayerEntity)source).getHeldItemMainhand();
			// i刀での攻撃
			if (stack.getItem() instanceof AbstractItemKatana) {
				event.setCanceled(execKatanaDamage((PlayerEntity)source, living, event.getSource(), event.getAmount(), stack));
				return;
			}
		}else if (living instanceof PlayerEntity) {
			if (execBlessEffect(living, event.getSource(), event.getAmount())) {
				event.setCanceled(true);
				return;
			}
		}
	}

	private boolean execKatanaDamage(PlayerEntity player, LivingEntity target, DamageSource source, float amount, ItemStack stack) {
		boolean ret = false;
		float def = amount;
		ItemStack helm = target.getItemStackFromSlot(EquipmentSlotType.HEAD);
		ItemStack chest = target.getItemStackFromSlot(EquipmentSlotType.CHEST);
		ItemStack legs = target.getItemStackFromSlot(EquipmentSlotType.LEGS);
		ItemStack feet = target.getItemStackFromSlot(EquipmentSlotType.FEET);
		int genbu = ((helm.getItem() == ItemCore.item_genbuhelmet)?1:0)+
					((chest.getItem() == ItemCore.item_genbubody)?1:0)+
					((legs.getItem() == ItemCore.item_genbulegs)?1:0)+
					((feet.getItem() == ItemCore.item_genbuboots)?1:0);

		int seiryu = ((helm.getItem() == ItemCore.item_seiryuhelmet)?1:0)+
					((chest.getItem() == ItemCore.item_seiryubody)?1:0)+
					((legs.getItem() == ItemCore.item_seiryulegs)?1:0)+
					((feet.getItem() == ItemCore.item_seiryuboots)?1:0);

		int suzaku = ((helm.getItem() == ItemCore.item_suzakuhelmet)?1:0)+
					((chest.getItem() == ItemCore.item_suzakubody)?1:0)+
					((legs.getItem() == ItemCore.item_suzakulegs)?1:0)+
					((feet.getItem() == ItemCore.item_suzakuboots)?1:0);

		int kirin = ((helm.getItem() == ItemCore.item_kirinhelmet)?1:0)+
					((chest.getItem() == ItemCore.item_kirinbody)?1:0)+
					((legs.getItem() == ItemCore.item_kirinlegs)?1:0)+
					((feet.getItem() == ItemCore.item_kirinboots)?1:0);

		int byako = ((helm.getItem() == ItemCore.item_byakohelmet)?1:0)+
					((chest.getItem() == ItemCore.item_byakobody)?1:0)+
					((legs.getItem() == ItemCore.item_byakolegs)?1:0)+
					((feet.getItem() == ItemCore.item_byakoboots)?1:0);

		int lv = AbstractItemKatana.getLevel(stack);
		int maxLv = AbstractItemKatana.getMaxLevel(stack);
		if (stack.getItem() == ItemCore.item_katana_genbu) {
			BlockState state = player.world.getBlockState(player.getPosition());
			if (seiryu == 4) {
				// i青龍シリーズ完全装備なら回復させる
				amount = damageHeal(amount);
				ret = true;
			}else if (byako == 4) {
				// i白虎シリーズ完全装備でダメージ増
				amount = damageUp(amount);
				ret = true;
			}else if(suzaku == 4) {
				// i朱雀シリーズ完全装備でダメージ増
				amount = damageCritical(amount);
				ret = true;
			}else if (kirin == 4) {
				// i麒麟シリーズ完全装備でダメージ減
				amount = damageHalf(amount);
				ret= true;
			}else if (genbu == 4) {
				// i玄武シリーズ完全装備でダメージ減
				amount = damageReduce(amount);
				ret = true;
			}else{
				// i炎上攻撃を受けないモブとエンダーマンへの攻撃でダメージ増加
			if (target.getType().isImmuneToFire() || target instanceof EndermanEntity) {
				amount *= 1.5F + lv/(maxLv/5);
				ret = true;
			}
			}
		}else if (stack.getItem() == ItemCore.item_katana_seiryu) {
			if (seiryu == 4) {
				// i青龍シリーズ完全装備ならダメージ減
				amount = damageReduce(amount);
				ret = true;
			}else if (byako == 4) {
				// i白虎シリーズ完全装備でダメージ減
				amount = damageHalf(amount);
				ret = true;
			}else if(suzaku == 4) {
				// i朱雀シリーズ完全装備で回復
				amount = damageHeal(amount);
				ret = true;
			}else if (kirin == 4) {
				// i麒麟シリーズ完全装備でダメージ増
				amount = damageCritical(amount);
				ret= true;
			}else if (genbu == 4) {
				// i玄武シリーズ完全装備でダメージ増
				amount = damageUp(amount);
				ret = true;
			}else{
				// iアンデッド、暴漢へのダメージ増
			if (target.getCreatureAttribute() == CreatureAttribute.UNDEAD|| target instanceof AbstractIllagerEntity) {
				amount *= 1.5F + lv/(maxLv/5);
				ret = true;
			}

			// i炎上ダメージを受けないモブに回復ポーション効果
			if ((target.getType().isImmuneToFire() && target.getCreatureAttribute() != CreatureAttribute.UNDEAD)) {
				amount *= -1 * (1.5F + lv/(maxLv/5));
				ret = true;
			}
			}
		}else if (stack.getItem() == ItemCore.item_katana_suzaku) {
			if (seiryu == 4) {
				// i青龍シリーズ完全装備ならダメージ増
				amount = damageUp(amount);
				ret = true;
			}else if (byako == 4) {
				// i白虎シリーズ完全装備でダメージ増
				amount = damageCritical(amount);
				ret = true;
			}else if(suzaku == 4) {
				// i朱雀シリーズ完全装備でダメージ減
				amount = damageReduce(amount);
				ret = true;
			}else if (kirin == 4) {
				// i麒麟シリーズ完全装備で回復
				amount = damageHeal(amount);
				ret= true;
			}else if (genbu == 4) {
				// i玄武シリーズ完全装備でダメージ減
				amount = damageHalf(amount);
				ret = true;
			}else{
			//i 動物系のモンスターに追加ダメージ
			if (target instanceof AnimalEntity || target instanceof RavagerEntity) {
				amount *= 1.5F + lv/(maxLv/5);
				ret = true;
			}

			// iアンデット、暴漢系のモブを回復
			if (target.getCreatureAttribute() == CreatureAttribute.UNDEAD|| target instanceof AbstractIllagerEntity) {
				amount *= -1 * (1.5F + lv/(maxLv/5));
				ret = true;
			}
			}
		}else if (stack.getItem() == ItemCore.item_katana_kirin) {
			if (seiryu == 4) {
				// i青龍シリーズ完全装備ならダメージ減
				amount = damageHalf(amount);
				ret = true;
			}else if (byako == 4) {
				// i白虎シリーズ完全装備で回復
				amount = damageHeal(amount);
				ret = true;
			}else if(suzaku == 4) {
				// i朱雀シリーズ完全装備でダメージ増
				amount = damageUp(amount);
				ret = true;
			}else if (kirin == 4) {
				// i麒麟シリーズ完全装備でダメージ減
				amount = damageReduce(amount);
				ret= true;
			}else if (genbu == 4) {
				// i玄武シリーズ完全装備でダメージ増
				amount = damageCritical(amount);
				ret = true;
			}else{
			// i水生生物へのダメージ増
			if (target.getCreatureAttribute() == CreatureAttribute.WATER) {
				amount *= 1.5F + lv/(maxLv/5);
				ret = true;
			}

			// i 動物系のモンスターを回復
			if (target instanceof AnimalEntity || target instanceof RavagerEntity) {
				amount *= -1 * (1.5F + lv/(maxLv/5));
				ret = true;
			}
			}
		}else if (stack.getItem() == ItemCore.item_katana_byako) {
			if (seiryu == 4) {
				// i青龍シリーズ完全装備ならダメージ増
				amount = damageCritical(amount);
				ret = true;
			}else if (byako == 4) {
				// i白虎シリーズ完全装備でダメージ減
				amount = damageReduce(amount);
				ret = true;
			}else if(suzaku == 4) {
				// i朱雀シリーズ完全装備でダメージ減
				amount = damageHalf(amount);
				ret = true;
			}else if (kirin == 4) {
				// i麒麟シリーズ完全装備でダメージ増
				amount = damageUp(amount);
				ret= true;
			}else if (genbu == 4) {
				// i玄武シリーズ完全装備で回復
				amount = damageHeal(amount);
				ret = true;
			}else{
			// i水性成物を回復
			if (target.getCreatureAttribute() == CreatureAttribute.WATER) {
				amount *= -1 * (1.5F + lv/(maxLv/5));
				ret = true;
			}
			}
		}else if (stack.getItem() == ItemCore.item_katana_niji) {
			// i70%の確率で会心の一撃
			if (ModUtil.randomD() < 0.7D) {
				amount *= (1.5F + lv/maxLv);
				ret = true;
			}
		}else if (stack.getItem() == ItemCore.item_katana_mugen) {
			// i80%の確率で会心の一撃
			if (ModUtil.randomD() < 0.8D) {
				amount *= (1.5F + lv/(maxLv/2));
				ret = true;
			}
		}
		if (ret == true) {
			if (amount == 0) {
				// iダメージ無効
			}else if (amount < 0) {
				// iヒール
				target.world.addParticle(ParticleTypes.INSTANT_EFFECT, target.posX, target.posY, target.posZ, 1, 1, 1);
				target.heal(amount * -1);
			}else {
				// iダメージ
				target.setHealth(target.getHealth() - amount);
				ret = false;
			}
		}

		return ret;
	}

	public boolean execBlessEffect(LivingEntity targetLiving, DamageSource source, float amount) {
		boolean ret = false;

		ItemStack helm = targetLiving.getItemStackFromSlot(EquipmentSlotType.HEAD);
		ItemStack chest = targetLiving.getItemStackFromSlot(EquipmentSlotType.CHEST);
		ItemStack legs = targetLiving.getItemStackFromSlot(EquipmentSlotType.LEGS);
		ItemStack feet = targetLiving.getItemStackFromSlot(EquipmentSlotType.FEET);

		Entity ent = source.getTrueSource();
		LivingEntity sourceLiving = null;
		if (ent instanceof LivingEntity) {
			sourceLiving = (LivingEntity)ent;
		}

		// i白虎シリーズ
		if (helm.getItem() == ItemCore.item_byakohelmet && chest.getItem() == ItemCore.item_byakobody && legs.getItem() == ItemCore.item_byakolegs && feet.getItem() == ItemCore.item_byakoboots) {
			// i動物からのダメージ無効
			if ((sourceLiving != null && (sourceLiving instanceof AnimalEntity || sourceLiving instanceof RavagerEntity))) {
				amount = 0;
				ret = true;
			}

			// iアンデッド 暴漢からのダメージで体力回復
			if ((sourceLiving != null && (sourceLiving.getCreatureAttribute() == CreatureAttribute.UNDEAD || sourceLiving instanceof AbstractIllagerEntity))) {
				amount = damageHeal(amount);
				ret = true;
			}

			// i炎のダメージ増加
			if (source.isFireDamage() || (sourceLiving != null && sourceLiving.isImmuneToFire())) {
				amount = damageCritical(amount);
				ret = true;
			}
		}

		// i玄武シリーズ
		if ((helm.getItem() == ItemCore.item_genbuhelmet && chest.getItem() == ItemCore.item_genbubody && legs.getItem() == ItemCore.item_genbulegs && feet.getItem() == ItemCore.item_genbuboots)) {
			// i水生生物からのダメージ無効 窒息ダメージ無効
			if (source.damageType.equals("drown") || (sourceLiving != null && (sourceLiving.getCreatureAttribute() == CreatureAttribute.WATER))) {
				amount = 0;
				ret = true;
			}

			// i動物からの攻撃で体力回復
			if ((sourceLiving != null && (sourceLiving instanceof AnimalEntity || sourceLiving instanceof RavagerEntity))) {
				amount = damageHeal(amount);
				ret = true;
			}

			// iアンデット、暴漢からのダメージ増加
			if ((sourceLiving != null && (sourceLiving.getCreatureAttribute() == CreatureAttribute.UNDEAD || sourceLiving instanceof AbstractIllagerEntity))) {
				amount=damageCritical(amount);
				ret = true;
			}

			//i 火属性のダメージ減少
			if (source.isFireDamage() || (sourceLiving != null && sourceLiving.isImmuneToFire())) {
				amount = damageHalf(amount);
				ret = true;
			}
		}

		// i青龍シリーズ
		if ((helm.getItem() == ItemCore.item_seiryuhelmet && chest.getItem() == ItemCore.item_seiryubody && legs.getItem() == ItemCore.item_seiryulegs && feet.getItem() == ItemCore.item_seiryuboots)) {
			// i水生生物の攻撃で体力回復え
			if ((sourceLiving != null && (sourceLiving.getCreatureAttribute() == CreatureAttribute.WATER))) {
				amount = damageHeal(amount);
				ret = true;
			}

			// i動物からのダメージ上昇
			if ((sourceLiving != null && (sourceLiving instanceof AnimalEntity || sourceLiving instanceof RavagerEntity))) {
				amount = damageCritical(amount);
				ret = true;
			}
		}


		// i朱雀シリーズ
		if ((helm.getItem() == ItemCore.item_suzakuhelmet && chest.getItem() == ItemCore.item_suzakubody && legs.getItem() == ItemCore.item_suzakulegs && feet.getItem() == ItemCore.item_suzakuboots)) {
			// i火炎ダメージ無効火
			if (source.isFireDamage() || (sourceLiving != null && sourceLiving.isImmuneToFire())) {
				amount = 0;
				ret = true;
			}

			// i水性生物からのダメージ増加
			if ((sourceLiving != null && (sourceLiving.getCreatureAttribute() == CreatureAttribute.WATER))) {
				amount = damageCritical(amount);
				ret = true;
			}

			// i動物からのダメージ減少
			if ((sourceLiving != null && (sourceLiving instanceof AnimalEntity || sourceLiving instanceof RavagerEntity))) {
				amount = damageHalf(amount);
				ret = true;
			}
		}


		// i麒麟シリーズ
		if ((helm.getItem() == ItemCore.item_kirinhelmet && chest.getItem() == ItemCore.item_kirinbody && legs.getItem() == ItemCore.item_kirinlegs && feet.getItem() == ItemCore.item_kirinboots)) {
			// iアンデット、暴漢からのダメージ無効
			if ((sourceLiving != null && (sourceLiving.getCreatureAttribute() == CreatureAttribute.UNDEAD || sourceLiving instanceof AbstractIllagerEntity))) {
				amount = 0;
				ret = true;
			}

			// i火属性の攻撃で体力回復
			if (source.isFireDamage() || (sourceLiving != null && sourceLiving.isImmuneToFire())) {
				amount = damageHeal(amount);
				ret = true;
			}

			// i水生生物からのダメージ減少
			if ((sourceLiving != null && (sourceLiving.getCreatureAttribute() == CreatureAttribute.WATER))) {
				amount = damageHalf(amount);
				ret = true;
			}
		}

		// iにじ
		if (helm.getItem() == ItemCore.item_nijihelmet && chest.getItem() == ItemCore.item_nijibody && legs.getItem() == ItemCore.item_nijilegs && feet.getItem() == ItemCore.item_nijiboots) {
			// i全ダメージ減少
			amount /= 5;
			ret = true;
		}

		if (ret == true) {
			if (amount == 0) {
				// iダメージ無効
			}else if (amount < 0) {
				// iヒール
				targetLiving.world.addParticle(ParticleTypes.INSTANT_EFFECT, targetLiving.posX, targetLiving.posY, targetLiving.posZ, 0, 0, 0);
				targetLiving.heal(amount * -1);
			}else {
				// iダメージ
				targetLiving.setHealth(targetLiving.getHealth() - amount);
				ret = false;
			}
		}
		return ret;
	}

	public float damageCritical(float amount) {return amount * 3.0F;}
	public float damageUp(float amount) {return amount * 2.0F;}
	public float damageHeal(float amount) {return -1 * amount;}
	public float damageHalf(float amount) {return amount/2.0F;}
	public float damageReduce(float amount) {return amount/1.5F;}
}



