# Addon Development

This is the main section of the documentation for ZPM3 addon development.

It covers the basic steps required to connect an addon to the ZPM3 API, the addon lifecycle, lifecycle contexts, available APIs, and some internal details that are important when developing more advanced addons.

Some APIs described in this section are relatively advanced. You do not need to understand all of them to create a basic addon.

---

# Setting Up the ZPM3 Development Environment

Before developing an addon, the ZPM3 mod must be added to the addon project as a development dependency.

> **Important:** ZPM3 is currently **not distributed through a Maven repository**. Therefore, the mod must be added to the project manually as a local JAR dependency.

## 1. Add the ZPM3 JAR

Create a `libs` directory in the root of your addon project:

```text
your-addon/
├── libs/
│   └── zpm3forge-0.1-imgui.jar
├── src/
├── build.gradle
└── ...
```

Copy the ZPM3 JAR into the `libs` directory.

**The exact filename may be different depending on the ZPM3 version and build variant.**

---

## 2. Configure the Local Repository

Open `build.gradle` and add the `libs` directory as a local repository:

```groovy
repositories {
    flatDir {
        dirs 'libs'
    }

    // Other repositories...
}
```

This tells Gradle to search the `libs` directory for local dependencies.

---

## 3. Add ZPM3 as a Dependency

Add ZPM3 to the `dependencies` section:

```groovy
dependencies {
    minecraft "net.minecraftforge:forge:${minecraft_version}-${forge_version}"

    implementation fg.deobf("blank:zpm3forge-0.1-imgui:0.1")
}
```

The dependency name depends on the actual JAR filename.

For example, a file named:

```text
zpm3forge-0.1-imgui.jar
```

can be referenced as:

```groovy
implementation fg.deobf("blank:zpm3forge-0.1-imgui:0.1")
```

> **Note:** The exact dependency coordinates may change between ZPM3 versions. Always use the coordinates specified for the version you are developing against.

---

## 4. Regenerate the Development Environment

After adding the dependency, regenerate the IntelliJ development runs:

![{9188D94E-3B2D-4208-9490-AC6171DEE69E}.png](pictures/%7B9188D94E-3B2D-4208-9490-AC6171DEE69E%7D.png)

After this completes, reload the Gradle project in IntelliJ IDEA if necessary.

---

## 5. Declare ZPM3 as a Mod Dependency

Adding the JAR as a Gradle dependency is not enough.

Forge also needs to know that your addon **requires ZPM3 to be present at runtime**.

Open:

```text
src/main/resources/META-INF/mods.toml
```

and add the following dependency:

```toml
[[dependencies."${mod_id}"]]
modId = "zpm3"
mandatory = true
versionRange = "[0.1,)"
ordering = "NONE"
side = "BOTH"
```

For example, if your addon has:

```toml
modId = "testzp3addon"
```

the dependency section becomes:

```toml
[[dependencies."testzp3addon"]]
modId = "zpm3"
mandatory = true
versionRange = "[0.1,)"
ordering = "NONE"
side = "BOTH"
```

These parameters may differ depending on the ZPM3 version you are targeting.

### What these parameters mean

* `modId = "zpm3"` — identifies ZPM3 as the required mod.
* `mandatory = true` — the addon cannot run without ZPM3.
* `versionRange = "[0.1,)"` — specifies the supported ZPM3 versions.
* `ordering = "NONE"` — does not impose an additional loading order.
* `side = "BOTH"` — the dependency is required on both client and server.

With this configuration, Forge will verify that ZPM3 is available before loading the addon.

---
 
## Connecting an Addon to the ZPM3 API

Connecting a Forge mod to the ZPM3 API consists of several steps.

### 1. Implement `IZPAddonEntry`

First, the main class of your addon — the class annotated with `@Mod` — must implement `IZPAddonEntry`.

![{0D601089-1D7F-4EAD-9826-A81A383749C3}.png](pictures/%7B0D601089-1D7F-4EAD-9826-A81A383749C3%7D.png)

After implementing the interface, you need to implement the following method:

![{ECE62360-BB00-4957-957F-D82276AAF4EB}.png](pictures/%7BECE62360-BB00-4957-957F-D82276AAF4EB%7D.png)

