package net.BKTeam.illagerrevolutionmod.entity.client.entityrenderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import jdk.incubator.vector.VectorShape;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.state.BlockState;
import net.BKTeam.illagerrevolutionmod.IllagerRevolutionMod;
import net.BKTeam.illagerrevolutionmod.entity.client.entitymodels.Blade_KnightModel;
import net.BKTeam.illagerrevolutionmod.entity.custom.Blade_KnightEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

import static net.BKTeam.illagerrevolutionmod.ModConstants.*;

public class Blade_knightRenderer extends GeoEntityRenderer<Blade_KnightEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(IllagerRevolutionMod.MOD_ID,
            "textures/entity/blade_knight/blade_knight.png");
    private static final ResourceLocation TEXTURE_LOWLIFE = new ResourceLocation(IllagerRevolutionMod.MOD_ID,
            "textures/entity/blade_knight/blade_knight_lowhealth.png");
    private static final ResourceLocation MODEL_RESLOC = new ResourceLocation(IllagerRevolutionMod.MOD_ID,
            "geo/blade_knight.geo.json");

    public Blade_knightRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Blade_KnightModel<Blade_KnightEntity>(MODEL_RESLOC,TEXTURE,TEXTURE_LOWLIFE,"blade_knight"));
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new BlockAndItemGeoLayer<>(this){
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, Blade_KnightEntity animatable) {
                return switch (bone.getName()){
                    case RIGHT_HAND_BONE_IDENT -> animatable.getMainHandItem();
                    default -> null;
                };
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, Blade_KnightEntity animatable) {
                return switch (bone.getName()){
                    case RIGHT_HAND_BONE_IDENT -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                };
            }

            @Override
            protected void renderStackForBone(PoseStack stack, GeoBone bone, ItemStack item, Blade_KnightEntity currentEntity, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                CompoundTag nbt;
                int cc1=6;
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
                    nbt=item.getOrCreateTag();
                    if(currentEntity.isLowLife()){
                        cc1=0;
                    }
                    nbt.putInt("CustomModelData", cc1);
                }
                super.renderStackForBone(stack, bone, item, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }



}
