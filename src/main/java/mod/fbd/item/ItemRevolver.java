package mod.fbd.item;

import java.util.ArrayList;
import java.util.List;

import mod.fbd.config.ConfigValue;
import mod.fbd.core.SoundManager;
import mod.fbd.entity.EntityBurret;
import mod.fbd.intaractionobject.IntaractionObjectRevolver;
import mod.fbd.inventory.InventoryRevolver;
import mod.fbd.util.ModUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ItemRevolver extends Item{
	public static final int BURRET_MAX = 6;
	public static final int RELOAD_TIME = 10;

	protected InventoryRevolver inventory;

	public ItemRevolver(Item.Properties property){
		super(property);
	}

    protected void gunFire(ItemStack stack, World worldIn, EntityPlayer entityLiving)
    {
    	if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
    		ItemStack burretStack = ItemRevolver.getNextBurret(stack);
            if (!worldIn.isRemote)
            {
            	EntityBurret entityburret = new EntityBurret(worldIn, entityLiving, burretStack);
            	entityburret.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, ItemBurret.getGunPowder(burretStack)+1 * 5.0F, 0.1F);
                stack.damageItem(1, entityplayer);
                worldIn.spawnEntity(entityburret);
            }
            // 残弾を減らす
            shootBurret(stack);
            worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundManager.sound_gun_gunshot, SoundCategory.PLAYERS, 1.0F, 1.0F / (ModUtil.randomF() * 0.4F + 1.2F) + ItemBurret.getGunPowder(burretStack)+1 * 0.5F);
        }
    }

    @Override
    public EnumAction getUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	if (handIn == EnumHand.MAIN_HAND){
    		ItemStack gunStack = playerIn.getHeldItem(handIn);
    		ItemStack offHand = playerIn.getHeldItemOffhand();
    		ItemStack burretStack = ItemRevolver.getNextBurret(gunStack);
    		if (getReloadTime(gunStack) <= 0){
        		if (burretStack.isEmpty()){
        			// 残弾無し
            		if (ConfigValue.GENERAL.GunGuiAutoOpen() && offHand.isEmpty()){
            			// 片手が空いてるなら弾込めする
            			NetworkHooks.openGui((EntityPlayerMP)playerIn,
                    			new IntaractionObjectRevolver(EnumHand.MAIN_HAND),
                    			(buf)->{
            						buf.writeInt(EnumHand.MAIN_HAND.ordinal());
            					});
                    	//playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_REVOLVER_MAINHAND, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
            		}else if (offHand.getItem() instanceof ItemBurret){
            			// 銃弾を持ってるなら弾込め
        				ItemStack newOffHand = reload(gunStack,offHand.copy());
            			if (!worldIn.isRemote){
                			playerIn.setHeldItem(EnumHand.OFF_HAND,newOffHand);
            			}
            			// リロード時間設定(6発分)
            			setReloadTime(gunStack,RELOAD_TIME*6);
            		}else{
            		    worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundManager.sound_gun_noburret, SoundCategory.PLAYERS, 1.0F, 1.0F / (ModUtil.randomF() * 0.4F + 1.2F));
            		}
        		}else{
        			// 残弾蟻なら弾を打つ
        			gunFire(gunStack, worldIn, playerIn);

        			// リロード時間設定
        			setReloadTime(gunStack,RELOAD_TIME);
        		}
    		}
    		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, gunStack);
    	}else{
    		// オフハンドで持っている場合GUIを開く
    		ItemStack gunStack = playerIn.getHeldItem(handIn);
    		if (gunStack.getItem() instanceof ItemRevolver && playerIn.getHeldItemMainhand().isEmpty()){
    			NetworkHooks.openGui((EntityPlayerMP)playerIn,
            			new IntaractionObjectRevolver(EnumHand.OFF_HAND),
            			(buf)->{
    						buf.writeInt(EnumHand.OFF_HAND.ordinal());
    					});
            	//playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_REVOLVER_OFFHAND, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
    		}
    		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, gunStack);
    	}
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	if (stack.getItem() instanceof ItemRevolver){
    		int time = getReloadTime(stack);
    		if (time > 0){
    			time--;
    			setReloadTime(stack,time);
    			if (time%RELOAD_TIME == 9){
    				worldIn.playSound((EntityPlayer)null, entityIn.posX, entityIn.posY, entityIn.posZ, SoundManager.sound_gun_reload, SoundCategory.PLAYERS, 1.0F, 1.0F / (ModUtil.randomF() * 0.4F + 1.2F));
    			}
    		}
    	}
    }

    public IInventory getInventory(ItemStack stack){
    	this.inventory = new InventoryRevolver(stack);
    	return inventory;
    }

    public static int getReloadTime(ItemStack stack){
    	NBTTagCompound tag = getItemTagCompound(stack);
    	if (tag.hasKey("reloadtime")){
    		return tag.getInt("reloadtime");
    	}
    	return 0;
    }

    public static void setReloadTime(ItemStack stack, int value){
    	NBTTagCompound tag = getItemTagCompound(stack);
    	tag.setInt("reloadtime", value);
    }

    public static ItemStack getNextBurret(ItemStack stack){
    	List<ItemStack> burrets = getBurrets(stack);
    	if (burrets.size() != 0){
    		return burrets.get(0);
    	}
    	return ItemStack.EMPTY;
    }

    public static void shootBurret(ItemStack stack){
    	List<ItemStack> burrets = getBurrets(stack);
    	if (burrets.size() != 0){
    		burrets.remove(0);
    	}
    	setBurrets(stack,burrets);
    }

    public static List<ItemStack> setBurrets(ItemStack stack, List<ItemStack> burrets){
    	List<ItemStack> retList = new ArrayList<ItemStack>();
    	NBTTagCompound tag = getItemTagCompound(stack);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < burrets.size(); ++i)
        {
            ItemStack itemstack = burrets.get(i);
            if (i < BURRET_MAX){
                if (!itemstack.isEmpty())
                {
                    nbttaglist.add(itemstack.write(new NBTTagCompound()));
                }
                itemstack = ItemStack.EMPTY;
            }
            retList.add(itemstack);
        }
        tag.setTag("burrets", nbttaglist);
        return retList;
    }

    public static List<ItemStack> getBurrets(ItemStack stack){
    	List<ItemStack> retList = new ArrayList<ItemStack>();
    	NBTTagCompound tag = getItemTagCompound(stack);
    	NBTTagList nbttaglist = tag.getList("burrets", 10);
        for (int i = 0; i < nbttaglist.size(); ++i)
        {
            ItemStack itemstack = ItemStack.read(nbttaglist.getCompound(i));
            if (!itemstack.isEmpty())
            {
            	retList.add(itemstack);
            }
        }
        return retList;
    }

    public static ItemStack reload(ItemStack stack, ItemStack burret){
    	List<ItemStack> setStack = new ArrayList<ItemStack>();
    	int loopMax = Math.min(burret.getCount(), BURRET_MAX);
    	ItemStack setBurret  = burret.copy();
    	setBurret.setCount(1);
    	for (int i = 0; i < loopMax; i++){
    		setStack.add(setBurret.copy());
    		burret.shrink(1);
    	}
    	ItemRevolver.setBurrets(stack, setStack);
    	if (burret.getCount() == 0){
    		burret = ItemStack.EMPTY;
    	}
    	return burret;
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
