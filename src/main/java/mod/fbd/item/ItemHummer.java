package mod.fbd.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemTier;

public class ItemHummer extends ItemPickaxe {

	protected EnumHunmmerType htype;
	public ItemHummer(EnumHunmmerType type, Item.Properties property){
        super(ItemTier.IRON,(int)(type.attackDamage + ItemTier.IRON.getAttackDamage()),type.attackSpeed,property);
        htype = type;
	}

	public static enum EnumHunmmerType{
		SMALL(0,2,-2.5F,128),
		BIG(1,3,-3.5F,248);

		private int index;
		private int attackDamage;
		private float attackSpeed;
		private int maxDamage;

		private EnumHunmmerType(int idx, int damage, float speed, int dmg){
			index = idx;
			attackDamage = damage;
			attackSpeed = speed;
			maxDamage = dmg;
		}

		public int getIndex(){return index;}
		public int getAttackDamage(){return attackDamage;}
		public float getAttackSpeed(){return attackSpeed;}
		public int getMaxDamage(){return maxDamage;}
	}
}