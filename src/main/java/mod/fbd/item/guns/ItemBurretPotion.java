package mod.fbd.item.guns;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import mod.fbd.entity.EntityBurret;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemBurretPotion extends ItemBurret {
	public ItemBurretPotion(Item.Properties property) {
		super(EnumBurret.POTION,property);
		// TODO 自動生成されたコンストラクター・スタブ
	}

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }


	public static void effectionPotion(World world, int potionType, EffectInstance effect, LivingEntity hitEntity, LivingEntity shooter, EntityBurret burret){
		if (potionType == 1){
			// i通常ポーションは当たった相手のみ
			hitEntity.addPotionEffect(effect);
		}else if (potionType == 2){
			// iスプラッシュポーション
			if (hitEntity != null){
				hitEntity.addPotionEffect(effect);
			}
            //applyWater(burret, hitEntity);
            applySplash(effect, burret, hitEntity, shooter);

		}else if (potionType == 3){
            makeAreaOfEffectCloud(effect, burret, shooter);
		}

	}

    private static void applyWater(EntityBurret burret, LivingEntity hitEntity) {
        AxisAlignedBB axisalignedbb = burret.getCollisionBoundingBox().grow(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = burret.world.<LivingEntity>getEntitiesWithinAABB(LivingEntity.class, axisalignedbb, WATER_SENSITIVE);

        if (!list.isEmpty())
        {
            for (LivingEntity entitylivingbase : list)
            {
                double d0 = getDistanceSq(entitylivingbase,burret);

                if (d0 < 16.0D && isWaterSensitiveEntity(entitylivingbase))
                {
                    entitylivingbase.attackEntityFrom(DamageSource.DROWN, 1.0F);
                }
            }
        }
    }

    private static void applySplash(EffectInstance effects, EntityBurret burret, LivingEntity hitEntity, LivingEntity shooter) {
        AxisAlignedBB axisalignedbb = burret.getBoundingBox().grow(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = burret.world.<LivingEntity>getEntitiesWithinAABB(LivingEntity.class, axisalignedbb);

        if (!list.isEmpty()) {
            for (LivingEntity entitylivingbase : list) {
                if (entitylivingbase.canBeHitWithPotion()) {
                    double d0 = burret.getDistanceSq(entitylivingbase);

                    if (d0 < 16.0D) {
                        double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                        if (entitylivingbase == hitEntity) {
                            d1 = 1.0D;
                        }
                        Effect potion = effects.getPotion();

                        if (potion.isInstant()) {
                            potion.affectEntity(hitEntity, shooter, entitylivingbase, effects.getAmplifier(), d1);
                        } else {
                            int i = (int)(d1 * (double)effects.getDuration() + 0.5D);

                            if (i > 20) {
                                entitylivingbase.addPotionEffect(new EffectInstance(potion, i, effects.getAmplifier(), effects.isAmbient(), effects.doesShowParticles()));
                            }
                        }
                    }
                }
            }
        }
    }

    private static void makeAreaOfEffectCloud(EffectInstance effects,EntityBurret burret, LivingEntity shooter) {
    	AreaEffectCloudEntity entityareaeffectcloud = new AreaEffectCloudEntity(burret.world, burret.posX, burret.posY, burret.posZ);
        entityareaeffectcloud.setOwner(shooter);
        entityareaeffectcloud.setRadius(3.0F);
        entityareaeffectcloud.setRadiusOnUse(-0.5F);
        entityareaeffectcloud.setWaitTime(10);
        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
        entityareaeffectcloud.setPotion(Potion.getPotionTypeForName(effects.getPotion().getRegistryName().toString()));
        entityareaeffectcloud.addEffect(new EffectInstance(effects));
        burret.world.addEntity(entityareaeffectcloud);
    }


    public static double getDistanceSq(Entity entityIn, Entity burret) {
        double d0 = burret.posX - entityIn.posX;
        double d1 = burret.posY - entityIn.posY;
        double d2 = burret.posZ - entityIn.posZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public static final Predicate<LivingEntity> WATER_SENSITIVE = ItemBurretPotion::isWaterSensitiveEntity;

    public static boolean isWaterSensitiveEntity(LivingEntity entitylivingbase){
    	return entitylivingbase instanceof EndermanEntity || entitylivingbase instanceof BlazeEntity;
    }


    public static boolean canEffectInstance(ItemStack stack){
    	List<EffectInstance> effects = PotionUtils.getEffectsFromStack(stack);
    	if (effects == null || (effects != null && effects.size() == 0)){
    		return true;
    	}
    	return false;
    }

	public static boolean setPotionEffect(ItemStack stack, EffectInstance effect){
		int now = getGunPowder(stack);
		if (canEffectInstance(stack)){
			PotionUtils.appendEffects(stack,
					new ArrayList<EffectInstance>(){
				{add(new EffectInstance(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));}
				});
			return true;
		}
		return false;
	}

	public static EffectInstance getEffectInstance(ItemStack stack){
		List<EffectInstance> effects = PotionUtils.getEffectsFromStack(stack);
		int powderLevel = ItemBurret.getGunPowder(stack);
		if (effects != null && effects.size() > 0){
			return new EffectInstance(effects.get(0).getPotion(),effects.get(0).getDuration() + 20 * powderLevel,effects.get(0).getAmplifier() + powderLevel);
		}
		return null;
	}

	public static int getPotionType(ItemStack stack){
		if (stack.getItem() instanceof ItemBurret){
			CompoundNBT tag = getItemTagCompound(stack);
			if (tag.contains("potiontype")){
				return tag.getInt("potiontype");
			}
		}
		return 0;
	}

	public static void setPotionType(ItemStack stack, int value){
		if (stack.getItem() instanceof ItemBurret){
			CompoundNBT tag = getItemTagCompound(stack);
			tag.putInt("potiontype", value);
		}
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
}
