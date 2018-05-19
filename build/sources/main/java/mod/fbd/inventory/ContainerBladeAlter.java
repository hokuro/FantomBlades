package mod.fbd.inventory;

import mod.fbd.item.ItemKatana;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBladeAlter extends Container{

	private IInventory entity;
    private int state;
    private int tank_iron;
    private int tank_coal;
    private EntityPlayer player;

	public ContainerBladeAlter(EntityPlayer playerIn, IInventory tile){
		entity = tile;
		player = playerIn;
		// 材料スロット
		this.addSlotToContainer(new Slot(this.entity, 0, 8, 17)
		{
			public boolean isItemValid(ItemStack stack)
			{
				return  (stack.getItem() instanceof ItemKatana);
			}
		});

        // プレイヤースロット
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerIn.inventory, j + i * 9 + 9, 8 + j * 18, 126 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerIn.inventory, k, 8 + k * 18, 184));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.entity.isUsableByPlayer(playerIn);
	}

	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.player.world.isRemote)
        {
            this.clearContainer(playerIn, this.player.world, this.entity);
        }
    }

	 @Override
	 public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	 {
		 ItemStack itemstack = ItemStack.EMPTY;
		 Slot slot = this.inventorySlots.get(index);

		 if (slot != null && slot.getHasStack())
		 {
		     ItemStack itemstack1 = slot.getStack();
		     itemstack = itemstack1.copy();

		     if (index == 0)
		     {
		    	 if (!this.mergeItemStack(itemstack1, 1, inventorySlots.size(), false))
			     {
			         return ItemStack.EMPTY;
			     }
		     }
		     else if (!this.mergeItemStack(itemstack1, 0, 1, false))
		     {
		         return ItemStack.EMPTY;
		     }

		     if (itemstack1.isEmpty())
		     {
		         slot.putStack(ItemStack.EMPTY);
		     }
		     else
		     {
		         slot.onSlotChanged();
		     }

		     if (itemstack1.getCount() == itemstack.getCount())
		     {
		         return ItemStack.EMPTY;
		     }

		     slot.onTake(playerIn, itemstack1);
		 }

		 return itemstack;
	 }

	 @Override
	 public void addListener(IContainerListener listener)
	 {
		 super.addListener(listener);
		 listener.sendAllWindowProperties(this, this.entity);
	 }
}
