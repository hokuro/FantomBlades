package mod.fbd.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mod.fbd.block.BlockHorizontalContainer;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.core.log.ModLog;
import mod.fbd.item.ItemCore;
import mod.fbd.item.ItemKatana;
import mod.fbd.item.ItemKatanaMugen;
import mod.fbd.util.Enchant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class TileEntityBladeAlter extends TileEntity implements IInventory {
	public static final String NAME = "bladealter";
	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(1,ItemStack.EMPTY);
	private EnumFacing facing;

	public TileEntityBladeAlter(){
		this(EnumFacing.NORTH);
	}

	public TileEntityBladeAlter(IBlockState face){
		this();
		facing = face.get(BlockHorizontalContainer.FACING);
	}

	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

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
		return "inventory.bladealter";
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
		if (index !=0){return ItemStack.EMPTY;}
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
		enc_index = 0;
		pot_index = 0;
		if (stack.isEmpty() || stack.getItem() == ItemCore.item_katana){
			pot_list = null;
			enc_list = null;
		}else{
			makePotionList(stack);
			makeEnchantList(stack);
		}
	}

	private void makePotionList(ItemStack stack){
		if (stack.getItem() == ItemCore.item_katana_niji || stack.getItem() == ItemCore.item_katana_mugen){
			List<PotionEffect> work_pot = ((ItemKatana)stack.getItem()).getPotionEffects(stack);
			pot_list = new ArrayList<PotionEffect>();
			IRegistry.field_212631_t.forEach(potion->{
				boolean flg = true;
				for (PotionEffect effect : work_pot){
					if (effect.getPotion() == potion){
						pot_list.add(new PotionEffect(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
						flg = false;
						break;
					}
				}
//				if (flg){
//					pot_list.add(new PotionEffect(potion,potion.,potion.getAmplifier()));
//				}
			});
		}
	}

	private void makeEnchantList(ItemStack stack){
		if (Mod_FantomBlade.swordEnchantments.size() == 0) {
			Mod_FantomBlade.createSwordEnchant();
		}
		Enchantment[] ignore = ((ItemKatana)stack.getItem()).ignoreEnchantments();
		Map<Enchantment,Integer> enchants = EnchantmentHelper.getEnchantments(stack);
		for (Enchantment enc : Mod_FantomBlade.swordEnchantments){
			if (!enchants.containsKey(enc)){
				enchants.put(enc, 0);
			}
		}
		enc_list = new ArrayList<Enchant>();
		for (Enchantment enc : enchants.keySet()){
			enc_list.add(new Enchant(enc, enchants.get(enc)));
			for (Enchantment ign : ignore){
				if (enc == ign){
					enc_list.remove(enc_list.size()-1);
				}
			}
		}
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

	public ItemStack getKatana(){
		ItemStack stack = ItemStack.EMPTY;
		for ( int i = 0; i < stacks.size(); i++){
			if (!stacks.get(i).isEmpty()){
				stack = stacks.get(i);
				break;
			}
		}
		return stack;
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
		return ((ItemKatana)stacks.get(index).getItem()).getBladeTexture();
	}


	private List<PotionEffect> pot_list = null;
	private List<Enchant> enc_list = null;
	private int enc_index = 0;
	private int pot_index = 0;

	public int getEnchantIndex(){
    	return enc_index;
    }

    public int getPotionIndex(){
    	return pot_index;
    }

    public void addEnchantIndex(int val){
    	if (enc_list != null){
        	enc_index += val;
        	if (enc_index < 0){
        		enc_index  = enc_list.size()-1;
        	}else if (enc_index >= enc_list.size()){
        		enc_index = 0;
        	}
    	}else{
    		enc_index = 0;
    	}
    }

    public void addPotionIndex(int val){
    	if (pot_list != null){
    		pot_index += val;
        	if (pot_index < 0){
        		pot_index  = pot_list.size()-1;
        	}else if (pot_index >= pot_list.size()){
        		pot_index = 0;
        	}
    	}else{
    		pot_index = 0;
    	}
    }

	public String getStrPotion(){
		if (pot_list != null && pot_list.size() > 0){
			addPotionIndex(0);
			PotionEffect effect = pot_list.get(pot_index);
			String ret = I18n.translateToLocal(effect.getEffectName()) + "/" + effect.getAmplifier() + " " + (effect.getDuration()/20)+"sec";

			return ret;
		}
		return "";
	}

	public String getStrEnchant(){
		if (enc_list != null && enc_list.size() > 0){
			addEnchantIndex(0);
			return enc_list.get(enc_index).toString();
		}
		return "";
	}

	public PotionEffect getPotion(){
		if (pot_list != null && pot_list.size() > 0){
			addPotionIndex(0);
			return pot_list.get(pot_index);
		}
		return null;
	}

	public Enchant getEnchant(){
		if (enc_list != null && enc_list.size() > 0){
			addEnchantIndex(0);
			return enc_list.get(enc_index);
		}
		return null;
	}

	public void BladeLevelUp() {
		ItemStack stack = this.getKatana();
		ItemKatana katana = ((ItemKatana)stack.getItem());
		int exp = ItemKatana.getExp(stack);
		int next = katana.getLevelUpExp();
		if (next <= exp){
			katana.setLevel(stack, katana.getLevel(stack)+1);
			ItemKatana.setExp(stack, exp - next);
		}
		this.setInventorySlotContents(0, stack);
	}

	public void BladeEnchantUpdate(int index, int value) {
		ItemStack stack = this.getKatana();
		ItemKatana katana = ((ItemKatana)stack.getItem());
		int exp = ItemKatana.getExp(stack);
		int next = katana.getEnchantLevelUpExp();
		if (next <= exp){
			if (index < 0 || index >= enc_list.size()){
				return;
			}
			Enchant enc = enc_list.get(index);
			Map<Enchantment,Integer> encmap = EnchantmentHelper.getEnchantments(stack);
			if (encmap.containsKey(enc.Enchantment())){
				if (value == 0){
					// エンチャント削除
					encmap.remove(enc.Enchantment());
				}else{
					// 既存エンチャントのレベル設定
					Integer lv = encmap.get(enc.Enchantment()) + value;
					if (lv <= 0){
						// レベルが0になるようならなんかばぐってる
						ModLog.log().info("エンチャントレベルがおかしいよ："+lv);
						return;
					}
					encmap.replace(enc.Enchantment(), lv);
				}
			}else{
				// 新規エンチャントを追加
				encmap.put(enc.Enchantment(), 1);
			}
			// エンチャントを更新
			EnchantmentHelper.setEnchantments(encmap, stack);
			// Expを更新
			//ItemKatana.setExp(stack, exp-next);
			// アイテムを再設定
			this.setInventorySlotContents(0, stack);
		}
	}

	public void BladePotionUpdate(int index, int value) {
		ItemStack stack = this.getKatana();
		ItemKatana katana = ((ItemKatana)stack.getItem());
		int exp = ItemKatana.getExp(stack);
		int next = katana.getPotionEffectUpExp();
		if (next <= exp){
			if (index < 0 || index >= pot_list.size()){
				return;
			}
			PotionEffect pot = pot_list.get(index);
			List<PotionEffect> effects = ItemKatana.getPotionEffects(stack);
			boolean find = false;
			int maxlp = effects.size();
			for (int i = 0; i < maxlp; i++){
				if (effects.get(i).getPotion() == pot.getPotion()){
					find = true;
					if (value == 0){
						// ポーション効果削除
						effects.remove(i);
					}else{
						// ポーション強度更新
						int amp = effects.get(i).getAmplifier() + value;
						if (amp<0){
							ModLog.log().info("ポーチョンがおかしいよ："+amp);
							return;
						}
						PotionEffect e = effects.get(i);
						effects.set(i, new PotionEffect(e.getPotion(),e.getDuration(),amp));
					}
					break;
				}
			}
			if (!find){
				// 新規ポーション追加
				effects.add(new PotionEffect(pot.getPotion(),(pot.getPotion().isInstant()?0:600),0));
			}
			// ポーション効果を更新
			ItemKatana.setPotionEffects(stack, effects);
			// Expを更新
			//ItemKatana.setExp(stack, exp - next);
			// アイテムを再設定
			this.setInventorySlotContents(0, stack);
		}
	}

	public void BladePotionDurationUpdate(int index, int value) {
		ItemStack stack = this.getKatana();
		ItemKatana katana = ((ItemKatana)stack.getItem());
		int exp = ItemKatana.getExp(stack);
		int next = katana.getPotionEffectUpExp();
		if (next <= exp){
			if (index < 0 || index >= pot_list.size()){
				return;
			}
			PotionEffect pot = pot_list.get(index);
			if (pot.getPotion().isInstant()){
				ModLog.log().info("即効性のポーションなんだけど・・・・:"+pot.toString());
				return;
			}
			List<PotionEffect> effects = ItemKatana.getPotionEffects(stack);
			boolean find = false;
			int maxlp = effects.size();
			for (int i = 0; i < maxlp; i++){
				if (effects.get(i).getPotion() == pot.getPotion()){
					find = true;

					// ポーション時間更新
					int duration = effects.get(i).getDuration() + (value*600);
					if (duration<=0){
						ModLog.log().info("ポーチョン時間がおかしいよ："+duration);
						return;
					}
					PotionEffect e = effects.get(i);
					effects.set(i, new PotionEffect(e.getPotion(),duration,e.getAmplifier()));
					break;
				}
			}
			if (!find){
				ModLog.log().info("こんな効果知らないわ:"+pot.toString());
				return;
			}
			// ポーション効果を更新
			ItemKatana.setPotionEffects(stack, effects);
			// Expを更新
			//ItemKatana.setExp(stack, exp - next);
			// アイテムを再設定
			this.setInventorySlotContents(0, stack);
		}
	}

	public void BladeUpdateMugen() {
		// 夢幻に更新
		this.setInventorySlotContents(0, ItemKatanaMugen.getDefaultStack());
	}
}