This method must return an instance of `IZPAddonImpl`.

For now, the implementation can be an empty instance of the default implementation:

![{CCDCABDE-08B8-43E8-8A14-5133502702D2}.png](pictures/%7BCCDCABDE-08B8-43E8-8A14-5133502702D2%7D.png)

---

### 2. Register the Addon

This is not enough yet.

Currently, ZPM3 does not automatically detect Forge mods that implement `IZPAddonEntry`. Therefore, the addon must be registered manually.

Call `RegisterMeAsAddon()` from the addon constructor:

![{F927A0BF-A878-4B11-9699-0B1FDC4F06E4}.png](pictures/%7BF927A0BF-A878-4B11-9699-0B1FDC4F06E4%7D.png)

Once this is done, the initial ZPM3 API integration is complete.

---

# `IZPAddonImpl`

`IZPAddonImpl` is the main interface used to define the behavior of an addon.

It currently contains five lifecycle methods:

```java
void preInitialize(@NotNull IAddonPreInitContext context);

void initialize(@NotNull IAddonInitContext context);

void postInitialize(@NotNull IAddonPostInitContext context);

void clientSetup(@NotNull IAddonClientSetupContext context);

void clientShutDown();
```

Each method is called at a different point in the ZPM3 lifecycle.

The lifecycle methods receive different contexts because different APIs and systems are available at different stages.

---

# Client Setup

```java
void clientSetup(@NotNull IAddonClientSetupContext context);
```

`clientSetup` is called during the client initialization stage.

It receives an `IAddonClientSetupContext`, which provides APIs for registering client-side functionality.

![{F7A61F2B-9CA9-4117-9135-E6F4A64A24E9}.png](pictures/%7BF7A61F2B-9CA9-4117-9135-E6F4A64A24E9%7D.png)

## `registerImGuiInterface`

```java
void registerImGuiInterface(
        @NotNull Supplier<IZPImGuiInterface> imGuiInterface
);
```

Registers a custom ImGui interface provided by the addon.

The registered interface will be rendered by ZPM3 when the ImGui environment is available.

![{D13745A9-A634-40F4-9FA2-4B36DFA6BB9A}.png](pictures/%7BD13745A9-A634-40F4-9FA2-4B36DFA6BB9A%7D.png)
![{D7C195AC-6585-43D5-93E1-7D87F97ABAFF}.png](pictures/%7BD7C195AC-6585-43D5-93E1-7D87F97ABAFF%7D.png)

---

## `registerArmorSound`

```java
void registerArmorSound(
        ZPPlayerArmorSoundOnClientEvent.@NotNull TrackedSoundLauncher trackedSoundLauncher
);
```

Registers an object responsible for playing a **looping sound on an entity based on a predicate**.

This can be used, for example, to play a continuous armor-related sound while a particular condition is satisfied.

![{A4CEC8F9-B4F0-4205-BCC5-B39F1BBFD1E0}.png](pictures/%7BA4CEC8F9-B4F0-4205-BCC5-B39F1BBFD1E0%7D.png)


---

## `registerZoneEffect`

```java
void registerZoneEffect(
        @NotNull ZPZoneFlag flag,
        ZPRenderSpecialZoneEffectsOnClient.@NotNull RenderZoneEffect effect
);
```

Registers a custom client-side visual effect for a zone.

The effect is associated with a `ZPZoneFlag` and can be used to render particles or other visual effects.

![{F0B9B792-7804-4283-956C-B373BFFD3682}.png](pictures/%7BF0B9B792-7804-4283-956C-B373BFFD3682%7D.png)


---

## `registerZpArchivedMap`

```java
void registerZpArchivedMap(
    @NotNull String modId,
    @NotNull String folder
);
```

Registers an archived ZPM3 map.

The map is searched for **exclusively inside the mod JAR**, allowing an addon to distribute its own archived maps together with the addon.

---

## `createConditionToApplyFakeEffect`

```java
void createConditionToApplyFakeEffect(
        @NotNull ZPFakeClientEffect key,
        ZPLocalPlayerFakeEffectsManager.@NotNull ZPFakeEffectSetOnPlayerCondition condition
);
```

