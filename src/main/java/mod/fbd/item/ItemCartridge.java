package mod.fbd.item;

import java.util.ArrayList;
import java.util.List;

import mod.fbd.intaractionobject.IntaractionObjectCartridge;
import mod.fbd.inventory.InventoryCartridge;
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
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ItemCartridge extends Item {
	public static final int BURRET_MAX = 10;
	public static final int RELOAD_TIME = 10;

	protected InventoryCartridge inventory;

	public ItemCartridge(Item.Properties propety){
		super(propety);
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
    		if (gunStack.getItem() instanceof ItemCartridge){
    			NetworkHooks.openGui((EntityPlayerMP)playerIn,
            			new IntaractionObjectCartridge(EnumHand.MAIN_HAND),
            			(buf)->{
    						buf.writeInt(EnumHand.MAIN_HAND.ordinal());
    					});
            	//playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_CARTRIDGE, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
    		}
    		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, gunStack);
    	}else{
    		// オフハンドで持っている場合GUIを開く
    		ItemStack gunStack = playerIn.getHeldItem(handIn);
    		if (gunStack.getItem() instanceof ItemCartridge && playerIn.getHeldItemMainhand().isEmpty()){
    			NetworkHooks.openGui((EntityPlayerMP)playerIn,
            			new IntaractionObjectCartridge(EnumHand.OFF_HAND),
            			(buf)->{
    						buf.writeInt(EnumHand.OFF_HAND.ordinal());
    					});
            	//playerIn.openGui(Mod_FantomBlade.instance, ModGui.GUI_ID_CARTRIDGE, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
    		}
    		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, gunStack);
    	}
    }

    public IInventory getInventory(ItemStack stack){
    	this.inventory = new InventoryCartridge(stack);
    	return inventory;
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
