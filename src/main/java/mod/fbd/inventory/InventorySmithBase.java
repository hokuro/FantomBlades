package mod.fbd.inventory;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import mod.fbd.entity.mob.EntitySmithBase;
import mod.fbd.item.piece.ItemBladePiece;
import mod.fbd.item.piece.ItemBladePiece.EnumBladePieceType;
import mod.fbd.item.piece.ItemTamahagane;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

public abstract class InventorySmithBase implements IInventory {
	public static final int INV_01_MATERIAL = 0;
	public static final int INV_02_PIECE = 1;
	public static final int INV_03_ENCHANT = 2;
	public static final int INV_04_POTION = 3;
	public static final int INV_05_RESULT = 4;
	public static final int INV_06_TOOL_BIGHAMMER=5;
	public static final int INV_07_TOOL_SMALLHAMMER = 6;
	public static final int INV_08_TOOL_SHEARS = 7;
	public static final int INV_09_TOOL_COAL = 8;

    protected final NonNullList<ItemStack> stacks;
    protected List<IInventoryChangedListener> changeListeners;
    protected EntitySmithBase smith;
    public InventorySmithBase(EntitySmithBase smithIn) {
        this.stacks = NonNullList.<ItemStack>withSize(INV_09_TOOL_COAL+1, ItemStack.EMPTY);
        smith = smithIn;
    }


    public void addInventoryChangeListener(IInventoryChangedListener listener) {
        if (this.changeListeners == null) {
            this.changeListeners = Lists.<IInventoryChangedListener>newArrayList();
        }
        this.changeListeners.add(listener);
    }

    public void removeInventoryChangeListener(IInventoryChangedListener listener) {
        this.changeListeners.remove(listener);
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index >= 0 && index < this.stacks.size() ? (ItemStack)this.stacks.get(index) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count)  {
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.stacks, index, count);

        if (!itemstack.isEmpty()) {
            this.markDirty();
        }

        return itemstack;
    }

    public ItemStack addItem(ItemStack stack) {
        ItemStack itemstack = stack.copy();

        for (int i = 0; i < this.stacks.size(); ++i) {
            ItemStack itemstack1 = this.getStackInSlot(i);

            if (itemstack1.isEmpty()) {
                this.setInventorySlotContents(i, itemstack);
                this.markDirty();
                return ItemStack.EMPTY;
            }

            if (ItemStack.areItemsEqual(itemstack1, itemstack)) {
                int j = Math.min(this.getInventoryStackLimit(), itemstack1.getMaxStackSize());
                int k = Math.min(itemstack.getCount(), j - itemstack1.getCount());

                if (k > 0) {
                    itemstack1.grow(k);
                    itemstack.shrink(k);

                    if (itemstack.isEmpty()) {
                        this.markDirty();
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (itemstack.getCount() != stack.getCount()) {
            this.markDirty();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack itemstack = this.stacks.get(index);

        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.stacks.set(index, ItemStack.EMPTY);
            return itemstack;
        }
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.stacks.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
    }

    @Override
    public int getSizeInventory() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        if (this.changeListeners != null) {
            for (int i = 0; i < this.changeListeners.size(); ++i) {
                ((IInventoryChangedListener)this.changeListeners.get(i)).onInventoryChanged(this);
            }
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void openInventory(PlayerEntity player) {
    }

    @Override
    public void closeInventory(PlayerEntity player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return allowItem(index,stack);
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }


    public int getRepaierCost(){
    	int cost = 0;
    	ItemStack stack = stacks.get(INV_05_RESULT);
    	if (!stack.isEmpty() && stack.isDamaged()){
    		cost = (int) MathHelper.absMax(stack.getDamage()/10 *(2.0F + ((float)(stack.getMaxDamage() - stack.getDamage()))/(float)stack.getMaxDamage()),1);
    	}
    	return cost;
    }

    public boolean canRepaier(){
    	int count = 0;
    	int cost = getRepaierCost();
    	for (int i = 0; i < this.INV_05_RESULT; i++){
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

	public void repair() {
		int count = 0;
		int cost = this.getRepaierCost();
    	for (int i = 0; i < this.INV_05_RESULT; i++){
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
    	stacks.get(INV_05_RESULT).setDamage(0);
	}

	public List<ItemStack> getTamahagane(){
		List<ItemStack> ret = new ArrayList<ItemStack>();
		for (ItemStack stack : stacks){
			if (stack.getItem() instanceof ItemTamahagane){
				ret.add(stack);
			}
		}
		return ret;
	}

    public int getTamahagane(Item tamahagane){
		int ret = 0;
		for (ItemStack stack : stacks){
			if (stack.getItem() == tamahagane){
				ret += stack.getCount();
			}
		}
		return ret;
    }

    public int getBladePiece(EnumBladePieceType piece){
    	int count = 0;
		for (ItemStack stack : stacks){
			if (stack.getItem() instanceof ItemBladePiece && ((ItemBladePiece)stack.getItem()).getPieceType() == piece){
				count += stack.getCount();
			}
		}
		return count;
    }

    public int[] getBladePiece() {
    	int[] ret = new int[EnumBladePieceType.values().length];
    	for (int i = 0; i < this.INV_05_RESULT; i++) {
    		ItemStack stack = stacks.get(i);
    		if (stack.getItem() instanceof ItemBladePiece) {
    			ret[((ItemBladePiece)stack.getItem()).getPieceType().getIndex()] += stack.getCount();
    		}
    	}
    	return ret;
    }

	public List<ItemStack> getPotions() {
		List<ItemStack> ret = new ArrayList<ItemStack>();
		for (ItemStack stack : stacks){
			if (stack.getItem() instanceof PotionItem){
				ret.add(stack);
			}
		}
		return ret;
	}

	public List<ItemStack> getEnchangedBooks(){
		List<ItemStack> ret = new ArrayList<ItemStack>();
		for (ItemStack stack: stacks){
			if (stack.getItem() instanceof EnchantedBookItem){
				ret.add(stack);
			}
		}
		return ret;
	}

	public boolean checkItem() {
		return (!stacks.get(INV_06_TOOL_BIGHAMMER).isEmpty() &&
		!stacks.get(INV_07_TOOL_SMALLHAMMER).isEmpty() &&
		!stacks.get(INV_08_TOOL_SHEARS).isEmpty() &&
		(stacks.get(INV_09_TOOL_COAL).getCount() >= 64));
	}

	public void setResult(ItemStack armor) {
		stacks.set(INV_05_RESULT, armor);
		for (int i = 0; i < INV_05_RESULT; i++){
			stacks.set(i, ItemStack.EMPTY);
		}
	}

	public ItemStack getResult() {
		return stacks.get(INV_05_RESULT);
	}

	public EntitySmithBase getSmith() {return this.smith;}

    public abstract boolean allowItem(int index, ItemStack stack);

}
