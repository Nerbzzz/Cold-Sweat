package dev.momostudios.coldsweat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.momostudios.coldsweat.ColdSweat;
import dev.momostudios.coldsweat.client.renderer.layer.ChameleonColorLayer;
import dev.momostudios.coldsweat.client.renderer.model.ChameleonModel;
import dev.momostudios.coldsweat.common.entity.Chameleon;
import dev.momostudios.coldsweat.util.math.CSMath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ChameleonEntityRenderer<T extends Chameleon> extends MobRenderer<Chameleon, ChameleonModel<Chameleon>>
{
    public static final ResourceLocation CHAMELEON_SHED  = new ResourceLocation(ColdSweat.MOD_ID, "textures/entities/chameleon_shed.png");
    public static final ResourceLocation CHAMELEON_GREEN = new ResourceLocation(ColdSweat.MOD_ID, "textures/entities/chameleon_green.png");
    public static final ResourceLocation CHAMELEON_RED   = new ResourceLocation(ColdSweat.MOD_ID, "textures/entities/chameleon_red.png");
    public static final ResourceLocation CHAMELEON_BLUE  = new ResourceLocation(ColdSweat.MOD_ID, "textures/entities/chameleon_blue.png");

    public ChameleonEntityRenderer(EntityRendererProvider.Context context)
    {
        super(context, new ChameleonModel<>(context.bakeLayer(ChameleonModel.LAYER_LOCATION)), 0.3f);
        this.addLayer(new ChameleonColorLayer<>(this));
    }

    @Override
    public void render(Chameleon entity, float p_115456_, float partialTick, PoseStack ps, MultiBufferSource buffer, int light)
    {
        if (entity.getVehicle() instanceof Player player)
        {
            float playerHeadYaw = CSMath.blend(player.yHeadRotO, player.yHeadRot, partialTick, 0, 1);
            float playerHeadPitch = player.getViewXRot(partialTick);
            ps.mulPose(CSMath.getQuaternion(CSMath.toRadians(playerHeadPitch), -CSMath.toRadians(playerHeadYaw), 0));
            ps.translate(0, Math.pow(playerHeadPitch, 2) * 0.000065, playerHeadPitch * 0.0053);
            ps.mulPose(CSMath.getQuaternion(0, CSMath.toRadians(playerHeadYaw), 0));
        }
        super.render(entity, p_115456_, partialTick, ps, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(Chameleon entity)
    {
        return CHAMELEON_GREEN;
    }
}