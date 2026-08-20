# Mod Architecture

ZombiePlague3 was originally designed as a **large-scale project**, so the architecture was built with flexibility and modularity in mind.

ZPM3 follows a **semi-modular monolithic architecture**. The mod is still distributed and loaded as a single Forge mod, but internally its functionality is divided into independent functional modules, each with its own initialization logic and responsibilities.

Each module is responsible for a specific area of functionality, such as blocks, items, entities, weapons, networking, or world generation. This makes it easier to develop, maintain, and extend individual systems without turning the entire codebase into one large interconnected structure.

## Project Structure

The main ZPM3 source code is divided into two major directories:

```text
zpm3/
├── engine/
└── modules/
```

### `engine`

The `engine` directory contains the **core infrastructure of ZPM3**.

It includes common utility classes, initialization infrastructure, internal frameworks, registries, lifecycle handling, and other systems that are used by multiple modules.

The `engine` is not intended to represent a specific gameplay feature. Instead, it provides the infrastructure on which the rest of ZPM3 is built.

### `modules`

The `modules` directory contains the actual **functional modules of ZPM3**.

Each module is responsible for a particular area of functionality.

Current modules include:

| Module                   | Description                                                                                                                           |
| ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------- |
| `armor`                  | Armor and armor-related functionality                                                                                                 |
| `blocks`                 | Custom blocks and block-related systems                                                                                               |
| `commands`               | Commands and command-related UI utilities                                                                                             |
| `common`                 | Shared functionality that does not belong to a more specific module, such as creative tabs, sounds, damage types, and similar systems |
| `debug`                  | Debugging and development utilities                                                                                                   |
| `entities`               | Entities and systems related to entities                                                                                              |
| `fluids`                 | Custom fluids and fluid-related functionality                                                                                         |
| `food_medicine`          | Food, medicine, and related mechanics                                                                                                 |
| `fx`                     | Particles and other visual effects                                                                                                    |
| `guns`                   | Firearms, gun mechanics, and gun rendering                                                                                            |
| `loot_cases`             | Loot cases and loot tables                                                                                                            |
| `melee_throwables_tools` | Melee weapons, throwable items, and tools                                                                                             |
| `misc_items`             | Miscellaneous items that do not belong to another specialized module                                                                  |
| `mob_effects`            | Custom mob effects and related functionality                                                                                          |
| `net_pack`               | Networking utilities and network data synchronization                                                                                 |
| `player`                 | Player-related functionality                                                                                                          |
| `ui`                     | User interface and client-side UI systems                                                                                             |
| `worldgen`               | World generation and related systems                                                                                                  |

This separation is primarily **architectural**, rather than a collection of completely independent mods. Modules can still communicate with each other when their functionality requires it, while the module boundaries provide a clear organization for the codebase.

![{A2E202AC-39EA-48EE-84E3-30F43260E1EB}.png](pictures/%7BA2E202AC-39EA-48EE-84E3-30F43260E1EB%7D.png)

Understanding this structure is useful when working with the ZPM3 API because most APIs and systems are associated with a particular module.

## Next Step

The next step is to start **developing the addon itself**.

This section will cover the addon structure, the available lifecycle contexts, and the main APIs and utility functions provided by ZPM3 for addon development.

See:

* [Addon Development](addon-development.md)
