package mod.fbd.item;

import net.minecraft.item.ItemPickaxe;

public class ItemHummer extends ItemPickaxe {

	protected EnumHunmmerType htype;
	public ItemHummer(EnumHunmmerType type){
        super(ToolMaterial.IRON);
        htype = type;
		this.attackDamage = type.attackDamage + this.toolMaterial.getAttackDamage();
		this.attackSpeed = type.attackSpeed;
		this.setMaxDamage(type.maxDamage);
	}

	public static enum EnumHunmmerType{
		SMALL(0,1.5F,-2.5F,128),
		BIG(1,3.0F,-3.5F,248);

		private int index;
		private float attackDamage;
		private float attackSpeed;
		private int maxDamage;

		private EnumHunmmerType(int idx, float damage, float speed, int dmg){
			index = idx;
			attackDamage = damage;
			attackSpeed = speed;
			maxDamage = dmg;
		}

		public int getIndex(){return index;}
		public float getAttackDamage(){return attackDamage;}
		public float getAttackSpeed(){return attackSpeed;}
		public int getMaxDamage(){return maxDamage;}
	}
}