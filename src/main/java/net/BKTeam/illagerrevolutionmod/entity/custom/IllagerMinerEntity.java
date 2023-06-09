package net.BKTeam.illagerrevolutionmod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.BKTeam.illagerrevolutionmod.item.ModItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;


public class IllagerMinerEntity extends IllagerMinerBadlandsEntity implements GeoEntity, InventoryCarrier {

    private final AnimatableInstanceCache cache= GeckoLibUtil.createInstanceCache(this);
    private final SimpleContainer inventory = new SimpleContainer(5);
    public boolean fistUseInvi;
    public boolean animIdle2;
    private int animIdle2Timer;

    public IllagerMinerEntity(EntityType<? extends AbstractIllager> entityType, Level level) {
        super(entityType, level);
        this.fistUseInvi = false;
        this.animIdle2 = false;
        this.animIdle2Timer = 0;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public boolean isAlliedTo(@NotNull Entity pEntity) {
        if (super.isAlliedTo(pEntity)) {
            return true;
        } else if (pEntity instanceof LivingEntity && ((LivingEntity) pEntity).getMobType() == MobType.ILLAGER) {
            return this.getTeam() == null && pEntity.getTeam() == null;
        } else {
            return false;
        }
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 27.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 30.D)
                .add(Attributes.MOVEMENT_SPEED, 0.30f).build();
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        if(this.level.getRandom().nextInt(0,5)==0 ){
            this.spawnAtLocation(ModItems.HELMET_MINER.get());
        }
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
    }

    private   <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {

        if (event.isMoving() && !this.isAggressive() && !this.isAttacking()) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.illagerminer.walk"+(this.isHasItems() ? "3" : "")));

        }else if (this.isAggressive() && event.isMoving() && !this.isAttacking()){
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.illagerminer.walk2"));

        }else if (this.isAttacking()){
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.illagerminer.attack"));

        }else event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.illagerminer.idle1"));

        return PlayState.CONTINUE;

    }

    @Override
    public void setHasItem(boolean pBoolean) {
        super.setHasItem(pBoolean);
        if(pBoolean){
            //bombSmoke();
            this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,150));
        }
    }

    /*public void bombSmoke(){
        this.playSound(SoundEvents.FIRE_EXTINGUISH,5.0f,-1.0f/(this.getRandom().nextFloat() * 0.4F + 0.8F));
        for (int i = 0; i < 24; i++) {
            double x1 = this.getX();
            double x2 = this.getY();
            double x3 = this.getZ();
            this.level.addParticle(ParticleTypes.LARGE_SMOKE, x1, x2, x3, this.getRandom().nextFloat(-0.1f, 0.1f), 0.1f, this.getRandom().nextFloat(-0.1f, 0.1f));
        }
    }*/

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return null;
    }

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.STONE_STEP, 0.15F, 1.5F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ILLUSIONER_AMBIENT;
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.PILLAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }


    @Override
    public SimpleContainer getInventory() {
        return this.inventory;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