Registers a condition that determines when a **fake client-side effect** should be displayed on the player.

Unlike a normal Minecraft `MobEffect`, this mechanism can display an effect without actually applying the corresponding effect to the player.

![{363B0E5C-004C-4609-B632-6ACBD30D3B91}.png](pictures/%7B363B0E5C-004C-4609-B632-6ACBD30D3B91%7D.png)

---

## `getClientCallbacksManager`

```java
@NotNull
IZPClientCallbacksManager getClientCallbacksManager();
```

Returns the ZPM3 client callbacks manager.

It can be used to register various client callbacks.

The manager also provides functionality related to the **GLFW window**.

This is a more specialized API. For available callbacks and their behavior, refer to the `IZPClientCallbacksManager` implementation and its related classes in the ZPM3 source code.

---

## `getClientRenderHooksManager`

```java
@NotNull
IZPRenderHooksManager getClientRenderHooksManager();
```

Returns the ZPM3 client rendering hooks manager.

> **Deprecated:** This API is considered legacy and should generally not be used for new addon development.

---

## `getClientManager`

```java
@NotNull
IZPClientManager getClientManager();
```

Returns the main ZPM3 client manager.

It provides access to various client-side ZPM3 systems.

For example, it can be used to obtain the `PostFXChain` and register custom **screen post-processing effects**.

This is an advanced feature and is outside the scope of the basic addon guide. For an implementation example, see:

```text
ru.gltexture.zpm3.engine.client.rendering.postfx.processors
```

---

## `isImGuiContextValid`

```java
default boolean isImGuiContextValid() {
    return ZombiePlague3.getClientManager().isImGuiValid();
}
```

ZPM3 is distributed in two variants:

* a build with the ImGui library;
* a build without the ImGui library.

This method can be used to determine whether an ImGui context is currently available.

This is especially useful for addons that provide optional debugging or development interfaces through ImGui.

---

# Pre-Initialization

```java
void preInitialize(@NotNull IAddonPreInitContext context);
```

`preInitialize` is called during the ZPM3 pre-initialization stage.

It receives an `IAddonPreInitContext`.

![{9D23D431-E232-494C-A42C-D13CFD0F498C}.png](pictures/%7B9D23D431-E232-494C-A42C-D13CFD0F498C%7D.png)

The context currently provides APIs for registering custom zone data.

## `registerZoneFlag`

```java
@NotNull
ZPZoneFlag registerZoneFlag(@NotNull String flag);
```

Registers a custom `ZPZoneFlag`.

Zone flags can be used to identify zones and can later be used by other ZPM3 systems.

![{A7A101E2-0E50-43C8-B27E-E72E908AA167}.png](pictures/%7BA7A101E2-0E50-43C8-B27E-E72E908AA167%7D.png)

---

## `registerZoneIntVar`

```java
@NotNull
ZPZoneIntVar registerZoneIntVar(
    @NotNull String variableId,
    @NotNull Integer defaultValue,
    @NotNull Integer min,
    @NotNull Integer max
);
```

Registers a custom integer variable for ZPM3 zones.

The variable has:

* an identifier;
* a default value;
* a minimum value;
* a maximum value.

![{0B49AF53-32CE-4E7B-9609-A7A39F3D1024}.png](pictures/%7B0B49AF53-32CE-4E7B-9609-A7A39F3D1024%7D.png)

---

# Initialization

```java
void initialize(@NotNull IAddonInitContext context);
```

`initialize` is the main initialization stage for an addon.

It receives an `IAddonInitContext`.

![{B86A9E77-F4C2-47FE-9A19-78096FF47688}.png](pictures/%7BB86A9E77-F4C2-47FE-9A19-78096FF47688%7D.png)

This context provides several APIs for integrating an addon with ZPM3 systems.

---

## `registerZP3EventHandlerClass`

```java
void registerZP3EventHandlerClass(
    @NotNull Class<? extends ZP3EventHandlerClass> clazz
);
```

Registers a class containing handlers for **ZPM3 events**.

This allows an addon to subscribe to the ZPM3 event system.

The event system is covered in a separate section.

