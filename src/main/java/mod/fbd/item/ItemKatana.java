package mod.fbd.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.resource.TextureInfo;
import mod.fbd.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemKatana extends ItemSword {
	public final static String NAME = "normalblade";
	protected float attackDamage;
	protected double attackSpeed;

	public final ResourceLocation DEFAULT_KATANATEXTURE;
	public ItemKatana(){
		super(ToolMaterial.IRON);
		DEFAULT_KATANATEXTURE = new ResourceLocation("fbd","textures/entity/normalblade_default.png");
		this.setMaxStackSize(1);
		LEVELUP_EXP = 500;
		POTION_UP = -1;
		ENCHANT_UP = -1;
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

	@Override
    public float getAttackDamage()
    {
        return attackDamage;
    }

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int indexOfMainSlot, boolean isCurrent) {

		int level = this.getLevel(stack);
		int rust = this.getRustValue(stack);
		double rust_h = (Math.exp(-1.0D*(rust/1024.0D))*(1.0D-Math.exp(-0.2D))) * (rust==0?0:1);			// 錆補正

		// 攻撃力設定
		attackDamage = this.getAttackDamage(stack) 																// 固有攻撃力
				+ MathHelper.floor(this.getAttackDamage(stack)*(1-Math.exp((-1*level/100)/(1-Math.exp(-1)))))   // レベル補正
				+ ToolMaterial.IRON.getAttackDamage();                                                          // 素材補正
		if (rust_h!= 0){
			attackDamage = (float) (attackDamage * MathHelper.clamp(rust_h,0.4,1.0)); // 錆補正 最低0.4倍
		}

		// 攻撃速度設定
		attackSpeed = this.getAttackSpeed(stack) + 1 - rust_h;

		// 耐久力設定
		this.setMaxDamage(this.getGetEndurance(stack));
		if (entity instanceof EntityPlayer){
			updateAttackAmplifier(stack,(EntityPlayer)entity);
		}


	}

	@Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Block block = state.getBlock();

        if (block == Blocks.WEB)
        {
            return 15.0F;
        }
        else
        {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

	 @Override
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		 ItemStack stack = playerIn.getHeldItemMainhand();
		 if (stack.getItem() == this){
//			 // ポーション効果を持っているなら自分にポーション効果を発動
//			 for(PotionEffect effect : this.getPotionEffects(stack)){
//				 playerIn.addPotionEffect(new PotionEffect(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
//			 }
			 return new ActionResult(EnumActionResult.SUCCESS, stack);
		 }
		 return new ActionResult(EnumActionResult.FAIL, stack);
	 }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		int level = this.getLevel(stack);
		// 相手を殺したら経験値獲得
		if(!target.isEntityAlive() && target.deathTime == 0 && attacker instanceof EntityPlayer){
			// 相手の最大HPが経験値
			this.addExp(stack, (int)target.getMaxHealth());
			if (level != this.getLevel(stack)){
				((EntityPlayer)attacker).sendStatusMessage(new TextComponentString("Katana Level Up "+level+"->"+this.getLevel(stack)),false);
			}
		}else if (target.isEntityAlive()){
			// 生きてるならポーション効果をお見舞い
			for (PotionEffect effect : this.getPotionEffects(stack)){
				target.addPotionEffect(new PotionEffect(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
			}
		}
		stack.damageItem(1, attacker);

		return true;
    }

    public void setDamage(ItemStack stack, int damage)
    {
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
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(2, entityLiving);
        }
        return true;
    }


    @Override
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        return blockIn.getBlock() == Blocks.WEB;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    @Override
    public int getItemEnchantability()
    {
        return 0;
    }


    @Override
    public String getToolMaterialName()
    {
        return "tamahagane";
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return isEnchantable;
    }

    protected boolean isEnchantable = false;
    public ItemKatana setEnchantable(boolean value){
    	isEnchantable = value;
    	return this;
    }


    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
       // Mod_FantomBlade.proxy.getEntityPlayerInstance().inventory.getStackInSlot(equipmentSlot.getIndex());

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeed, 0));
        }

        return multimap;
    }

	 @Override
	 public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	 {
		    if (this.isInCreativeTab(tab))
		    {
		        items.add(getDefaultStack());
		    }
	 }

	 public static ItemStack getDefaultStack(){
		 ItemStack ret = new ItemStack(ItemCore.item_katana);
		 ItemKatana.setMaxLevel(ret,255);
			ItemKatana.setExp(ret,0);
			ItemKatana.setRustValue(ret, 0);
			ItemKatana.setAttackDamage(ret,5.0F);
			ItemKatana.setAttackSpeed(ret,-2.0D);
			ItemKatana.setEndurance(ret, 1000);
			if (ModCommon.isDevelop){
				ItemKatana.setExp(ret, 10000);
			}
			ItemKatana.setRandomModelTexture(ret);
		 return ret;
	 }



    public int getLevel(ItemStack stack){
        NBTTagCompound tag = getItemTagCompound(stack);
        if (tag.hasKey("BladeLevel")){
        	return tag.getInteger("BladeLevel");
        }
        return 1;
    }

    public void setLevel(ItemStack stack,int value){
    	if (value > 255){value = 255;}
    	NBTTagCompound tag = getItemTagCompound(stack);
    	tag.setFloat("BladeLevel", value);
    }

    public static float getAttackDamage(ItemStack stack){
        NBTTagCompound tag = getItemTagCompound(stack);
        if (tag.hasKey("AttackDamage")){
        	return tag.getFloat("AttackDamage");
        }
        return ToolMaterial.IRON.getAttackDamage();
    }

    public static void setAttackDamage(ItemStack stack, float damager){
    	NBTTagCompound tag = getItemTagCompound(stack);
    	tag.setFloat("AttackDamage", damager);
    }

    public static double getAttackSpeed(ItemStack stack){
        NBTTagCompound tag = getItemTagCompound(stack);
        if (tag.hasKey("AttackSpeed")){
        	return tag.getDouble("AttackSpeed");
        }
        return ToolMaterial.IRON.getAttackDamage();
    }

    public static void setAttackSpeed(ItemStack stack, double value){
    	NBTTagCompound tag = getItemTagCompound(stack);
    	tag.setDouble("AttackSpeed", MathHelper.clamp(value, -3.0, 0));
    }

    public static int getGetEndurance(ItemStack stack){
        NBTTagCompound tag = getItemTagCompound(stack);
        if (tag.hasKey("MaxEndurance")){
        	return tag.getInteger("MaxEndurance");
        }
        return ToolMaterial.IRON.getMaxUses();
    }

    public static void setEndurance(ItemStack stack, int value){
    	NBTTagCompound tag = getItemTagCompound(stack);
    	tag.setInteger("MaxEndurance", value);
    }

    public static int getRustValue(ItemStack stack){
        NBTTagCompound tag = getItemTagCompound(stack);
        if (tag.hasKey("RustValue")){
        	return tag.getInteger("RustValue");
        }
        return ToolMaterial.IRON.getMaxUses();
    }

    public static void setRustValue(ItemStack stack, int value){
    	if (value < 0){value =0;}
    	else if (value > 1024){value = 1024;}
    	NBTTagCompound tag = getItemTagCompound(stack);
    	tag.setInteger("RustValue", value);
    }

    public static void addRustValue(ItemStack stack, int add){
    	setRustValue(stack,getRustValue(stack)+add);
    }

    public static int getMaxLevel(ItemStack stack){
        NBTTagCompound tag = getItemTagCompound(stack);
        if (tag.hasKey("MaxLevel")){
        	return tag.getInteger("MaxLevel");
        }
        return 1;
    }

    public static void setMaxLevel(ItemStack stack, int value){
    	NBTTagCompound tag = getItemTagCompound(stack);
    	tag.setInteger("MaxLevel", value);
    }

    public static int getExp(ItemStack stack){
        NBTTagCompound tag = getItemTagCompound(stack);
        if (tag.hasKey("Exp")){
        	return tag.getInteger("Exp");
        }
        return 0;
    }

    protected int LEVELUP_EXP;
    protected int POTION_UP;
    protected int ENCHANT_UP;
    public static void setExp(ItemStack stack, int value){
    	ItemKatana katana = ((ItemKatana)stack.getItem());
    	if (value < 0){value = 0;}
    	else if (katana.getLevelUpExp() * 255 < value){value = katana.getLevelUpExp() *255;}
    	NBTTagCompound tag = getItemTagCompound(stack);
    	tag.setInteger("Exp", value);
    }

    public static void addExp(ItemStack stack, int add){
    	setExp(stack,getExp(stack) + add);
    }

    protected final static List<PotionEffect> nonEffect = new ArrayList<PotionEffect>();
    public static List<PotionEffect> getPotionEffects(ItemStack stack){
    	return PotionUtils.getEffectsFromStack(stack);
    }

    public static void setPotionEffects(ItemStack stack, List<PotionEffect> value){
    	PotionUtils.appendEffects(stack, value);
    }

	public ResourceLocation getBladeTexture() {
		return DEFAULT_KATANATEXTURE;
	}

