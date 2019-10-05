package mod.fbd.inventory;

import java.util.ArrayList;
import java.util.List;

import mod.fbd.item.guns.ItemBurret;
import mod.fbd.item.guns.ItemCartridge;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class InventoryCartridge implements IInventory {
	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(10,ItemStack.EMPTY);
	private ItemStack cartridge;

	public InventoryCartridge(ItemStack gun){
		cartridge = gun;
		List<ItemStack> burrets = ItemCartridge.getBurrets(cartridge);
		for (int i = 0; i < burrets.size(); i++){
			stacks.set(i, burrets.get(i).copy());
		}
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
	public boolean isUsableByPlayer(PlayerEntity player) {
		return true;
	}

	@Override
	public void openInventory(PlayerEntity player) {
	}

	@Override
	public void closeInventory(PlayerEntity player) {
		List<ItemStack> burrets = new ArrayList<ItemStack>();
		for (ItemStack burret : stacks){
			if (!stacks.isEmpty()){
				burrets.add(burret.copy());
			}
		}
		ItemCartridge.setBurrets(cartridge, burrets);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return (stack.getItem() instanceof ItemBurret);
	}

	@Override
	public void clear() {
		stacks.clear();
	}
}
