package mod.fbd.item.guns;

import java.util.ArrayList;
import java.util.List;

import mod.fbd.inventory.ContainerCartridge;
import mod.fbd.inventory.InventoryCartridge;
import mod.fbd.item.ItemCore;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.NONE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
    	if (handIn == Hand.MAIN_HAND){
    		ItemStack gunStack = playerIn.getHeldItem(handIn);
    		if (gunStack.getItem() instanceof ItemCartridge){
    			if (!worldIn.isRemote) {
	    			NetworkHooks.openGui((ServerPlayerEntity)playerIn,
	            			new SimpleNamedProvider(Hand.MAIN_HAND),
	            			(buf)->{
	    						buf.writeInt(Hand.MAIN_HAND.ordinal());
	    					});
    			}
    		}
    		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, gunStack);
    	}else{
    		// iオフハンドで持っている場合GUIを開く
    		ItemStack gunStack = playerIn.getHeldItem(handIn);
    		if (gunStack.getItem() instanceof ItemCartridge && playerIn.getHeldItemMainhand().isEmpty()){
    			if (!worldIn.isRemote) {
	    			NetworkHooks.openGui((ServerPlayerEntity)playerIn,
	            			new SimpleNamedProvider(Hand.OFF_HAND),
	            			(buf)->{
	    						buf.writeInt(Hand.OFF_HAND.ordinal());
	    					});
    			}
    		}
    		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, gunStack);
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
    	CompoundNBT tag = getItemTagCompound(stack);
    	ListNBT nbttaglist = new ListNBT();
        for (int i = 0; i < burrets.size(); ++i)
        {
            ItemStack itemstack = burrets.get(i);
            if (i < BURRET_MAX){
                if (!itemstack.isEmpty())
                {
                    nbttaglist.add(itemstack.write(new CompoundNBT()));
                }
                itemstack = ItemStack.EMPTY;
            }
            retList.add(itemstack);
        }
        tag.put("burrets", nbttaglist);
        return retList;
    }

    public static List<ItemStack> getBurrets(ItemStack stack){
    	List<ItemStack> retList = new ArrayList<ItemStack>();
    	CompoundNBT tag = getItemTagCompound(stack);
    	ListNBT nbttaglist = tag.getList("burrets", 10);
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

    public static CompoundNBT getItemTagCompound(ItemStack stack){
		CompoundNBT tag;
		if(stack.hasTag()){
			tag = stack.getTag();
		}else{
			tag = new CompoundNBT();
			stack.setTag(tag);
		}
		return tag;
	}


    class SimpleNamedProvider implements INamedContainerProvider{
    	private Hand hand;

    	public SimpleNamedProvider(Hand handIn) {
    		hand = handIn;
    	}

		@Override
		public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
			ItemStack stack = player.getHeldItem(hand);
			if (stack.getItem() == ItemCore.item_cartridge) {
				return new ContainerCartridge(id, playerInv, ((ItemCartridge)stack.getItem()).getInventory(stack));
			}
			return null;
		}

		@Override
		public ITextComponent getDisplayName() {
			return new TranslationTextComponent("container.cartridge");
		}
    }
}
