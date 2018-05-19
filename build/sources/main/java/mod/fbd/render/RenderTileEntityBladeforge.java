package mod.fbd.render;

import java.nio.FloatBuffer;

import mod.fbd.model.ModelAirPomp;
import mod.fbd.tileentity.TileEntityBladeforge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderTileEntityBladeforge  extends TileEntitySpecialRenderer<TileEntityBladeforge> {
	private static final ResourceLocation tex = new ResourceLocation("fbd:textures/entity/airpomp.png");

	private ModelAirPomp mainModel = new ModelAirPomp();

	@Override
	public void render(TileEntityBladeforge te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		dorender((TileEntityBladeforge)te,x,y,z,64);

	}


    public static final FloatBuffer DEPTH = GLAllocation.createDirectFloatBuffer(32);
    public static final FloatBuffer BLEND = GLAllocation.createDirectFloatBuffer(32);

    protected void dorender(TileEntityBladeforge entityIn, double x, double y, double z, int maxDistance)
    {
    	if (!entityIn.isRun()){return;}
		RenderManager manager = Minecraft.getMinecraft().getRenderManager();
		double d0 = entityIn.getDistanceSq(manager.renderViewEntity.posX,
     		   manager.renderViewEntity.posY,
     		   manager.renderViewEntity.posZ);

        if (d0 <= (double)(maxDistance*maxDistance))
        {
            float f = manager.playerViewY;
            float f1 = manager.playerViewX;
            boolean flag1 = manager.options.thirdPersonView == 2;
            float f2 = 1.0F + 0.5F;
            Tessellator tessellator;
            BufferBuilder bufferbuilder;

            GlStateManager.pushMatrix();
            GlStateManager.translate(x+0.5F, y+f2, z);
            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-f, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float)(flag1 ? -1 : 1) * f1, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-0.025F, -0.025F, 0.025F);
//			GlStateManager.depthFunc(519);

			// 下地
            renderRectangle(-25,25,-1,8,0.0F,0.0F,0.0F,0.25F);

            // 進捗バー
            int bar =(int)(48.0F * ((float)(TileEntityBladeforge.SMELTING_FINISH-entityIn.getField(TileEntityBladeforge.FIELD_COUNT))/(float)TileEntityBladeforge.SMELTING_FINISH));
            renderRectangle(-24,bar-24,0,7, 0.0F, 24.0F,0.0F,0.6F);
//            GlStateManager.disableDepth();
            GlStateManager.popMatrix();
        }
    }


    public void renderRectangle(double left, double right, double top, double bottom, float red, float green, float blue, float alpha){
        Tessellator tessellator;
        BufferBuilder bufferbuilder;
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        tessellator = Tessellator.getInstance();
        bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)(left), (double)(top), 0.0D).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)(left), (double)(bottom), 0.0D).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)(right), (double)(bottom), 0.0D).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)(right), (double)(top), 0.0D).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableLighting();
	    GlStateManager.disableBlend();
	    GlStateManager.enableAlpha();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
    }
}
