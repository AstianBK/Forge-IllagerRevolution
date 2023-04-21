package net.BKTeam.illagerrevolutionmod.entity.client.entitymodels;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.BKTeam.illagerrevolutionmod.IllagerRevolutionMod;
import net.BKTeam.illagerrevolutionmod.entity.custom.Blade_KnightEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class Blade_KnightModel<I extends AbstractIllager> extends GeoModel<Blade_KnightEntity> {

    protected final ResourceLocation MODEL_RESLOC;
    protected final ResourceLocation TEXTURE_DEFAULT;
    protected final ResourceLocation TEXTURE_HURT;
    protected final String ENTITY_REGISTRY_PATH_NAME;

    protected static final ResourceLocation ANIMATION_RESLOC = new ResourceLocation(IllagerRevolutionMod.MOD_ID,
            "animations/blade_knight.animation.json");

    public Blade_KnightModel(ResourceLocation model, ResourceLocation textureDefault,ResourceLocation textureHurt, String entityName) {
        super();
        this.MODEL_RESLOC = model;
        this.TEXTURE_HURT=textureHurt;
        this.TEXTURE_DEFAULT =textureDefault;
        this.ENTITY_REGISTRY_PATH_NAME = entityName;
    }




    @Override
    public ResourceLocation getModelResource(Blade_KnightEntity animatable) {
        return MODEL_RESLOC;
    }

    @Override
    public ResourceLocation getTextureResource(Blade_KnightEntity animatable) {
        if(!animatable.isFase2()){
            return TEXTURE_DEFAULT;
        }
        return TEXTURE_HURT;
    }

    @Override
    public ResourceLocation getAnimationResource(Blade_KnightEntity animatable) {
        return ANIMATION_RESLOC;
    }

    @Override
    public void setCustomAnimations(Blade_KnightEntity entity, long instanceId, AnimationState<Blade_KnightEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");

        EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        if (head != null && !entity.isCastingSpell() && !entity.isAttackingShield()) {
            head.setRotX(extraData.headPitch() *  Mth.DEG_TO_RAD);
            head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
