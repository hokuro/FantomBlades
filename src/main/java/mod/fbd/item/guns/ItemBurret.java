package mod.fbd.item.guns;

import mod.fbd.core.ModCommon;
import mod.fbd.entity.EntityBurret;
import mod.fbd.entity.EntityGravity;
import mod.fbd.entity.EntityLevitate;
import mod.fbd.util.ModUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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
        return new TranslationTextComponent(name);
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
		SNOWMAN(11,false,false,0.0F,0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_snowman.png")),				// スノーゴーレムを召喚する
		HEAL(12, true, true, 3.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_heal.png")),
		SUZAKU(13,false,true,8.0F, 3, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_suzaku.png")),
		KIRIN(14,false,true,8.0F, 3, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_kirin.png")),
		BYAKO(15,true,true,8.0F, 3, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_byako.png")),
		GENBU(16,true,true,8.0F, 3, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_genbu.png")),
		SEIRYU(17,true,true,8.0F, 3, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_seiryu.png")),
		SILVER(18,true,true,3.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_silver.png")),
		GRAVITY(19,true,true,1.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_gravity.png")),
		REVITATE(20,true,true,1.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_revitate.png")),
		WITHER(21,true,true,1.0F, 0, new ResourceLocation(ModCommon.MOD_ID,"textures/entity/burret_wither.png"));

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
			int powderLevel = ItemBurret.getGunPowder(stack);
			switch(kind){
			case ASSASINATION:
				if (hitEntity instanceof LivingEntity){
					// i最大体力の999倍のダメージを与える
					damage = ((LivingEntity)hitEntity).getMaxHealth()*999;
				}
				break;
			case DRAW:
				if (hitEntity instanceof LivingEntity && shootingEntity instanceof LivingEntity){
					// iあったったモブを引き寄せる
					BlockPos shotPos = ((LivingEntity)shootingEntity).getPosition();
					BlockPos tagPos = ((LivingEntity)hitEntity).getPosition();
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
					((LivingEntity)hitEntity).setPositionAndRotation(nextPos.getX()+0.5, shotPos.getY(), nextPos.getZ() + 0.5,
							(MathHelper.floor((double)((((LivingEntity)shootingEntity).rotationYaw*4F)/360F)+2.5D)&3)*90, 0.0F);

				}
				break;
			case EXPLOSION:
				// i爆発
				world.createExplosion(null, hitEntity.posX, hitEntity.posY, hitEntity.posZ, 5.0F + powderLevel, true,  Explosion.Mode.NONE);
				break;

				// i生き物にあたった際はダメージだけ与える
			case NORMAL:
			case BLOW:
			case DRAIN:
			case GOLEM:
			case SNOWMAN:
				break;
			case POTION:
				if (hitEntity instanceof LivingEntity && shootingEntity instanceof LivingEntity){
					// i弾薬についているポーション効果を発動
					EffectInstance effect = ItemBurretPotion.getEffectInstance(stack);
					if (effect != null){
						int potionType = ItemBurretPotion.getPotionType(stack);
						ItemBurretPotion.effectionPotion(world, potionType, effect, (LivingEntity)hitEntity, (LivingEntity)shootingEntity, burretEntity);
					}
				}
				break;
			case TELEPORT:
				if (hitEntity instanceof LivingEntity && shootingEntity instanceof LivingEntity){
					// iぶつかったモブの位置へテレポート
					shootingEntity.setPositionAndUpdate((double)hitEntity.posX, (double)hitEntity.posY, (double)hitEntity.posZ);
				}
				break;
			case FLAME:
				BlockState state = world.getBlockState(hitEntity.getPosition());
				// i相手が水中や溶岩中にいる場合効果を発揮しない
				if (state.getMaterial() != Material.WATER && state.getMaterial() != Material.LAVA){
					// iぶつかった相手を燃やす
					if (hitEntity instanceof LivingEntity){
						hitEntity.setFire(3 + 3*powderLevel);
					}
				}
				break;
			case THUNDER:
				// 部t勝った相手に雷を落とす
				if (hitEntity instanceof LivingEntity){
					if (!world.isRemote){
						for (int i =0; i < 1 + powderLevel; i++) {
							((ServerWorld)hitEntity.world).addLightningBolt(new LightningBoltEntity(hitEntity.world, hitEntity.posX, hitEntity.posY, hitEntity.posZ, true));
						}
					}
				}
				break;
			case HEAL:
				// iダメージを回復
				damage *= -1;
				break;
			case SUZAKU:
	    		// iアンデット、暴漢系のモブを回復
	    		if (hitEntity instanceof LivingEntity && (((LivingEntity)hitEntity).getCreatureAttribute() == CreatureAttribute.UNDEAD|| hitEntity instanceof AbstractIllagerEntity)) {
	    			damage *= -3;
	    		}else {
		    		//i 動物系のモンスターに追加ダメージ
		    		if (hitEntity instanceof AnimalEntity || hitEntity instanceof RavagerEntity) {
		    			damage *= 3;
		    		}
	    			hitEntity.setFire(30);
	    		}
				break;
			case KIRIN:
	    		// i水生生物へのダメージ増
	    		if (hitEntity instanceof LivingEntity && (((LivingEntity)hitEntity).getCreatureAttribute() == CreatureAttribute.WATER)) {
	    			damage *=3;
	    		}

	    		// i 動物系のモンスターを回復
	    		if (hitEntity instanceof AnimalEntity || hitEntity instanceof RavagerEntity) {
	    			damage *= -3;
	    		}
				break;
			case BYAKO:
	    		// i水性成物を回復
	    		if (hitEntity instanceof LivingEntity && (((LivingEntity)hitEntity).getCreatureAttribute() == CreatureAttribute.WATER)) {
	    			damage *= -3;
	    		}
				break;
			case GENBU:
				// i炎上攻撃を受けないモブとエンダーマンへの攻撃でダメージ増加
	    		if (hitEntity.getType().isImmuneToFire() || hitEntity instanceof EndermanEntity) {
	    			damage *= 3;
	    		}
				break;
			case SEIRYU:
				// iアンデッド、暴漢へのダメージ増
	    		if (hitEntity instanceof LivingEntity && (((LivingEntity)hitEntity).getCreatureAttribute() == CreatureAttribute.UNDEAD|| hitEntity instanceof AbstractIllagerEntity)) {
	    			damage *= 3;
	    		}

	    		// i炎上ダメージを受けないモブに回復ポーション効果
	    		if ((hitEntity.getType().isImmuneToFire() && hitEntity instanceof LivingEntity && (((LivingEntity)hitEntity).getCreatureAttribute() != CreatureAttribute.UNDEAD))) {
	    			damage *= -3;
	    		}
				break;
			case SILVER:
				if (hitEntity instanceof ZombieVillagerEntity) {
					// iゾンビ村人を治療する
					CompoundNBT nbt = new CompoundNBT();
					((ZombieVillagerEntity) hitEntity).writeAdditional(nbt);
					nbt.putInt("ConversionTime", ModUtil.random(2401) + 3600);
					((ZombieVillagerEntity) hitEntity).readAdditional(nbt);
					damage = 0;
				}else if (hitEntity instanceof LivingEntity) {
					// iアンデッド直葬
					LivingEntity living = (LivingEntity)hitEntity;
					if (living.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
						damage *= 999;
					}
 				}
				break;
			case GRAVITY:
				if (hitEntity instanceof LivingEntity) {
					LivingEntity living = (LivingEntity)hitEntity;
					living.removeActivePotionEffect(Effects.LEVITATION);
	            	living.onGround = false;
					hitEntity.fallDistance = 50 * (powderLevel+1);
				}
				break;
			case REVITATE:
				EffectInstance effect = new EffectInstance(Effects.LEVITATION, (30*20) + (5*20*powderLevel), 1 + powderLevel);
				ItemBurretPotion.effectionPotion(world, 1, effect, (LivingEntity)hitEntity, (LivingEntity)shootingEntity, burretEntity);
				break;
			case WITHER:
				ItemBurretPotion.effectionPotion(world, 1, new EffectInstance(Effects.WITHER, (30*20) + (5*20*powderLevel), 1 + powderLevel), (LivingEntity)hitEntity, (LivingEntity)shootingEntity, burretEntity);
				break;
			default:
				break;

			}

			if (kind.canFullmetal && !ItemBurret.isFullMetal(stack)){
				// iフルメタルジャケットでなければダメージ+2
				if ( damage >= 0) {
					damage += 2;
				}
			}
			return damage;

		}

		public void hitBlock(World world, ItemStack stack, Entity shootingEntity, EntityBurret entityBurret, BlockState state, BlockPos pos) {
			ItemBurret burret = ((ItemBurret)stack.getItem());
			EnumBurret kind = burret.getBurret();
			double damage = kind.getDamage();
			int powderLevel = ItemBurret.getGunPowder(stack);
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
				world.createExplosion(null, entityBurret.posX, entityBurret.posY, entityBurret.posZ, 5.0F + powderLevel, true, Explosion.Mode.NONE);
				break;
			case POTION:
				// iスプラッシュか残留ポーションなら効果を発揮
				EffectInstance effect = ItemBurretPotion.getEffectInstance(stack);
				if (effect != null){
					int potionType = ItemBurretPotion.getPotionType(stack);
					if (potionType == 2 || potionType == 3){
						ItemBurretPotion.effectionPotion(world, potionType, effect, (LivingEntity)null, (LivingEntity)shootingEntity, entityBurret);
					}
				}
				break;
			case TELEPORT:
				if (shootingEntity instanceof LivingEntity) {
					// iぶつかったブロックの上に強制テレポート(地面に埋まる場合でも手レポートするよ)
					shootingEntity.setPositionAndUpdate((double)entityBurret.posX, (double)entityBurret.posY, (double)entityBurret.posZ);
				}
				break;
			case FLAME:
				// i着弾地点を燃やす
				//BlockPos pos = entityBurret.getPosition();
				if ((world.getBlockState(pos).getMaterial() != Material.WATER && world.getBlockState(pos).getMaterial() != Material.LAVA) &&
						world.getBlockState(pos.offset(Direction.UP)).getMaterial() == Material.AIR){
					world.setBlockState(pos.offset(Direction.UP), Blocks.FIRE.getDefaultState());
				}
				break;
			case THUNDER:
				// i着弾地点に雷を落とす
				if (!world.isRemote){
					for (int i =0; i < 1 + powderLevel; i++) {
						((ServerWorld)world).addLightningBolt(new LightningBoltEntity(entityBurret.world, entityBurret.posX, entityBurret.posY, entityBurret.posZ, false));
					}
				}
				break;
			case GOLEM:
				if (!world.isRemote){
					IronGolemEntity entityirongolem = EntityType.IRON_GOLEM.create(world);
					entityirongolem.setPlayerCreated(true);
					entityirongolem.setLocationAndAngles((double)entityBurret.posX + 0.5D, (double)entityBurret.posY + 1.05D, (double)entityBurret.posZ + 0.5D, 0.0F, 0.0F);
					world.addEntity(entityirongolem);

					for (ServerPlayerEntity entityplayermp1 : world.getEntitiesWithinAABB(ServerPlayerEntity.class, entityirongolem.getBoundingBox().grow(5.0D))) {
						CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp1, entityirongolem);
					}
				}
				break;
			case SNOWMAN:
				if (!world.isRemote){
					SnowGolemEntity entitysnowman = EntityType.SNOW_GOLEM.create(world);
					entitysnowman.setLocationAndAngles((double)entityBurret.posX + 0.5D, (double)entityBurret.posY + 1.05D, (double)entityBurret.posZ + 0.5D, 0.0F, 0.0F);
					world.addEntity(entitysnowman);

					for (ServerPlayerEntity entityplayermp : world.getEntitiesWithinAABB(ServerPlayerEntity.class, entitysnowman.getBoundingBox().grow(5.0D))){
						CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp, entitysnowman);
					}

					for (int l = 0; l < 120; ++l){
						world.addParticle(ParticleTypes.ITEM_SNOWBALL, (double)entityBurret.posX + world.rand.nextDouble(), (double)entityBurret.posY + 1.0D + world.rand.nextDouble() * 2.5D, (double)entityBurret.posZ + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
					}
				}
				break;
			case SUZAKU:
				if (entityBurret.world != null && !entityBurret.world.isRemote) {
		    		//BlockState state = entityBurret.world.getBlockState(entityBurret.getPosition());
		    		BlockState state_up = entityBurret.world.getBlockState(pos.up());
		    		if (state.getMaterial().isSolid() && (!state_up.getMaterial().isLiquid() || state.getMaterial() == Material.AIR)) {
		    			entityBurret.world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState().with(FireBlock.AGE, 15));
		    		}
				}
				break;
			case GRAVITY:
				// i超重力を発生させるような何か
				if (!entityBurret.world.isRemote) {
					EntityGravity gravity = new EntityGravity(entityBurret.world, pos.getX() + 0.5, pos.getY()+1, pos.getZ() + 0.5);
					entityBurret.world.addEntity(gravity);
				}
				break;
			case REVITATE:
				// i超浮力を発生させるような何か
				if (!entityBurret.world.isRemote) {
					EntityLevitate levitate = new EntityLevitate(entityBurret.world, pos.getX() + 0.5, pos.getY()+1, pos.getZ() + 0.5);
					entityBurret.world.addEntity(levitate);
				}
				break;
			case WITHER:
				if (powderLevel > 2) {
					// i残留ポーション
					ItemBurretPotion.effectionPotion(world, 3, new EffectInstance(Effects.WITHER, (30*20) + (5*20*powderLevel), 1 + powderLevel), null, (LivingEntity)shootingEntity, entityBurret);
				}else {
					// iスプラッシュポーション
					ItemBurretPotion.effectionPotion(world, 2, new EffectInstance(Effects.WITHER, (30*20) + (5*20*powderLevel), 1 + powderLevel), null, (LivingEntity)shootingEntity, entityBurret);
				}
				break;
			case KIRIN:
			case BYAKO:
			case GENBU:
			case SEIRYU:
			case SILVER:
			default:
				break;

			}
		}
	}


	public static boolean canWater(ItemStack stack){
		if (stack.getItem() instanceof ItemBurret){
			if (((ItemBurret)stack.getItem()).burretType.canWater){
				CompoundNBT tag = getItemTagCompound(stack);
				if (tag.contains("water")){
					return tag.getBoolean("water");
				}
			}
		}
		return false;
	}
	public static void setCanWater(ItemStack stack, boolean value){
		if (stack.getItem() instanceof ItemBurret){
			if (((ItemBurret)stack.getItem()).burretType.canWater){
				CompoundNBT tag = getItemTagCompound(stack);
				tag.putBoolean("water", value);
			}
		}
	}

	public static boolean isFullMetal(ItemStack stack){
		if (stack.getItem() instanceof ItemBurret){
			if (((ItemBurret)stack.getItem()).burretType.canFullmetal){
				CompoundNBT tag = getItemTagCompound(stack);
				if (tag.contains("fullmetal")){
					return tag.getBoolean("fullmetal");
				}
			}
		}
		return false;
	}

	public static void setFullMetal(ItemStack stack, boolean value){
		if (stack.getItem() instanceof ItemBurret){
			if (((ItemBurret)stack.getItem()).burretType.canFullmetal){
				CompoundNBT tag = getItemTagCompound(stack);
				tag.putBoolean("fullmetal", value);
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
			CompoundNBT tag = getItemTagCompound(stack);
			tag.putInt("gunpowder", now+add);
			return true;
		}
		return false;
	}

	public static int getGunPowder(ItemStack stack){
		CompoundNBT tag = getItemTagCompound(stack);
		if (tag.contains("gunpowder")){
			return tag.getInt("gunpowder");
		}
		return 0;
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
