// Date: 2018/04/28 13:12:49
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package net.minecraft.src;

public class Modelbladealter extends ModelBase
{
  //fields
    RendererModel base;
    RendererModel bladeRite;
    RendererModel bladeLeft;
    RendererModel bladeLeft2;
    RendererModel bladeLeft3;
    RendererModel bladeRight2;
    RendererModel bladeRight3;
    RendererModel door1;
    RendererModel door2;
    RendererModel kagami;
    RendererModel kagamidai1;
    RendererModel kagamidai2;
    RendererModel kagamidai3;
    RendererModel torii1;
    RendererModel torii2;
    RendererModel torii3;
    RendererModel torii4;
    RendererModel yane;
    RendererModel yaneL1;
    RendererModel yaneL2;
    RendererModel yaneL3;
    RendererModel yaneL4;
    RendererModel yaneR1;
    RendererModel yaneR2;
    RendererModel yaneR3;
    RendererModel yaneR4;
    RendererModel kabeback1;
    RendererModel kabeback2;
    RendererModel kabeback3;
    RendererModel kabeback4;
    RendererModel kabeback5;
    RendererModel kabeside1;
    RendererModel kabesaid2;
    RendererModel kabefront1;
    RendererModel kabefront2;
    RendererModel kabefront3;
    RendererModel kabefront4;
    RendererModel kabefront6;
    RendererModel kabefront5;
  
