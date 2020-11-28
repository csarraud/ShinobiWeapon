package fr.sonkuun.shinobiweapon.renderer;


import java.awt.Color;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import fr.sonkuun.shinobiweapon.ShinobiWeapon;
import fr.sonkuun.shinobiweapon.entity.kunai.KunaiEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.data.EmptyModelData;

public class KunaiRenderer extends EntityRenderer<KunaiEntity> {

	public static final ResourceLocation KUNAI_MODEL_RESOURCE_LOCATION = new ResourceLocation(ShinobiWeapon.MODID, "entity/kunai");
	
	public final float MODEL_SIZE_IN_ORIGINAL_COORDINATES = 4.0F;  // size of the wavefront model
	public final float TARGET_SIZE_WHEN_RENDERED = 1.0F;  // desired size when rendered (in metres)

	public final float SCALE_FACTOR = TARGET_SIZE_WHEN_RENDERED / MODEL_SIZE_IN_ORIGINAL_COORDINATES;
	
	public KunaiRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public ResourceLocation getEntityTexture(KunaiEntity entity) {
		return KUNAI_MODEL_RESOURCE_LOCATION;
	}
	
	public void render(KunaiEntity kunaiEntity, float entityYaw, float partialTicks,
			MatrixStack matrixStack, IRenderTypeBuffer renderBuffers, int packedLightIn) {
		
		IBakedModel kunaiModel = Minecraft.getInstance().getModelManager().getModel(KUNAI_MODEL_RESOURCE_LOCATION);
		System.out.println(getEntityTexture(kunaiEntity).getPath());
		matrixStack.push();
		MatrixStack.Entry currentMatrix = matrixStack.getLast();

		// rotate to set the pitch and yaw correctly.

	    matrixStack.rotate(Vector3f.XP.rotationDegrees(0));
		matrixStack.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, kunaiEntity.prevRotationYaw, entityYaw) - 90));
	    matrixStack.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, kunaiEntity.prevRotationPitch, kunaiEntity.rotationPitch) - 45));
	    
	    matrixStack.translate(-0.28, -0.05, -0.1);
	    
		matrixStack.scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);

		Color blendColour = Color.WHITE;
		float red = blendColour.getRed() / 255.0F;
		float green = blendColour.getGreen() / 255.0F;
		float blue = blendColour.getBlue() / 255.0F;

		// We're going to use the block renderer to render our model, even though it's not a block, because we baked
		// our entity model as if it were a block model.
		BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();

		IVertexBuilder vertexBuffer = renderBuffers.getBuffer(RenderType.getSolid());
		dispatcher.getBlockModelRenderer().renderModel(currentMatrix, vertexBuffer, null, kunaiModel,
				red, green, blue, packedLightIn, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);

		matrixStack.pop(); // restore the original transformation matrix + normals matrix
		
		super.render(kunaiEntity, entityYaw, partialTicks, matrixStack, renderBuffers, packedLightIn);
	}
}
