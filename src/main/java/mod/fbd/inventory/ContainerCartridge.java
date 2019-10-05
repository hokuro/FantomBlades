package mod.fbd.inventory;


import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.guns.ItemBurret;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCartridge extends Container{
	/** Instance of Merchant. */
    private final IInventory cartridge;
    private final PlayerInventory player;

    public ContainerCartridge(int id, PlayerInventory playerIn, IInventory gun){
    	super(Mod_FantomBlade.CONTAINER_CARTRIDGE, id);
    	cartridge = gun;
    	player = playerIn;
    	for (int i = 0; i < 2; i++){
    		for (int j = 0; j < 5; j++ ){
    		this.addSlot(new Slot(cartridge, j + (5*i), 8 + 18*j, 17 + 18*i)
    			{
    				public boolean isItemValid(ItemStack stack)
    				{
    					return  (stack.getItem() instanceof ItemBurret);
    				}
    			});
    		}
    	}


        // iプレイヤースロット
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(player, j + i * 9 + 9, 8 + j * 18, 111 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlot(new Slot(player, k, 8 + k * 18, 169));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn){
        return true;
    }


    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index){
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index > 10)
            {
                if (!this.mergeItemStack(itemstack1, 0, 10, false))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else
            {
            	if (!this.mergeItemStack(itemstack1, 10, this.inventorySlots.size(), false))
                {
                    return ItemStack.EMPTY;
                }
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

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(PlayerEntity playerIn)
    {
        super.onContainerClosed(playerIn);
        cartridge.closeInventory(playerIn);
    }

    public IInventory gunInventory() {
    	return cartridge;
    }
}
