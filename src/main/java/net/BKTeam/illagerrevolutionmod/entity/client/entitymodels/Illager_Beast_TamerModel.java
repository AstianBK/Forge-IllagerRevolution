package net.BKTeam.illagerrevolutionmod.entity.client.entitymodels;

import net.BKTeam.illagerrevolutionmod.entity.custom.FallenKnight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.BKTeam.illagerrevolutionmod.IllagerRevolutionMod;
import net.BKTeam.illagerrevolutionmod.entity.custom.IllagerBeastTamerEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class Illager_Beast_TamerModel<I extends AbstractIllager> extends GeoModel<IllagerBeastTamerEntity> {

    protected final ResourceLocation MODEL_RESLOC;
    protected final ResourceLocation TEXTURE_DEFAULT;
    protected final String ENTITY_REGISTRY_PATH_NAME;

    protected static final ResourceLocation ANIMATION_RESLOC = new ResourceLocation(IllagerRevolutionMod.MOD_ID,
            "animations/beasttamerillager.animation.json");

    public Illager_Beast_TamerModel(ResourceLocation model, ResourceLocation textureDefault,
                                    String entityName) {
        super();
        this.MODEL_RESLOC = model;
        this.TEXTURE_DEFAULT = textureDefault;
        this.ENTITY_REGISTRY_PATH_NAME = entityName;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setCustomAnimations(IllagerBeastTamerEntity entity, long instanceId,
                                    AnimationState<IllagerBeastTamerEntity> animationState) {
        CoreGeoBone head = this.getAnimationProcessor().getBone("bipedHead");

        EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        if (head != null) {
            head.setRotX(extraData.headPitch() *  Mth.DEG_TO_RAD);
            head.setRotY(extraData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public ResourceLocation getModelResource(IllagerBeastTamerEntity animatable) {
        return MODEL_RESLOC;
    }

    @Override
    public ResourceLocation getTextureResource(IllagerBeastTamerEntity animatable) {
        return TEXTURE_DEFAULT;
    }

    @Override
    public ResourceLocation getAnimationResource(IllagerBeastTamerEntity animatable) {
        return ANIMATION_RESLOC;
    }
}
