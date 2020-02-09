package mod.fbd.item.armor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import mod.fbd.item.ItemCore;
import mod.fbd.item.piece.ItemBladePiece.EnumBladePieceType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemHaganeAromor extends ArmorItem {
	private EnumBladePieceType type;
	private double speed = 1;
	private double reduce = 1;

    public ItemHaganeAromor(IArmorMaterial armorMaterial, EnumBladePieceType armorType, EquipmentSlotType equipmentSlotIn, Item.Properties property) {
        super(armorMaterial, equipmentSlotIn, property);
        type = armorType;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }


	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int indexOfMainSlot, boolean isCurrent) {
		super.inventoryTick(stack, world, entity, indexOfMainSlot, isCurrent);
		if (entity instanceof LivingEntity) {
			LivingEntity living = (LivingEntity)entity;



			ItemStack helmet = living.getItemStackFromSlot(EquipmentSlotType.HEAD);
			ItemStack chest = living.getItemStackFromSlot(EquipmentSlotType.CHEST);
			ItemStack legs = living.getItemStackFromSlot(EquipmentSlotType.LEGS);
			ItemStack feet = living.getItemStackFromSlot(EquipmentSlotType.FEET);
			if (stack == helmet || stack == chest || stack == legs || stack == feet) {
				if (countup(stack)) {
					BlockPos pos = living.getPosition().offset(Direction.DOWN);
					BlockState state = world.getBlockState(pos);
					if (type == EnumBladePieceType.BYAKO) {
						// i金属ブロックの上にいると耐久力回復
						if (state.getMaterial() == Material.IRON || state.getBlock().getRegistryName().getPath().contains("_ore")) {
							stack.damageItem(-1, living, (ent)->{ent.sendBreakAnimation(((ArmorItem)stack.getItem()).getEquipmentSlot());});
						}
						// i土ブロックの上にいると体力回復
						if ( ((ArmorItem)stack.getItem()).getEquipmentSlot() == EquipmentSlotType.HEAD &&
								(helmet.getItem() == ItemCore.item_byakohelmet && chest.getItem() == ItemCore.item_byakobody && legs.getItem() == ItemCore.item_byakolegs && feet.getItem() == ItemCore.item_byakoboots)) {
							if (state.getMaterial() == Material.EARTH || state.getBlock() == Blocks.GRASS_BLOCK) {
								living.heal(0.5F);
								// i満腹度も回復
								if (living instanceof PlayerEntity) {
									((PlayerEntity)living).getFoodStats().addStats(2, 1.0F);
								}
							}
						}
						// i溶岩、炎上中に耐久力減少
						if (living.isInLava() || living.isBurning()) {
							stack.damageItem(1, living, (ent)->{ent.sendBreakAnimation(((ArmorItem)stack.getItem()).getEquipmentSlot());});
						}
					}else if (type == EnumBladePieceType.GENBU) {
						// i水中にいる間耐久力回復
						if (living.isInWater()) {
							stack.damageItem(-1, living, (ent)->{ent.sendBreakAnimation(((ArmorItem)stack.getItem()).getEquipmentSlot());});
						}

						if ( ((ArmorItem)stack.getItem()).getEquipmentSlot() == EquipmentSlotType.HEAD &&
								(helmet.getItem() == ItemCore.item_genbuhelmet && chest.getItem() == ItemCore.item_genbubody && legs.getItem() == ItemCore.item_genbulegs && feet.getItem() == ItemCore.item_genbuboots)) {
							// i金属ブロックの上にいる間体力回復
							if (state.getMaterial() == Material.IRON || state.getBlock().getRegistryName().getPath().contains("_ore")) {
								living.heal(0.5F);
								// i満腹度も回復
								if (living instanceof PlayerEntity) {
									((PlayerEntity)living).getFoodStats().addStats(1, 0.5F);
								}
							}
						}

						// i土ブロックの上にいる間耐久力減少
						if (state.getMaterial() == Material.EARTH && !living.isInWater()) {
							stack.damageItem(1, living, (ent)->{ent.sendBreakAnimation(((ArmorItem)stack.getItem()).getEquipmentSlot());});
						}
					}else if (type == EnumBladePieceType.SEIRYU) {
						// i木の上にいる間耐久力回復
						if (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.ORGANIC) {
							stack.damageItem(-1, living, (ent)->{ent.sendBreakAnimation(((ArmorItem)stack.getItem()).getEquipmentSlot());});
						}

						if ( ((ArmorItem)stack.getItem()).getEquipmentSlot() == EquipmentSlotType.HEAD &&
								(helmet.getItem() == ItemCore.item_seiryuhelmet && chest.getItem() == ItemCore.item_seiryubody && legs.getItem() == ItemCore.item_seiryulegs && feet.getItem() == ItemCore.item_seiryuboots)) {
							// i水中にいる間体力回復
							if (living.isInWater()) {
								living.heal(0.5F);
								// i満腹度も回復
								if (living instanceof PlayerEntity) {
									((PlayerEntity)living).getFoodStats().addStats(1, 0.5F);
								}
							}
						}

						// i金属ブロックの上にいる間大尉級力減少
						if (state.getMaterial() == Material.IRON || state.getBlock().getRegistryName().getPath().contains("_ore")) {
							stack.damageItem(1, living, (ent)->{ent.sendBreakAnimation(((ArmorItem)stack.getItem()).getEquipmentSlot());});
						}
					}else if (type == EnumBladePieceType.SUZAKU) {
						// i溶岩の中にいる、炎上中に耐久力回復
						if (living.isInLava() || living.isBurning()) {
							stack.damageItem(-1, living, (ent)->{ent.sendBreakAnimation(((ArmorItem)stack.getItem()).getEquipmentSlot());});
						}

						if ( ((ArmorItem)stack.getItem()).getEquipmentSlot() == EquipmentSlotType.HEAD &&
								(helmet.getItem() == ItemCore.item_suzakuhelmet && chest.getItem() == ItemCore.item_suzakubody && legs.getItem() == ItemCore.item_suzakulegs && feet.getItem() == ItemCore.item_suzakuboots)) {
							// i木の上にいる間体力回復
							if (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.ORGANIC) {
								living.heal(0.5F);
								// i満腹度も回復
								if (living instanceof PlayerEntity) {
									((PlayerEntity)living).getFoodStats().addStats(1, 0.5F);
								}
							}
						}

						// i水中にいる間耐久力減少
						if (living.isInWater()) {
							stack.damageItem(1, living, (ent)->{ent.sendBreakAnimation(((ArmorItem)stack.getItem()).getEquipmentSlot());});
						}
					}else if (type == EnumBladePieceType.KIRIN) {
						// i土ブロックの上にいる間耐久力回復
						if (state.getMaterial() == Material.EARTH || state.getBlock() == Blocks.GRASS_BLOCK) {
							stack.damageItem(-1, living, (ent)->{ent.sendBreakAnimation(((ArmorItem)stack.getItem()).getEquipmentSlot());});
						}

						if ( ((ArmorItem)stack.getItem()).getEquipmentSlot() == EquipmentSlotType.HEAD &&
								(helmet.getItem() == ItemCore.item_kirinhelmet && chest.getItem() == ItemCore.item_kirinbody && legs.getItem() == ItemCore.item_kirinlegs && feet.getItem() == ItemCore.item_kirinboots)) {
							// i溶岩の中、炎上中体力回復
							if (living.isInLava() || living.isBurning()) {
								living.heal(0.5F);
								// i満腹度も回復
								if (living instanceof PlayerEntity) {
									((PlayerEntity)living).getFoodStats().addStats(1, 0.5F);
								}
							}
						}

						// i木ブロックの上にいる間耐久力減少
						if (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.ORGANIC) {
							stack.damageItem(1, living, (ent)->{ent.sendBreakAnimation(((ArmorItem)stack.getItem()).getEquipmentSlot());});
						}
					}else if (type == EnumBladePieceType.NIJI) {
						if (((ArmorItem)stack.getItem()).getEquipmentSlot() == EquipmentSlotType.HEAD) {
							// i全てにじ装備かどうか確認
							if (helmet.getItem() == ItemCore.item_nijihelmet &&
								chest.getItem() == ItemCore.item_nijibody &&
								legs.getItem() == ItemCore.item_nijilegs &&
								feet.getItem() == ItemCore.item_nijiboots) {
								// i害悪ポーション効果を除去する
								List<Effect> effectlist = new ArrayList<Effect>();
								living.getActivePotionEffects().forEach((effect)->{
									if (effect.getPotion().getEffectType() == EffectType.HARMFUL) {
										effectlist.add(effect.getPotion());
									}
	 							});
								for(Effect potion : effectlist) {
									living.removePotionEffect(potion);
								}
							}
						}
					}
				}
			}

			if (living.getItemStackFromSlot(this.slot).getItem() == this) {
				// iポーション効果付きならポーション効果を付与
				ItemHaganeAromor.getPotionEffects(stack).forEach((effect)->{
					living.addPotionEffect(new EffectInstance(effect.getPotion(), effect.getDuration(), effect.getAmplifier()));
				});
				reduce = ItemHaganeAromor.getHardness(stack);
				speed = ItemHaganeAromor.getWeight(stack);
				// i性能変更
				if (entity instanceof PlayerEntity){
					updateAttackAmplifier(stack,(PlayerEntity)entity);
				}
			}
		}
	}

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    	PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }

	private static final UUID[] ARMOR_MODIFIERS = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
    	Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
        if (equipmentSlot == this.slot) {
        	multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(UUID.fromString("40ce64b3-52c8-4d77-b2b8-b012bc6de1dd"), "weight modifier", speed,  AttributeModifier.Operation.MULTIPLY_TOTAL));
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", (double)this.damageReduceAmount * reduce, AttributeModifier.Operation.ADDITION));
            multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor toughness", (double)this.toughness, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    protected void updateAttackAmplifier(@Nonnull ItemStack itemStack, @Nonnull PlayerEntity player) {
        CompoundNBT CompoundNBT = getItemTagCompound(itemStack);
        ListNBT nbtTagList = new ListNBT();
        nbtTagList.add(
                getAttrTag(
                		SharedMonsterAttributes.ARMOR.getName()
                        , new AttributeModifier(ARMOR_MODIFIERS[this.getEquipmentSlot().getIndex()], "Armor modifier", (double)this.damageReduceAmount * reduce,  AttributeModifier.Operation.ADDITION)
                        , this.getEquipmentSlot())
        );

        nbtTagList.add(
                getAttrTag(SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
                		new AttributeModifier(UUID.fromString("40ce64b3-52c8-4d77-b2b8-b012bc6de1dd"), "weight modifier",  speed,  AttributeModifier.Operation.MULTIPLY_TOTAL),
                        EquipmentSlotType.MAINHAND)
        );
        CompoundNBT.put("AttributeModifiers",nbtTagList);

        player.getAttributes().removeAttributeModifiers(itemStack.getAttributeModifiers(EquipmentSlotType.MAINHAND));
        player.getAttributes().applyAttributeModifiers(itemStack.getAttributeModifiers(EquipmentSlotType.MAINHAND));
    }

    public CompoundNBT getAttrTag(String attrName , AttributeModifier par0AttributeModifier, EquipmentSlotType slot) {
        CompoundNBT CompoundNBT = new CompoundNBT();
        CompoundNBT.putString("AttributeName",attrName);
        CompoundNBT.putString("Name", par0AttributeModifier.getName());
        CompoundNBT.putDouble("Amount", par0AttributeModifier.getAmount());
        CompoundNBT.putInt("Operation", par0AttributeModifier.getOperation().getId());
        CompoundNBT.putUniqueId("UUID",par0AttributeModifier.getID());
        CompoundNBT.putString("Slot", slot.getName());
        return CompoundNBT;
    }

	@Override
	public void setDamage(ItemStack stack, int damage) {
		// iにじは壊れない
		if (type != EnumBladePieceType.NIJI) {
			super.setDamage(stack,damage);
		}
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
    	double dmg = getEndurance(stack);
    	return (int)Math.round(this.getMaxDamage() * dmg>0?dmg:1);
    }

	public EnumBladePieceType getPieceType() {
		return type;
	}

	public boolean countup(ItemStack stack) {
		boolean ret = false;
		CompoundNBT compouond = stack.getOrCreateTag();
		int tick = compouond.getInt("tickCount");
		if (tick <= 0) {
			ret = true;
			tick = 20;
		}else {
			tick--;
		}
		compouond.putInt("tickCount", tick);
		stack.setTag(compouond);
		return ret;
	}

    public static double getHardness(ItemStack stack){
        CompoundNBT tag = getItemTagCompound(stack);
        if (tag.contains("hardness")){
        	return tag.getDouble("hardness");
        }
        return 0;
    }

    public static void setHardness(ItemStack stack, double value){
    	CompoundNBT tag = getItemTagCompound(stack);
    	tag.putDouble("hardness", value);
    }

    public static double getWeight(ItemStack stack){
        CompoundNBT tag = getItemTagCompound(stack);
        if (tag.contains("weight")){
        	return tag.getDouble("weight");
        }
        return 0;
    }

    public static void setWeight(ItemStack stack, double value){
    	CompoundNBT tag = getItemTagCompound(stack);
    	tag.putDouble("weight", value);
    }

    public static double getEndurance(ItemStack stack){
        CompoundNBT tag = getItemTagCompound(stack);
        if (tag.contains("MaxEndurance")){
        	return tag.getDouble("MaxEndurance");
        }
        return 0;
    }

    public static void setEndurance(ItemStack stack, double value){
    	CompoundNBT tag = getItemTagCompound(stack);
    	tag.putDouble("MaxEndurance", value);
    }

    public static List<EffectInstance> getPotionEffects(ItemStack stack){
    	return PotionUtils.getEffectsFromStack(stack);
    }

    public static void setPotionEffects(ItemStack stack, List<EffectInstance> value){
    	PotionUtils.appendEffects(stack, value);
    }

    public static CompoundNBT getItemTagCompound(ItemStack stack){
		CompoundNBT tag;
		if(stack.hasTag()){
			tag = stack.getTag();
		}else{
			tag = new CompoundNBT();
			stack.setTag(tag);
		}
		return tag;
	}

    @Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			ItemStack stack = new ItemStack(this);
			//ItemHaganeAromor.setWeight(stack, -0.5);
			ItemHaganeAromor.setHardness(stack, 1.5);
			items.add(stack);
		}
	}

}