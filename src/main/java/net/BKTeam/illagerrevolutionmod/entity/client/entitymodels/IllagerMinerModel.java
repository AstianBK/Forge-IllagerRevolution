package net.BKTeam.illagerrevolutionmod.entity.client.entitymodels;

import net.BKTeam.illagerrevolutionmod.entity.custom.IllagerMinerBadlandsEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.BKTeam.illagerrevolutionmod.IllagerRevolutionMod;
import net.BKTeam.illagerrevolutionmod.entity.custom.IllagerMinerEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class IllagerMinerModel<I extends AbstractIllager> extends GeoModel<IllagerMinerEntity> {

    protected final ResourceLocation MODEL_RESLOC;
    protected final ResourceLocation TEXTURE_DEFAULT;
    protected final String ENTITY_REGISTRY_PATH_NAME;

    protected static final ResourceLocation ANIMATION_RESLOC = new ResourceLocation(IllagerRevolutionMod.MOD_ID,
            "animations/illagerminer.animation.json");

    public IllagerMinerModel(ResourceLocation model, ResourceLocation textureDefault,
                             String entityName) {
        super();
        this.MODEL_RESLOC = model;
        this.TEXTURE_DEFAULT = textureDefault;
        this.ENTITY_REGISTRY_PATH_NAME = entityName;
    }

    @Override
    public ResourceLocation getModelResource(IllagerMinerEntity animatable) {
        return MODEL_RESLOC;
    }

    @Override
    public ResourceLocation getTextureResource(IllagerMinerEntity animatable) {
        return TEXTURE_DEFAULT;
    }

    @Override
    public ResourceLocation getAnimationResource(IllagerMinerEntity animatable) {
        return ANIMATION_RESLOC;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setCustomAnimations(IllagerMinerEntity entity, long instanceId,
                                    AnimationState<IllagerMinerEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");

        EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        if (head != null) {
            head.setRotX(extraData.headPitch() *  Mth.DEG_TO_RAD);
            head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
