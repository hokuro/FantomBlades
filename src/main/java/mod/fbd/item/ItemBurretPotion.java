package mod.fbd.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import mod.fbd.entity.EntityBurret;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
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


	public static void effectionPotion(World world, int potionType, PotionEffect effect, EntityLivingBase hitEntity, EntityLivingBase shooter, EntityBurret burret){
		if (potionType == 1){
			// 通常ポーションは当たった相手のみ
			hitEntity.addPotionEffect(effect);
		}else if (potionType == 2){
			// スプラッシュポーション
			if (hitEntity != null){
				hitEntity.addPotionEffect(effect);
			}
            //applyWater(burret, hitEntity);
            applySplash(effect, burret, hitEntity, shooter);

		}else if (potionType == 3){
            makeAreaOfEffectCloud(effect, burret, shooter);
		}

	}

    private static void applyWater(EntityBurret burret, EntityLivingBase hitEntity)
    {
        AxisAlignedBB axisalignedbb = burret.getCollisionBoundingBox().grow(4.0D, 2.0D, 4.0D);
        List<EntityLivingBase> list = burret.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, WATER_SENSITIVE);

        if (!list.isEmpty())
        {
            for (EntityLivingBase entitylivingbase : list)
            {
                double d0 = getDistanceSq(entitylivingbase,burret);

                if (d0 < 16.0D && isWaterSensitiveEntity(entitylivingbase))
                {
                    entitylivingbase.attackEntityFrom(DamageSource.DROWN, 1.0F);
                }
            }
        }
    }

    private static void applySplash(PotionEffect effects,EntityBurret burret, EntityLivingBase hitEntity, EntityLivingBase shooter)
    {
        AxisAlignedBB axisalignedbb = burret.getBoundingBox().grow(4.0D, 2.0D, 4.0D);
        List<EntityLivingBase> list = burret.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

        if (!list.isEmpty())
        {
            for (EntityLivingBase entitylivingbase : list)
            {
                if (entitylivingbase.canBeHitWithPotion())
                {
                    double d0 = burret.getDistanceSq(entitylivingbase);

                    if (d0 < 16.0D)
                    {
                        double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                        if (entitylivingbase == hitEntity)
                        {
                            d1 = 1.0D;
                        }
                        Potion potion = effects.getPotion();

                        if (potion.isInstant())
                        {
                            potion.affectEntity(hitEntity, shooter, entitylivingbase, effects.getAmplifier(), d1);
                        }
                        else
                        {
                            int i = (int)(d1 * (double)effects.getDuration() + 0.5D);

                            if (i > 20)
                            {
                                entitylivingbase.addPotionEffect(new PotionEffect(potion, i, effects.getAmplifier(), effects.isAmbient(), effects.doesShowParticles()));
                            }
                        }
                    }
                }
            }
        }
    }

    private static void makeAreaOfEffectCloud(PotionEffect effects,EntityBurret burret, EntityLivingBase shooter)
    {
        EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(burret.world, burret.posX, burret.posY, burret.posZ);
        entityareaeffectcloud.setOwner(shooter);
        entityareaeffectcloud.setRadius(3.0F);
        entityareaeffectcloud.setRadiusOnUse(-0.5F);
        entityareaeffectcloud.setWaitTime(10);
        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
        entityareaeffectcloud.setPotion(PotionType.getPotionTypeForName(effects.getPotion().getRegistryName().toString()));
        entityareaeffectcloud.addEffect(new PotionEffect(effects));
        burret.world.spawnEntity(entityareaeffectcloud);
    }


    public static double getDistanceSq(Entity entityIn, Entity burret)
    {
        double d0 = burret.posX - entityIn.posX;
        double d1 = burret.posY - entityIn.posY;
        double d2 = burret.posZ - entityIn.posZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }


    public static final Predicate<EntityLivingBase> WATER_SENSITIVE = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return isWaterSensitiveEntity(p_apply_1_);
        }
    };
    public static boolean isWaterSensitiveEntity(EntityLivingBase entitylivingbase){
    	return entitylivingbase instanceof EntityEnderman || entitylivingbase instanceof EntityBlaze;
    }


    public static boolean canPotionEffect(ItemStack stack){
    	List<PotionEffect> effects = PotionUtils.getEffectsFromStack(stack);
    	if (effects == null || (effects != null && effects.size() == 0)){
    		return true;
    	}
    	return false;
    }

	public static boolean setPotionEffect(ItemStack stack, PotionEffect effect){
		int now = getGunPowder(stack);
		if (canPotionEffect(stack)){
			PotionUtils.appendEffects(stack,
					new ArrayList<PotionEffect>(){
				{add(new PotionEffect(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));}
				});
			return true;
		}
		return false;
	}

	public static PotionEffect getPotionEffect(ItemStack stack){
		List<PotionEffect> effects = PotionUtils.getEffectsFromStack(stack);
		if (effects != null && effects.size() > 0){
			return new PotionEffect(effects.get(0).getPotion(),effects.get(0).getDuration(),effects.get(0).getAmplifier());
		}
		return null;
	}

	public static int getPotionType(ItemStack stack){
		if (stack.getItem() instanceof ItemBurret){
			NBTTagCompound tag = getItemTagCompound(stack);
			if (tag.hasKey("potiontype")){
				return tag.getInt("potiontype");
			}
		}
		return 0;
	}

	public static void setPotionType(ItemStack stack, int value){
		if (stack.getItem() instanceof ItemBurret){
			NBTTagCompound tag = getItemTagCompound(stack);
			tag.setInt("potiontype", value);
		}
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
}
