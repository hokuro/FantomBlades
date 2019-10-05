package mod.fbd.inventory;

import java.util.List;

import com.google.common.collect.Lists;

import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemCore;
import mod.fbd.tileentity.TileEntityBladeforge;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerBladeforge extends Container {

	private TileEntityBladeforge entity;
    private int state;
    private int tank_iron;
    private int tank_coal;
	private final List<IContainerListener> mylisteners = Lists.newArrayList();

	public ContainerBladeforge(int id,  PlayerInventory playerIn,  TileEntityBladeforge tile){
    	super(Mod_FantomBlade.CONTAINER_BLADEFORGE, id);
		entity = tile;
		// i砂鉄
		addSlot(
				new Slot(tile, 0, 26, 17){
					public boolean isItemValid(ItemStack stack) {
						return (stack.getItem() == ItemCore.item_satetu);
					}
				});
		// i木炭
		addSlot(
				new Slot(tile, 1, 134, 17){
					public boolean isItemValid(ItemStack stack) {
						return (stack.getItem() == Items.CHARCOAL);
					}
				});

		// iプレイヤー
        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerIn, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerIn, l, 8 + l * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.entity.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 2) {
				return ItemStack.EMPTY;
			} else if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		if (!this.mylisteners.contains(listener)) {
			this.mylisteners.add(listener);
		}
		for(int i = 0; i < entity.getFieldCount(); ++i) {
			listener.sendWindowProperty(this, i, entity.getField(i));
		}
	}

	@Override
	public void removeListener(IContainerListener listener) {
		super.removeListener(listener);
		this.mylisteners.remove(listener);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		TileEntityBladeforge ent = (TileEntityBladeforge)this.entity;
		for (int i = 0; i < this.mylisteners.size(); ++i) {
			IContainerListener icontainerlistener = this.mylisteners.get(i);

			if (this.state != ent.getField(TileEntityBladeforge.FIELD_STATE)) {
				icontainerlistener.sendWindowProperty(this, TileEntityBladeforge.FIELD_STATE, ent.getField(TileEntityBladeforge.FIELD_STATE));
			}
			if (this.tank_iron != ent.getField(TileEntityBladeforge.FIELD_IRON)) {
				icontainerlistener.sendWindowProperty(this, TileEntityBladeforge.FIELD_IRON, ent.getField(TileEntityBladeforge.FIELD_IRON));
			}
			if (this.tank_coal != ent.getField(TileEntityBladeforge.FIELD_COAL)) {
				icontainerlistener.sendWindowProperty(this, TileEntityBladeforge.FIELD_COAL, ent.getField(TileEntityBladeforge.FIELD_COAL));
			}
		}
		this.state = ent.getField(TileEntityBladeforge.FIELD_STATE);
		this.tank_iron = ent.getField(TileEntityBladeforge.FIELD_IRON);
		this.tank_coal = ent.getField(TileEntityBladeforge.FIELD_COAL);
	}


	@OnlyIn(Dist.CLIENT)
	public void updateProgressBar(int id, int data) {
		((TileEntityBladeforge)this.entity).setField(id, data);
	}

	public TileEntityBladeforge getTileEntity() {
		return entity;
	}
}
