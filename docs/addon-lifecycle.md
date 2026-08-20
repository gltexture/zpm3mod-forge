# Addon Lifecycle

ZombiePlague3 is built on top of the standard **Minecraft Forge lifecycle**. On top of the Forge lifecycle, ZPM3 provides its own lifecycle for its internal systems and addons.

The simplified lifecycle looks like this:

```text
Forge
 │
 ├── Mod Constructor
 │       │
 │       └── ZPM3 initialization
 │
 ├── FML Common Setup
 │       │
 │       └── ZPM3 Common Setup
 │               │
 │               └── Addon lifecycle
 │                    ├── PreInit
 │                    ├── Init
 │                    └── PostInit
 │
 ├── FML Client Setup
 │       │
 │       └── ZPM3 Client Setup
 │               │
 │               └── Addon Client Setup
 │
 └── Client Shutdown
         │
         └── ZPM3 Client Shutdown
                 │
                 └── Addon Client Shutdown
```

![{E20BCB5A-13E3-41C9-8A09-7F248BFA43C4}.png](pictures/%7BE20BCB5A-13E3-41C9-8A09-7F248BFA43C4%7D.png)

> **Note:** The names of the lifecycle stages and their corresponding methods in the source code may differ slightly from the names used in this documentation.

---

## Lifecycle Contexts

During the **PreInit**, **Init**, **PostInit**, and **ClientInit** stages, addons receive specialized **lifecycle contexts**.

Each context provides the parameters and APIs available to the addon during the corresponding lifecycle stage.

Instead of accessing ZPM3's internal systems directly, addons receive the necessary functionality through the appropriate context.

Different lifecycle stages use different contexts because the systems and operations available at each stage are different.

Therefore, when developing an addon, it is important to consider **both the lifecycle stage and the context provided during that stage**.

## Next Step

The next step is to understand the **basic architecture of ZPM3** and how its major systems and modules are organized.

See:

* [Mod Architecture](mod-architecture.md)
