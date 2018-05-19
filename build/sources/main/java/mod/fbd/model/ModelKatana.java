// Date: 2018/04/26 13:39:46
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package mod.fbd.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKatana extends ModelBase
{
  //fields
    ModelRenderer Shape16;
    ModelRenderer Shape17;
    ModelRenderer Shape18;
    ModelRenderer Shape19;
    ModelRenderer Shape20;

  public ModelKatana()
  {
    textureWidth = 64;
    textureHeight = 32;

      Shape16 = new ModelRenderer(this, 8, 0);
      Shape16.addBox(-6F, 0F, -2F, 16, 1, 1);
      Shape16.setRotationPoint(0F, 0F, 0F);
      Shape16.setTextureSize(64, 32);
      Shape16.mirror = true;
      setRotation(Shape16, 0F, 0F, 0F);
      Shape17 = new ModelRenderer(this, 0, 3);
      Shape17.addBox(-7F, -1F, -2F, 1, 3, 1);
      Shape17.setRotationPoint(0F, 0F, 0F);
      Shape17.setTextureSize(64, 32);
      Shape17.mirror = true;
      setRotation(Shape17, 0F, 0F, 0F);
      Shape18 = new ModelRenderer(this, 5, 5);
      Shape18.addBox(-7F, 0F, -1F, 1, 1, 1);
      Shape18.setRotationPoint(0F, 0F, 0F);
      Shape18.setTextureSize(64, 32);
      Shape18.mirror = true;
      setRotation(Shape18, 0F, 0F, 0F);
      Shape19 = new ModelRenderer(this, 5, 3);
      Shape19.addBox(-7F, 0F, -3F, 1, 1, 1);
      Shape19.setRotationPoint(0F, 0F, 0F);
      Shape19.setTextureSize(64, 32);
      Shape19.mirror = true;
      setRotation(Shape19, 0F, 0F, 0F);
      Shape20 = new ModelRenderer(this, 0, 0);
      Shape20.addBox(-10F, 0F, -2F, 3, 1, 1);
      Shape20.setRotationPoint(0F, 0F, 0F);
      Shape20.setTextureSize(64, 32);
      Shape20.mirror = true;
      setRotation(Shape20, 0F, 0F, 0F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape16.render(f5);
    Shape17.render(f5);
    Shape18.render(f5);
    Shape19.render(f5);
    Shape20.render(f5);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
  }

  @Override
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent)
  {
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
  }

  public void setVisible(boolean value){
	    Shape16.showModel = value;
	    Shape17.showModel = value;
	    Shape18.showModel = value;
	    Shape19.showModel = value;
	    Shape20.showModel = value;
  }

}