![{2D88B9DC-A417-415D-9956-86134CED5B5D}.png](pictures/%7B2D88B9DC-A417-415D-9956-86134CED5B5D%7D.png)
![{3212D77E-2706-4E48-BA80-39045B30F331}.png](pictures/%7B3212D77E-2706-4E48-BA80-39045B30F331%7D.png)

---

## `defineNetAccessorOnEntity`

```java
void defineNetAccessorOnEntity(
    @NotNull Class<? extends Entity> clazz,
    @NotNull ZPNetDataAccessor<?> dataAccessor
);
```

Registers a **network-synchronized data accessor** for a specific entity type.

This allows an addon to attach custom synchronized data to entities.

![{CD86EB14-19E4-4F17-9BD0-926E24AF13B0}.png](pictures/%7BCD86EB14-19E4-4F17-9BD0-926E24AF13B0%7D.png)
![{5F9F3EA8-A9C6-47D2-B70A-4E8D0E542760}.png](pictures/%7B5F9F3EA8-A9C6-47D2-B70A-4E8D0E542760%7D.png)

> **Advanced API:** This system is part of the internal ZPM3 networking architecture and is covered separately.

---

## Static Network Accessors

### Server

```java
<E> void defineStaticNetAccessor_ForServer(
    @NotNull ZPNetDataAccessor<E> accessor,
    @NotNull ZPNetDataVar<E> defaultValue
);
```

Registers a static network accessor whose value is managed on the server.

### Client

```java
<E> void defineStaticNetAccessor_ForClient(
    @NotNull ZPNetDataAccessor<E> accessor,
    @NotNull ZPNetDataVar<E> defaultValue
);
```

Registers a static network accessor whose value is managed on the client.

![{7F8DFD49-1CE7-471D-8BA9-D9080E7B84E2}.png](pictures/%7B7F8DFD49-1CE7-471D-8BA9-D9080E7B84E2%7D.png)

# Using Network Accessors

## Entity Network Accessors

After registering an accessor with `defineNetAccessorOnEntity`, the accessor can be used to read and modify synchronized data associated with a specific entity.

### Example

The following example exposes a synchronized `seasicknessLevel` value for a player:

```java
@Override
public int zpm3forge$getSeasicknessLevel() {
    final boolean server = !((Player) (Object) this).level().isClientSide();

    return ZombiePlague3.net(server)
            .getNetEntDataSyncer()
            .getVarOfDefault(
                    ((LivingEntity) (Object) this),
                    ZPNetPackModule.SEASICKNESS
            )
            .getValue();
}

@Override
public void zpm3forge$setSeasicknessLevel(int level) {
    final boolean server = !((Player) (Object) this).level().isClientSide();

    ZombiePlague3.net(server)
            .getNetEntDataSyncer()
            .setVar(
                    ((LivingEntity) (Object) this),
                    ZPNetPackModule.SEASICKNESS,
                    new ZPNetDataInt(Math.min(level, 512))
            );
}
```

The side is selected based on the entity's current level:

```java
final boolean server = !entity.level().isClientSide();
```

This ensures that the accessor uses the appropriate network handler on the current side.

`getVarOfDefault` returns the synchronized value or the accessor's default value when no value has been explicitly assigned to the entity.

`setVar` updates the synchronized value for the specified entity.

---

## Static Network Accessors

### Example

The following example reads a client-side static accessor:

```java
final boolean pickUpOnKey =
        ZombiePlague3.netClient()
                .getNetStaticDataSyncer()
                .getVar(ZPNetPackModule.StoC__SERVER_PICK_UP_ON_KEY)
                .orElse(new ZPNetDataBoolean(ZPClientConfig.PICK_UP_ON_KEY.getVar()))
                .getValue();
```

In this example, the accessor value is used when it is available. Otherwise, the local client configuration value is used as a fallback.

This pattern is useful when an addon needs to support both:

* a synchronized value supplied by the server;
* a local default value when the synchronized value is unavailable.

### General Pattern

```java
final T value =
        ZombiePlague3.netClient()
                .getNetStaticDataSyncer()
                .getVar(ACCESSOR)
                .orElse(DEFAULT_VALUE)
                .getValue();
```

For server-managed static accessors, the corresponding server-side network handler should be used.


> **Advanced API:** This system is part of the internal ZPM3 networking architecture and is covered separately.

