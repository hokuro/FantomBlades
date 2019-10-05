package mod.fbd.tileentity;

import mod.fbd.block.BlockBladeStand.EnumBladeStand;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemCore;
import mod.fbd.item.katana.AbstractItemKatana;
import mod.fbd.resource.TextureInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class TileEntityBladeStand extends TileEntity implements IInventory {
	public static final String NAME = "bladestand";
	private NonNullList<ItemStack> stacks;
	private ResourceLocation[] resource;

	private EnumBladeStand standType;
	private Direction facing;
	private boolean needMake = true;

	public TileEntityBladeStand(){
		super(Mod_FantomBlade.RegistryEvents.BLADESTAND);
		stacks = NonNullList.<ItemStack>withSize(0,ItemStack.EMPTY);
	}

	public TileEntityBladeStand(EnumBladeStand stand, Direction face){
		this();
		standType = stand;
		makeInventory(stand);
		for (int i = 0; i < resource.length; i++){
			resource[i] = null;
		}
		facing = face;
	}

	public void makeInventory(EnumBladeStand stand) {
		if (needMake) {
			standType = stand;
			stacks = NonNullList.<ItemStack>withSize(stand.getInventorySize(),ItemStack.EMPTY);
			resource = new ResourceLocation[stand.getInventorySize()];
			needMake = false;
		}
	}

	@Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        standType = EnumBladeStand.getFromIndex(compound.getInt("standType"));
        makeInventory(standType);
        stacks = NonNullList.<ItemStack>withSize(standType.getInventorySize(),ItemStack.EMPTY);

        ListNBT nbttaglist = compound.getList("Stacks", 10);
        for (int i = 0; i < nbttaglist.size(); ++i) {
            ItemStack itemstack = ItemStack.read((CompoundNBT)nbttaglist.get(i));

            if (!itemstack.isEmpty())
            {
                this.stacks.set(i,itemstack);
            }
        }
        facing = Direction.byHorizontalIndex(compound.getInt("face"));
    }

	@Override
    public CompoundNBT write(CompoundNBT compound) {
        compound = super.write(compound);
        ListNBT nbttaglist = new ListNBT();
        for (int i = 0; i < this.stacks.size(); ++i)
        {
            ItemStack itemstack = this.stacks.get(i);

            if (!itemstack.isEmpty())
            {
                nbttaglist.add(itemstack.write(new CompoundNBT()));
            }
        }
        compound.put("Stacks", nbttaglist);
        compound.putInt("standType",standType.getIndex());
        compound.putInt("face", facing.getHorizontalIndex());
        return compound;
    }

	@Override
    public CompoundNBT getUpdateTag()
    {
        CompoundNBT cp = super.getUpdateTag();
        return this.write(cp);
    }

	@Override
    public void handleUpdateTag(CompoundNBT tag)
    {
		super.handleUpdateTag(tag);
		this.read(tag);
    }

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT CompoundNBT = new CompoundNBT();
        return new SUpdateTileEntityPacket(this.pos, 1,  this.write(CompoundNBT));
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

	public Direction getFace(){
		return this.facing;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
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
//		if (resource== null) {
//			resource = new ResourceLocation[standType.getInventorySize()];
//		}
		resource[index] = null;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return true;
	}

	@Override
	public void openInventory(PlayerEntity player) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void closeInventory(PlayerEntity player) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return (stack.getItem() instanceof AbstractItemKatana);
	}

	public int getField(int id) {
		int ret = 0;
		switch(id){
		case 0:
			ret = facing.getHorizontalIndex();
			break;
		case 1:
			ret = standType.getIndex();
			break;
		}
		return ret;
	}

	public void setField(int id, int value) {
		switch(id){
		case 0:
			facing = Direction.byHorizontalIndex(value);
			break;
		case 1:
			standType = EnumBladeStand.getFromIndex(value);
			break;
		}
	}

	public int getFieldCount() {
		return 2;
	}

	@Override
	public void clear() {
		this.stacks.clear();
	}

	public boolean setKatana(ItemStack stack) {
		boolean ret = false;
		for (int i = 0; i < stacks.size(); i++){
			if (stacks.get(i).isEmpty()){
				this.setInventorySlotContents(i,stack.copy());
				ret = true;
				break;
			}
		}
		return ret;
	}

	public ItemStack getKatana(){
		ItemStack stack = ItemStack.EMPTY;
		for ( int i = 0; i < stacks.size(); i++){
			if (!stacks.get(i).isEmpty()){
				stack = stacks.get(i);
				stacks.set(i, ItemStack.EMPTY);
				break;
			}
		}
		return stack;
	}

	public EnumBladeStand getStandType() {
		return this.standType;
	}

	public boolean hasKatana(int index){
		if (index < 0 || stacks.size() <= index){
			return false;
		}
		return !stacks.get(index).isEmpty();
	}


	public ResourceLocation getKatanaTexture(int index) {
		if (index < 0 && stacks.size() <= index){
			return null;
		}
		if (stacks.get(index).getItem() == ItemCore.item_katana){
			if (resource[index] == null){
				resource[index] = makeDynamicTexture(index,
						((AbstractItemKatana)stacks.get(index).getItem()).getModelTexture(stacks.get(index)),
						stacks.get(index));
			}
			return resource[index];
		}else{
			return ((AbstractItemKatana)stacks.get(index).getItem()).getBladeTexture();
		}
	}

	public int getLightLevel() {
		int ret = 0;
		for (ItemStack stack: stacks){
			if (stack.getItem() == ItemCore.item_katana_kirin){
				ret = 12;
				break;
			}else if (stack.getItem() == ItemCore.item_katana_suzaku){
				ret = 10;
			}
		}
		return ret;
	}

	public ResourceLocation makeDynamicTexture(int index,TextureInfo texture, ItemStack stack){
		ResourceLocation ret = null;
		if (texture != null){
			try {
				// 特製テクスチャを読み込む
				ret = Minecraft.getInstance().getTextureManager().getDynamicTextureLocation(standType.toString() + "_"+index+"_texture",new DynamicTexture(texture.Image()));;
			} catch (Exception e) {
				// 読み込めない場合デフォルトテクスチャを使用する
				ret = null;
			}
		}
		return ret!=null?ret:((AbstractItemKatana)stack.getItem()).getBladeTexture();
	}
}
