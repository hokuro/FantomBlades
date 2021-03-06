package mod.fbd.resource;

import java.io.IOException;
import java.io.InputStream;

import mod.fbd.core.log.ModLog;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.NativeImage.PixelFormat;

public class TextureInfo {
	// ファイル名
	private String fileName;
	// モデル名
	private String modelName;
	// テクスチャ名
	private String textureName;
	// テクスチャのサイズ
	private int[] texSize = new int[2];
	// テクスチャイメージ
	NativeImage img;

	// i使える？
	private boolean canUse;

	public TextureInfo(String path, InputStream istream) throws Exception{
		try {
			String[] tex = path.split("/");
			fileName = tex[tex.length-1];
			String[] work = fileName.split("_");
			// iモデル名を確認
			if ((modelName=TextureUtil.checkModelName(work[0].replace(".png", ""))) == null){
				// iモデル名がない場合登録しない
				ModLog.log().debug("path:"+path);
				canUse = false;
				return;
			}
			modelName = work[0].replace(".png", "");
			textureName = fileName.toString().replace(".png", "");

			// iサイズの取得
			img = NativeImage.read(PixelFormat.RGBA, istream);
			texSize = new int[]{img.getWidth(),img.getHeight()};
			canUse = true;
		} catch (IOException e) {
			canUse = false;
			throw e;
		}

	}

	public boolean canUse(){
		return canUse;
	}

    // iテクスチャのファイル名を返す
	public String FileName(){
		return this.fileName;
	}

	// iテクスチャの名称を返す
	public String Name(){
		return textureName;
	}

	// iテクスチャのサイズを返す
	public int[] Size(){
		return texSize;
	}

	// iテクスチャの画像を返す
	public NativeImage Image(){
		return img;
	}

	// モデル名を返す
	public String getTarget(){
		return modelName;
	}

	/***************************/
	/** デバッグ用            **/
	/***************************/
	public String toString(){
		StringBuilder resInf = new StringBuilder();
		resInf.append(this.fileName);
		resInf.append("\t");
		resInf.append(modelName);
		resInf.append("\t");
		resInf.append(textureName);
		resInf.append("\t");
		resInf.append(texSize[0]);
		resInf.append("\t");
		resInf.append(texSize[1]);
		return resInf.toString();
	}
}