---

## `runPopulationSetup`

```java
void runPopulationSetup(
    @NotNull ZPSetupPopulation setup
);
```

Allows an addon to modify the rules used for **mob spawning/population setup**.

![{4366B4BB-5C74-4F9D-9143-C41EA163A9D1}.png](pictures/%7B4366B4BB-5C74-4F9D-9143-C41EA163A9D1%7D.png)
![{69A40828-949E-454C-B19C-04201F878684}.png](pictures/%7B69A40828-949E-454C-B19C-04201F878684%7D.png)

This can be used when an addon needs to customize how entities are populated or spawned in the world.

---

# Post-Initialization

```java
void postInitialize(@NotNull IAddonPostInitContext context);
```

`postInitialize` is called after the main ZPM3 initialization process has completed.

It receives an `IAddonPostInitContext`.

The context is currently empty, but the lifecycle stage is reserved for operations that need to run after the main initialization phase.

---

# Client Shutdown

```java
void clientShutDown();
```

`clientShutDown` is called when the client process is shutting down.

This stage can be used to **release client-side resources** created by the addon during its lifetime.

---

# Useful ZPM3 Features

This section covers several ZPM3 features that are particularly useful when developing addons.

These systems are not required for a basic addon, but they provide convenient ways to integrate an addon with ZPM3.

# ZPM3 Event System

ZPM3 provides its own event system, designed with the **Forge event system** as a reference.

Events allow addons to execute custom code when specific actions occur inside ZPM3.

## Registering an Event Handler

Event handler classes are registered through `IAddonInitContext`:

```java
void registerZP3EventHandlerClass(
    @NotNull Class<? extends ZP3EventHandlerClass> clazz
);
```

The method receives a class containing the event handler methods.

For example:

```java
public class TestAddonZP3Events implements ZP3EventHandlerClass {

    @ZombiePlagueEvent
    public static void onSomething(ZPEventBus_Guns.GunShotEvent event) {
        System.out.println("SHOT!");
    }
}
```

The class is then registered during addon initialization:

```java
context.registerZP3EventHandlerClass(TestAddonZP3Events.class);
```

### Event Handler Requirements

Event handler methods must:

* be `public`;
* be `static`;
* have the `@ZombiePlagueEvent` annotation;
* accept the corresponding event object as their parameter.

When an event occurs inside the ZPM3 core, the event system invokes the registered addon callbacks.

This allows addons to execute their own code in response to events without modifying ZPM3's internal implementation.

---

## Cancellable Events

Some ZPM3 events implement:

```java
ZPEventDef.Cancellable
```

These events can be cancelled in the same general way as cancellable Forge events.

For example:

```java
@ZombiePlagueEvent
public static void onGunShot(ZPEventBus_Guns.GunShotEvent event) {
    event.setCancelled(true);
}
```

When a cancellable event is cancelled, the corresponding ZPM3 operation can be prevented from continuing.

> The exact behavior after cancellation depends on the event. In general, cancellation is intended to prevent the core operation associated with the event from being executed.

---

## Available Events

The event system is currently relatively small and will be expanded as ZPM3 development continues.

### `ZPEventBus_ClientInput`

Client input and GLFW-related events:

```text
CharEvent
WindowResizeEvent
MouseButtonEvent
MouseScrollEvent
KeyboardEvent
```

These events are generated from client-side GLFW callbacks.

---

### `ZPEventBus_ClientRendering`

Client rendering events:

```text
PreCalcMinecraftLightMapEvent
PostCalcMinecraftLightMapEvent

SceneRenderEvent

ItemRenderFirstPersonEvent
ItemRenderThirdPersonEvent

ItemSceneRenderFirstPersonEvent
ItemSceneRenderThirdPersonEvent
```

Some item rendering events are cancellable.

---

### `ZPEventBus_ClientResources`

Client resource events:

```text
ReloadGameResourcesEvent
```

This event is triggered when Minecraft's client resources are reloaded.

---

### `ZPEventBus_Blocks`

Block-related events:

```text
LootCaseRespawnEvent
FadingBlockExtinguishEvent
CandleExtinguishEvent
```

The listed events are cancellable.

---

