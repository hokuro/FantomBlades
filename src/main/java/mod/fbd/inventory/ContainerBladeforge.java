package mod.fbd.inventory;

import mod.fbd.item.ItemCore;
import mod.fbd.tileentity.TileEntityBladeforge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBladeforge extends Container{

	private IInventory entity;
    private int state;
    private int tank_iron;
    private int tank_coal;

	public ContainerBladeforge(IInventory player, IInventory tile){
		entity = tile;
		// 砂鉄
	  	  addSlotToContainer(
				  new Slot(tile, 0, 26, 17){
					    public boolean isItemValid(ItemStack stack)
					    {
					        return (stack.getItem() == ItemCore.item_satetu);
					    }
				  });
	  	// 木炭
	  	  addSlotToContainer(
				  new Slot(tile, 1, 134, 17){
					    public boolean isItemValid(ItemStack stack)
					    {
					        return (stack.getItem() == Items.COAL && stack.getMetadata() == 1);
					    }
				  });

  	    // プレイヤー
        for (int k = 0; k < 3; ++k)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(player, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(player, l, 8 + l * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.entity.isUsableByPlayer(playerIn);
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

		     if (index < 2)
		     {
		    	 return ItemStack.EMPTY;
		     }
		     else if (!this.mergeItemStack(itemstack1, 0, 2, false))
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

	 @Override
	 public void detectAndSendChanges()
	 {
	        super.detectAndSendChanges();

	        TileEntityBladeforge ent = (TileEntityBladeforge)this.entity;
	        for (int i = 0; i < this.listeners.size(); ++i)
	        {
	            IContainerListener icontainerlistener = this.listeners.get(i);

	            if (this.state != ent.getField(TileEntityBladeforge.FIELD_STATE))
	            {
	                icontainerlistener.sendWindowProperty(this, TileEntityBladeforge.FIELD_STATE, ent.getField(TileEntityBladeforge.FIELD_STATE));
	            }
	            if (this.tank_iron != ent.getField(TileEntityBladeforge.FIELD_IRON))
	            {
	                icontainerlistener.sendWindowProperty(this, TileEntityBladeforge.FIELD_IRON, ent.getField(TileEntityBladeforge.FIELD_IRON));
	            }
	            if (this.tank_coal != ent.getField(TileEntityBladeforge.FIELD_COAL))
	            {
	                icontainerlistener.sendWindowProperty(this, TileEntityBladeforge.FIELD_COAL, ent.getField(TileEntityBladeforge.FIELD_COAL));
	            }
	        }

	        this.state = ent.getField(TileEntityBladeforge.FIELD_STATE);
	        this.tank_iron = ent.getField(TileEntityBladeforge.FIELD_IRON);
	        this.tank_coal = ent.getField(TileEntityBladeforge.FIELD_COAL);
	    }


	    @SideOnly(Side.CLIENT)
	    public void updateProgressBar(int id, int data)
	    {
	    	((TileEntityBladeforge)this.entity).setField(id, data);
	    }

}