  public Modelbladealter()
  {
    textureWidth = 128;
    textureHeight = 256;
    
      base = new RendererModel(this, 0, 0);
      base.addBox(-8F, 0F, -8F, 16, 1, 16);
      base.setRotationPoint(0F, 0F, 0F);
      base.setTextureSize(128, 256);
      base.mirror = true;
      setRotation(base, 0F, 0F, 0F);
      bladeRite = new RendererModel(this, 66, 0);
      bladeRite.addBox(-7F, -2F, -7F, 1, 2, 1);
      bladeRite.setRotationPoint(0F, 0F, 0F);
      bladeRite.setTextureSize(128, 256);
      bladeRite.mirror = true;
      setRotation(bladeRite, 0F, 0F, 0F);
      bladeLeft = new RendererModel(this, 66, 0);
      bladeLeft.addBox(6F, -2F, -7F, 1, 2, 1);
      bladeLeft.setRotationPoint(0F, 0F, 0F);
      bladeLeft.setTextureSize(128, 256);
      bladeLeft.mirror = true;
      setRotation(bladeLeft, 0F, 0F, 0F);
      bladeLeft2 = new RendererModel(this, 66, 4);
      bladeLeft2.addBox(6F, -3F, -6F, 1, 2, 1);
      bladeLeft2.setRotationPoint(0F, 0F, 0F);
      bladeLeft2.setTextureSize(128, 256);
      bladeLeft2.mirror = true;
      setRotation(bladeLeft2, 0F, 0F, 0F);
      bladeLeft3 = new RendererModel(this, 66, 8);
      bladeLeft3.addBox(6F, -3F, -8F, 1, 2, 1);
      bladeLeft3.setRotationPoint(0F, 0F, 0F);
      bladeLeft3.setTextureSize(128, 256);
      bladeLeft3.mirror = true;
      setRotation(bladeLeft3, 0F, 0F, 0F);
      bladeRight2 = new RendererModel(this, 66, 4);
      bladeRight2.addBox(-7F, -3F, -6F, 1, 2, 1);
      bladeRight2.setRotationPoint(0F, 0F, 0F);
      bladeRight2.setTextureSize(128, 256);
      bladeRight2.mirror = true;
      setRotation(bladeRight2, 0F, 0F, 0F);
      bladeRight3 = new RendererModel(this, 66, 8);
      bladeRight3.addBox(-7F, -3F, -8F, 1, 2, 1);
      bladeRight3.setRotationPoint(0F, 0F, 0F);
      bladeRight3.setTextureSize(128, 256);
      bladeRight3.mirror = true;
      setRotation(bladeRight3, 0F, 0F, 0F);
      door1 = new RendererModel(this, 13, 54);
      door1.addBox(-5F, -3F, -1F, 5, 9, 1);
      door1.setRotationPoint(5F, -6F, -3F);
      door1.setTextureSize(128, 256);
      door1.mirror = true;
      setRotation(door1, 0F, 1.570796F, 0F);
      door2 = new RendererModel(this, 0, 54);
      door2.addBox(0F, -4F, -1F, 5, 9, 1);
      door2.setRotationPoint(-5F, -5F, -3F);
      door2.setTextureSize(128, 256);
      door2.mirror = true;
      setRotation(door2, 0F, -1.570796F, 0F);
      kagami = new RendererModel(this, 66, 54);
      kagami.addBox(-2F, -2F, -1F, 4, 4, 1);
      kagami.setRotationPoint(0F, -3F, 5F);
      kagami.setTextureSize(128, 256);
      kagami.mirror = true;
      setRotation(kagami, -0.1858931F, 0F, 0F);
      kagamidai1 = new RendererModel(this, 66, 61);
      kagamidai1.addBox(-3F, -1F, 3F, 6, 1, 2);
      kagamidai1.setRotationPoint(0F, 0F, 0F);
      kagamidai1.setTextureSize(128, 256);
      kagamidai1.mirror = true;
      setRotation(kagamidai1, 0F, 0F, 0F);
      kagamidai2 = new RendererModel(this, 66, 66);
      kagamidai2.addBox(2F, -2F, 3F, 1, 1, 2);
      kagamidai2.setRotationPoint(0F, 0F, 0F);
      kagamidai2.setTextureSize(128, 256);
      kagamidai2.mirror = true;
      setRotation(kagamidai2, 0F, 0F, 0F);
      kagamidai3 = new RendererModel(this, 75, 66);
      kagamidai3.addBox(-3F, -2F, 3F, 1, 1, 2);
      kagamidai3.setRotationPoint(0F, 0F, 0F);
      kagamidai3.setTextureSize(128, 256);
      kagamidai3.mirror = true;
      setRotation(kagamidai3, 0F, 0F, 0F);
      torii1 = new RendererModel(this, 72, 0);
      torii1.addBox(0F, -8F, 0F, 1, 8, 1);
      torii1.setRotationPoint(-4F, 0F, 2F);
      torii1.setTextureSize(128, 256);
      torii1.mirror = true;
      setRotation(torii1, 0F, 0F, 0.1134464F);
      torii2 = new RendererModel(this, 78, 0);
      torii2.addBox(0F, -8F, 0F, 1, 8, 1);
      torii2.setRotationPoint(3F, 0F, 2F);
      torii2.setTextureSize(128, 256);
      torii2.mirror = true;
      setRotation(torii2, 0F, 0F, -0.1134464F);
      torii3 = new RendererModel(this, 85, 0);
      torii3.addBox(-4F, -8.7F, 2F, 8, 1, 1);
      torii3.setRotationPoint(0F, 0F, 0F);
      torii3.setTextureSize(128, 256);
      torii3.mirror = true;
      setRotation(torii3, 0F, 0F, 0F);
      torii4 = new RendererModel(this, 89, 5);
      torii4.addBox(-3F, -7F, 2F, 6, 1, 1);
      torii4.setRotationPoint(0F, 0F, 0F);
      torii4.setTextureSize(128, 256);
      torii4.mirror = true;
      setRotation(torii4, 0F, 0F, 0F);
      yane = new RendererModel(this, 96, 53);
      yane.addBox(-1F, -15F, -6F, 2, 1, 14);
      yane.setRotationPoint(0F, 0F, 0F);
      yane.setTextureSize(128, 256);
      yane.mirror = true;
      setRotation(yane, 0F, 0F, 0F);
      yaneL1 = new RendererModel(this, 0, 20);
      yaneL1.addBox(1F, -14F, -6F, 2, 1, 14);
      yaneL1.setRotationPoint(0F, 0F, 0F);
      yaneL1.setTextureSize(128, 256);
      yaneL1.mirror = true;
      setRotation(yaneL1, 0F, 0F, 0F);
      yaneL2 = new RendererModel(this, 32, 20);
      yaneL2.addBox(3F, -13F, -6F, 2, 1, 14);
      yaneL2.setRotationPoint(0F, 0F, 0F);
      yaneL2.setTextureSize(128, 256);
      yaneL2.mirror = true;
      setRotation(yaneL2, 0F, 0F, 0F);
      yaneL3 = new RendererModel(this, 64, 20);
      yaneL3.addBox(5F, -12F, -6F, 2, 1, 14);
      yaneL3.setRotationPoint(0F, 0F, 0F);
      yaneL3.setTextureSize(128, 256);
      yaneL3.mirror = true;
      setRotation(yaneL3, 0F, 0F, 0F);
      yaneL4 = new RendererModel(this, 96, 20);
      yaneL4.addBox(7F, -11F, -6F, 2, 1, 14);
      yaneL4.setRotationPoint(0F, 0F, 0F);
      yaneL4.setTextureSize(128, 256);
      yaneL4.mirror = true;
      setRotation(yaneL4, 0F, 0F, 0F);
      yaneR1 = new RendererModel(this, 0, 36);
      yaneR1.addBox(-3F, -14F, -6F, 2, 1, 14);
      yaneR1.setRotationPoint(0F, 0F, 0F);
      yaneR1.setTextureSize(128, 256);
      yaneR1.mirror = true;
      setRotation(yaneR1, 0F, 0F, 0F);
      yaneR2 = new RendererModel(this, 32, 36);
      yaneR2.addBox(-5F, -13F, -6F, 2, 1, 14);
      yaneR2.setRotationPoint(0F, 0F, 0F);
      yaneR2.setTextureSize(128, 256);
      yaneR2.mirror = true;
      setRotation(yaneR2, 0F, 0F, 0F);
      yaneR3 = new RendererModel(this, 64, 36);
      yaneR3.addBox(-7F, -12F, -6F, 2, 1, 14);
      yaneR3.setRotationPoint(0F, 0F, 0F);
      yaneR3.setTextureSize(128, 256);
      yaneR3.mirror = true;
      setRotation(yaneR3, 0F, 0F, 0F);
      yaneR4 = new RendererModel(this, 96, 36);
      yaneR4.addBox(-9F, -11F, -6F, 2, 1, 14);
      yaneR4.setRotationPoint(0F, 0F, 0F);
      yaneR4.setTextureSize(128, 256);
      yaneR4.mirror = true;
      setRotation(yaneR4, 0F, 0F, 0F);
      kabeback1 = new RendererModel(this, 0, 78);
      kabeback1.addBox(-7F, -10F, 6F, 14, 10, 1);
      kabeback1.setRotationPoint(0F, 0F, 0F);
      kabeback1.setTextureSize(128, 256);
      kabeback1.mirror = true;
      setRotation(kabeback1, 0F, 0F, 0F);
      kabeback2 = new RendererModel(this, 0, 75);
      kabeback2.addBox(-7F, -11F, 6F, 13, 1, 1);
      kabeback2.setRotationPoint(0F, 0F, 0F);
      kabeback2.setTextureSize(128, 256);
      kabeback2.mirror = true;
      setRotation(kabeback2, 0F, 0F, 0F);
      kabeback3 = new RendererModel(this, 0, 72);
      kabeback3.addBox(-5F, -12F, 6F, 9, 1, 1);
      kabeback3.setRotationPoint(0F, 0F, 0F);
      kabeback3.setTextureSize(128, 256);
      kabeback3.mirror = true;
      setRotation(kabeback3, 0F, 0F, 0F);
      kabeback4 = new RendererModel(this, 0, 69);
      kabeback4.addBox(-3F, -13F, 6F, 5, 1, 1);
      kabeback4.setRotationPoint(0F, 0F, 0F);
      kabeback4.setTextureSize(128, 256);
      kabeback4.mirror = true;
      setRotation(kabeback4, 0F, 0F, 0F);
      kabeback5 = new RendererModel(this, 0, 66);
      kabeback5.addBox(-1F, -14F, 6F, 2, 1, 1);
      kabeback5.setRotationPoint(0F, 0F, 0F);
      kabeback5.setTextureSize(128, 256);
      kabeback5.mirror = true;
      setRotation(kabeback5, 0F, 0F, 0F);
      kabeside1 = new RendererModel(this, 0, 92);
      kabeside1.addBox(6F, -11F, -2F, 1, 11, 8);
      kabeside1.setRotationPoint(0F, 0F, 0F);
      kabeside1.setTextureSize(128, 256);
      kabeside1.mirror = true;
      setRotation(kabeside1, 0F, 0F, 0F);
      kabesaid2 = new RendererModel(this, 19, 92);
      kabesaid2.addBox(-7F, -11F, -2F, 1, 11, 8);
      kabesaid2.setRotationPoint(0F, 0F, 0F);
      kabesaid2.setTextureSize(128, 256);
      kabesaid2.mirror = true;
      setRotation(kabesaid2, 0F, 0F, 0F);
      kabefront1 = new RendererModel(this, 27, 54);
      kabefront1.addBox(-6F, -11F, -3F, 1, 11, 1);
      kabefront1.setRotationPoint(0F, 0F, 0F);
      kabefront1.setTextureSize(128, 256);
      kabefront1.mirror = true;
      setRotation(kabefront1, 0F, 0F, 0F);
      kabefront2 = new RendererModel(this, 32, 54);
      kabefront2.addBox(5F, -11F, -3F, 1, 11, 1);
      kabefront2.setRotationPoint(0F, 0F, 0F);
      kabefront2.setTextureSize(128, 256);
      kabefront2.mirror = true;
      setRotation(kabefront2, 0F, 0F, 0F);
      kabefront3 = new RendererModel(this, 39, 54);
      kabefront3.addBox(-1F, -14F, -5F, 2, 1, 1);
      kabefront3.setRotationPoint(0F, 0F, 0F);
      kabefront3.setTextureSize(128, 256);
      kabefront3.mirror = true;
      setRotation(kabefront3, 0F, 0F, 0F);
      kabefront4 = new RendererModel(this, 39, 57);
      kabefront4.addBox(-3F, -13F, -4F, 6, 1, 1);
      kabefront4.setRotationPoint(0F, 0F, 0F);
      kabefront4.setTextureSize(128, 256);
      kabefront4.mirror = true;
      setRotation(kabefront4, 0F, 0F, 0F);
      kabefront6 = new RendererModel(this, 39, 61);
      kabefront6.addBox(-5F, -11F, -4F, 10, 2, 1);
      kabefront6.setRotationPoint(0F, 0F, 0F);
      kabefront6.setTextureSize(128, 256);
      kabefront6.mirror = true;
      setRotation(kabefront6, 0F, 0F, 0F);
      kabefront5 = new RendererModel(this, 39, 66);
      kabefront5.addBox(-5F, -12F, -5F, 10, 1, 1);
      kabefront5.setRotationPoint(0F, 0F, 0F);
      kabefront5.setTextureSize(128, 256);
      kabefront5.mirror = true;
      setRotation(kabefront5, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    base.render(f5);
    bladeRite.render(f5);
    bladeLeft.render(f5);
    bladeLeft2.render(f5);
    bladeLeft3.render(f5);
    bladeRight2.render(f5);
    bladeRight3.render(f5);
    door1.render(f5);
    door2.render(f5);
    kagami.render(f5);
    kagamidai1.render(f5);
    kagamidai2.render(f5);
    kagamidai3.render(f5);
    torii1.render(f5);
    torii2.render(f5);
    torii3.render(f5);
    torii4.render(f5);
    yane.render(f5);
    yaneL1.render(f5);
    yaneL2.render(f5);
    yaneL3.render(f5);
    yaneL4.render(f5);
    yaneR1.render(f5);
    yaneR2.render(f5);
    yaneR3.render(f5);
    yaneR4.render(f5);
    kabeback1.render(f5);
    kabeback2.render(f5);
    kabeback3.render(f5);
    kabeback4.render(f5);
    kabeback5.render(f5);
    kabeside1.render(f5);
    kabesaid2.render(f5);
    kabefront1.render(f5);
    kabefront2.render(f5);
    kabefront3.render(f5);
    kabefront4.render(f5);
    kabefront6.render(f5);
    kabefront5.render(f5);
  }
  
  private void setRotation(RendererModel model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5);
  }

}
