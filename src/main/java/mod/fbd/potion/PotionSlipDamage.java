package mod.fbd.potion;

import javax.annotation.Nonnull;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public class PotionSlipDamage extends Potion {

    public PotionSlipDamage(){
        super(true, 0xFFFFFF);
    }

    @Override
    public void performEffect(@Nonnull EntityLivingBase entityLivingBaseIn, int p_76394_2_){
    	// 体力が0になるまでスリップダメージを与える
        if (entityLivingBaseIn.getHealth() > 0.0F)
        {
            entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);
        }
    }

    @Override
    public boolean isInstant(){
        return false;
    }

    @Override
    public boolean isReady(int duration, int amplifier){
        int j = 25 >> amplifier;

        if (j > 0)
        {
            return duration % j == 0;
        }
        else
        {
            return true;
        }
    }

    /*
    ポーションのアイコンを表示する際に実装する。
    @Override
    public int getStatusIconIndex(){
        //Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));//ポーションのアイコンのテクスチャの場所を指定する。
        return super.getStatusIconIndex();
    }

    @Override
    public boolean hasStatusIcon(){
        return true;
    }
    */
}