### `ZPEventBus_Guns`

Gun-related events:

```text
ClientGunEmptyShotEvent
ClientGunShotEvent
GunShotEvent
GunInsertAmmoEvent
GunReloadStartEvent
GunExtractAmmoEvent
GunReloadEndEvent
```

Most gun events are cancellable, allowing addons to intercept and potentially prevent specific gun operations.

---

### `ZPEventBus_World`

World-related events:

```text
ZombieMiningShortMemAddEntryEvent
ZombieMiningLongMemAddEntryEvent
```

These events are cancellable and are related to ZPM3's shared zombie mining memory system.

---

## Event Context

ZPM3 events generally contain **context objects** describing the operation that caused the event.

For example, a gun event can provide information about the weapon, entity, or operation being performed.

The exact information available depends on the event.

The event classes themselves should be used as the primary reference when developing against a particular event.

---

# Network Accessors

ZPM3 provides a **Network Accessor** system for convenient registration and synchronization of custom variables.

The system has two main purposes:

1. provide a unique key for network-synchronized data;
2. automatically register and synchronize the corresponding variables.

This avoids manually implementing network packets for every individual variable.

---

## 1. Creating an Accessor

First, an accessor must be created.

An accessor represents the **key through which a network variable is accessed**.

For example:

```java
public static final ZPNetDataIntAccessor SEASICKNESS =
    new ZPNetDataIntAccessor(
        ResourceLocation.fromNamespaceAndPath(
            ZombiePlague3.MOD_ID(),
            "seasickness"
        )
    );
```

Accessor fields must be:

* `public`;
* `static`;
* fields of the class.

ZPM3 processes these accessors during initialization and assigns each accessor a unique internal ID.

### Accessor Types

There are different accessor types for different data types.

For example:

```java
ZPNetDataBooleanAccessor
ZPNetDataIntAccessor
ZPNetDataFloatAccessor
```

The accessor type determines the type of data stored and synchronized through it.

---

# Entity Network Accessors

After creating an accessor, it must be registered through `IAddonInitContext`:

```java
context.defineNetAccessorOnEntity(
    Player.class,
    SEASICKNESS
);
```

This associates the accessor with the specified entity class.

As a result, every instance of the specified entity type has its own value for that accessor.

For example, registering it for `Player` means that every player has their own `SEASICKNESS` value.

The variable is **server-managed**. When its value changes on the server, ZPM3 synchronizes the updated value to clients that are tracking the corresponding entity.

This is useful for storing custom per-entity state that must remain synchronized between the server and clients.

---

# Static Network Accessors

Network accessors can also represent **global/static data** instead of data belonging to individual entities.

## Server-Managed Static Data

```java
context.defineStaticNetAccessor_ForServer(
    ZPNetPackModule.StoC__DARKNESS_ENABLED,
    new ZPNetDataBoolean(
        ZPWorldConfig.ENABLE_HARDCORE_DARKNESS_SERVER_SIDE.getVar()
    )
);
```

The server owns the value.

Clients receive the value when they connect and receive updates whenever the server-side value changes.

This is useful for synchronizing global server settings, such as gameplay rules or world configuration.

Conceptually:

```text
Server
  │
  │ value/update
  ▼
Clients
```

---

## Client-Managed Static Data

There is also a client-managed variant:

```java
context.defineStaticNetAccessor_ForClient(
    ZPNetPackModule.CtoS__PICK_UP_ON_KEY,
    new ZPNetDataBoolean(
        ZPWorldConfig.ALLOW_ITEMS_PICKING_ON_KEY.getVar()
    )
);
```

In this case, the client owns the value and the server stores the corresponding value for that client.

This can be useful for client-specific settings that the server needs to know about.

For example:

```text
Client A ── setting ──► Server
Client B ── setting ──► Server
Client C ── setting ──► Server
```

---

# Addon Configuration

`IZPAddonEntry` provides a convenient way to register addon-specific configuration files.

```java
default void registerConfig(
    @NotNull String confName,
    @NotNull Class<ZPConfigConstantsClass> clazz
)
```

The method creates a JSON configuration file for the addon inside:

```text
zpm3_files/
```

