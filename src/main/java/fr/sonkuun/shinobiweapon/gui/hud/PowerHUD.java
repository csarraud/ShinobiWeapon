package fr.sonkuun.shinobiweapon.gui.hud;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import fr.sonkuun.shinobiweapon.ShinobiWeapon;
import fr.sonkuun.shinobiweapon.items.IPoweredItem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class PowerHUD {

	public static final int WIDTH = 256;
	public static final int HEIGHT = 256;
	public static final int POWER_SEPARATOR = 64;

	public static final ResourceLocation POWER_SLOT_TEXTURE = new ResourceLocation(ShinobiWeapon.MODID, "textures/hud/slot.png");
	public static final ResourceLocation COOLDOWN_TEXTURE = new ResourceLocation(ShinobiWeapon.MODID, "textures/hud/power_cooldown.png");

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		if(event.getType() != ElementType.ALL) {
			return;
		}

		Minecraft mc = Minecraft.getInstance();

		if(mc.player.getHeldItemMainhand().getItem() instanceof IPoweredItem) {
			IPoweredItem item = (IPoweredItem) mc.player.getHeldItemMainhand().getItem();

			/* Render first power HUD texture */
			ResourceLocation firstPowerHUDTexture = item.getFirstPowerHUDTexture();

			if(firstPowerHUDTexture != null) {
				drawFirstPowerHUD(item);
			}

			/* Render second power HUD texture */
			ResourceLocation secondPowerHUDTexture = item.getSecondPowerHUDTexture();

			if(secondPowerHUDTexture != null) {
				drawSecondPowerHUD(item);
			}

			RenderSystem.pushMatrix();
			RenderSystem.popMatrix();
		}
	}

	public void drawFirstPowerHUD(IPoweredItem item) {
		int cooldownInPercent = (int) (item.getFirstPowerLastUseInTicks() * 100 / item.getFirstPowerCooldownInTicks());
		drawPower(item.getFirstPowerHUDTexture(), cooldownInPercent, 3);
	}

	public void drawSecondPowerHUD(IPoweredItem item) {
		int cooldownInPercent = (int) (item.getSecondPowerLastUseInTicks() * 100 / item.getSecondPowerCooldownInTicks());
		drawPower(item.getSecondPowerHUDTexture(), cooldownInPercent, 2);
	}

	public void drawPower(ResourceLocation texture, int cooldownInPercent, int powerId) {
		AbstractGui gui = Minecraft.getInstance().ingameGUI;
		MainWindow window = Minecraft.getInstance().getMainWindow();
		
		double coef = 0.08D;
		
		int posX = (int) (window.getScaledWidth() / coef) - (WIDTH + POWER_SEPARATOR);
		int posY = (int) (window.getScaledHeight() / coef) - (HEIGHT + POWER_SEPARATOR) * powerId;
		
		/* Draw power slot */
		GL11.glScaled(coef, coef, coef);
		bind(POWER_SLOT_TEXTURE);
		gui.blit(posX, posY, 0, 0, WIDTH, HEIGHT);

		/* Draw power texture */
		bind(texture);
		gui.blit(posX, posY, 0, 0, WIDTH, HEIGHT);

		/* Draw power cooldown */
		if(cooldownInPercent < 100 && cooldownInPercent > 0) {
			bind(COOLDOWN_TEXTURE);
			gui.blit(posX, posY + (HEIGHT * cooldownInPercent / 100), 0, 0, WIDTH, HEIGHT - (HEIGHT * cooldownInPercent / 100));
		}
		
		GL11.glScaled(12.5D, 12.5D, 12.5D);
		bind(AbstractGui.GUI_ICONS_LOCATION);
	}

	public void bind(ResourceLocation texture) {
		Minecraft.getInstance().getTextureManager().bindTexture(texture);
	}
}
