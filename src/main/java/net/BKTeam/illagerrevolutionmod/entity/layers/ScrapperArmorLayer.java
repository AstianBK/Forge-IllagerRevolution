package net.BKTeam.illagerrevolutionmod.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.BKTeam.illagerrevolutionmod.entity.client.entitymodels.RakerModel;
import net.BKTeam.illagerrevolutionmod.entity.custom.RakerEntity;
import net.BKTeam.illagerrevolutionmod.item.custom.RakerArmorItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;


@OnlyIn(Dist.CLIENT)
public class ScrapperArmorLayer extends GeoRenderLayer<RakerEntity> {

    private final RakerModel model;

    public ScrapperArmorLayer(GeoRenderer entityRendererIn, RakerModel model) {
        super(entityRendererIn);
        this.model = model;
    }


    @Override
    public void render(PoseStack poseStack, RakerEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        ItemStack itemstack =entityLivingBaseIn.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack itemstack1 = entityLivingBaseIn.getItemBySlot(EquipmentSlot.LEGS);
        if (itemstack.getItem() instanceof RakerArmorItem armor) {
            RenderType renderType1=RenderType.armorCutoutNoCull(armor.getArmorTexture());
            renderer.reRender(getDefaultBakedModel(entityLivingBaseIn),poseStack,bufferSource,entityLivingBaseIn,renderType1,bufferSource.getBuffer(renderType1),partialTick,packedLight, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);
        }
        if(itemstack1.getItem() instanceof RakerArmorItem armorItem){
            RenderType renderType1=RenderType.armorCutoutNoCull(armorItem.getArmorTexture());
            renderer.reRender(getDefaultBakedModel(entityLivingBaseIn),poseStack,bufferSource,entityLivingBaseIn,renderType1,bufferSource.getBuffer(renderType1),partialTick,packedLight, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);
        }
    }
}

