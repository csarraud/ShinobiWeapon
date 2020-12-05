package fr.sonkuun.shinobiweapon.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;

import fr.sonkuun.shinobiweapon.items.IPoweredItem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PowerHUD {

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		if(event.getType() != ElementType.ALL) {
			return;
		}

		Minecraft mc = Minecraft.getInstance();

		if(mc.player.getHeldItemMainhand().getItem() instanceof IPoweredItem) {
			IPoweredItem item = (IPoweredItem) mc.player.getHeldItemMainhand().getItem();

			RenderSystem.pushMatrix();

			RenderSystem.pushTextureAttributes();

			RenderSystem.enableAlphaTest();
			RenderSystem.enableBlend();
			RenderSystem.color4f(1F, 1F, 1F, 1F);

			/* Render first power HUD texture */
			ResourceLocation firstPowerHUDTexture = item.getFirstPowerHUDTexture();
			
			if(firstPowerHUDTexture != null) {
				int posX = 16;
				int posY = 16;
				
				int width = 16;
				int height = 16;
				
				mc.getTextureManager().bindTexture(firstPowerHUDTexture);
				mc.ingameGUI.blit(posX, posY, 0, 0, width, height);
			}
			
			/* Render second power HUD texture */
			ResourceLocation secondPowerHUDTexture = item.getSecondPowerHUDTexture();
			
			if(secondPowerHUDTexture != null) {
				int posX = 16;
				int posY = 16;
				
				int width = 16;
				int height = 16;
				
				mc.getTextureManager().bindTexture(secondPowerHUDTexture);
				mc.ingameGUI.blit(posX, posY, 0, 0, width, height);
			} 

			RenderSystem.popAttributes();

			RenderSystem.popMatrix();
		}
	}
}
