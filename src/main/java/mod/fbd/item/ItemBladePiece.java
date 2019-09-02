package mod.fbd.item;

import net.minecraft.item.Item;

public class ItemBladePiece extends Item {

	private EnumBladePieceType pieceType;
	public ItemBladePiece(EnumBladePieceType piece, Item.Properties property){
		super(property);
		pieceType = piece;
	}

	public EnumBladePieceType getPieceType() {
		return pieceType;
	}

    public static enum EnumBladePieceType{
    	NORMAL(0,"normal",100,100,1),
    	SEIRYU(1,"seiryu",300,500,15),
    	SUZAKU(2,"suzaku",300,500,15),
    	BYAKO(3,"byako",300,500,15),
    	GENBU(4,"genbu",300,500,15),
    	KIRIN(5,"kirin",400,600,17),
    	NIJI(6,"niji",500,1000,25);

    	private int index;
    	private int metaData;
    	private String registerName;
    	private int baseExp;
    	private int optionExp;
    	private int canMakeLevel;
    	private static EnumBladePieceType[] values = {NORMAL,SEIRYU,SUZAKU,BYAKO,GENBU,KIRIN,NIJI};

    	private EnumBladePieceType(int idx, String str, int exp_base, int exp_opt, int makeLevel){
    		index = idx;
    		metaData = idx;
    		registerName = str;
    		baseExp = exp_base;
    		optionExp = exp_opt;
    		canMakeLevel = makeLevel;
    	}

    	public int getIndex(){
    		return index;
    	}

    	public int getMetaData(){
    		return this.metaData;
    	}

    	public String getRegisterName(){
    		return this.registerName;
    	}

    	public String getDisplayName(){
    		return this.registerName;
    	}

    	public int getBaseExp(){return baseExp;}
    	public int getOptionExp(){return optionExp;}
		public int getMakeLevel() {
			return canMakeLevel;
		}

    	public static EnumBladePieceType getFromIndex(int idx){
    		if (idx < 0 || idx >= values.length){
    			idx = 0;
    		}
    		return values[idx];
    	}

    	public static int bladeNum(){
    		return values.length;
    	}



    }
}
