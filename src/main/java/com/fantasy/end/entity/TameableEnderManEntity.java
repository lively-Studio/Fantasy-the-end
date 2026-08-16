/*
 * Copyright (C) 2026 cangcang
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.fantasy.end.entity;

import com.fantasy.end.item.PurplePoppedChorusFruitItem;
import com.fantasy.end.screen.EnderManScreenHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class TameableEnderManEntity extends EndermanEntity implements NamedScreenHandlerFactory {

    private static final TrackedData<Boolean> TAMED = DataTracker.registerData(TameableEnderManEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<String> OWNER_UUID_STRING = DataTracker.registerData(TameableEnderManEntity.class, TrackedDataHandlerRegistry.STRING);

    private static final int INVENTORY_SIZE = 54;
    private static final int ARMOR_SLOT_COUNT = 4;
    private static final int TOTAL_INVENTORY_SIZE = INVENTORY_SIZE + ARMOR_SLOT_COUNT;

    // 背包槽位索引: 0-53 为物品, 54-57 为盔甲 (HEAD=54, CHEST=55, LEGS=56, FEET=57)
    private final SimpleInventory inventory = new SimpleInventory(TOTAL_INVENTORY_SIZE) {
        @Override
        public void markDirty() {
            super.markDirty();
            TameableEnderManEntity.this.syncArmorFromInventory();
        }
    };

    private int itemPickupCooldown = 0;

    public TameableEnderManEntity(EntityType<? extends EndermanEntity> entityType, World world) {
        super(entityType, world);
        // 设置攻击伤害为 10.0
        if (this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE) != null) {
            this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(10.0);
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TAMED, false);
        builder.add(OWNER_UUID_STRING, "");
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
    }

    // ========== 驯服 ==========

    public boolean isTamed() {
        return this.dataTracker.get(TAMED);
    }

    public void setTamed(boolean tamed) {
        this.dataTracker.set(TAMED, tamed);
        if (tamed) {
            // 驯服后：忽略玩家视线，不搬运方块
            this.setDespawnCounter(0);
        }
    }

    @Nullable
    public UUID getOwnerUuid() {
        String uuidStr = this.dataTracker.get(OWNER_UUID_STRING);
        if (uuidStr.isEmpty()) return null;
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID_STRING, uuid != null ? uuid.toString() : "");
    }

    @Nullable
    public PlayerEntity getOwner() {
        UUID uuid = this.getOwnerUuid();
        if (uuid == null || !(this.getEntityWorld() instanceof ServerWorld serverWorld)) {
            return null;
        }
        return serverWorld.getPlayerByUuid(uuid);
    }

    public boolean tame(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerUuid(player.getUuid());
        // 清除愤怒目标
        this.setAngryAt(null);
        // 设置为不敌对
        this.setTarget(null);
        // 增加好感度
        if (this.getEntityWorld() instanceof ServerWorld serverWorld) {
            this.getNavigation().stop();
        }
        return true;
    }

    // ========== 交互 ==========

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!this.isTamed()) {
            // 未驯服：使用紫色末影珍珠驯服
            if (stack.getItem() instanceof PurplePoppedChorusFruitItem) {
                if (!this.getEntityWorld().isClient()) {
                    if (!player.isCreative()) {
                        stack.decrement(1);
                    }
                    this.tame(player);
                    this.getEntityWorld().sendEntityStatus(this, (byte) 18); // 驯服成功爱心粒子
                }
                return ActionResult.SUCCESS;
            }
            // 未驯服时不做其他交互
            return super.interactMob(player, hand);
        }

        // 驯服后：右键打开GUI
        if (!this.getEntityWorld().isClient() && player.isSneaking()) {
            player.openHandledScreen(this);
            return ActionResult.SUCCESS;
        }

        // 非潜行右键：如果手持物品，放入背包（简单交互）
        if (!this.getEntityWorld().isClient() && !stack.isEmpty()) {
            // 尝试将物品放入背包
            ItemStack remaining = this.addToInventory(stack);
            if (remaining.isEmpty()) {
                stack.decrement(1);
            } else {
                stack.setCount(remaining.getCount());
            }
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    // ========== 背包 ==========

    public SimpleInventory getInventory() {
        return this.inventory;
    }

    /**
     * 手动将物品添加到背包中
     */
    private ItemStack addToInventory(ItemStack stack) {
        ItemStack remaining = stack.copy();
        for (int i = 0; i < this.inventory.size(); i++) {
            ItemStack slotStack = this.inventory.getStack(i);
            if (slotStack.isEmpty()) {
                this.inventory.setStack(i, remaining);
                remaining = ItemStack.EMPTY;
                break;
            } else if (ItemStack.areItemsAndComponentsEqual(slotStack, remaining) && slotStack.getCount() < slotStack.getMaxCount()) {
                int space = slotStack.getMaxCount() - slotStack.getCount();
                int transfer = Math.min(space, remaining.getCount());
                slotStack.increment(transfer);
                remaining.decrement(transfer);
                if (remaining.isEmpty()) break;
            }
        }
        return remaining;
    }

    /**
     * 从背包同步盔甲到装备槽
     */
    private void syncArmorFromInventory() {
        if (this.getEntityWorld() == null || this.getEntityWorld().isClient()) return;

        ItemStack head = this.inventory.getStack(INVENTORY_SIZE);     // 54
        ItemStack chest = this.inventory.getStack(INVENTORY_SIZE + 1); // 55
        ItemStack legs = this.inventory.getStack(INVENTORY_SIZE + 2);  // 56
        ItemStack feet = this.inventory.getStack(INVENTORY_SIZE + 3);  // 57

        this.equipStack(EquipmentSlot.HEAD, head);
        this.equipStack(EquipmentSlot.CHEST, chest);
        this.equipStack(EquipmentSlot.LEGS, legs);
        this.equipStack(EquipmentSlot.FEET, feet);
    }

    /**
     * 从装备槽同步到背包
     */
    private void syncArmorToInventory() {
        this.inventory.setStack(INVENTORY_SIZE, this.getEquippedStack(EquipmentSlot.HEAD));
        this.inventory.setStack(INVENTORY_SIZE + 1, this.getEquippedStack(EquipmentSlot.CHEST));
        this.inventory.setStack(INVENTORY_SIZE + 2, this.getEquippedStack(EquipmentSlot.LEGS));
        this.inventory.setStack(INVENTORY_SIZE + 3, this.getEquippedStack(EquipmentSlot.FEET));
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        super.equipStack(slot, stack);
        if (!this.getEntityWorld().isClient()) {
            this.syncArmorToInventory();
        }
    }

    // ========== 捡拾物品 ==========

    @Override
    protected void mobTick(ServerWorld world) {
        super.mobTick(world);

        if (!this.isTamed() || world.isClient()) return;

        // 降低捡拾冷却
        if (this.itemPickupCooldown > 0) {
            this.itemPickupCooldown--;
            return;
        }

        // 捡拾周围的掉落物
        Box box = this.getBoundingBox().expand(3.0, 1.0, 3.0);
        List<ItemEntity> items = world.getEntitiesByClass(
                ItemEntity.class, box, itemEntity -> {
                    if (itemEntity == null || !itemEntity.isAlive()) return false;
                    ItemStack stack = itemEntity.getStack();
                    if (stack.isEmpty()) return false;
                    // 不捡拾本模组物品（紫色末影珍珠）
                    return !(stack.getItem() instanceof PurplePoppedChorusFruitItem);
                }
        );

        for (ItemEntity itemEntity : items) {
            if (itemEntity == null) continue;
            ItemStack stack = itemEntity.getStack();
            if (stack.isEmpty()) continue;

            ItemStack remaining = this.addToInventory(stack);
            if (remaining.isEmpty()) {
                itemEntity.discard();
                this.itemPickupCooldown = 10; // 0.5秒冷却
            } else {
                stack.setCount(remaining.getCount());
            }
        }
    }

    // ========== 攻击伤害 ==========

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        boolean attacked = super.tryAttack(world, target);
        if (attacked && this.isTamed()) {
            // 驯服后攻击伤害 10 点
            // 由于 EndermanEntity 的 tryAttack 会调用 super.tryAttack，我们直接修改属性
            // 实际上通过属性控制
        }
        return attacked;
    }

    // ========== 愤怒/眼睛状态 ==========

    @Override
    public boolean isAngry() {
        // 驯服后不愤怒（眼睛不变红）
        if (this.isTamed()) return false;
        return super.isAngry();
    }

    // ========== 传送行为 ==========

    // teleportRandomly() 已被重写以阻止驯服后传送
    @Override
    public boolean teleportRandomly() {
        if (this.isTamed()) return false;
        return super.teleportRandomly();
    }

    // ========== 屏幕 ==========

    @Override
    public Text getDisplayName() {
        if (this.isTamed()) {
            PlayerEntity owner = this.getOwner();
            if (owner != null) {
                return Text.translatable("entity.fantasy_the_end.tameable_enderman.tamed", owner.getName());
            }
        }
        return Text.translatable("entity.fantasy_the_end.tameable_enderman");
    }

    public Text getTitle() {
        return this.getDisplayName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new EnderManScreenHandler(syncId, playerInventory, this);
    }

    // ========== NBT 持久化 ==========

    @Override
    protected void writeCustomData(WriteView nbt) {
        super.writeCustomData(nbt);
        nbt.putBoolean("Tamed", this.isTamed());

        UUID ownerUuid = this.getOwnerUuid();
        if (ownerUuid != null) {
            nbt.putString("Owner", ownerUuid.toString());
        }

        // 保存背包
        DefaultedList<ItemStack> list = DefaultedList.ofSize(TOTAL_INVENTORY_SIZE, ItemStack.EMPTY);
        for (int i = 0; i < TOTAL_INVENTORY_SIZE; i++) {
            list.set(i, this.inventory.getStack(i));
        }
        WriteView inventoryView = nbt.get("Inventory");
        Inventories.writeData(inventoryView, list);
    }

    @Override
    protected void readCustomData(ReadView nbt) {
        super.readCustomData(nbt);
        this.setTamed(nbt.getBoolean("Tamed", false));

        nbt.getOptionalString("Owner").ifPresent(uuidStr -> {
            try {
                this.setOwnerUuid(UUID.fromString(uuidStr));
            } catch (IllegalArgumentException ignored) {
            }
        });

        // 读取背包
        nbt.getOptionalReadView("Inventory").ifPresent(inventoryView -> {
            DefaultedList<ItemStack> list = DefaultedList.ofSize(TOTAL_INVENTORY_SIZE, ItemStack.EMPTY);
            Inventories.readData(inventoryView, list);
            for (int i = 0; i < TOTAL_INVENTORY_SIZE; i++) {
                this.inventory.setStack(i, list.get(i));
            }
        });

        // 同步盔甲
        this.syncArmorFromInventory();
    }

    // ========== 实体属性 ==========

    // 攻击伤害通过构造函数中的属性设置
    // EndermanEntity 默认 7.0，我们改为 10.0

    // ========== 内部 AI 目标 ==========

    /**
     * 驯服末影人攻击伤害主人的目标
     */
    static class TrackOwnerAttackerGoal extends TrackTargetGoal {
        private final TameableEnderManEntity enderman;
        private LivingEntity attacker;
        private int lastAttackTime;

        public TrackOwnerAttackerGoal(TameableEnderManEntity enderman) {
            super(enderman, false);
            this.enderman = enderman;
            this.setControls(EnumSet.of(Control.TARGET));
        }

        @Override
        public boolean canStart() {
            if (!this.enderman.isTamed()) return false;
            LivingEntity owner = this.enderman.getOwner();
            if (owner == null) return false;
            this.attacker = owner.getAttacker();
            return this.attacker != null;
        }

        @Override
        public void start() {
            this.enderman.setTarget(this.attacker);
            super.start();
        }
    }

    /**
     * 驯服末影人与主人攻击同一目标
     */
    static class AttackWithOwnerGoal extends TrackTargetGoal {
        private final TameableEnderManEntity enderman;
        private LivingEntity target;
        private int lastAttackTime;

        public AttackWithOwnerGoal(TameableEnderManEntity enderman) {
            super(enderman, false);
            this.enderman = enderman;
            this.setControls(EnumSet.of(Control.TARGET));
        }

        @Override
        public boolean canStart() {
            if (!this.enderman.isTamed()) return false;
            LivingEntity owner = this.enderman.getOwner();
            if (owner == null) return false;
            this.target = owner.getAttacking();
            if (this.target == null) return false;
            // 不攻击主人
            if (this.target == owner) return false;
            // 不攻击其他驯服末影人
            if (this.target instanceof TameableEnderManEntity other && other.isTamed()) return false;
            // 检查距离
            if (this.enderman.squaredDistanceTo(this.target) > 256.0) return false;
            return true;
        }

        @Override
        public void start() {
            this.enderman.setTarget(this.target);
            super.start();
        }
    }

    // ========== 额外逻辑 ==========

    @Override
    public boolean canImmediatelyDespawn(double distance) {
        return !this.isTamed() && super.canImmediatelyDespawn(distance);
    }
}