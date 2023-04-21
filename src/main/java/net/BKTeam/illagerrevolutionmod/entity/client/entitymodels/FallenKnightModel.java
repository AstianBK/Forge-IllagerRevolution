package net.BKTeam.illagerrevolutionmod.entity.client.entitymodels;

import net.BKTeam.illagerrevolutionmod.IllagerRevolutionMod;
import net.BKTeam.illagerrevolutionmod.entity.custom.Blade_KnightEntity;
import net.BKTeam.illagerrevolutionmod.entity.custom.FallenKnight;
import net.BKTeam.illagerrevolutionmod.entity.custom.FallenKnight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Monster;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
public class FallenKnightModel<I extends Monster> extends GeoModel<FallenKnight> {

    private static final ResourceLocation MODEL=new ResourceLocation(IllagerRevolutionMod.MOD_ID,
            "geo/fallen_knight.geo.json");

    protected static final ResourceLocation ANIMATION_RESLOC = new ResourceLocation(IllagerRevolutionMod.MOD_ID,
            "animations/fallen_knight.animation.json");


    @Override
    public void setCustomAnimations(FallenKnight entity, long instanceId, AnimationState<FallenKnight> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");

        EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        if (head != null && !entity.isArmed()) {
            head.setRotX(extraData.headPitch() *  Mth.DEG_TO_RAD);
            head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }


    @Override
    public ResourceLocation getModelResource(FallenKnight animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(FallenKnight animatable) {
        return new ResourceLocation(IllagerRevolutionMod.MOD_ID,
                "textures/entity/fallen_knight/fallen_knight.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FallenKnight animatable) {
        return ANIMATION_RESLOC;
    }
}
