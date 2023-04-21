package net.BKTeam.illagerrevolutionmod.entity.client.entitymodels;

import net.BKTeam.illagerrevolutionmod.entity.custom.IllagerBeastTamerEntity;
import net.minecraft.resources.ResourceLocation;
import net.BKTeam.illagerrevolutionmod.IllagerRevolutionMod;
import net.BKTeam.illagerrevolutionmod.entity.custom.IllagerMinerBadlandsEntity;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class IllagerMinerBadlandsModel extends GeoModel<IllagerMinerBadlandsEntity> {

    @Override
    public ResourceLocation getModelResource(IllagerMinerBadlandsEntity animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID, "geo/illagerminerbadlands.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IllagerMinerBadlandsEntity animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID, "textures/entity/illagerminerbadlands/badlandsminer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IllagerMinerBadlandsEntity animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID, "animations/illagerminerbadlands.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setCustomAnimations(IllagerMinerBadlandsEntity entity, long instanceId,
                                    AnimationState<IllagerMinerBadlandsEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");

        EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        if (head != null) {
            head.setRotX(extraData.headPitch() *  Mth.DEG_TO_RAD);
            head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
