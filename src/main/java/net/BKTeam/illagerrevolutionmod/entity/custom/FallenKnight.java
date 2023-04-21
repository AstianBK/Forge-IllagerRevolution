package net.BKTeam.illagerrevolutionmod.entity.custom;

import net.BKTeam.illagerrevolutionmod.api.IHasInventory;
import net.BKTeam.illagerrevolutionmod.api.INecromancerEntity;
import net.BKTeam.illagerrevolutionmod.entity.goals.*;
import net.BKTeam.illagerrevolutionmod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;

public class FallenKnight extends ReanimatedEntity implements GeoEntity, IHasInventory {
    private final AnimatableInstanceCache cache= GeckoLibUtil.createInstanceCache(this);
    private final SimpleContainer inventory=new SimpleContainer(1);
    public int attackTimer;
    public int unarmedTimer;
    public int rearmedTimer;
    public int reviveTimer;
    public int dispawnTimer;
    public boolean isEndless;
    public int damageLinkTimer;
    private static final EntityDataAccessor<Boolean> LINKED =
            SynchedEntityData.defineId(FallenKnight.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(FallenKnight.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> UNARMED =
            SynchedEntityData.defineId(FallenKnight.class,EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ON_GROUND_UNARMED =
            SynchedEntityData.defineId(FallenKnight.class,EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> REARMED =
            SynchedEntityData.defineId(FallenKnight.class,EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ARMED=
            SynchedEntityData.defineId(FallenKnight.class,EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DAMAGE_LINK =
            SynchedEntityData.defineId(FallenKnight.class, EntityDataSerializers.BOOLEAN);
    public FallenKnight(EntityType<? extends Monster> p_33570_, Level p_33571_) {
        super(p_33570_, p_33571_);
        this.dispawnTimer=0;
        this.attackTimer=0;
        this.unarmedTimer=0;
        this.rearmedTimer=0;
        this.reviveTimer=0;
        this.isEndless=false;
        this.damageLinkTimer=0;
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.ARMOR,8.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 45.D)
                .add(Attributes.MOVEMENT_SPEED, 0.25f).build();
    }
    @Override
    public SimpleContainer getContainer() {
        return this.inventory;
    }

    public void setDispawnTimer(int dispawnTimer,@Nullable Player player,boolean isInfinite) {
        if(!isInfinite){
            this.dispawnTimer=dispawnTimer;
            this.isEndless=true;
        }else {
            this.dispawnTimer=0;
            this.isEndless=false;
        }
    }

    @Override
    public void die(DamageSource pCause) {
        LivingEntity entity1=pCause.getEntity() instanceof LivingEntity ? (LivingEntity) pCause.getEntity() : null;
        boolean flag= entity1!=null && checkSmiteInItem(entity1.getMainHandItem(),entity1);
        boolean flag1= this.getOwner()!=null || this.getNecromancer()!=null;
        if(!flag){
            if(flag1){
                if(this.getOwner()!=null){
                    if(this.getDispawnTimer()!=0){
                        this.removeEntityOfList();
                        this.unarmedMoment();
                    }
                }
                if(this.getNecromancer()!=null){
                    this.removeEntityOfList();
                    this.unarmedMoment();
                }
            }else {
                super.die(pCause);
            }

        }else {
            super.die(pCause);
        }
    }

    public boolean getDamageLink() {
        return this.entityData.get(DAMAGE_LINK);
    }

    public void setDamageLink(boolean b) {
        this.entityData.set(DAMAGE_LINK,b);
        this.damageLinkTimer=b ? 20 : 0;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if(this.itIsLinked()){
            this.setDamageLink(true);
        }
        return super.hurt(pSource, pAmount);
    }

    private void unarmedMoment(){
        this.setHealth(1.0f);
        this.setInvulnerable(true);
        this.setIsArmed(false,false);
        this.setUnarmed(true);
    }

    public boolean checkSmiteInItem(ItemStack itemStack,LivingEntity entity){
        return itemStack.getItem() instanceof SwordItem && entity instanceof Player && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SMITE,itemStack)!=0;
    }

    public int getDispawnTimer() {
        return this.dispawnTimer;
    }

    protected void populateDefaultEquipmentSlots(DifficultyInstance pDifficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(this.level.random.nextFloat() < 0.5 ? Items.STONE_SWORD : Items.STONE_AXE));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.populateDefaultEquipmentSlots(pDifficulty);
        this.spawnAnim();
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0,new SpawnReanimatedGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(2,new FallenKnightAttack(this,1.0D,true));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.7));
        this.goalSelector.addGoal(4, new RandomLookAroundFallenKnightGoal(this));
        this.goalSelector.addGoal(6, new FloatGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(1,new Owner_Defend(this,false){
            @Override
            public boolean canUse() {
                if(this.mob instanceof FallenKnight fallenKnight){
                    return super.canUse() && fallenKnight.isArmed();
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(2,new Owner_Attacking(this){
            @Override
            public boolean canUse() {
                if(this.mob instanceof FallenKnight fallenKnight){
                    return super.canUse() && fallenKnight.isArmed();
                }
                return super.canUse();
            }
        });
        this.goalSelector.addGoal(3,new FollowOwnerGoalReanimate(this,1.0d,10.0f,3.0f,false));
    }
    public boolean isArmed(){
        return this.entityData.get(ARMED);
    }

    public void setIsArmed(boolean b,boolean isSpawn){
        this.entityData.set(ARMED,b);
        if(!isSpawn){
            this.reviveTimer= !b ? 200 : 0;
        }
        if(b){
            this.setInvulnerable(false);
        }
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setIsAttacking(boolean pBoolean){
        this.entityData.set(ATTACKING,pBoolean);
        this.attackTimer= pBoolean ? 10 : 0;
    }

    public boolean isOnGroundUnarmed() {
        return this.entityData.get(ON_GROUND_UNARMED);
    }

    public void setOnGroundUnarmed(boolean b){
        this.entityData.set(ON_GROUND_UNARMED,b);
        this.reviveTimer= b ? 200 : 0;
    }

    public boolean isUnarmed() {
        return this.entityData.get(UNARMED);
    }

    public void setUnarmed(boolean b){
        this.entityData.set(UNARMED,b);
        this.unarmedTimer= b ? 10 : 0;
        if (b){
            this.level.playSound(null,this, SoundEvents.SKELETON_DEATH, SoundSource.AMBIENT,1.0f,1.0f);
        }
    }
    public boolean itIsLinked() {
        return this.entityData.get(LINKED);
    }

    public void setLink(boolean b){
        this.entityData.set(LINKED,b);
    }

    private void setIsRearmed(boolean b) {
        this.entityData.set(REARMED,b);
        this.rearmedTimer= b ? 20 : 0;
        if (b){
            this.level.playSound(null,this, ModSounds.FALLEN_KNIGHT_REVIVE.get(), SoundSource.AMBIENT,1.5f,1.0f);
        }
    }

    public boolean isRearmed() {
        return this.entityData.get(REARMED);
    }
    @Override
    public void aiStep() {
        if(this.getDamageLink()){
            this.damageLinkTimer--;
        }
        if (this.damageLinkTimer==0 && this.getDamageLink()){
            this.setDamageLink(false);
        }
        if(this.isEndless && this.dispawnTimer>0){
            this.dispawnTimer--;
        }
        if (this.dispawnTimer==0 && this.isEndless) {
            this.removeEntityOfList();
            this.hurt(this.damageSources().magic(),this.getMaxHealth());
        }
        if(this.isAttacking()){
            this.attackTimer--;
        }
        if(this.attackTimer==0 && this.isAttacking()){
            this.setIsAttacking(false);
        }
        if(this.isUnarmed()){
            this.removeEntityOfList();
            this.unarmedTimer--;
        }
        if(this.unarmedTimer==0 && this.isUnarmed()){
            this.setUnarmed(false);
            this.setOnGroundUnarmed(true);
        }
        if(this.isOnGroundUnarmed()){
            this.reviveTimer--;
        }
        if(this.reviveTimer==0 && this.isOnGroundUnarmed()){
            this.setOnGroundUnarmed(false);
            this.setIsRearmed(true);
        }
        if(this.isRearmed()){
            this.rearmedTimer--;
        }
        if (this.rearmedTimer==0 && this.isRearmed()){
            this.setIsRearmed(false);
            this.setIsArmed(true,false);
            this.heal(this.getMaxHealth());
            this.addEntityOfList();
        }
        super.aiStep();
    }
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isUnarmed",this.isUnarmed());
        pCompound.putBoolean("isAttacking",this.isAttacking());
        pCompound.putBoolean("isRearmed",this.isRearmed());
        pCompound.putBoolean("isArmed",this.isArmed());
        pCompound.putBoolean("isOnGround",this.isOnGroundUnarmed());
        pCompound.putBoolean("ItIsLinked",this.itIsLinked());
        pCompound.putBoolean("damageLink",this.getDamageLink());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setIsRearmed(pCompound.getBoolean("isRearmed"));
        this.setUnarmed(pCompound.getBoolean("isUnarmed"));
        this.setIsAttacking(pCompound.getBoolean("isAttacking"));
        this.setIsArmed(pCompound.getBoolean("isArmed"),false);
        this.setOnGroundUnarmed(pCompound.getBoolean("isOnGround"));
        this.setLink(pCompound.getBoolean("ItIsLinked"));
        this.setDamageLink(pCompound.getBoolean("damageLink"));
        this.updateListLinked();
    }

    public void removeEntityOfList(){
        if(this.getOwner()!=null){
            if(this.getOwner() instanceof INecromancerEntity entity){
                entity.getBondedMinions().remove(this);
            }
        }
    }
    public void addEntityOfList(){
        if(this.getOwner()!=null){
            if(this.getOwner() instanceof INecromancerEntity entity){
                entity.getBondedMinions().add(this);
            }
        }
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING,false);
        this.entityData.define(UNARMED,false);
        this.entityData.define(REARMED,false);
        this.entityData.define(ARMED,true);
        this.entityData.define(ON_GROUND_UNARMED,false);
        this.entityData.define(LINKED,false);
        this.entityData.define(DAMAGE_LINK,false);
    }

    private void updateListLinked(){
        if(this.getOwner()!=null){
            if(this.itIsLinked()){
                if(this.getOwner() instanceof INecromancerEntity entity){
                    entity.getBondedMinions().add(this);
                    this.dispawnTimer=150;
                }
            }
        }
        if(this.getIdNecromancer()!=null){
            if(this.getNecromancer() instanceof Blade_KnightEntity bk){
                bk.getKnights().add(this);
            }
        }
    }
    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        if(this.isArmed()){
            if (event.isMoving() && !this.isAttacking() && !this.isAggressive()) {
                event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.fallenknight.walk1"));
            }else if(event.isMoving() && this.isAggressive() && !this.isAttacking()){
                event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.fallenknight.walk2"));
            } else if (this.isAttacking()){
                event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.fallenknight.attack1"));
            }else   {
                event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.fallenknight.idle"));
            }
        }else{
            if(this.isUnarmed() && !this.isOnGroundUnarmed()){
                event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.fallenknight.death1"));
            } else if (this.isRearmed() && !this.isOnGroundUnarmed()) {
                event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.fallenknight.revive1"));
            }else {
                event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.fallenknight.death2"));
            }
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void spawnAnim() {
        this.setIsArmed(false,true);
        this.setIsRearmed(true);
        super.spawnAnim();
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

    static class FallenKnightAttack extends MeleeAttackGoal {
        private final FallenKnight goalOwner;

        public FallenKnightAttack(FallenKnight entity, double speedModifier, boolean followWithoutLineOfSight) {
            super(entity, speedModifier, followWithoutLineOfSight);
            this.goalOwner = entity;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
        }

        @Override
        protected void checkAndPerformAttack(@NotNull LivingEntity entity, double distance) {
            double d0 = this.getAttackReachSqr(entity);
            if (distance <= d0 && this.goalOwner.attackTimer <= 0 && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                this.goalOwner.playSound(SoundEvents.STRAY_HURT, 1.0F, -1.0F);
                this.goalOwner.doHurtTarget(entity);
                this.goalOwner.getNavigation().stop();
            }
        }

        @Override
        public boolean canUse() {
            return super.canUse()  && this.goalOwner.isArmed();
        }

        @Override
        protected void resetAttackCooldown() {
            super.resetAttackCooldown();
            this.goalOwner.setIsAttacking(true);
        }

    }
    
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.STRAY_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.STRAY_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.STRAY_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }
}
