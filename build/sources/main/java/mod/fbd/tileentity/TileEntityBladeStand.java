package mod.fbd.tileentity;

import mod.fbd.block.BlockBladeStand.EnumBladeStand;
import mod.fbd.item.ItemCore;
import mod.fbd.item.ItemKatana;
import mod.fbd.resource.TextureInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class TileEntityBladeStand extends TileEntity implements IInventory {
	public static final String NAME = "bladestand";
	private NonNullList<ItemStack> stacks;
	private ResourceLocation[] resource;

	private EnumBladeStand standType;
	private EnumFacing facing;

	public TileEntityBladeStand(){
		stacks = NonNullList.<ItemStack>withSize(0,ItemStack.EMPTY);
	}

	public TileEntityBladeStand(EnumBladeStand stand, EnumFacing face){
		standType = stand;
		stacks = NonNullList.<ItemStack>withSize(stand.getInventorySize(),ItemStack.EMPTY);
		resource = new ResourceLocation[stand.getInventorySize()];
		for (int i = 0; i < resource.length; i++){
			resource[i] = null;
		}
		facing = face;
	}

	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        standType = EnumBladeStand.getFromIndex(compound.getInteger("standType"));
        stacks = NonNullList.<ItemStack>withSize(standType.getInventorySize(),ItemStack.EMPTY);

        NBTTagList nbttaglist = compound.getTagList("Stacks", 10);
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            ItemStack itemstack = new ItemStack(nbttaglist.getCompoundTagAt(i));

            if (!itemstack.isEmpty())
            {
                this.stacks.set(i,itemstack);
            }
        }
        facing = EnumFacing.getHorizontal(compound.getInteger("face"));
    }

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.stacks.size(); ++i)
        {
            ItemStack itemstack = this.stacks.get(i);

            if (!itemstack.isEmpty())
            {
                nbttaglist.appendTag(itemstack.writeToNBT(new NBTTagCompound()));
            }
        }
        compound.setTag("Stacks", nbttaglist);
        compound.setInteger("standType",standType.getIndex());
        compound.setInteger("face", facing.getHorizontalIndex());
        return compound;
    }

	@Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound cp = super.getUpdateTag();
        return this.writeToNBT(cp);
    }

	@Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
		super.handleUpdateTag(tag);
		this.readFromNBT(tag);
    }

	@Override
	public String getName() {
		return "inventory.bladestand";
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

	public EnumFacing getFace(){
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
		resource[index] = null;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return (stack.getItem() instanceof ItemKatana);
	}

	@Override
	public int getField(int id) {
		int ret = 0;
		switch(id){
		case 0:
			ret = facing.getHorizontalIndex();
			break;
		}
		return ret;
	}

	@Override
	public void setField(int id, int value) {
		switch(id){
		case 0:
			facing = EnumFacing.getHorizontal(value);
			break;
		}
	}

	@Override
	public int getFieldCount() {
		return 1;
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
						((ItemKatana)stacks.get(index).getItem()).getModelTexture(stacks.get(index)),
						stacks.get(index));
			}
			return resource[index];
		}else{
			return ((ItemKatana)stacks.get(index).getItem()).getBladeTexture();
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
				ret = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation(standType.toString() + "_"+index+"_texture",new DynamicTexture(texture.Image()));;
			} catch (Exception e) {
				// 読み込めない場合デフォルトテクスチャを使用する
				ret = null;
			}
		}
		return ret!=null?ret:((ItemKatana)stack.getItem()).getBladeTexture();
	}
}
