# Getting Started

> **This guide is intended for beginners.**
> No prior experience with Minecraft modding is required. Basic knowledge of Java is **HIGHLY recommended**, but the steps below cover the initial project setup from scratch.

This guide explains how to create a development environment for a **ZombiePlague3 addon for Minecraft Forge 1.20.1**.

You will create a standard Forge mod project, configure IntelliJ IDEA, and launch Minecraft with your addon.

---

## 1. Install IntelliJ IDEA

Download and install **IntelliJ IDEA** from the official JetBrains website:

[https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)

After installation, launch IntelliJ IDEA.

---

## 2. Open IntelliJ IDEA Settings

Open IntelliJ IDEA and open the application settings.

You can usually access them through:

**File → Settings**

or by pressing:

```text
Ctrl + Alt + S
```

![{88BB4189-745C-41CD-BBEB-D08C06BF89F2}.png](pictures/%7B88BB4189-745C-41CD-BBEB-D08C06BF89F2%7D.png)

---

## 3. Install Minecraft Development

In the Settings window, open:

**Plugins**

Then search for:

```text
Minecraft Development
```

Install the **Minecraft Development** plugin.

![{A78A11C8-58B1-4BCE-A1A0-2CC21F365EC6}.png](pictures/%7BA78A11C8-58B1-4BCE-A1A0-2CC21F365EC6%7D.png)

After installation, IntelliJ IDEA may ask you to restart the IDE. Restart it if requested.

This plugin provides tools for creating and working with Minecraft mod projects directly from IntelliJ IDEA.

---

## 4. Create a New Project

Create a new project using:

**File → New → Project**

or the **New Project** button on the IntelliJ IDEA start screen.

![{0C7C0662-EB08-4DAA-9D9E-882575B90A0B}.png](pictures/%7B0C7C0662-EB08-4DAA-9D9E-882575B90A0B%7D.png)

---

## 5. Configure the Minecraft Project

In the project creation window, select:

**Generators → Minecraft**

Then configure the project according to the screenshot.

![{7FA70611-CBCF-4C21-9739-B419E941F5CD}.png](pictures/%7B7FA70611-CBCF-4C21-9739-B419E941F5CD%7D.png)

For your addon, choose your own values for the project and mod information, such as:

* **Mod Name** — the name of your addon
* **Mod ID** — unique identifier of your addon
* **Package** — Java package of your project
* **Minecraft Version** — `1.20.1`
* **Forge Version** — the Forge version you are targeting

For example:

```text
Mod Name: My ZPM3 Addon
Mod ID: my_zpm3_addon
Package: com.example.myaddon
Minecraft: 1.20.1
```

> **Important:** The `Mod ID` must be unique. It is used by Forge to identify your mod and is also used by ZombiePlague3 when declaring addon dependencies.

After configuring the project, create it.

---

## 6. Generate IntelliJ Run Configurations

The Minecraft Development plugin will generate the basic project structure and required Gradle files.

Wait until IntelliJ IDEA finishes importing the Gradle project.

Then open the **Gradle** tool window on the right side of IntelliJ IDEA.

Find:

```text
Tasks
└── forgegradle runs
    └── genIntellijRuns
```

Run:

```text
genIntellijRuns
```

![{76BFE745-E423-4708-A80D-851D9ECED58A}.png](pictures/%7B76BFE745-E423-4708-A80D-851D9ECED58A%7D.png)

This generates the run configurations required to launch Minecraft from IntelliJ IDEA.

After Gradle finishes, you should have configurations such as:

```text
runClient
runServer
```

depending on your project configuration.

---

## 7. Launch Minecraft

At the top-right of IntelliJ IDEA, select:

```text
runClient
```

Then launch it using the green **Run ▶** button.

You can also use the **Debug 🐞** button if you want to run Minecraft under the debugger.

![{B520E894-F7B1-4D14-A328-D66C4F29DBE8}.png](pictures/%7BB520E894-F7B1-4D14-A328-D66C4F29DBE8%7D.png)

Minecraft should launch with your newly created mod.

If Minecraft starts successfully and your addon appears in the loaded mods, **your development environment is ready**.

You can now proceed with implementing your ZombiePlague3 addon.

---

## Next step

After the basic Forge project is working, the next step is to convert the ordinary Forge mod into a **ZombiePlague3 addon** and connect it to the ZPM3 API.

See:
* [Addon Lifecycle](addon-lifecycle.md)
