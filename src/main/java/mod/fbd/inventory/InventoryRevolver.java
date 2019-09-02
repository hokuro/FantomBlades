package mod.fbd.inventory;

import java.util.ArrayList;
import java.util.List;

import mod.fbd.item.ItemBurret;
import mod.fbd.item.ItemRevolver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryRevolver implements IInventory {
	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(6,ItemStack.EMPTY);
	private ItemStack revolver;

	public InventoryRevolver(ItemStack gun){
		revolver = gun;
		List<ItemStack> burrets = ItemRevolver.getBurrets(revolver);
		for (int i = 0; i < burrets.size(); i++){
			stacks.set(i, burrets.get(i).copy());
		}
	}

	@Override
	public ITextComponent getName() {
		return new TextComponentTranslation("inventory.revolver");
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
		List<ItemStack> burrets = new ArrayList<ItemStack>();
		for (ItemStack burret : stacks){
			if (!stacks.isEmpty()){
				burrets.add(burret.copy());
			}
		}
		ItemRevolver.setBurrets(revolver, burrets);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return (stack.getItem() instanceof ItemBurret);
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

	@Override
	public ITextComponent getCustomName() {
		// TODO 自動生成されたメソッド・スタブ
		return this.getName();
	}
}
