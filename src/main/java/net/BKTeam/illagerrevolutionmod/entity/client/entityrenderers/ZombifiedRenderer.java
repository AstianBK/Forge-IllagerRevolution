package net.BKTeam.illagerrevolutionmod.entity.client.entityrenderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.BKTeam.illagerrevolutionmod.entity.client.entitymodels.ZombifiedModel;
import net.BKTeam.illagerrevolutionmod.entity.custom.ZombifiedEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import static net.BKTeam.illagerrevolutionmod.ModConstants.*;

public class ZombifiedRenderer extends GeoEntityRenderer<ZombifiedEntity> {
    public ZombifiedRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new ZombifiedModel<>());;
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new BlockAndItemGeoLayer<>(this){
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, ZombifiedEntity animatable) {
                return switch (bone.getName()){
                    case RIGHT_HAND_BONE_IDENT -> animatable.getMainHandItem();
                    default -> null;
                };
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, ZombifiedEntity animatable) {
                return switch (bone.getName()){
                    case RIGHT_HAND_BONE_IDENT -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                };
            }

            @Override
            protected void renderStackForBone(PoseStack stack, GeoBone bone, ItemStack item, ZombifiedEntity currentEntity, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (item == currentEntity.getMainHandItem()) {
                    stack.mulPose(Axis.XP.rotationDegrees(-90f));
                    boolean shieldFlag = item.getItem() instanceof ShieldItem;

                    if (item == currentEntity.getMainHandItem()) {
                        if (shieldFlag) {
                            stack.translate(0, 0.125, -15);
                            stack.mulPose(Axis.YP.rotationDegrees(180f));
                        }
                    }else {
                        if (shieldFlag) {
                            stack.translate(0, 0.125, 0.25);
                            stack.mulPose(Axis.YP.rotationDegrees(180f));
                        }
                    }
                }
                super.renderStackForBone(stack, bone, item, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }
}
