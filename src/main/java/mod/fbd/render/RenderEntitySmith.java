package mod.fbd.render;

import com.mojang.blaze3d.platform.GlStateManager;

import mod.fbd.entity.mob.EntityArmorSmith;
import mod.fbd.entity.mob.EntityBladeSmith;
import mod.fbd.entity.mob.EntitySmithBase;
import mod.fbd.model.ModelSmithBase;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderEntitySmith extends LivingRenderer<EntitySmithBase, ModelSmithBase<EntitySmithBase>> {
	/**
	* デフォルトテクスチャ
	*/
	protected ResourceLocation defaultTexture;
	/**
	* カスタムテクスチャ
	*/
	protected ResourceLocation dynamicTexture = null;

	public RenderEntitySmith(EntityRendererManager renderManagerIn){
		this(renderManagerIn,0);
	}

    public RenderEntitySmith(EntityRendererManager renderManagerIn, float f)
    {
        super(renderManagerIn, new ModelSmithBase(),f);
    }

    public ModelSmithBase getMainModel(){
        return (ModelSmithBase)super.getEntityModel();
    }

    @Override
    protected void preRenderCallback(EntitySmithBase entitylivingbaseIn, float partialTickTime) {
        float f = 0.9375F;
        this.shadowSize = 0.5F;
        GlStateManager.scalef(f, f, f);
    }

    @Override
    public void doRender(EntitySmithBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        if (entity.Dw_ISWORK()){
        	((ModelSmithBase)getMainModel()).swing(entity.Dw_Swing());
        }else{
        	((ModelSmithBase)getMainModel()).resetSwing();
        }
    }

	@Override
	protected ResourceLocation getEntityTexture(EntitySmithBase entity) {
		ResourceLocation ret = null;
		if (entity != null) {
			ret = entity.getTexture();
		}else {
			if (entity instanceof EntityBladeSmith) {ret = EntityBladeSmith.TEXTURE_DEFAULT;}
			else if (entity instanceof EntityArmorSmith) {ret = EntityArmorSmith.TEXTURE_DEFAULT;}
			else {ret = dynamicTexture!=null?dynamicTexture:EntitySmithBase.TEXTURE_SMITH_DEFAULT;}
		}
		return ret;
	}
}
