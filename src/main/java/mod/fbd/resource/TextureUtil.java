package mod.fbd.resource;

import java.util.ArrayList;
import java.util.List;

import mod.fbd.core.log.ModLog;

public class TextureUtil {
	private static ResourceManager texturemanager;
	public static ResourceManager TextureManager(){
		if ( texturemanager == null){
			texturemanager = new ResourceManager();
			try{
			texturemanager.initResource();
			}catch(Exception ex){
				ModLog.log().info("Can't Use CusutomTexture");
			}
		}
		return texturemanager;
	}

	public static String checkModelName(String name){
		int idx=-1;
		if ( ((idx = modelNames.indexOf(name)) >= 0)){
			return modelNames.get(idx);
		}
		return null;
	}

	public static final List<String> modelNames = new ArrayList<String>(){
		{add("bladesmith");}
		{add("armorsmith");}
		{add("normalblade");}
	};
}
