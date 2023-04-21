package net.BKTeam.illagerrevolutionmod.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.BKTeam.illagerrevolutionmod.enchantment.Init_enchantment;
import net.BKTeam.illagerrevolutionmod.item.ModArmorMaterials;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ArmorGogglesItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ArmorGogglesItem(ModArmorMaterials material, ArmorItem.Type slot, Properties settings) {
        super(material, slot, settings);
    }


    private <P extends GeoItem> PlayState predicate(AnimationState<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (!world.isClientSide()) {
            double cc=20.0d;
            if(player.hasEffect(MobEffects.BLINDNESS)){
                player.removeEffect(MobEffects.BLINDNESS);
            }
            cc+=5*EnchantmentHelper.getItemEnchantmentLevel(Init_enchantment.WARYLENSES.get(),stack);
            if(world.getMaxLocalRawBrightness(player.blockPosition())<=1){
                player.level.getEntitiesOfClass(Monster.class,player.getBoundingBox().inflate(cc)).forEach(entity ->{
                    if(!entity.hasEffect(MobEffects.GLOWING)){
                        entity.addEffect(new MobEffectInstance(MobEffects.GLOWING,20,0));
                    }
                });
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller",
                20, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}