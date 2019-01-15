package mod.fbd.item;

import mod.fbd.core.Mod_FantomBlade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemHaganeAromor extends ItemArmor {
    public ItemHaganeAromor(ItemArmor.ArmorMaterial armorMaterial, int renderIndex, EntityEquipmentSlot equipmentSlotIn) {
        super(armorMaterial, renderIndex, equipmentSlotIn);
//        出来たらいいな(めんどくさい)
//        if (armorMaterial == Mod_FantomBlade.AROMORSUZAKU && equipmentSlotIn == EntityEquipmentSlot.HEAD){
//        	// 反撃:火炎 ダメージ減少:ネザーのエンチャントを追加　ダメージ増加:水生生物
//        }else if (armorMaterial == Mod_FantomBlade.AROMORGENBU && equipmentSlotIn == EntityEquipmentSlot.HEAD){
//        	// 水生生物ダメージ半減  ダメージ増加：ネザー生物 水中採掘　水中歩行
//        }else if (armorMaterial == Mod_FantomBlade.AROMORGENBU && equipmentSlotIn == EntityEquipmentSlot.HEAD){
//        	// 水生生物ダメージ半減  ダメージ増加：ネザー生物
//        }else if (armorMaterial == Mod_FantomBlade.AROMORKIRIN && equipmentSlotIn == EntityEquipmentSlot.HEAD){
//        	// プロテクション 爆発耐性 落下耐性
//        }
    }

    /**
     * Return whether this item is repairable in an anvil.
     *
     * @param toRepair the {@code ItemStack} being repaired
     * @param repair the {@code ItemStack} being used to perform the repair
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }


	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int indexOfMainSlot, boolean isCurrent) {
		super.onUpdate(stack, world, entity, indexOfMainSlot, isCurrent);
		if (this.getEquipmentSlot() != EntityEquipmentSlot.HEAD){
			// 頭防具で判定をする
			return;
		}
		if (entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)entity;
			Iterable<ItemStack> equip = player.getArmorInventoryList();
			int count = 0;
			if (this.getArmorMaterial() == Mod_FantomBlade.AROMORSEIRYU){
				for(ItemStack st : equip){
					if (st.getItem() == ItemCore.item_seiryuhelmet ||
							st.getItem() == ItemCore.item_seiryubody ||
							st.getItem() == ItemCore.item_seiryulegs ||
							st.getItem() == ItemCore.item_seiryuboots){
						count++;
					}
				}
				if (count == 4){
					// セットボーナス起動
					// 毒、盲目、吐き気、空腹、ウィザーのポーション効果を解除
					for(PotionEffect effect : player.getActivePotionEffects()){
						if (effect.getPotion() == MobEffects.POISON ||
								effect.getPotion() == MobEffects.NAUSEA ||
								effect.getPotion() == MobEffects.HUNGER ||
								effect.getPotion() == MobEffects.BLINDNESS ||
								effect.getPotion() == MobEffects.WITHER ||
								effect.getPotion() == MobEffects.MINING_FATIGUE){
							player.removeActivePotionEffect(effect.getPotion());
						}
					}

				}
			}else if  (this.getArmorMaterial() == Mod_FantomBlade.AROMORBYAKO){
				for(ItemStack st : equip){
					if (st.getItem() == ItemCore.item_byakohelmet ||
							st.getItem() == ItemCore.item_byakobody ||
							st.getItem() == ItemCore.item_byakolegs ||
							st.getItem() == ItemCore.item_byakoboots){
						count++;
					}
				}
				if (count == 4){
					// セットボーナス起動
					// 鈍足無効、浮遊無効
					for(PotionEffect effect : player.getActivePotionEffects()){
						if (effect.getPotion() == MobEffects.SLOWNESS ||
								effect.getPotion() == MobEffects.LEVITATION ||
								effect.getPotion() == MobEffects.HUNGER ||
								effect.getPotion() == MobEffects.BLINDNESS ||
								effect.getPotion() == MobEffects.WITHER ||
								effect.getPotion() == MobEffects.MINING_FATIGUE){
							player.removeActivePotionEffect(effect.getPotion());
						}
					}
					// ジャンプ力3倍＋移動速度3倍
					player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST,20,24));
					player.addPotionEffect(new PotionEffect(MobEffects.SPEED,20,15));
				}
			}else if  (this.getArmorMaterial() == Mod_FantomBlade.AROMORSUZAKU){
				for(ItemStack st : equip){
					if (st.getItem() == ItemCore.item_suzakuhelmet ||
							st.getItem() == ItemCore.item_suzakubody ||
							st.getItem() == ItemCore.item_suzakulegs ||
							st.getItem() == ItemCore.item_suzakuboots){
						count++;
					}
				}
				if (count == 4){
					// セットボーナス起動
					//　火炎体制
					player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE,20,100));
				}
			}else if  (this.getArmorMaterial() == Mod_FantomBlade.AROMORGENBU){
				for(ItemStack st : equip){
					if (st.getItem() == ItemCore.item_genbuhelmet ||
							st.getItem() == ItemCore.item_genbubody ||
							st.getItem() == ItemCore.item_genbulegs||
							st.getItem() == ItemCore.item_genbuboots){
						count++;
					}
				}
				if (count == 4){
					// セットボーナス起動
					// 水中呼吸
					player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING,20,24));
				}
			}else if  (this.getArmorMaterial() == Mod_FantomBlade.AROMORKIRIN){
				for(ItemStack st : equip){
					if (st.getItem() == ItemCore.item_kirinhelmet ||
							st.getItem() == ItemCore.item_kirinbody ||
							st.getItem() == ItemCore.item_kirinlegs ||
							st.getItem() == ItemCore.item_kirinboots){
						count++;
					}
				}
				if (count == 4){
					// セットボーナス起動
					// リジェネレーション　耐性
					player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,20,20));
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,20,2));
				}
			}else if  (this.getArmorMaterial() == Mod_FantomBlade.ARMORNIJI){
				for(ItemStack st : equip){
					if (st.getItem() == ItemCore.item_nijihelmet ||
							st.getItem() == ItemCore.item_nijibody ||
							st.getItem() == ItemCore.item_nijilegs ||
							st.getItem() == ItemCore.item_nijiboots){
						count++;
					}
				}
				if (count == 4){
					// セットボーナス起動
					player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,20,31));
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,20,4));
					player.addPotionEffect(new PotionEffect(MobEffects.LUCK,20,31));
				}
			}
		}
	}

//    /**
//     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
//     */
//    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
//    {
//        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
//
//        if (equipmentSlot == this.armorType)
//        {
//            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", (double)this.damageReduceAmount, 0));
//            multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor toughness", (double)this.toughness, 0));
//        }
//
//        return multimap;
//    }
}
