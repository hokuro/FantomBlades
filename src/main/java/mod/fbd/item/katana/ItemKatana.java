package mod.fbd.item.katana;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemCore;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemKatana extends AbstractItemKatana {
	public final static String NAME = "normalblade";
	public final ResourceLocation DEFAULT_KATANATEXTURE;

	public ItemKatana(Item.Properties property){
		this(Mod_FantomBlade.HAGANE,property);
	}

	public ItemKatana(IItemTier tier, Item.Properties property){
		super(tier, property);
		DEFAULT_KATANATEXTURE = new ResourceLocation("fbd","textures/entity/normalblade_default.png");
		LEVELUP_EXP = 500;
		POTION_UP = -1;
		ENCHANT_UP = -1;
	}

	@Override
	public ResourceLocation getBladeTexture() {
		return DEFAULT_KATANATEXTURE;
	}

	@Override
	public Enchantment[] ignoreEnchantments(){
		return	new Enchantment[]{};
	}

	@Override
	protected String getKatanaName() {
		// TODO 自動生成されたメソッド・スタブ
		return NAME;
	}

	@Override
	protected ItemStack getDefault() {
		ItemStack ret = new ItemStack(ItemCore.item_katana);
		setMaxLevel(ret,255);
		setExp(ret,0);
		setRustValue(ret, 0);
		setAttackDamage(ret,5.0F);
		setAttackSpeed(ret,-2.0D);
		setEndurance(ret, 1000);
		if (ModCommon.isDevelop){
			setExp(ret, 10000);
		}
		setRandomModelTexture(ret);
		return ret;
	}
}
