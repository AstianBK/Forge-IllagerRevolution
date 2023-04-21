package net.BKTeam.illagerrevolutionmod.entity.client.armor;

import net.BKTeam.illagerrevolutionmod.item.custom.ArmorGogglesItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class Goggles_Miner_ReinforcedRenderer extends GeoArmorRenderer<ArmorGogglesItem> {

    public Goggles_Miner_ReinforcedRenderer() {
        super(new Goggles_Miner_ReinforcedModel());
    }
}