A configuration class implements `ZPConfigConstantsClass` and defines its settings using ZPM3 configuration types.

For example:

```java
public class ZPCombatConfig implements ZPConfigConstantsClass {

    @ZPVarDefinition(
        description = "Spec types of armor can break when used."
    )
    public static final ZPConfig_BOOL SPEC_ARMOR_CAN_BREAK_PER_TICK =
        new ZPConfig_BOOL(true);

    @ZPVarDefinition(
        description = "Default hand reach distance."
    )
    public static final ZPConfig_FLOAT PLAYER_DEFAULT_HAND_REACH_DISTANCE =
        new ZPConfig_FLOAT(2.375f);
}
```

The addon can then register the configuration:

```java
registerConfig("combat", ZPCombatConfig.class);
```

It is recommended to register addon configurations **as early as possible**, preferably during `PreInit`.

---

# Utility Classes

ZPM3 contains a large number of utility classes that can be useful when developing addons.

Many common operations are already implemented by ZPM3, so it is worth checking the existing utilities before implementing equivalent functionality yourself.

Useful utilities can be found throughout the `engine` and `modules` packages.

---

## Player Statistics

For player-specific ZPM3 data, utilities such as `ZPPlayerStat` provide access to internal player statistics.

For example, ZPM3 can expose values such as:

```text
SEASICKNESS
```

These utilities provide a higher-level API for accessing ZPM3-specific player state instead of directly interacting with the underlying mixin extensions.

---

## Mob Effect Utilities

`ZPEffectUtils` provides convenient helpers for working with ZPM3's custom mob effects.

It is intended to simplify common checks involving ZPM3 effects and statuses.

For example, addons can use the utility layer to determine whether an entity is affected by particular ZPM3 effects without directly accessing the effect registry or implementing the same checks themselves.

---

## Entity Utilities

`ZPEntityUtil` contains general-purpose helpers for working with Minecraft entities and ZPM3-specific entity mechanics.

It covers various common entity-related checks and operations, including interaction with ZPM3-specific systems and tags.

---

## Entity and Living Entity Statistics

ZPM3 exposes some internal entity state through utility abstractions such as:

```text
ZPEntityStat
ZPLivingStat
```

These provide access to ZPM3-specific state attached to entities.

For example, living entities can expose values such as:

```text
RADIATION
INTOXICATION
```

while entities can have their own ZPM3-specific statistics.

These abstractions are preferable to directly interacting with the underlying mixin extension interfaces.

---

# Zone Checks

ZPM3 provides `ZPZoneChecks` for checking whether an entity is currently affected by particular zone properties.

For example, the API can be used to determine whether a player or entity is currently inside a zone with a specific flag.

The same mechanism can also be used with **custom zone flags registered by addons**.

This allows an addon to define its own zone behavior without implementing the zone lookup logic itself.

---

# World and Block Utilities

ZPM3 also contains utilities for working with world and block-related mechanics.

For example, `ZPGlobalBlocksDestroyMemory` provides functionality related to ZPM3's shared zombie block-destruction/mining memory system, as well as some related block visual effects.

---

# Client Rendering Utilities

Client-side utility classes are available for common rendering and visual effects.

`ZPCommonClientUtils` contains helpers for spawning ZPM3-specific visual particles and effects.

`ZPRenderingUtil` provides utilities related to Minecraft's rendering pipeline, including calculations and helpers used by ZPM3's custom first-person and item rendering systems.

These utilities can be useful when implementing custom client-side visual effects or integrating an addon with ZPM3's rendering behavior.

---

## Exploring the Existing API

ZPM3 contains a significant number of utility classes and systems, and not all of them are covered by this documentation.

When looking for functionality, it is recommended to search the ZPM3 source tree for:

```text
engine/
modules/
```

In particular, check the `util`, `utils`, `init`, and API-related classes inside the relevant module.

Often the functionality required by an addon already exists somewhere in ZPM3.

> **Recommendation:** Before implementing a new utility or reimplementing an existing ZPM3 mechanic, search the API and source code first. This helps keep addons smaller and ensures that they use the same abstractions as the core mod.

## **Conclusion**

