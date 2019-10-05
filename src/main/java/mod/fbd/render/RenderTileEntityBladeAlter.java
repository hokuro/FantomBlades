package mod.fbd.render;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

import mod.fbd.model.ModelBladeAlter;
import mod.fbd.tileentity.TileEntityBladeAlter;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderTileEntityBladeAlter extends TileEntityRenderer<TileEntityBladeAlter> {
	private static ResourceLocation tex = new ResourceLocation("fbd:textures/entity/bladealter.png");
	private ModelBladeAlter mainModel = new ModelBladeAlter();


	@Override
	public void render(TileEntityBladeAlter te, double x, double y, double z, float partialTicks, int destroyStage) {
		dorender((TileEntityBladeAlter)te,x,y,z,partialTicks,destroyStage);
	}

	public void dorender(TileEntityBladeAlter te, double x, double y, double z, float partialTicks, int destroyStage) {
		double sx,sy,sz = 0.0D;
		double tx,ty,tz = 0.0D;
		sx = sy = sz = 0.03125D;
		tx = ty = tz = 0.5D;

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(5.0F, 4.0F, 1.0F);
            GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }else{
    		this.bindTexture(tex);
        }

		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x + 0.5F , (float) y +  0.06F, (float) z + 0.5F);
		GlStateManager.scalef(0.0625F,0.0625F,0.0625F);
		GlStateManager.rotatef(180,0F,0F,1F);
		int idx = te.getFace().getHorizontalIndex();
		GlStateManager.rotatef(90F * (idx+2),0F,1F,0F);
		GlStateManager.disableLighting();
		GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 15 * 16, 15 * 16);
		this.mainModel.render(te,1.0F);
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();


		this.mainModel.bladeRender(te,x,y,z,this);

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
	}

	public void bindTexturePublic(ResourceLocation location){
		this.bindTexture(location);
	}
}
