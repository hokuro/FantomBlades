// Date: 2018/04/26 2:10:01
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package mod.fbd.model;

import mod.fbd.render.RenderTileEntityBladeStand;
import mod.fbd.tileentity.TileEntityBladeStand;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBladeStand2 extends ModelBlaseStandBase
{
  //fields
    ModelRenderer stand1;
    ModelRenderer stand2;
    ModelRenderer stand3;
    ModelRenderer arm11;
    ModelRenderer arm12;
    ModelRenderer arm13;
    ModelRenderer arm21;
    ModelRenderer arm22;
    ModelRenderer arm23;
    ModelRenderer base1;
    ModelRenderer base2;
    ModelRenderer base3;
    ModelRenderer base4;
    ModelRenderer base5;
    ModelRenderer base6;
    ModelRenderer base7;
    ModelRenderer base8;
    ModelRenderer base9;

  public ModelBladeStand2()
  {
    textureWidth = 128;
    textureHeight = 64;

      stand1 = new ModelRenderer(this, 0, 12);
      stand1.addBox(-1F, -5F, 5F, 2, 5, 1);
      stand1.setRotationPoint(0F, 0F, 0F);
      stand1.setTextureSize(128, 64);
      stand1.mirror = true;
      setRotation(stand1, 0F, 0F, 0F);
      stand2 = new ModelRenderer(this, 0, 6);
      stand2.addBox(-1F, -10F, 6F, 2, 5, 1);
      stand2.setRotationPoint(0F, 0F, 0F);
      stand2.setTextureSize(128, 64);
      stand2.mirror = true;
      setRotation(stand2, 0F, 0F, 0F);
      stand3 = new ModelRenderer(this, 0, 0);
      stand3.addBox(-1F, -15F, 7F, 2, 5, 1);
      stand3.setRotationPoint(0F, 0F, 0F);
      stand3.setTextureSize(128, 64);
      stand3.mirror = true;
      setRotation(stand3, 0F, 0F, 0F);
      arm11 = new ModelRenderer(this, 11, 0);
      arm11.addBox(1F, -14F, 7F, 1, 1, 1);
      arm11.setRotationPoint(0F, 0F, 0F);
      arm11.setTextureSize(128, 64);
      arm11.mirror = true;
      setRotation(arm11, 0F, 0F, 0F);
      arm12 = new ModelRenderer(this, 18, 0);
      arm12.addBox(0F, 0F, -3F, 1, 1, 3);
      arm12.setRotationPoint(2F, -14F, 7F);
      arm12.setTextureSize(128, 64);
      arm12.mirror = true;
      setRotation(arm12, 0F, 0.3490659F, 0F);
      arm13 = new ModelRenderer(this, 30, 0);
      arm13.addBox(0F, 0F, 0F, 1, 1, 1);
      arm13.setRotationPoint(1.2F, -14F, 3.4F);
      arm13.setTextureSize(128, 64);
      arm13.mirror = true;
      setRotation(arm13, 0F, -0.7330383F, 0F);
      arm21 = new ModelRenderer(this, 11, 6);
      arm21.addBox(-2F, -14F, 7F, 1, 1, 1);
      arm21.setRotationPoint(0F, 0F, 0F);
      arm21.setTextureSize(128, 64);
      arm21.mirror = true;
      setRotation(arm21, 0F, 0F, 0F);
      arm22 = new ModelRenderer(this, 18, 6);
      arm22.addBox(0F, 0F, -3F, 1, 1, 3);
      arm22.setRotationPoint(-3F, -14F, 7F);
      arm22.setTextureSize(128, 64);
      arm22.mirror = true;
      setRotation(arm22, 0F, -0.3490659F, 0F);
      arm23 = new ModelRenderer(this, 30, 6);
      arm23.addBox(0F, 0F, 0F, 1, 1, 1);
      arm23.setRotationPoint(-2F, -14F, 4.4F);
      arm23.setTextureSize(128, 64);
      arm23.mirror = true;
      setRotation(arm23, 0F, 0.7330383F, 0F);
      base1 = new ModelRenderer(this, 12, 13);
      base1.addBox(-2F, 0F, -8F, 4, 1, 14);
      base1.setRotationPoint(0F, 0F, 0F);
      base1.setTextureSize(128, 64);
      base1.mirror = true;
      setRotation(base1, 0F, 0F, 0F);
      base2 = new ModelRenderer(this, 48, 13);
      base2.addBox(2F, 0F, -7F, 2, 1, 12);
      base2.setRotationPoint(0F, 0F, 0F);
      base2.setTextureSize(128, 64);
      base2.mirror = true;
      setRotation(base2, 0F, 0F, 0F);
      base3 = new ModelRenderer(this, 76, 13);
      base3.addBox(4F, 0F, -6F, 1, 1, 10);
      base3.setRotationPoint(0F, 0F, 0F);
      base3.setTextureSize(128, 64);
      base3.mirror = true;
      setRotation(base3, 0F, 0F, 0F);
      base4 = new ModelRenderer(this, 98, 13);
      base4.addBox(5F, 0F, -5F, 1, 1, 8);
      base4.setRotationPoint(0F, 0F, 0F);
      base4.setTextureSize(128, 64);
      base4.mirror = true;
      setRotation(base4, 0F, 0F, 0F);
      base5 = new ModelRenderer(this, 116, 13);
      base5.addBox(6F, 0F, -3F, 1, 1, 5);
      base5.setRotationPoint(0F, 0F, 0F);
      base5.setTextureSize(128, 64);
      base5.mirror = true;
      setRotation(base5, 0F, 0F, 0F);
      base6 = new ModelRenderer(this, 48, 28);
      base6.addBox(-4F, 0F, -7F, 2, 1, 12);
      base6.setRotationPoint(0F, 0F, 0F);
      base6.setTextureSize(128, 64);
      base6.mirror = true;
      setRotation(base6, 0F, 0F, 0F);
      base7 = new ModelRenderer(this, 76, 28);
      base7.addBox(-5F, 0F, -6F, 1, 1, 10);
      base7.setRotationPoint(0F, 0F, 0F);
      base7.setTextureSize(128, 64);
      base7.mirror = true;
      setRotation(base7, 0F, 0F, 0F);
      base8 = new ModelRenderer(this, 98, 28);
      base8.addBox(-6F, 0F, -5F, 1, 1, 8);
      base8.setRotationPoint(0F, 0F, 0F);
      base8.setTextureSize(128, 64);
      base8.mirror = true;
      setRotation(base8, 0F, 0F, 0F);
      base9 = new ModelRenderer(this, 116, 28);
      base9.addBox(-7F, 0F, -3F, 1, 1, 5);
      base9.setRotationPoint(0F, 0F, 0F);
      base9.setTextureSize(128, 64);
      base9.mirror = true;
      setRotation(base9, 0F, 0F, 0F);
  }

  @Override
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    stand1.render(f5);
    stand2.render(f5);
    stand3.render(f5);
    arm11.render(f5);
    arm12.render(f5);
    arm13.render(f5);
    arm21.render(f5);
    arm22.render(f5);
    arm23.render(f5);
    base1.render(f5);
    base2.render(f5);
    base3.render(f5);
    base4.render(f5);
    base5.render(f5);
    base6.render(f5);
    base7.render(f5);
    base8.render(f5);
    base9.render(f5);
  }



  @Override
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent)
  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
  }

  @Override
  public void render(TileEntityBladeStand entity, float scale) {
  	stand1.render(scale);
      stand2.render(scale);
      stand3.render(scale);
      arm11.render(scale);
      arm12.render(scale);
      arm13.render(scale);
      arm21.render(scale);
      arm22.render(scale);
      arm23.render(scale);
      base1.render(scale);
      base2.render(scale);
      base3.render(scale);
      base4.render(scale);
      base5.render(scale);
      base6.render(scale);
      base7.render(scale);
      base8.render(scale);
      base9.render(scale);
  }
  @Override
  public void setRotation(ModelRenderer model, float x, float y, float z)
  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
  }


  private ModelKatana katana1 = new ModelKatana();

  private static float offtx=-0.25F;
  private static float offty=0.59375F;
  private static float offtz=0.09375F;
  private static float rotx=0;
  private static float roty=90;
  private static float rotz=68;
  private static float angle = -70;
  @Override
  public void bladeRender(TileEntityBladeStand stand, double x, double y, double z, RenderTileEntityBladeStand render){
	  boolean has1 = stand.hasKatana(0);
	  katana1.setVisible(has1);
	  if (has1){
			GlStateManager.pushMatrix();
			int idx = stand.getFace().getHorizontalIndex();
			if (idx == 3){
				offtx = -0.25F;
				offty = 0.59375F;
				offtz = 0.09375F;
				angle = -70;
			}else if (idx == 1){
				offtx = 0.25F;
				offty = 0.59375F;
				offtz = -0.09375F;
				angle = -70;
			}else if (idx == 0){
				offtz = -0.1875F;
				offty = 0.59375F;
				offtx = 0.09375F;
				angle = -110;
			}else if (idx==2){
				offtx = -0.09375F;
				offty = 0.59375F;
				offtz=0.1875F;
				angle=-110F;
			}
			GlStateManager.translatef((float) x + offtx + 0.5F , (float) y + offty +  0.06F, (float) z + offtz + 0.5F);
			GlStateManager.scaled(0.0625D,0.0625D,0.0625D);
			GlStateManager.rotatef(90F * (idx+2),0,1,0);
			GlStateManager.rotatef(-90F,0,1,0);;
			GlStateManager.rotatef(angle,0,0,1);
			render.bindTexturePublic(stand.getKatanaTexture(0));
			this.katana1.render(null, 0, 0, 0, 0, 0, 1.0F);
			GlStateManager.popMatrix();
	  }
  }
}
