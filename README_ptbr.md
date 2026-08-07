# NOMAD:HANDHELD

> Hub mobile de curadoria e onboarding para ferramentas offline-first (survival / off-grid)

![status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![platform](https://img.shields.io/badge/platform-Android-3DDC84)
![license](https://img.shields.io/badge/license-Apache%202.0-blue)
![weekend project](https://img.shields.io/badge/tipo-weekend%20project-blueviolet)
![made in brazil](https://img.shields.io/badge/feito%20no-🇧🇷%20Brasil-009c3b)

**[🇺🇸 Read in English](./README.md)**

---

## 🚧 Status: em desenvolvimento — fases 0, 1, 2, 3, 4 e 5 concluídas

Este é um **projeto de fim de semana**, feito nas horas vagas. A estrutura do projeto, a camada de dados, a lógica de negócio, a UI da Home, a UI de Detalhe de App e a UI do Ficha de Campo estão completas.

| Fase | Descrição | Status |
|---|---|---|
| 0 | Scaffold do projeto (Gradle, Compose, tema) | ✅ Concluída |
| 1 | Modelo de dados + curadoria (10 apps, guias de onboarding) | ✅ Concluída |
| 2 | Persistência de progresso (DataStore + detecção via PackageManager) | ✅ Concluída |
| 3 | Home / Dashboard UI | ✅ Concluída |
| 4 | Detalhe do card + renderização do onboarding | ✅ Concluída |
| 5 | Modo Ficha de Campo | ✅ Concluída |
| 6 | Configurações + polish + APK | 🔄 Próxima |

---

## O que é

**NOMAD:HANDHELD** é um dashboard Android que centraliza a curadoria, instalação e configuração de um "kit de sobrevivência digital offline" no seu próprio celular — Wikipedia offline, mapas sem sinal, IA local, biblioteca de guias, tudo funcionando sem depender de internet.

Não é um app que reimplementa essas ferramentas. É um **hub de descoberta e onboarding**: te diz o que instalar, como configurar, onde ficam os dados e o que realmente funciona 100% offline — e rastreia seu progresso no aparelho.

### Inspiração

O projeto é inspirado no [Project N.O.M.A.D.](https://github.com/Crosstalk-Solutions/project-nomad) (Crosstalk Solutions), um servidor offline-first para desktop/homelab que orquestra Kiwix, Ollama, Kolibri e mapas via Docker.

**NOMAD:HANDHELD não é um fork.** Nenhum código do projeto original é reaproveitado. O que se aproveita é a **filosofia de produto**: categorização clara, cards por ferramenta, documentação de onboarding embutida. O nome carrega essa relação — a "edição de bolso" do universo NOMAD.

---

## Por que existe

Ferramentas offline-first pra Android já existem soltas (Kiwix, OsmAnd, MLC-Chat, PocketPal), mas ninguém centraliza a experiência: descobrir o que baixar, entender o que cada app faz, saber o que funciona sem sinal e acompanhar se seu "kit" está completo. É esse gap que o projeto preenche.

---

## Catálogo curado (v1)

10 apps em 5 categorias, com package names verificados e guias de onboarding offline embutidos:

| App | Categoria | Prioridade | Fonte |
|---|---|---|---|
| Kiwix | Biblioteca de Informação | 🔴 Crítico | F-Droid |
| MLC Chat | IA Local | 🔴 Crítico | Play Store |
| PocketPal AI | IA Local | 🟡 Recomendado | Play Store |
| OsmAnd~ | Mapas Offline | 🔴 Crítico | F-Droid (\*) |
| Organic Maps | Mapas Offline | 🟡 Recomendado | F-Droid |
| Khan Academy | Educação | 🟡 Recomendado | Play Store |
| Moon+ Reader | Educação | ⚪ Opcional | Play Store |
| Unit Converter Ultimate | Ferramentas de Dados | 🟡 Recomendado | Play Store |
| KeePassDX | Ferramentas de Dados | 🟡 Recomendado | F-Droid |
| Termux | Ferramentas de Dados | 🔴 Crítico | F-Droid (\*\*) |

(\*) Versão F-Droid = downloads de mapas ilimitados. Versão Play Store limita a 7.  
(\*\*) Termux na Play Store está descontinuado. F-Droid é a única versão mantida.

---

## Funcionalidades planejadas (MVP)

- [x] Scaffold do projeto: Kotlin + Jetpack Compose + Material 3, tema dark
- [x] Catálogo de apps como JSON estático (`assets/curated_apps.json`) — sem lógica hardcoded
- [x] Guias de onboarding offline embutidos (Markdown, PT-BR) para os 10 apps
- [x] Detecção automática de apps instalados via `PackageManager`
- [x] Persistência de progresso via DataStore (sem backend, sem conta)
- [x] Cálculo de progresso: % geral + contagem de apps críticos prontos
- [x] Dashboard com categorias, barra de progresso e cards de apps
- [x] Detalhe do card com guia de onboarding e toggle de status
- [x] Modo "Ficha de Campo" — resumo compacto para apps críticos
- [ ] Configurações: reset de progresso, sobre/créditos, versão do app
- [ ] Zero dependência de rede após instalado

Detalhes completos de escopo e decisões técnicas estão no [PRD](./PRD-nomad-mobile-hub.md).

---

## Stack

- **Kotlin** + **Jetpack Compose** (Material 3, tema dark exclusivo)
- **DataStore Preferences** — persistência local de progresso
- **kotlinx.serialization** — parsing do JSON do catálogo
- **PackageManager** — detecção de apps instalados
- Curadoria de apps como **dado** (JSON estático), não código

---

## Contribuindo

**Contribuições são muito bem-vindas.** Especialmente:

- 📋 **Curadoria** — sugestões de apps offline-first (formato de entrada no PRD seção 6)
- 🌍 **Cobertura regional** — apps/conteúdo relevantes fora do Brasil
- 🐛 **Bugs e UX** — feedback de uso real é ouro
- 🧩 **Código** — PRs bem-vindos; abra uma issue antes de PRs grandes

---

## Não-objetivos

- Não reimplementa Wikipedia offline, mapas ou LLMs locais — usa os apps que já existem
- Não tem backend, conta de usuário ou sincronização em nuvem
- Não é fork nem substituto do Project NOMAD (desktop)
- Sem iOS na v1

---

## Licença

NOMAD:HANDHELD is licensed under the [Apache License 2.0](LICENSE).

---

## Créditos

Filosofia de produto e estrutura de categorias inspiradas no [Project N.O.M.A.D.](https://github.com/Crosstalk-Solutions/project-nomad), de Chris Sherwood / Crosstalk Solutions.
