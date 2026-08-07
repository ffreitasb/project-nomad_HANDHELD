# NOMAD:HANDHELD

> A mobile curation and onboarding hub for offline-first tools (survival / off-grid)

![status](https://img.shields.io/badge/status-in%20development-yellow)
![platform](https://img.shields.io/badge/platform-Android-3DDC84)
![license](https://img.shields.io/badge/license-Apache%202.0-blue)
![weekend project](https://img.shields.io/badge/type-weekend%20project-blueviolet)
![made in brazil](https://img.shields.io/badge/made%20in-🇧🇷%20Brazil-009c3b)

**[🇧🇷 Leia em Português](./README_ptbr.md)**

---

## 🚧 Status: in development — phases 0, 1, 2, 3, 4 complete

This is a **weekend project**, built in spare time with no fixed schedule. The project structure, data layer, business logic, Home UI, and Card Detail UI are complete.

| Phase | Description | Status |
|---|---|---|
| 0 | Project scaffold (Gradle, Compose, theme) | ✅ Done |
| 1 | Data model + curation (10 apps, onboarding guides) | ✅ Done |
| 2 | Progress persistence (DataStore + PackageManager detection) | ✅ Done |
| 3 | Home / Dashboard UI | ✅ Done |
| 4 | Card detail + onboarding rendering | ✅ Done |
| 5 | Field Sheet mode | 🔄 Next |
| 6 | Settings + polish + APK | ⏳ Pending |

---

## What it is

**NOMAD:HANDHELD** is an Android dashboard that centralizes the curation, installation, and configuration of a "digital survival kit" on your own phone — offline Wikipedia, maps without signal, local AI, a library of guides, all working without an internet connection.

It's not an app that reimplements these tools. It's a **discovery and onboarding hub**: it tells you what to install, how to configure it, where the data lives, and what actually works fully offline — while tracking your progress on-device.

### Inspiration

The project is inspired by [Project N.O.M.A.D.](https://github.com/Crosstalk-Solutions/project-nomad) (Crosstalk Solutions), a desktop/homelab offline-first server that orchestrates Kiwix, Ollama, Kolibri, and maps through Docker.

**NOMAD:HANDHELD is not a fork.** No code from the original project is reused — NOMAD's container orchestration architecture doesn't apply to Android. What carries over is the **product philosophy**: clear categorization, per-tool cards, embedded onboarding documentation. The name reflects that relationship — the "pocket edition" of the NOMAD universe.

---

## Why it exists

Offline-first tools for Android already exist as standalone apps (Kiwix, OsmAnd, MLC-Chat, PocketPal), but nothing centralizes the experience: discovering what to download, understanding what each app does, knowing what works without signal, and tracking whether your "kit" is complete. That's the gap this project fills.

---

## Curated catalog (v1)

10 apps across 5 categories, with verified package names and bundled offline onboarding guides:

| App | Category | Priority | Source |
|---|---|---|---|
| Kiwix | Information Library | 🔴 Critical | F-Droid |
| MLC Chat | Local AI | 🔴 Critical | Play Store |
| PocketPal AI | Local AI | 🟡 Recommended | Play Store |
| OsmAnd~ | Offline Maps | 🔴 Critical | F-Droid (\*) |
| Organic Maps | Offline Maps | 🟡 Recommended | F-Droid |
| Khan Academy | Education | 🟡 Recommended | Play Store |
| Moon+ Reader | Education | ⚪ Optional | Play Store |
| Unit Converter Ultimate | Data Tools | 🟡 Recommended | Play Store |
| KeePassDX | Data Tools | 🟡 Recommended | F-Droid |
| Termux | Data Tools | 🔴 Critical | F-Droid (\*\*) |

(\*) F-Droid version = unlimited map downloads. Play Store version limits to 7.  
(\*\*) Termux on Play Store is discontinued. F-Droid is the only maintained version.

---

## Planned features (MVP)

- [x] Project scaffold: Kotlin + Jetpack Compose + Material 3, dark theme
- [x] App catalog as static JSON (`assets/curated_apps.json`) — schema-driven, no hardcoded logic
- [x] Bundled offline onboarding guides (Markdown, PT-BR) for all 10 apps
- [x] Automatic detection of installed apps via `PackageManager`
- [x] Progress persistence via DataStore (no backend, no account)
- [x] Progress calculation: overall % + critical apps readiness
- [x] Dashboard with categories, progress bar, and app cards
- [x] Per-app card detail with onboarding guide and status toggle
- [ ] "Field Sheet" mode — compact emergency view for critical apps
- [ ] Settings: reset progress, about/credits, app version
- [ ] Zero network dependency once installed

Full scope details and technical decisions in the [PRD](./PRD-nomad-mobile-hub.md).

---

## Stack

- **Kotlin** + **Jetpack Compose** (Material 3, dark-only theme)
- **DataStore Preferences** — local progress persistence
- **kotlinx.serialization** — catalog JSON parsing
- **PackageManager** — installed app detection
- App curation as **data** (static JSON), not code

---

## Contributing

**Contributions are very welcome.** Especially:

- 📋 **Curation** — offline-first app suggestions (JSON entry format in PRD section 6)
- 🌍 **Regional coverage** — apps/content relevant beyond Brazil
- 🐛 **Bugs and UX** — real-world feedback is gold
- 🧩 **Code** — PRs welcome; open an issue first for large changes

---

## Non-goals

- Doesn't reimplement Wikipedia, maps, or local LLMs — uses existing apps
- No backend, user accounts, or cloud sync
- Not a fork or replacement for Project NOMAD (desktop)
- No iOS in v1

---

## License

NOMAD:HANDHELD is licensed under the [Apache License 2.0](LICENSE).

---

## Credits

Product philosophy and category structure inspired by [Project N.O.M.A.D.](https://github.com/Crosstalk-Solutions/project-nomad), by Chris Sherwood / Crosstalk Solutions.
