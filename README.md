# NOMAD:HANDHELD

> A mobile curation and onboarding hub for offline-first tools (survival / off-grid)

![status](https://img.shields.io/badge/status-in%20development-yellow)
![platform](https://img.shields.io/badge/platform-Android-3DDC84)
![license](https://img.shields.io/badge/license-TBD-lightgrey)
![weekend project](https://img.shields.io/badge/type-weekend%20project-blueviolet)
![made in brazil](https://img.shields.io/badge/made%20in-🇧🇷%20Brazil-009c3b)

**[🇧🇷 Leia em Português](./README_ptbr.md)**

---

## 🚧 Status: in development

This is a **weekend project**, built in spare time with no fixed schedule. Features, scope, and even the name may change without notice. Don't use it in production, and definitely don't rely on it as your only digital survival plan (yet).

---

## What it is

**NOMAD:HANDHELD** is an Android dashboard that centralizes the curation, installation, and configuration of a "digital survival kit" on your own phone — offline Wikipedia, maps without signal, local AI, a library of guides, all working without an internet connection.

It's not an app that reimplements these tools. It's a **discovery and onboarding hub**: it tells you what to install, how to configure it, where the data lives, and what actually works fully offline — while tracking your progress on-device.

### Inspiration

The project is inspired by [Project N.O.M.A.D.](https://github.com/Crosstalk-Solutions/project-nomad) (Crosstalk Solutions), a desktop/homelab offline-first server that orchestrates Kiwix, Ollama, Kolibri, and maps through Docker.

**NOMAD:HANDHELD is not a fork.** No code from the original project is reused — NOMAD's container orchestration architecture doesn't apply to Android (no native Docker, no need for orchestration, since the equivalent tools already exist as standalone native apps). What carries over is the **product philosophy**: clear categorization, per-tool cards, embedded onboarding documentation. The name reflects that relationship — the "pocket edition" of the NOMAD universe.

The original repository is licensed under Apache 2.0.

---

## Why it exists

Offline-first tools for Android already exist as standalone apps (Kiwix, OsmAnd, MLC-Chat, PocketPal), but nothing centralizes the experience: discovering what to download, understanding what each app does, knowing what works without signal, and tracking whether your "kit" is complete. That's the gap this project tries to fill — with the minimum development necessary and the maximum curation quality possible.

---

## Planned features (MVP)

- [ ] Dashboard with categories: Information Library, Local AI, Offline Maps, Education, Data Tools
- [ ] Per-app cards with status (not installed / installed / configured), deep links, and offline onboarding guide
- [ ] Automatic detection of apps already installed on the device
- [ ] Locally persisted progress checklist (no backend, no account)
- [ ] "Field Sheet" mode — a summary view optimized for quick reading in low light / high-stress scenarios
- [ ] Zero network dependency once installed

Full scope details and technical decisions live in the [PRD](./PRD-nomad-mobile-hub.md).

---

## Stack

- Kotlin + Jetpack Compose (Material 3)
- Local persistence via DataStore
- App curation as data (embedded static JSON), not code

---

## Contributing

**Contributions are very welcome.** This is especially true for:

- 📋 **Curation** — suggestions of offline-first apps that should be in the catalog (the entry format is just JSON, see `PRD` section 6)
- 🌍 **Coverage beyond Brazil** — recommendations for content/maps/apps relevant to other regions
- 🐛 **Bugs and UX** — this project is built in very few hours, so real-world usage feedback is gold
- 🧩 **Code** — PRs are welcome, but since this is a weekend project, review may take a while

If you want to contribute, please open an issue before a large PR to align on scope — the goal is to keep the project lean (curation over development).

---

## Non-goals

To be clear about what this project is **not** trying to be:

- It doesn't reimplement offline Wikipedia, maps, or local LLMs — it uses the apps that already exist
- It has no backend, user accounts, or cloud sync
- It's not a fork or a replacement for Project NOMAD (desktop) — it's a companion for people without a homelab/dedicated hardware

---

## License

TBD.

---

## Credits

Product philosophy and category structure inspired by [Project N.O.M.A.D.](https://github.com/Crosstalk-Solutions/project-nomad), by Chris Sherwood / Crosstalk Solutions.
