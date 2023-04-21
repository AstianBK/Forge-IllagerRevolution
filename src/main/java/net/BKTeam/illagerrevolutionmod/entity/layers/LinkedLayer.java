package net.BKTeam.illagerrevolutionmod.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.BKTeam.illagerrevolutionmod.entity.client.entitymodels.FallenKnightModel;
import net.BKTeam.illagerrevolutionmod.entity.custom.FallenKnight;
import net.BKTeam.illagerrevolutionmod.item.ModItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class LinkedLayer extends GeoRenderLayer<FallenKnight> {
    private final ResourceLocation LINKED_ARMOR=new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private float tick;

    public LinkedLayer(GeoRenderer<FallenKnight> entityRendererIn, FallenKnightModel<FallenKnight> model) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, FallenKnight entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if(entityLivingBaseIn.getOwner()!=null){
            if(entityLivingBaseIn.getOwner().getMainHandItem().is(ModItems.ILLAGIUM_ALT_RUNED_BLADE.get())){
                if(entityLivingBaseIn.itIsLinked() && entityLivingBaseIn.isArmed()){
                    float f=!entityLivingBaseIn.getDamageLink() ? 0.1f : 1.0f;
                    float f1=!entityLivingBaseIn.getDamageLink() ? 0.8f : 0.0f;
                    float f2=!entityLivingBaseIn.getDamageLink() ? 0.4f : 0.0f;

                    tick=(float) entityLivingBaseIn.tickCount+partialTick;
                    renderer.reRender(getDefaultBakedModel(entityLivingBaseIn),poseStack,bufferSource,entityLivingBaseIn,getRenderType(LINKED_ARMOR),bufferSource.getBuffer(getRenderType(LINKED_ARMOR)),partialTick,packedLight, OverlayTexture.NO_OVERLAY,f,f1,f2,1f);
                }
            }
        }
    }

    public RenderType getRenderType(ResourceLocation textureLocation){
        return RenderType.energySwirl(textureLocation,tick*0.01f,tick*0.01f);
    }
}
