package mod.fbd.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;

public class ItemTamahagane extends Item {
	public ItemTamahagane(){
		super();
		this.setHasSubtypes(true);
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            items.add(new ItemStack(this,1,EnumTamahagane.FIRST_GRADE.getIndex()));
            items.add(new ItemStack(this,1,EnumTamahagane.SECOND_GRADE.getIndex()));
            items.add(new ItemStack(this,1,EnumTamahagane.THERD_GRADE.getIndex()));
            items.add(new ItemStack(this,1,EnumTamahagane.LOWEST_GRADE.getIndex()));
        }
    }

	@Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack)+"_"+EnumTamahagane.getFromIndex(stack.getMetadata()).getDisplayName() + ".name").trim();
    }


	public static enum EnumTamahagane{
		FIRST_GRADE(0,2.0F,1F,"first"),
		SECOND_GRADE(1,1.0F,-0.5F,"second"),
		THERD_GRADE(2,0.5F,-1.0F,"therd"),
		LOWEST_GRADE(3,0.1F,-2.0F,"lowest");

		private int index;
		private float repair;
		private float sepcRev;
		private String displayName;
		private EnumTamahagane(int idx, float rp, float rev,String name){
			index = idx;
			repair = rp;
			sepcRev = rev;
			displayName = name;
		}

		public int getIndex(){return index;}
		public float getRepair(){return repair;}
		public String getDisplayName(){return displayName;}
		public float getSpecRev() { return sepcRev; }


		private static final EnumTamahagane[] values = {FIRST_GRADE,SECOND_GRADE,THERD_GRADE,LOWEST_GRADE};
		public static EnumTamahagane getFromIndex(int index){
			if (index < 0 || index >= values.length){
				index = 0;
			}
			return values[index];
		}

	}
}