> ⚠️ **IMPORTANT — READ THIS SECTION BEFORE DEVELOPING YOUR ADDON**
>
> This documentation provides only a **brief overview of the ZPM3 API** and the main mechanisms available to addon developers. It does **not** replace the standard Forge documentation.
>
> In general, a ZPM3 addon should be developed like a **regular Forge mod**. Items, blocks, entities, weapons, creative tabs, sounds, resources, and other content should be registered using the standard Forge/Minecraft APIs.
>
> ZPM3 provides additional APIs for integrating your addon with its internal systems: lifecycle contexts, events, network synchronization, zones, configuration, client hooks, and other utilities.

### Use Tags

One of the systems that is becoming increasingly important in ZPM3 is the **Minecraft Tag system**.

Tags allow ZPM3 to group blocks, items, fluids, and other registry objects under a common semantic category. Instead of checking for a specific block or item, ZPM3 systems can check whether an object belongs to a particular tag.

For example, ZPM3 provides tags for things such as:

* blocks that can be mined with specific tools;
* blocks ignored by zombies;
* blocks ignored by bullets;
* fluids with specific properties;
* items providing radiation protection;
* items used as oxygen sources;
* armor with specific properties.

This allows addons to integrate with existing ZPM3 mechanics **without directly depending on specific ZPM3 objects**.

ZPM3 is actively expanding its tag system starting with version **0.2**, so when implementing mechanics that interact with existing ZPM3 systems, **check the available tags first (/zpm3/data/tags/)**.

---

### Loot Tables

ZPM3 also uses JSON-based loot tables that can be loaded and reloaded as Minecraft/Forge resources.

The built-in loot table definitions can be found under:

```text
data/zpm3/zp_loot_tables/
```

Addon developers can use the same resource-based approach to provide their own loot data.

ZPM3 also supports **loot table extensions**. An addon can provide a JSON resource under:

```text
zp_loot_tables_extensions/

...
{
  "extendByList": [
    {
      "extendByResourceLoc": "zpm3:sampleExtension",
      "newTableRollRules": {
        "chanceToStartRoll": 0.5,
        "maxRolls": 2,
        "minRolls": 1,
        "randomization": {
          "type": "UNIFORM",
          "parameter": 1.0
        }
      }
    }
  ],
  "lootTableId": "zpm3:sample"
}
```

to extend an existing ZPM3 loot table with additional entries.

This makes it possible to add content to existing ZPM3 loot tables without modifying the original table directly.

**Loot cases** are a separate system. Their data is stored independently and is intended for local/static use rather than the reloadable resource-based workflow used by loot tables.

---

## ⚠️ Do Not Use `ZPCommonRegistry`

While exploring the ZPM3 source code, you may notice that the core mod uses a proprietary registration system called **`ZPCommonRegistry`**.

**Do not use it in addons.**

`ZPCommonRegistry` and its related APIs are intended **strictly for internal ZPM3 implementation**. They are not part of the public addon API and are not guaranteed to work from external mods.

For addon development, use the standard Forge registration mechanisms:

* `DeferredRegister`
* `RegistryObject`
* Forge/Minecraft registries
* standard Forge event systems
* standard Minecraft data/resource systems

The ZPM3 source code contains many examples of how its internal systems work, but **internal implementation does not automatically mean public API**.

---

## Final Notes

The most important rule is:

> **Use ZPM3 APIs for integration with ZPM3. Use Forge APIs for developing your mod itself.**

For example:

* Register an item → **Forge `DeferredRegister`**
* Register a block → **Forge `DeferredRegister`**
* Register an entity → **Forge entity registration**
* Add a ZPM3-specific event handler → **ZPM3 Event API**
* Synchronize custom entity data → **ZPM3 Net Accessors**
* Register a ZPM3 zone flag → **ZPM3 lifecycle context**
* Add custom loot → **Minecraft/Forge resource system OR ZP3 Loot tables for loot-cases**
* React to ZPM3 gameplay events → **ZPM3 Event API**

The ZPM3 source tree contains many practical examples of standard Forge development as well as ZPM3-specific integration. It is therefore worth studying the corresponding modules when implementing more advanced addon features.

---

## Next Step

The next step is to put everything together and create a **complete working ZPM3 addon**.

See:

* [Addon Example](addon-example.md)
