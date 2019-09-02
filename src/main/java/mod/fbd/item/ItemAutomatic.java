package mod.fbd.item;

import java.util.ArrayList;
import java.util.List;

import mod.fbd.config.ConfigValue;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.core.SoundManager;
import mod.fbd.entity.EntityBurret;
import mod.fbd.intaractionobject.IntaractionObjectAutomatic;
import mod.fbd.inventory.InventoryAutomatic;
import mod.fbd.util.ModUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ItemAutomatic extends Item {
	public static final int BURRET_MAX = 6;
	public static final int RELOAD_TIME = 5;

	protected InventoryAutomatic inventory;

	public ItemAutomatic(Item.Properties property){
		super(property);
	}

    protected void gunFire(ItemStack stack, World worldIn, EntityPlayer entityLiving)
    {
    	if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
    		ItemStack burretStack = getNextBurret(stack);
            if (!worldIn.isRemote)
            {
            	EntityBurret entityburret = new EntityBurret(worldIn, entityLiving, burretStack);
            	entityburret.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, ItemBurret.getGunPowder(burretStack)+1 * 5.0F, 0.1F);
                stack.damageItem(1, entityplayer);
                worldIn.spawnEntity(entityburret);
            }
            // 残弾を減らす
            shootBurret(stack);
            worldIn.playSound((EntityPlayer)null,
            		entityplayer.posX,
            		entityplayer.posY,
            		entityplayer.posZ,
            		SoundManager.sound_gun_gunshot,
            		SoundCategory.PLAYERS, 1.0F,
            		(float)(1.0F / (ModUtil.randomF() * 0.4F + 1.2F) + ItemBurret.getGunPowder(burretStack)+1 * 0.5F));
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
    		ItemStack burretStack = getNextBurret(gunStack);
    		if (getReloadTime(gunStack) <= 0){
        		if (burretStack.isEmpty()){
        			// 残弾無し
            		if (ConfigValue.GENERAL.GunGuiAutoOpen() && offHand.isEmpty()){
            			// 片手が空いてるなら弾込めする
            			// GUI表示
                		NetworkHooks.openGui((EntityPlayerMP)playerIn,
                    			new IntaractionObjectAutomatic(EnumHand.MAIN_HAND),
                    			(buf)->{
            						buf.writeInt(EnumHand.MAIN_HAND.ordinal());
            					});
                    	//playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_AUTOMATIC_MAINHAND, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
            		}else if (offHand.getItem() instanceof ItemCartridge && ItemCartridge.getBurrets(offHand).size() > 0){
            			// 弾入りのカートリッジを持っている場合交換
        				ItemStack retcartridge = reload(gunStack,offHand.copy());
            			if (!worldIn.isRemote){
                			playerIn.setHeldItem(EnumHand.OFF_HAND,ItemStack.EMPTY);
                			if (!retcartridge.isEmpty()){
                				//　元のカートリッジを足元へ
                				ModUtil.spawnItemStack(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, retcartridge, Mod_FantomBlade.rnd);
                			}
            			}
            			// リロード時間設定(6発分)
            			setReloadTime(gunStack,RELOAD_TIME*2);
            			worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundManager.sound_gun_reload, SoundCategory.PLAYERS, 1.0F, (float)(1.0F / (ModUtil.randomF() * 0.4F + 1.2F)));
            		}else{
               		    worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundManager.sound_gun_noburret, SoundCategory.PLAYERS, 1.0F,  (float)(1.0F / (ModUtil.randomF() * 0.4F + 1.2F)));
        			}
        		}else{
        			if (ModUtil.random(100000) > 8){
        				// 残弾ありなら弾を打つ
        				gunFire(gunStack, worldIn, playerIn);
            			// リロード時間設定
            			setReloadTime(gunStack,RELOAD_TIME);
        			}else{
        				// 0.0008 %の確率でジャム
        	            // 詰まった弾を捨てる
        	            shootBurret(gunStack);
            			// リロード時間設定(2.5秒)
            			setReloadTime(gunStack,RELOAD_TIME*10);
        			}

        		}
    		}
    		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, gunStack);
    	}else{
    		// オフハンドで持っている場合GUIを開く
    		ItemStack gunStack = playerIn.getHeldItem(handIn);
    		if (gunStack.getItem() instanceof ItemAutomatic && playerIn.getHeldItemMainhand().isEmpty()){
    			NetworkHooks.openGui((EntityPlayerMP)playerIn,
            			new IntaractionObjectAutomatic(EnumHand.OFF_HAND),
            			(buf)->{
    						buf.writeInt(EnumHand.OFF_HAND.ordinal());
    					});
            	//playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_AUTOMATIC_OFFHAND, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
    		}
    		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, gunStack);
    	}
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	if (stack.getItem() instanceof ItemAutomatic){
    		int time = getReloadTime(stack);
    		if (time > 0){
    			time--;
    			setReloadTime(stack,time);
    		}
    	}
    }

    public IInventory getInventory(ItemStack stack){
    	this.inventory = new InventoryAutomatic(stack);
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
    	ItemStack cartridge = getCartridge(stack);
    	if (cartridge.isEmpty()){
    		return burrets;
    	}
    	return ItemCartridge.setBurrets(cartridge,burrets);
    }

    public static List<ItemStack> getBurrets(ItemStack stack){
    	List<ItemStack> retList = new ArrayList<ItemStack>();

    	ItemStack cartridge = getCartridge(stack);
    	if (cartridge.isEmpty()){
    		return retList;
    	}
    	return ItemCartridge.getBurrets(cartridge);
    }

    public static ItemStack reload(ItemStack stack, ItemStack cartridge){
    	List<ItemStack> setStack = new ArrayList<ItemStack>();
    	return setCartridge(stack,cartridge);
    }

    public static ItemStack setCartridge(ItemStack stack, ItemStack cartridge){
    	NBTTagCompound tag = getItemTagCompound(stack);
    	ItemStack ret= getCartridge(stack).copy();
    	if (cartridge.isEmpty()){
    		tag.removeTag("cartridge");
    	}else{
    		tag.setTag("cartridge", cartridge.write(new NBTTagCompound()));
    	}
    	return ret;
    }

    public static ItemStack getCartridge(ItemStack stakc){
    	NBTTagCompound tag = getItemTagCompound(stakc);
    	if (tag.hasKey("cartridge")){
    		return ItemStack.read(tag.getCompound("cartridge"));
    	}
    	return ItemStack.EMPTY;
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
