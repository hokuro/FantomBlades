package mod.fbd.item.katana;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.resource.TextureInfo;
import mod.fbd.resource.TextureUtil;
import mod.fbd.util.ModUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractItemKatana  extends SwordItem {
	protected float attackDamage;
	protected double attackSpeed;
    protected boolean isEnchantable = false;
    protected int LEVELUP_EXP;
    protected int POTION_UP;
    protected int ENCHANT_UP;
    protected final static List<EffectInstance> nonEffect = new ArrayList<EffectInstance>();

	public AbstractItemKatana(Item.Properties property){
		this(Mod_FantomBlade.HAGANE,property);
	}

	public AbstractItemKatana(IItemTier tier, Item.Properties property){
		super(tier, (int)tier.getAttackDamage(), -2.4F, property);
		LEVELUP_EXP = 500;
		POTION_UP = -1;
		ENCHANT_UP = -1;
	}

	@Override
    public float getAttackDamage() {
        return attackDamage;
    }

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int indexOfMainSlot, boolean isCurrent) {
		if (entity instanceof LivingEntity) {
			if (((LivingEntity)entity).getHeldItemMainhand().getItem() == this) {
				// i攻撃力設定
				attackDamage = calcurateAttackDamage(stack,world,entity);

				// i攻撃速度設定
				attackSpeed = calcureteAttackSpeed(stack,world,entity);

				ModUtil.setPrivateValue(Item.class, this, this.getEndurance(stack), "maxDamage");
				if (entity instanceof PlayerEntity){
					updateAttackAmplifier(stack,(PlayerEntity)entity);
				}
			}
		}
	}

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		int level = this.getLevel(stack);
		// i相手を殺したら経験値獲得
		if(!target.isAlive() && target.deathTime == 0 && attacker instanceof PlayerEntity){
			// i相手の最大HPが経験値
			this.addExp(stack, (int)target.getMaxHealth());
			if (level != this.getLevel(stack)){
				((PlayerEntity)attacker).sendStatusMessage(new StringTextComponent("Katana Level Up "+level+"->"+this.getLevel(stack)),false);
			}
		}else if (target.isAlive()){
			// i生きてるならポーション効果をお見舞い
			for (EffectInstance effect : this.getPotionEffects(stack)){
				target.addPotionEffect(new EffectInstance(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
			}
		}
		stack.damageItem(1, attacker, (living) -> {
			living.sendBreakAnimation(EquipmentSlotType.MAINHAND);
         });

		return true;
    }


	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			items.add(getDefaultStack(this));
		}
	}

	@Override
	public void setDamage(ItemStack stack, int damage){
		if (damage >= this.getMaxDamage()){
			this.addRustValue(stack, 1);
			int rust = this.getRustValue(stack);
			if ((ModUtil.random(100) < (20+(rust/1024*0.6)))){
				damage = this.getMaxDamage()-1;
			}
		}
		super.setDamage(stack, damage);
	}

    @Override
    public int getItemEnchantability(){
        return 0;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair){
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return isEnchantable;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EquipmentSlotType.MAINHAND){
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    	PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }

	public int getLevelUpExp(){
		return LEVELUP_EXP;
	}
	public int getPotionEffectUpExp(){
		return POTION_UP;
	}
	public int getEnchantLevelUpExp(){
		return ENCHANT_UP;
	}

    public AbstractItemKatana setEnchantable(boolean value){
    	isEnchantable = value;
    	return this;
    }

    public static int getLevel(ItemStack stack){
        CompoundNBT tag = getItemTagCompound(stack);
        if (tag.contains("BladeLevel")){
        	return tag.getInt("BladeLevel");
        }
        return 1;
    }

    public static void setLevel(ItemStack stack,int value){
    	if (value > 255){value = 255;}
    	CompoundNBT tag = getItemTagCompound(stack);
    	tag.putFloat("BladeLevel", value);
    }

    public static float getAttackDamage(ItemStack stack){
        CompoundNBT tag = getItemTagCompound(stack);
        if (tag.contains("AttackDamage")){
        	return tag.getFloat("AttackDamage");
        }
        return 0.0F;
    }

    public static void setAttackDamage(ItemStack stack, float damager){
    	CompoundNBT tag = getItemTagCompound(stack);
    	tag.putFloat("AttackDamage", damager);
    }

    public static double getAttackSpeed(ItemStack stack){
        CompoundNBT tag = getItemTagCompound(stack);
        if (tag.contains("AttackSpeed")){
        	return tag.getDouble("AttackSpeed");
        }
        return -3.0F;
    }

    public static void setAttackSpeed(ItemStack stack, double value){
    	CompoundNBT tag = getItemTagCompound(stack);
    	tag.putDouble("AttackSpeed", MathHelper.clamp(value, -3.0, 0));
    }

    public static int getEndurance(ItemStack stack){
        CompoundNBT tag = getItemTagCompound(stack);
        if (tag.contains("MaxEndurance")){
        	return tag.getInt("MaxEndurance");
        }
        return Mod_FantomBlade.HAGANE.getMaxUses();
    }

    public static void setEndurance(ItemStack stack, int value){
    	CompoundNBT tag = getItemTagCompound(stack);
    	tag.putInt("MaxEndurance", value);
    }

    public static int getRustValue(ItemStack stack){
        CompoundNBT tag = getItemTagCompound(stack);
        if (tag.contains("RustValue")){
        	return tag.getInt("RustValue");
        }
        return Mod_FantomBlade.HAGANE.getMaxUses();
    }

    public static void setRustValue(ItemStack stack, int value){
    	if (value < 0){value =0;}
    	else if (value > 1024){value = 1024;}
    	CompoundNBT tag = getItemTagCompound(stack);
    	tag.putInt("RustValue", value);
    }

    public static void addRustValue(ItemStack stack, int add){
    	setRustValue(stack,getRustValue(stack)+add);
    }

    public static int getMaxLevel(ItemStack stack){
        CompoundNBT tag = getItemTagCompound(stack);
        if (tag.contains("MaxLevel")){
        	return tag.getInt("MaxLevel");
        }
        return 1;
    }

    public static void setMaxLevel(ItemStack stack, int value){
    	CompoundNBT tag = getItemTagCompound(stack);
    	tag.putInt("MaxLevel", value);
    }

    public static int getExp(ItemStack stack){
        CompoundNBT tag = getItemTagCompound(stack);
        if (tag.contains("Exp")){
        	return tag.getInt("Exp");
        }
        return 0;
    }

    public static void setExp(ItemStack stack, int value){
    	AbstractItemKatana katana = ((AbstractItemKatana)stack.getItem());
    	if (value < 0){value = 0;}
    	else if (katana.getLevelUpExp() * 255 < value){value = katana.getLevelUpExp() *255;}
    	CompoundNBT tag = getItemTagCompound(stack);
    	tag.putInt("Exp", value);
    }

    public static void addExp(ItemStack stack, int add){
    	setExp(stack,getExp(stack) + add);
    }

    public static List<EffectInstance> getPotionEffects(ItemStack stack){
    	return PotionUtils.getEffectsFromStack(stack);
    }

    public static void setPotionEffects(ItemStack stack, List<EffectInstance> value){
    	PotionUtils.appendEffects(stack, value);
    }



    public static TextureInfo getModelTexture(ItemStack par1ItemStack){
        CompoundNBT tag = getItemTagCompound(par1ItemStack);
        String file = tag.getString("TextureFile");
        TextureInfo info;
        return TextureUtil.TextureManager().getTexture(((AbstractItemKatana)par1ItemStack.getItem()).getKatanaName(), file);
    }

    public static void setModelTexture(ItemStack par1ItemStack, TextureInfo info){
    	CompoundNBT tag = getItemTagCompound(par1ItemStack);
    	tag.putString("TextureFile", info.FileName());

    }
    public static void setRandomModelTexture(ItemStack par1ItemStack){
    	TextureInfo info = TextureUtil.TextureManager().getRandomTexture(((AbstractItemKatana)par1ItemStack.getItem()).getKatanaName());
    	CompoundNBT tag = getItemTagCompound(par1ItemStack);
    	tag.putString("TextureFile", info.FileName());
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

    protected void updateAttackAmplifier(@Nonnull ItemStack itemStack, @Nonnull PlayerEntity player) {
        CompoundNBT CompoundNBT = getItemTagCompound(itemStack);
        ListNBT nbtTagList = new ListNBT();
        nbtTagList.add(
                getAttrTag(
                        SharedMonsterAttributes.ATTACK_DAMAGE.getName()
                        , new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION)
                        , EquipmentSlotType.MAINHAND)
        );
        nbtTagList.add(
                getAttrTag(SharedMonsterAttributes.ATTACK_SPEED.getName()
                        , new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier",attackSpeed, AttributeModifier.Operation.ADDITION)
                        , EquipmentSlotType.MAINHAND)
        );
        CompoundNBT.put("AttributeModifiers",nbtTagList);
        player.getAttributes().removeAttributeModifiers(itemStack.getAttributeModifiers(EquipmentSlotType.MAINHAND));
        player.getAttributes().applyAttributeModifiers(itemStack.getAttributeModifiers(EquipmentSlotType.MAINHAND));
    }

	protected float calcurateAttackDamage(ItemStack stack, World world, Entity entity) {
		int level = this.getLevel(stack);
		int rust = this.getRustValue(stack);
		double rust_h = (Math.exp(-1.0D*(rust/1024.0D))*(1.0D-Math.exp(-0.2D))) * (rust==0?0:1);			// i錆補正

		// i攻撃力設定
		float damage = this.getAttackDamage(stack) 																// i固有攻撃力
				+ MathHelper.floor(this.getAttackDamage(stack)*(1-Math.exp((-1*level/100)/(1-Math.exp(-1)))))   // iレベル補正
				+ Mod_FantomBlade.HAGANE.getAttackDamage();                                                                            // 素材補正
		if (rust_h!= 0){
			damage = (float) (damage * MathHelper.clamp(rust_h,0.4,1.0));                           // i錆補正 最低0.4倍
		}
		return damage;
	}

	protected double calcureteAttackSpeed(ItemStack stack, World world, Entity entity) {
		int rust = this.getRustValue(stack);
		double rust_h = (Math.exp(-1.0D*(rust/1024.0D))*(1.0D-Math.exp(-0.2D))) * (rust==0?0:1);			// i錆補正
		// i攻撃速度設定
		double Speed = this.getAttackSpeed(stack) + 1 - rust_h;
		return Speed;
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

    public static List<EffectInstance> updatePotionList(ItemStack stack){
    	List<EffectInstance> ret = new ArrayList<EffectInstance>();
    	CompoundNBT tag = getItemTagCompound(stack);
    	ListNBT nbttaglist = tag.getList("updatePotion", 10);
        for (int i = 0; i < nbttaglist.size(); ++i)
        {
			CompoundNBT CompoundNBT = nbttaglist.getCompound(i);
			int id = CompoundNBT.getInt("id");
			int d = CompoundNBT.getInt("dulation");
			int a = CompoundNBT.getInt("amplefi");
			ret.add(new EffectInstance(Registry.EFFECTS.getByValue(id),d,a));
        }
        return ret;
    }

    public static void setUpdatePotionList(ItemStack stack, List<EffectInstance> potions){
    	CompoundNBT tag = getItemTagCompound(stack);
    	ListNBT nbttaglist = new ListNBT();
        for (int i = 0; i < potions.size(); ++i)
        {
			CompoundNBT CompoundNBT = new CompoundNBT();

			CompoundNBT.putInt("id", Registry.EFFECTS.getId(potions.get(i).getPotion()));
			CompoundNBT.putInt("dulation",potions.get(i).getDuration());
			CompoundNBT.putInt("amplefi",potions.get(i).getAmplifier());
			nbttaglist.add(CompoundNBT);

        }
        tag.put("updatePotion", nbttaglist);
    }

	 protected abstract String getKatanaName();
	 public abstract ResourceLocation getBladeTexture();
	 public abstract Enchantment[] ignoreEnchantments();

	 protected abstract ItemStack getDefault();
	 public static ItemStack getDefaultStack(AbstractItemKatana katana) {
		 return katana.getDefault();
	 }
}
