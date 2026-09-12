# 末地维度改版计划（参考 BetterEnd）

## Context（背景）

本模组 `fantasy_the_end`（Fabric 1.21.11）当前以「可驯服末影人」为核心玩法，已具备区块/物品/实体/自定义结构删除等基础。用户希望将**末地维度完整改版**，参考开源模组 **BetterEnd**（github.com/quiqueck/BetterEnd，MIT 协议），并且明确：范围**整跑全套**（主岛改版 + 生物群系 + 怪物 + 方块/矿石/装备 + 仪式系统等），可复制 BetterEnd 的内容/代码/纹理，但**必须在 README 中注明参考来源**。

### 关键决策
- **许可**：BetterEnd 为 MIT，允许复制/改写。沿用其代码时保留原 LICENSE 头；在 README 的**致谢段落**（放在靠下的致谢/Thanks 部分，不在顶部写「基于 XX 改造」）里感谢所有被参考的项目即可。参考来源可能不止 BetterEnd 一个，若有多个就在同一致谢段落里逐一感谢，不夸大"基于某单一项目改造全体"。
- **版本适配**：BetterEnd 官方适配到 1.21.8/26.x，本模组为 **1.21.11**；复制代码需按 1.21.11 的 Yarn Mappings 调整（1.21.11 有大量 API 变动，与训练数据版本差异大，需边拷边适配编译）。
- **形态**：将 BetterEnd 的相关代码以 `com.fantasy.end.*` 重打包进本模组（自成一体、可直接进 mods 文件夹），而非把 BetterEnd 当作外部依赖。
- **范围**：全套内容分批移植，一次会话无法全部改造完成，按阶段交付、每阶段验证。

### 现实范围提示
BetterEnd 全套（24+ 生物群系 / 6 怪物 / 9 木 / 7 石 / 装备 / 注入仪式 / 自定义世界生成器）是数周量级的完整维度大修。本计划按**阶段交付**，先落地用户最关心的**末地主岛改版**（原视频展示内容），再逐阶段扩展，每阶段可独立构建、进游戏验证。

---

## Phase 1 — 末地主岛改版（核心，优先交付）

目标：替换原版主岛的观感与生成，覆盖「黑曜石柱子/水晶塔 + 中央岛结构 + 龙战流程」。

### 1.1 注册骨架（从零搭建 worldgen 注册）
- 新建 `ModBiomes.java`（`com.fantasy.end.registry`）：`Registry.register(Registries.BIOME, ...)` 注册自定义主岛 Biome。
- 新建 `ModStructures.java`：结构三级注册 `StructureFeatureType` → `ConfiguredStructureFeature` → `PlacedStructureFeature`。
- 新建 `ModFeatures.java`：自定义 Feature 注册（现有 `EnderVineFeature` 归入统一管理）。
- `fabric.mod.json` 增加 `worldgen` 相关 entrypoint（如需）。

### 1.2 黑曜石柱子 / 水晶塔重做
- **Mixin** 替换原版 `EndPillarFeature#generate`（1.21.11 仍在 `net.minecraft.world.gen.feature`），改用自己的塔生成逻辑（参考 BetterEnd `structure/` 下的 pillar 模板）。
- 提供水晶塔 NBT/程序化第列模板，落位到原版主岛 ring 位置。
- 沿用当前 `ModBlocks` 的方块（`ender_stone`、`phantom_stone`、石英/紫珀等）做塔体材质。

### 1.3 中央岛结构与地形
- 在 `0,0` 龙息柱区域用 Jigsaw 放置自定义结构（参考原版 `minecraft:end/city` 池注入方案）。
- 地形：通过 NoiseSettings 微调主岛起伏（如需，参考 BetterEnd 自定义噪声思路）。

### 1.4 末影龙战斗流程
- **Mixin** `EnderDragonEntity` 相关 phase / AI（`net.minecraft.entity.boss.dragon.phase.*`）微调飞行路线。
- 控制龙柱水晶的生成点与新塔对齐。

### Phase 1 验证
- 重建进入末地主岛：确认新塔/柱子生成、中央岛结构存在、龙战可正常进行。

---

## Phase 2 — 生物群系（源自 BetterEnd 24+）
- 从 BetterEnd 移植代表性表面生物群系（琥珀之地、水晶山、暗影森林、雾之蘑菇地、紫颂森林等），注册 Biome + 对应植物/雾效。
- 通过生物群系替换逻辑（Mixin `EndBiomeProvider` 或 worldgen 注入）让末地外围生成这些群系。
- 为每个群系补 `worldgen/biome/*.json` + 植被 `placed_feature/configured_feature`。

## Phase 3 — 怪物 / 方块 / 矿石 / 装备
- 移植 6 个 BetterEnd 怪物（附生成规则）。
- 移植 9 种木 / 7 种石 / 矿石 / 装备 / 工具，接入现有 `ModBlocks`/`ModItems`/`ModItemGroups`。

## Phase 4 — 注入仪式 / 进阶机制
- 移植 Infusion Ritual 系统（BCLib 相关功能若依赖外部，用 Fabric API 等价实现或精简版）。

---

## 复用的现有代码
- `/Users/cangcang/code/Fantasy/Fantasy - The End/src/main/java/com/fantasy/end/registry/ModBlocks.java`（方块注册模式）
- `/Users/cangcang/code/Fantasy/Fantasy - The End/src/main/java/com/fantasy/end/registry/ModItems.java`（物品注册模式）
- `/Users/cangcang/code/Fantasy/Fantasy - The End/src/main/java/com/fantasy/end/registry/ModEntities.java`（实体 + 生成蛋模式）
- `/Users/cangcang/code/Fantasy/Fantasy - The End/src/main/java/com/fantasy/end/world/EnderVineFeature.java`（自定义 Feature 范式）
- 现有 `worldgen/configured_feature`、`placed_feature` JSON（如 `ender_vine`）作为数据驱动范式。

## 需要新建的文件（代表路径）
```
src/main/java/com/fantasy/end/registry/ModBiomes.java
src/main/java/com/fantasy/end/registry/ModStructures.java
src/main/java/com/fantasy/end/registry/ModFeatures.java
src/main/java/com/fantasy/end/world/EndPillarFeature.java      (或 Mixin)
src/main/java/com/fantasy/end/mixin/EndPillarFeatureMixin.java
src/main/java/com/fantasy/end/mixin/EnderDragonPhaseMixin.java
src/main/resources/data/fantasy_the_end/worldgen/biome/*.json
src/main/resources/data/fantasy_the_end/worldgen/structure/*.json
src/main/resources/data/fantasy_the_end/worldgen/configured_feature/*.json
src/main/resources/data/fantasy_the_end/worldgen/placed_feature/*.json
```
（README 增加 BetterEnd 致谢段落）

---

## 验证方式（端到端）
1. 每阶段 `./gradlew build`（本地 Java 构建，已验证可行），替换到 SulfurLauncher `fantasy-the-end` 实例的 mods 目录。
2. 进游戏：`/execute in minecraft:the_end run ...` 传送至主岛 `0,0`，检查塔/柱、中央结构、龙战。
3. 外围末地：检查新生物群系与植被是否按预期生成。
4. 在 README 的致谢段落中致谢所参考的项目（BetterEnd 等），并保留其 LICENSE 头，完成后 commit + push（触发云端构建）。

## 待定点
- Phase 2-4 的庞大内容采用「分批移植 + 每批验证」策略，避免一次性大量代码导致编译爆错难定位（前几轮已多次因 1.21.11 API 差异连环爆错）。