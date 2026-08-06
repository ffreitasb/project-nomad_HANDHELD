# NOMAD:HANDHELD

> Hub mobile de curadoria e onboarding para ferramentas offline-first (survival / off-grid)

![status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![platform](https://img.shields.io/badge/platform-Android-3DDC84)
![license](https://img.shields.io/badge/license-TBD-lightgrey)
![weekend project](https://img.shields.io/badge/tipo-weekend%20project-blueviolet)
![made in brazil](https://img.shields.io/badge/feito%20no-🇧🇷%20Brasil-009c3b)

**[🇺🇸 Read in English](./README.md)**

---

## 🚧 Status: em desenvolvimento

Este é um **projeto de fim de semana**, feito nas horas vagas e sem cronograma fixo. Features, escopo e até nome podem mudar sem aviso. Não use em produção nem confie nele como seu único plano de sobrevivência digital (ainda).

---

## O que é

**NOMAD:HANDHELD** é um dashboard Android que centraliza a curadoria, instalação e configuração de um "kit de sobrevivência digital offline" no seu próprio celular — Wikipedia offline, mapas sem sinal, IA local, biblioteca de guias, tudo funcionando sem depender de internet.

Não é um app que reimplementa essas ferramentas. É um **hub de descoberta e onboarding**: te diz o que instalar, como configurar, onde ficam os dados e o que realmente funciona 100% offline — e rastreia seu progresso no aparelho.

### Inspiração

O projeto é inspirado no [Project N.O.M.A.D.](https://github.com/Crosstalk-Solutions/project-nomad) (Crosstalk Solutions), um servidor offline-first para desktop/homelab que orquestra Kiwix, Ollama, Kolibri e mapas via Docker.

**NOMAD:HANDHELD não é um fork.** Nenhum código do projeto original é reaproveitado — a arquitetura de containers do NOMAD não se aplica ao Android (sem Docker nativo, sem necessidade de orquestração, já que as ferramentas equivalentes já existem como apps nativos soltos). O que se aproveita é a **filosofia de produto**: categorização clara, cards por ferramenta, documentação de onboarding embutida. O nome carrega essa relação de propósito — a "edição de bolso" do universo NOMAD.

O repositório original é licenciado sob Apache 2.0.

---

## Por que existe

Ferramentas offline-first pra Android já existem soltas (Kiwix, OsmAnd, MLC-Chat, PocketPal), mas ninguém centraliza a experiência: descobrir o que baixar, entender o que cada app faz, saber o que funciona sem sinal e acompanhar se seu "kit" está completo. É esse gap que o projeto tenta preencher — com o mínimo de desenvolvimento necessário e o máximo de curadoria bem feita.

---

## Funcionalidades planejadas (MVP)

- [ ] Dashboard com categorias: Biblioteca de Informação, IA Local, Mapas Offline, Educação, Ferramentas de Dados
- [ ] Cards por app com status (não instalado / instalado / configurado), deep links e guia de onboarding offline
- [ ] Detecção automática de apps já instalados no aparelho
- [ ] Checklist de progresso persistido localmente (sem backend, sem conta)
- [ ] Modo "Ficha de Campo" — resumo otimizado para leitura rápida em baixa luz / cenário de estresse
- [ ] Zero dependência de rede após instalado

Detalhes completos de escopo e decisões técnicas estão no [PRD](./PRD-nomad-mobile-hub.md).

---

## Stack

- Kotlin + Jetpack Compose (Material 3)
- Persistência local via DataStore
- Curadoria de apps como dado (JSON estático embutido), não como código

---

## Contribuindo

**Contribuições são muito bem-vindas.** Isso vale especialmente para:

- 📋 **Curadoria** — sugestões de apps offline-first que deveriam entrar no catálogo (o formato de entrada é só um JSON, ver `PRD` seção 6)
- 🌍 **Cobertura fora do Brasil** — recomendações de conteúdo/mapas/apps relevantes para outras regiões
- 🐛 **Bugs e UX** — o projeto é feito em pouquíssimas horas, então feedback de uso real é ouro
- 🧩 **Código** — PRs são bem-vindos, mas dado que é projeto de fim de semana, revisão pode demorar

Se quiser contribuir, abra uma issue antes de um PR grande para alinhar escopo — o objetivo é manter o projeto enxuto (curadoria > desenvolvimento).

---

## Não-objetivos

Pra deixar claro o que este projeto **não** tenta ser:

- Não reimplementa Wikipedia offline, mapas ou LLMs locais — usa os apps que já existem
- Não tem backend, conta de usuário ou sincronização em nuvem
- Não é fork nem substituto do Project NOMAD (desktop) — é um complemento para quem não tem homelab/hardware dedicado

---

## Licença

A definir.

---

## Créditos

Filosofia de produto e estrutura de categorias inspiradas no [Project N.O.M.A.D.](https://github.com/Crosstalk-Solutions/project-nomad), de Chris Sherwood / Crosstalk Solutions.
