package mod.fbd.item;

import mod.fbd.core.ModCommon;
import mod.fbd.entity.EntityBurret;
import mod.fbd.util.ModUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemBurret extends Item {

	protected EnumBurret burretType = EnumBurret.NORMAL;

	public ItemBurret(EnumBurret burret, Item.Properties property){
		super(property);
		burretType = burret;
	}

	public EnumBurret getBurret(){
		return burretType;
	}

	@Override
    public ITextComponent getDisplayName(ItemStack stack)
    {
    	String name = I18n.format(stack.getTranslationKey());
    	if (ItemBurret.isFullMetal(stack)){
    		name += " " + I18n.format("burret.piassing");
    	}
    	if (ItemBurret.canWater(stack)){
    		name += " " + I18n.format("burret.canwater");
    	}
    	name += " +" + ItemBurret.getGunPowder(stack);
        return new TextComponentTranslation(name);
    }

	public static enum EnumBurret{
		NORMAL(0,true,true,5.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_normal.png")),						// 通常弾
		POTION(1,true,true,3.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_normal.png")),						// ポーション効果を埋め込める弾
		EXPLOSION(2,false,false,3.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_explod.png")),					// 着弾時に爆発する弾
		TELEPORT(3,true,false,0.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_teleport.png")),					// 着弾箇所にてレポートする弾
		DRAW(4,true,false,0.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_draw.png")),						// 着弾した敵を目の前まで引き寄せる弾
		ASSASINATION(5,true,false,0.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_assasin.png")),				// 着弾した相手を一撃で殺す弾
		FLAME(6,false,true,5.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_flame.png")),						// 着弾した相手を炎上させる
		THUNDER(7,true,true,5.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_thunder.png")),						// 着弾地点に雷を召喚
		BLOW(8,true,true,5.0F, 1000, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_blow.png")),						// 着弾した相手をぶっ飛ばす
		DRAIN(9,true,true, 1.0F,  0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_drain.png")),					// 与えたダメージ分体力を回復する
		GOLEM(10,false,false, 0.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_golem.png")),				// アイアンゴーレムを召喚する
		SNOWMAN(11,false,false,0.0F,0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_snowman.png"));				// スノーゴーレムを召喚する

		private int index;
		private boolean canWater;
		private boolean canFullmetal;
		private float burretDamage;
		private int knockbackBounus;
		private ResourceLocation texture;
		private EnumBurret(int idx, boolean flagwater, boolean flagfullmetal,float damage, int bounus, ResourceLocation tex){
			index = idx;
			canWater = flagwater;
			canFullmetal = flagfullmetal;
			burretDamage = damage;
			knockbackBounus = bounus;
			texture = tex;
		}

		public int getIndex(){return index;}
		public boolean canWater(){return this.canWater;}
		public boolean canFullmetal(){return this.canFullmetal;}
		public float getDamage(){
			if (this == DRAIN){
				// ドレインの場合はランダムダメージ
				return this.burretDamage + ModUtil.random(7);
			}else{
				return this.burretDamage;
			}
		}
		public int getKnockBackBounus(){return this.knockbackBounus;}
		public ResourceLocation getEntityTextrue(){return texture;}

		private static EnumBurret[] values ={NORMAL,POTION,EXPLOSION,TELEPORT,ASSASINATION};
		public static EnumBurret getFromIndex(int index){
			if (index < 0 || index >= values.length){
				index = 0;
			}
			return values[index];
		}

		public double hitEntity(World world, ItemStack stack, Entity hitEntity, Entity shootingEntity, EntityBurret burretEntity) {
			ItemBurret burret = ((ItemBurret)stack.getItem());
			EnumBurret kind = burret.getBurret();
			double damage = kind.getDamage();
			switch(kind){
			case ASSASINATION:
				if (hitEntity instanceof EntityLivingBase){
					// 最大体力の999倍のダメージを与える
					damage = ((EntityLivingBase)hitEntity).getMaxHealth()*999;
				}
				break;
			case DRAW:
				if (hitEntity instanceof EntityLivingBase && shootingEntity instanceof EntityLivingBase){
					// あったったモブを引き寄せる
					BlockPos shotPos = ((EntityLivingBase)shootingEntity).getPosition();
					BlockPos tagPos = ((EntityLivingBase)hitEntity).getPosition();
					BlockPos nextPos = shotPos;
					if (shotPos.getX() < tagPos.getX()){
						nextPos = nextPos.add(1,0,0);
					}else{
						nextPos = nextPos.add(-1,0,0);
					}
					if (shotPos.getZ() < tagPos.getZ()){
						nextPos = nextPos.add(0,0,1);
					}else{
						nextPos = nextPos.add(0,0,-1);
					}
					((EntityLivingBase)hitEntity).setPositionAndRotation(nextPos.getX()+0.5, shotPos.getY(), nextPos.getZ() + 0.5,
							(MathHelper.floor((double)((((EntityLivingBase)shootingEntity).rotationYaw*4F)/360F)+2.5D)&3)*90, 0.0F);

				}
				break;
			case EXPLOSION:
				// 爆発
				world.newExplosion(null, hitEntity.posX, hitEntity.posY, hitEntity.posZ, 5.0F + ItemBurret.getGunPowder(stack), true, false);
				break;

				// 生き物にあたった際はダメージだけ与える
			case NORMAL:
			case BLOW:
			case DRAIN:
			case GOLEM:
			case SNOWMAN:
				break;
			case POTION:
				if (hitEntity instanceof EntityLivingBase && shootingEntity instanceof EntityLivingBase){
					// 弾薬についているポーション効果を発動
					PotionEffect effect = ItemBurretPotion.getPotionEffect(stack);
					if (effect != null){
						int potionType = ItemBurretPotion.getPotionType(stack);
						ItemBurretPotion.effectionPotion(world, potionType, effect, (EntityLivingBase)hitEntity, (EntityLivingBase)shootingEntity, burretEntity);
					}
				}
				break;
			case TELEPORT:
				if (hitEntity instanceof EntityLivingBase && shootingEntity instanceof EntityLivingBase){
					// ぶつかったモブの位置へテレポート
					shootingEntity.setPositionAndUpdate((double)hitEntity.posX, (double)hitEntity.posY, (double)hitEntity.posZ);
				}
				break;
			case FLAME:
				IBlockState state = world.getBlockState(hitEntity.getPosition());
				// 相手が水中や溶岩中にいる場合効果を発揮しない
				if (state.getMaterial() != Material.WATER && state.getMaterial() != Material.LAVA){
					// ぶつかった相手を燃やす
					if (hitEntity instanceof EntityLivingBase){
						hitEntity.setFire(3 + 3*getGunPowder(stack));
					}
				}
				break;
			case THUNDER:
				// 部t勝った相手に雷を落とす
				if (hitEntity instanceof EntityLivingBase){
					if (!world.isRemote){
						hitEntity.world.addWeatherEffect(new EntityLightningBolt(hitEntity.world, hitEntity.posX, hitEntity.posY, hitEntity.posZ, true));
					}
				}
				break;
			default:
				break;

			}

			if (kind.canFullmetal && !ItemBurret.isFullMetal(stack)){
				// フルメタルジャケットでなければダメージ+2
				damage = +2;
			}
			return damage;

		}

		public void hitBlock(World world, ItemStack stack, Entity shootingEntity, EntityBurret entityBurret) {
			ItemBurret burret = ((ItemBurret)stack.getItem());
			EnumBurret kind = burret.getBurret();
			double damage = kind.getDamage();
			switch(kind){
			// 通常弾は何もしない
			case NORMAL:
			case ASSASINATION:
			case DRAW:
			case BLOW:
			case DRAIN:
				break;
			case EXPLOSION:
				// 爆発
				world.newExplosion(null, entityBurret.posX, entityBurret.posY, entityBurret.posZ, 5.0F + ItemBurret.getGunPowder(stack), true, false);
				break;
			case POTION:
				// スプラッシュか残留ポーションなら効果を発揮
				PotionEffect effect = ItemBurretPotion.getPotionEffect(stack);
				if (effect != null){
					int potionType = ItemBurretPotion.getPotionType(stack);
					if (potionType == 2 || potionType == 3){
						ItemBurretPotion.effectionPotion(world, potionType, effect, (EntityLivingBase)null, (EntityLivingBase)shootingEntity, entityBurret);
					}
				}
				break;
			case TELEPORT:
				// ぶつかったブロックの上に強制テレポート(地面に埋まる場合でも手レポートするよ)
				shootingEntity.setPositionAndUpdate((double)entityBurret.posX, (double)entityBurret.posY, (double)entityBurret.posZ);
				break;
			case FLAME:
				// 着弾地点を燃やす
				BlockPos pos = entityBurret.getPosition();
				if ((world.getBlockState(pos).getMaterial() != Material.WATER && world.getBlockState(pos).getMaterial() != Material.LAVA) &&
						world.getBlockState(pos.offset(EnumFacing.UP)).getMaterial() == Material.AIR){
					world.setBlockState(pos.offset(EnumFacing.UP), Blocks.FIRE.getDefaultState());
				}
				break;
			case THUNDER:
				// 着弾地点に雷を落とす
				if (!world.isRemote){
					world.addWeatherEffect(new EntityLightningBolt(entityBurret.world, entityBurret.posX, entityBurret.posY, entityBurret.posZ, false));
				}
				break;
			case GOLEM:
				if (!world.isRemote){
					EntityIronGolem entityirongolem = new EntityIronGolem(world);
	                entityirongolem.setPlayerCreated(true);
	                entityirongolem.setLocationAndAngles((double)entityBurret.posX + 0.5D, (double)entityBurret.posY + 1.05D, (double)entityBurret.posZ + 0.5D, 0.0F, 0.0F);
	                world.spawnEntity(entityirongolem);

	                for (EntityPlayerMP entityplayermp1 : world.getEntitiesWithinAABB(EntityPlayerMP.class, entityirongolem.getBoundingBox().grow(5.0D)))
	                {
	                    CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp1, entityirongolem);
	                }

	                for (int j1 = 0; j1 < 120; ++j1)
	                {
	                	world.spawnParticle(Particles.ITEM_SNOWBALL, (double)entityBurret.posX + world.rand.nextDouble(), (double)entityBurret.posY + 1.0D +world.rand.nextDouble() * 3.9D, (double)entityBurret.posZ + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	                }
				}
			case SNOWMAN:
				if (!world.isRemote){
		            EntitySnowman entitysnowman = new EntitySnowman(world);
		            entitysnowman.setLocationAndAngles((double)entityBurret.posX + 0.5D, (double)entityBurret.posY + 1.05D, (double)entityBurret.posZ + 0.5D, 0.0F, 0.0F);
		            world.spawnEntity(entitysnowman);

		            for (EntityPlayerMP entityplayermp : world.getEntitiesWithinAABB(EntityPlayerMP.class, entitysnowman.getBoundingBox().grow(5.0D)))
		            {
		                CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp, entitysnowman);
		            }

		            for (int l = 0; l < 120; ++l)
		            {
		            	world.spawnParticle(Particles.ITEM_SNOWBALL, (double)entityBurret.posX + world.rand.nextDouble(), (double)entityBurret.posY + 1.0D + world.rand.nextDouble() * 2.5D, (double)entityBurret.posZ + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		            }
				}
				break;
			default:
				break;

			}
		}
	}


	public static boolean canWater(ItemStack stack){
		if (stack.getItem() instanceof ItemBurret){
			if (((ItemBurret)stack.getItem()).burretType.canWater){
				NBTTagCompound tag = getItemTagCompound(stack);
				if (tag.hasKey("water")){
					return tag.getBoolean("water");
				}
			}
		}
		return false;
	}
	public static void setCanWater(ItemStack stack, boolean value){
		if (stack.getItem() instanceof ItemBurret){
			if (((ItemBurret)stack.getItem()).burretType.canWater){
				NBTTagCompound tag = getItemTagCompound(stack);
				tag.setBoolean("water", value);
			}
		}
	}

	public static boolean isFullMetal(ItemStack stack){
		if (stack.getItem() instanceof ItemBurret){
			if (((ItemBurret)stack.getItem()).burretType.canFullmetal){
				NBTTagCompound tag = getItemTagCompound(stack);
				if (tag.hasKey("fullmetal")){
					return tag.getBoolean("fullmetal");
				}
			}
		}
		return false;
	}

	public static void setFullMetal(ItemStack stack, boolean value){
		if (stack.getItem() instanceof ItemBurret){
			if (((ItemBurret)stack.getItem()).burretType.canFullmetal){
				NBTTagCompound tag = getItemTagCompound(stack);
				tag.setBoolean("fullmetal", value);
			}
		}
	}


	public static boolean canAddGunPowder(ItemStack stack, int add){
		if (stack.getItem() instanceof ItemBurret){
			int now = getGunPowder(stack);
			if (now + add>= 4){
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean addGunPowder(ItemStack stack,int add){
		int now = getGunPowder(stack);
		if (canAddGunPowder(stack,add)){
			NBTTagCompound tag = getItemTagCompound(stack);
			tag.setInt("gunpowder", now+add);
			return true;
		}
		return false;
	}

	public static int getGunPowder(ItemStack stack){
		NBTTagCompound tag = getItemTagCompound(stack);
		if (tag.hasKey("gunpowder")){
			return tag.getInt("gunpowder");
		}
		return 0;
	}

    public static NBTTagCompound getItemTagCompound(ItemStack stack){
		NBTTagCompound tag;
		if(stack.hasTag()){
			tag = stack.getTag();
		}else{
			tag = new NBTTagCompound();
			stack.setTag(tag);
		}
		return tag;
	}

	public static int knockBackStrength(ItemStack stack, EntityBurret burret) {
		ItemBurret bu = ((ItemBurret)stack.getItem());
		EnumBurret kind = bu.getBurret();

		int knock = 2 + kind.knockbackBounus;
		if (kind != EnumBurret.DRAW){
			knock += ItemBurret.getGunPowder(stack);
			knock += ItemBurret.isFullMetal(stack)?0:1;

			if (burret.world.getBlockState(burret.getPosition()).getMaterial() == Material.WATER || burret.world.getBlockState(burret.getPosition()).getMaterial() == Material.LAVA){
				if (!ItemBurret.canWater(stack)){
					knock/=2;
				}
			}
		}else{
			knock = 0;
		}
		return knock;
	}
}
