package mod.fbd.inventory;

import mod.fbd.item.ItemAutomatic;
import mod.fbd.item.ItemCartridge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class InventoryAutomatic implements IInventory {
	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(1,ItemStack.EMPTY);
	private ItemStack automatic;

	public InventoryAutomatic(ItemStack gun){
		automatic = gun;
		stacks.set(0, ItemAutomatic.getCartridge(automatic));
	}

	@Override
	public String getName() {
		return "inventory.automatic";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }
        return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index <0 || index>=stacks.size()){
			return ItemStack.EMPTY;
		}
		return stacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		 return ItemStackHelper.getAndSplit(this.stacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.stacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		stacks.set(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		ItemAutomatic.setCartridge(automatic, stacks.get(0));
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return (stack.getItem() instanceof ItemCartridge);
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		stacks.clear();
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}