//	public ResourceLocation getModelTexture(){
//		return null;
//	}

    public static TextureInfo getModelTexture(ItemStack par1ItemStack){
        NBTTagCompound tag = getItemTagCompound(par1ItemStack);

        String file = tag.getString("TextureFile");
        TextureInfo info;
        return Mod_FantomBlade.instance.TextureManager().getTexture(NAME, file);
    }

    public static void setModelTexture(ItemStack par1ItemStack, TextureInfo info){
    	NBTTagCompound tag = getItemTagCompound(par1ItemStack);
    	tag.setString("TextureFile", info.FileName());

    }
    public static void setRandomModelTexture(ItemStack par1ItemStack){
    	TextureInfo info = Mod_FantomBlade.instance.TextureManager().getRandomTexture(NAME);
    	NBTTagCompound tag = getItemTagCompound(par1ItemStack);
    	tag.setString("TextureFile", info.FileName());

    }

    public static NBTTagCompound getItemTagCompound(ItemStack stack){
		NBTTagCompound tag;
		if(stack.hasTagCompound()){
			tag = stack.getTagCompound();
		}else{
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		return tag;
	}

    protected void updateAttackAmplifier(@Nonnull ItemStack itemStack, @Nonnull EntityPlayer player) {
        NBTTagList nbtTagList = new NBTTagList();
        NBTTagCompound nbtTagCompound = getItemTagCompound(itemStack);


        nbtTagList.appendTag(
                getAttrTag(
                        SharedMonsterAttributes.ATTACK_DAMAGE.getName()
                        , new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage, 0)
                        , EntityEquipmentSlot.MAINHAND)
        );
        nbtTagList.appendTag(
                getAttrTag(SharedMonsterAttributes.ATTACK_SPEED.getName()
                        , new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier",attackSpeed, 0)
                        , EntityEquipmentSlot.MAINHAND)
        );
        nbtTagCompound.setTag("AttributeModifiers",nbtTagList);

        player.getAttributeMap().removeAttributeModifiers(itemStack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND));
        player.getAttributeMap().applyAttributeModifiers(itemStack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND));
    }

    public NBTTagCompound getAttrTag(String attrName ,AttributeModifier par0AttributeModifier, EntityEquipmentSlot slot)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("AttributeName",attrName);
        nbttagcompound.setString("Name", par0AttributeModifier.getName());
        nbttagcompound.setDouble("Amount", par0AttributeModifier.getAmount());
        nbttagcompound.setInteger("Operation", par0AttributeModifier.getOperation());
        nbttagcompound.setUniqueId("UUID",par0AttributeModifier.getID());
        nbttagcompound.setString("Slot", slot.getName());
        return nbttagcompound;
    }

    public static List<PotionEffect> updatePotionList(ItemStack stack){
    	List<PotionEffect> ret = new ArrayList<PotionEffect>();
    	NBTTagCompound tag = getItemTagCompound(stack);
    	NBTTagList nbttaglist = tag.getTagList("updatePotion", 10);
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int id = nbttagcompound.getInteger("id");
			int d = nbttagcompound.getInteger("dulation");
			int a = nbttagcompound.getInteger("amplefi");
			ret.add(new PotionEffect(Potion.getPotionById(id),d,a));
        }
        return ret;
    }

    public static void setUpdatePotionList(ItemStack stack, List<PotionEffect> potions){
    	NBTTagCompound tag = getItemTagCompound(stack);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < potions.size(); ++i)
        {
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			nbttagcompound.setInteger("id",Potion.getIdFromPotion(potions.get(i).getPotion()));
			nbttagcompound.setInteger("dulation",potions.get(i).getDuration());
			nbttagcompound.setInteger("amplefi",potions.get(i).getAmplifier());
			nbttaglist.appendTag(nbttagcompound);

        }
        tag.setTag("updatePotion", nbttaglist);
    }

	 public Enchantment[] ignoreEnchantments(){
		 return	new Enchantment[]{};
	 }
}
