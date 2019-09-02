package mod.fbd.inventory;

import java.util.List;

import com.google.common.collect.Lists;

import mod.fbd.item.ItemBladePiece;
import mod.fbd.item.ItemCore;
import mod.fbd.item.ItemKatana;
import mod.fbd.item.ItemTamahagane;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryArmorSmith  implements IInventory {

    private final NonNullList<ItemStack> stacks;
    private List<IInventoryChangedListener> changeListeners;

    public InventoryArmorSmith()
    {
        this.stacks = NonNullList.<ItemStack>withSize(7, ItemStack.EMPTY);
    }

    /**
     * Add a listener that will be notified when any item in this inventory is modified.
     */
    public void addInventoryChangeListener(IInventoryChangedListener listener)
    {
        if (this.changeListeners == null)
        {
            this.changeListeners = Lists.<IInventoryChangedListener>newArrayList();
        }

        this.changeListeners.add(listener);
    }

    /**
     * removes the specified IInvBasic from receiving further change notices
     */
    public void removeInventoryChangeListener(IInventoryChangedListener listener)
    {
        this.changeListeners.remove(listener);
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getStackInSlot(int index)
    {
        return index >= 0 && index < this.stacks.size() ? (ItemStack)this.stacks.get(index) : ItemStack.EMPTY;
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.stacks, index, count);

        if (!itemstack.isEmpty())
        {
            this.markDirty();
        }

        return itemstack;
    }

    public ItemStack addItem(ItemStack stack)
    {
        ItemStack itemstack = stack.copy();

        for (int i = 0; i < this.stacks.size(); ++i)
        {
            ItemStack itemstack1 = this.getStackInSlot(i);

            if (itemstack1.isEmpty())
            {
                this.setInventorySlotContents(i, itemstack);
                this.markDirty();
                return ItemStack.EMPTY;
            }

            if (ItemStack.areItemsEqual(itemstack1, itemstack))
            {
                int j = Math.min(this.getInventoryStackLimit(), itemstack1.getMaxStackSize());
                int k = Math.min(itemstack.getCount(), j - itemstack1.getCount());

                if (k > 0)
                {
                    itemstack1.grow(k);
                    itemstack.shrink(k);

                    if (itemstack.isEmpty())
                    {
                        this.markDirty();
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (itemstack.getCount() != stack.getCount())
        {
            this.markDirty();
        }

        return itemstack;
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack itemstack = this.stacks.get(index);

        if (itemstack.isEmpty())
        {
            return ItemStack.EMPTY;
        }
        else
        {
            this.stacks.set(index, ItemStack.EMPTY);
            return itemstack;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.stacks.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.stacks.size();
    }

    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.stacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public ITextComponent getName()
    {
        return new TextComponentTranslation("inventory.armorsmith");
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName()
    {
        return false;
    }

    /**
     * Sets the name of this inventory. This is displayed to the client on opening.
     */
    public void setCustomName(String inventoryTitleIn)
    {
    }

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    public ITextComponent getDisplayName()
    {
        return this.getName();
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    public void markDirty()
    {
        if (this.changeListeners != null)
        {
            for (int i = 0; i < this.changeListeners.size(); ++i)
            {
                ((IInventoryChangedListener)this.changeListeners.get(i)).onInventoryChanged(this);
            }
        }
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return true;
    }

    public void openInventory(EntityPlayer player)
    {
    }

    public void closeInventory(EntityPlayer player)
    {
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return allowItem(index,stack);
    }

    public int getField(int id)
    {
        return 0;
    }

    public void setField(int id, int value)
    {
    }

    public int getFieldCount()
    {
        return 0;
    }

    public void clear()
    {
        this.stacks.clear();
    }
    public static boolean allowItem(int index ,ItemStack stack){
    	if (index == 2){
    		return (stack.getItem() instanceof ItemKatana);
    	}else if(index == 3){
    		return (stack.getItem() == ItemCore.item_hunmmer_big);
    	}else if (index == 4){
    		return (stack.getItem() == ItemCore.item_hunmmer_small);
    	}else if (index == 5){
    		return (stack.getItem() == Items.SHEARS);
    	}else if (index == 6){
    		return (stack.getItem() == Items.COAL);
    	}else{
    		return ((stack.getItem() instanceof ItemTamahagane) ||
    			(stack.getItem() instanceof ItemBladePiece));
    	}
    }

    public int getRepaierCost(){
    	int cost = 0;
    	ItemStack stack = stacks.get(2);
    	if (!stack.isEmpty() && stacks.get(2).isDamaged()){
    		cost = (int) MathHelper.absMax(stack.getDamage()/10 *(2.0F + ((float)(stack.getMaxDamage() - stack.getDamage()))/(float)stack.getMaxDamage()),1);
    	}
    	return cost;
    }

    public boolean canRepaier(){
    	int count = 0;
    	int cost = getRepaierCost();
    	for (int i = 0; i < 1; i++){
    		ItemStack stack = stacks.get(i);
    		if (stack.getItem() instanceof ItemTamahagane){
    			count += MathHelper.fastFloor(stack.getCount() * ((ItemTamahagane)stack.getItem()).getRepair());
    		}
    		if (count >= cost ){
    			break;
    		}
    	}
    	return (count >= cost);
    }


	public void repairArmor() {
		int count = 0;
		int cost = this.getRepaierCost();
    	for (int i = 0; i < 1; i++){
    		ItemStack stack = stacks.get(i);
    		if (stack.getItem() instanceof ItemTamahagane){
    			if (count + MathHelper.fastFloor(stack.getCount() * ((ItemTamahagane)stack.getItem()).getRepair()) <= cost){
    				count += MathHelper.fastFloor(stack.getCount() * ((ItemTamahagane)stack.getItem()).getRepair());
    				stacks.set(i, ItemStack.EMPTY);
    			}else{
    				int max = stack.getCount();
    				for (int j = 0; j < max; j++){
    					if (count >= cost){
    						stacks.get(i).shrink(j);
    						break;
    					}
    					count += MathHelper.fastFloor(((ItemTamahagane)stack.getItem()).getRepair());
    				}
    			}
        		if (count >= cost ){
        			break;
        		}
    		}

    	}
    	stacks.get(2).setDamage(0);
    	ItemKatana.setRustValue(stacks.get(2), 0);
	}



	public ItemStack getTamahagane(){
		return stacks.get(0);
	}

    public int getTamahagane(ItemStack grade){
		int ret = 0;
		for (ItemStack stack : stacks){
			if (stack.getItem() == grade.getItem()){
				ret += stack.getCount();
			}
		}
		return ret;
    }

    public ItemStack getBladePiece(){
    	return stacks.get(1);
    }

	public void setBlade(ItemStack katana) {
		stacks.set(2, katana);
		for (int i = 0; i < 2; i++){
			stacks.set(i, ItemStack.EMPTY);
		}
	}

	public boolean checkItem() {
		return (!stacks.get(3).isEmpty() &&
		!stacks.get(4).isEmpty() &&
		!stacks.get(5).isEmpty() &&
		(stacks.get(6).getCount() >= 64));
	}

	public ItemStack getArmor() {
		return stacks.get(0);
	}

	public ItemStack getMaterial() {
		return stacks.get(1);
	}

	@Override
	public ITextComponent getCustomName() {
		// TODO 自動生成されたメソッド・スタブ
		return this.getName();
	}
}
