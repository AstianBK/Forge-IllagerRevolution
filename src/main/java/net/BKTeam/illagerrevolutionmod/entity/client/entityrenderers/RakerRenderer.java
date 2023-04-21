package net.BKTeam.illagerrevolutionmod.entity.client.entityrenderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.BKTeam.illagerrevolutionmod.entity.client.entitymodels.RakerModel;
import net.BKTeam.illagerrevolutionmod.entity.custom.RakerEntity;
import net.BKTeam.illagerrevolutionmod.entity.layers.ScrapperArmorLayer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RakerRenderer extends GeoEntityRenderer<RakerEntity> {

    public RakerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,new RakerModel());
        this.addRenderLayer(new ScrapperArmorLayer(this,new RakerModel()));
        this.shadowRadius = 0.5f;
    }

}
