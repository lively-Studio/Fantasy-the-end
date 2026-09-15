# Fantasy: The End

> 一个不可能到来的末地更新 —— 为玩家带来幻想中的末地维度扩展。

## 简介

**幻想:末地** 是一个 Minecraft Fabric 模组，支持 **1.21.x ~ 26.x** 整条版本线，旨在为玩家提供原版未曾实现的末地维度扩展体验。模组新增了末影系列物品、方块、状态效果以及自定义结构生成，让末地探索更加丰富。

## 功能特性

### 物品

| 物品 | 说明 |
|------|------|
| 幻影之眼 (Phantom Eye) | 右键使用后获得 1 小时「末影」状态效果。合成配方：9 个末影之眼（无序合成） |

### 状态效果

**末影 (Ender)** — 持续 1 小时，提供以下能力：

- **末影之眼不消耗**：投掷末影之眼传送时不会消耗物品（Mixin 实现）
- **空手传送**：空手右键点击任意位置（空中或方块），传送到视线终点（最远 32 格），含安全落点检测与 1 秒冷却
- **传送门自动激活**：站在未激活的末地传送门框架中心，自动填满末影之眼并生成传送门

### 方块

| 方块 | 中文 | 英文 |
|------|------|------|
| 原石 | 末影原石 / 幻影原石 | Ender Ore / Phantom Ore |
| 半砖 | 末影原石半砖 / 幻影原石半砖 | Ender Ore Slab / Phantom Ore Slab |
| 门 | 末影原石门 / 幻影原石门 | Ender Ore Door / Phantom Ore Door |
| 活板门 | 末影原石活板门 / 幻影原石活板门 | Ender Ore Trapdoor / Phantom Ore Trapdoor |

### 合成配方

| 产物 | 配方 |
|------|------|
| 幻影之眼 | 9 x 末影之眼（无序合成） |
| 末影原石 | 4 x 末影之眼 |
| 幻影原石 | 4 x 幻影之眼 |

### 结构生成

- 支持自定义 NBT 结构在末地外岛随机生成
- 原版末地城可被抹除或替换为自定义建筑

## 环境要求

- Minecraft 1.21.11
- Fabric Loader >= 0.18.4
- Fabric API
- Java 21+

## 安装

1. 确保已安装 Fabric Loader 和 Fabric API
2. 将 `fantasy-the-end-1.0.0.jar` 放入 `.minecraft/mods/` 目录
3. 完全重启游戏

> 服务器和客户端均需安装本模组。

## 开发

### 技术栈

- Java 21
- Fabric Loom 1.14
- Yarn Mappings 1.21.11+build.6
- Gradle 9.2.1

### 构建

```bash
./gradlew build -x test
```

构建产物位于 `build/libs/fantasy-the-end-1.0.0.jar`。

### 项目结构

```
src/
├── main/
│   ├── java/com/fantasy/end/
│   │   ├── FantasyTheEnd.java          # 主入口
│   │   ├── effect/                     # 状态效果
│   │   ├── event/                      # 事件处理（传送门激活）
│   │   ├── handler/                    # 事件处理（空手传送）
│   │   ├── item/                       # 自定义物品
│   │   ├── mixin/                      # Mixin（末影之眼不消耗）
│   │   └── registry/                   # 注册（方块/物品/效果/配方/物品组）
│   └── resources/
│       ├── fabric.mod.json
│       ├── assets/fantasy_the_end/     # 客户端资源（模型/纹理/语言/方块状态）
│       └── data/fantasy_the_end/       # 服务端数据（合成配方/结构/世界生成）
└── client/
    ├── java/com/fantasy/end/
    │   └── FantasyTheEndClient.java    # 客户端入口（渲染层注册）
    └── resources/
        └── fantasy_the_end.client.mixins.json
```

## 多版本构建 (Multi-Version Build)

本模组采用多版本 Gradle 工程，同一套共享代码（`common/`）分别构建出 1.21.x 与 26.x 两个版本的 jar：

```
Fantasy - The End/
├── common/                 # 双版本共享源码（BetterEnd 移植代码在此）
├── versions/
│   ├── fabric-1.21.x/       # 1.21.x 专属源码 + 构建配置
│   └── fabric-26.x/         # 26.x 专属源码 + 构建配置（含 BCLib / WorldWeaver）
└── settings.gradle         # 挂载两个版本工程
```

各版本独立构建：

```bash
# 构建 1.21.x
./gradlew :fabric-1.21.x:build
# 构建 26.x（需 JDK 25+）
./gradlew :fabric-26.x:build
```

- `common/` 中的代码由两版本工程一起编译，力求使用纯 Fabric API / 原版接口以保证双版本通用；
- 版本差异（API、Mixin 目标等）放在对应 `versions/<ver>/src` 中；
- 26.x 线依赖 BetterX 的 **BCLib / WorldWeaver**（BetterEnd 的基础库）。

> 注：26.x 的 `yarn_mappings` 等版本号需在 [fabricmc.net/versions](https://fabricmc.net/versions.html) 核对实际值。

## 衍生声明与致谢 (Derivative Notice & Credits)

**Fantasy: The End 是开源模组 [BetterEnd](https://github.com/quiqueck/BetterEnd)（MIT 协议，Copyright © 2020 paulevsGitch）的二次开发（衍生作品）。** 我们在 BetterEnd 的末地维度改版之上进行移植与再开发，将其生物群系、主岛结构、黑曜石柱 / 水晶塔、方块 / 矿石 / 装备、怪物、注入仪式等功能整合进本模组。

BetterEnd 以 MIT 许可证发布，我们在复用其代码时保留原有版权声明与许可证头，并在此致谢原作者与贡献者：

- **BetterEnd**（MIT）— 本模组所基于的衍生来源，末地维度改版的核心实现与美术设计
- **BCLib / WorldWeaver（BetterX）** — BetterEnd 所依赖的基础库
- **Fabric API / Fabric Loom** — 模组开发工具链
- **Minecraft 社区 / Yarn Mappings** — 开发依赖

> 说明：本模组复用 BetterEnd 的**代码**（MIT 许可），美术资源（纹理 / 模型）由本模组自行生成，未直接采用 BetterEnd 的 CC BY-NC-SA 资源，以避免非商业（NC）许可限制。

本模组自身同样以 MIT 许可证发布，详见 [LICENSE](LICENSE)。

## 许可证

本项目基于 [MIT](LICENSE) 许可证开源。

## 链接

- [GitHub 仓库](https://github.com/lively-Studio/Fantasy-the-end)
