package mod.fbd.item;

import java.util.Map;
import java.util.Random;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.equipmenteffect.EnchantmentCore;
import mod.fbd.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

public class ItemKatanaKirin extends ItemKatana {

	public ItemKatanaKirin(){
		super();
		LEVELUP_EXP = 1000;
		POTION_UP = 200;
		ENCHANT_UP = 200;
	}


	@Override
	public int getLevelUpExp(){
		return LEVELUP_EXP;
	}
	@Override
	public int getPotionEffectUpExp(){
		return POTION_UP;
	}
	@Override
	public int getEnchantLevelUpExp(){
		return ENCHANT_UP;
	}
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int indexOfMainSlot, boolean isCurrent) {

		int level = this.getLevel(stack);
		int rust = this.getRustValue(stack);
		double rust_h = (Math.exp(-1.0D*(rust/1024.0D))*(1.0D-Math.exp(-0.2D))) * (rust==0?0:1);			// 錆補正

		// 攻撃力設定
		attackDamage = this.getAttackDamage(stack) 																// 固有攻撃力
				+ MathHelper.floor(this.getAttackDamage(stack)*(1-Math.exp((-1*level/100)/(1-Math.exp(-1)))))   // レベル補正
				+ Mod_FantomBlade.HAGANE.getAttackDamage()                                                                             // 素材補正
				+ (world.getWorldInfo().isRaining()?20.0F:0F);												    // 嵐補正
		// 嵐の場合錆補正無効
		if (rust_h!= 0 && !world.getWorldInfo().isRaining()){
			attackDamage = (float) (attackDamage * MathHelper.clamp(rust_h,0.4,1.0)); // 錆補正 最低0.4倍
		}

		// 攻撃速度設定
		attackSpeed = this.getAttackSpeed(stack) - rust_h;

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

		 ItemStack main = playerIn.getHeldItemMainhand();
		 if (!worldIn.isRemote){
			 int i = (300 + (new Random()).nextInt(600)) * 20;
			 if (hand == EnumHand.MAIN_HAND){
				 // メインハンドに持って右クリックで天候を晴に
				 if (main.getItem() == this){
					 //CommandWeather
					 WorldInfo worldinfo = worldIn.getWorldInfo();
					 worldinfo.setCleanWeatherTime(i);
		             worldinfo.setRainTime(0);
		             worldinfo.setThunderTime(0);
		             worldinfo.setRaining(false);
		             worldinfo.setThundering(false);
		             //notifyCommandListener(sender, this, "commands.weather.clear", new Object[0]);
				 }
			 }else if (hand == EnumHand.OFF_HAND){
				 ItemStack off = playerIn.getHeldItemOffhand();
				 if (off.getItem() == this){
					 // オフハンドに持って右クリックで天候を嵐に
					 main = playerIn.getHeldItemOffhand();
					 if (main.getItem() == this){
						 WorldInfo worldinfo = worldIn.getWorldInfo();
			             worldinfo.setCleanWeatherTime(0);
			             worldinfo.setRainTime(i);
			             worldinfo.setThunderTime(i);
			             worldinfo.setRaining(true);
			             worldinfo.setThundering(true);
					 }
				 }
			 }else{
				 return new ActionResult(EnumActionResult.FAIL, main);
			 }
		 }
		 return new ActionResult(EnumActionResult.SUCCESS, main);
	 }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
    	super.hitEntity(stack, target, attacker);
    	if (target instanceof EntityMob && target.isEntityAlive()){
    		// 生きているなら落雷を落とす
    		if (!target.world.isRemote){
    			//target.onStruckByLightning(new EntityLightningBolt(target.world, target.posX, target.posY, target.posZ, true));
    			target.world.addWeatherEffect(new EntityLightningBolt(target.world, target.posX, target.posY, target.posZ, true));
    		}
    	}
    	ResourceLocation soulentity = null;
    	if(!target.isEntityAlive() && ((soulentity = EntityList.getKey(target.getClass()))!=null)){
    		// 死んでいるなら10%の確率でソウルをドロップ
    		if (ModUtil.random(100) < 80){
        		ItemStack soul = new ItemStack(ItemCore.item_mobsoule,1);
        		ItemMobSoule.applyEntityIdToItemStack(soul, soulentity);
        		InventoryHelper.spawnItemStack(target.world, target.posX, target.posY, target.posZ, soul);
    		}
    	}
		return true;
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
		 ItemStack ret = new ItemStack(ItemCore.item_katana_kirin);
		 ItemKatana.setMaxLevel(ret,255);
			ItemKatana.setExp(ret,0);
			ItemKatana.setRustValue(ret, 0);
			ItemKatana.setAttackDamage(ret,14.0F);
			ItemKatana.setAttackSpeed(ret,-0.1D);
			ItemKatana.setEndurance(ret, 1000);
			if (ModCommon.isDevelop){
				ItemKatana.setExp(ret, 10000);
			}
			ret.addEnchantment(EnchantmentCore.enc_monsterkiller, 1);
		 return ret;
	 }

	 public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_kirin.png");
	 public ResourceLocation getBladeTexture() {
			return DEFAULT_KATANATEXTURE;
	 }

	 public void setLevel(ItemStack stack, int value){
	    	if (value > 255){value = 255;}
	    	NBTTagCompound tag = getItemTagCompound(stack);
	    	tag.setFloat("BladeLevel", value);

			boolean update=false;
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
			int lv = EnchantmentHelper.getEnchantmentLevel(EnchantmentCore.enc_monsterkiller, stack);
			if (lv < MathHelper.ceil(value/3F) && lv < 21){
				map.replace(EnchantmentCore.enc_monsterkiller, MathHelper.ceil(value/3F));
				update = true;
			}
			if (update){
				EnchantmentHelper.setEnchantments(map, stack);
			}
	 }
	 public Enchantment[] ignoreEnchantments(){
		 return	new Enchantment[]{EnchantmentCore.enc_monsterkiller};
	 }
}
