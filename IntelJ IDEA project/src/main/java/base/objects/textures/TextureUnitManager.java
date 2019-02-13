package base.objects.textures;

import com.jogamp.opengl.GL4;

public class TextureUnitManager {
	private static class Constants {
		public static int maximumNubmeerOfTextures = 8;
	}
	private static TextureUnitManager instance;
	
	public static TextureUnitManager getInstance() {
		if (TextureUnitManager.instance == null) {
			TextureUnitManager.instance = new TextureUnitManager();
		}
		return instance;
	}
	
	private boolean isTextureUnitTaken[];
	
	public TextureUnitManager() {
		this.isTextureUnitTaken = new boolean[Constants.maximumNubmeerOfTextures];
	}
	
	public int getTextureUnit() {
		int textureUnit = -1;
		
		for (int i = 0; i < this.isTextureUnitTaken.length; i++) {
			if (!this.isTextureUnitTaken[i]) {
				this.isTextureUnitTaken[i] = true;
				textureUnit = i;
				break;
			}
		}
		
		return textureUnit;
	}
	
	public void freeTextureUnit(int textureUnit) {
		this.isTextureUnitTaken[textureUnit] = false;
	}
}
