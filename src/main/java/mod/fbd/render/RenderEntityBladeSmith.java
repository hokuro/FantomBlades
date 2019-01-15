package mod.fbd.render;

import mod.fbd.entity.mob.EntityBladeSmith;
import mod.fbd.entity.mob.EntitySmithBase;
import mod.fbd.model.ModelBladeSmith;
import mod.fbd.resource.TextureInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityBladeSmith extends RenderLiving<EntitySmithBase>
{
	/**
	 * デフォルトテクスチャ
	 */
	protected ResourceLocation defaultTexture;
	/**
	 * カスタムテクスチャ
	 */
	protected ResourceLocation dynamicTexture = null;

    public RenderEntityBladeSmith(RenderManager renderManagerIn, float f)
    {
        super(renderManagerIn, new ModelBladeSmith(),f);
    }

    public ModelBladeSmith getMainModel()
    {
        return (ModelBladeSmith)super.getMainModel();
    }

    @Override
    protected void preRenderCallback(EntitySmithBase entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.9375F;
        this.shadowSize = 0.5F;
        GlStateManager.scale(f, f, f);
    }

    @Override
    public void doRender(EntitySmithBase entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        if (entity.Dw_ISWORK()){
        	((ModelBladeSmith)mainModel).swing(entity.Dw_Swing());
        }else{
        	((ModelBladeSmith)mainModel).resetSwing();
        }
    }

	@Override
	protected ResourceLocation getEntityTexture(EntitySmithBase entity) {
		if (entity != null && (dynamicTexture == null || !entity.updateTexture())){
			TextureInfo tex = entity.getTexture();
			if (tex != null){
				try {
					// 特製テクスチャを読み込む
					dynamicTexture = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation(entity.getUniqueID() + "_texture",new DynamicTexture(tex.Image()));;
				} catch (Exception e) {
					// 読み込めない場合デフォルトテクスチャを使用する
					dynamicTexture = null;
				}
				entity.updateTexture(true);
			}
		}
		return dynamicTexture!=null?dynamicTexture:EntityBladeSmith.TEXTURE_DEFAULT;
	}
}