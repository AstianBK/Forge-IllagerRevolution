package net.BKTeam.illagerrevolutionmod.entity.client.entitymodels;

import net.BKTeam.illagerrevolutionmod.entity.custom.IllagerMinerEntity;
import net.minecraft.resources.ResourceLocation;
import net.BKTeam.illagerrevolutionmod.IllagerRevolutionMod;
import net.BKTeam.illagerrevolutionmod.entity.custom.RakerEntity;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class RakerModel extends GeoModel<RakerEntity> {
    ResourceLocation TEXTURE_REGULAR = IllagerRevolutionMod.rl("textures/entity/raker/raker.png");

    @Override
    public ResourceLocation getModelResource(RakerEntity animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID, "geo/raker.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RakerEntity animatable) {
        return TEXTURE_REGULAR;
        }

    @Override
    public ResourceLocation getAnimationResource(RakerEntity animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID, "animations/raker.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setCustomAnimations(RakerEntity entity, long instanceId,
                                    AnimationState<RakerEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");

        EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        if (head != null) {
            head.setRotX(extraData.headPitch() *  Mth.DEG_TO_RAD);
            head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}