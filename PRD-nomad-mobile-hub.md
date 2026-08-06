# PRD — NOMAD:HANDHELD
### Hub mobile de curadoria e onboarding para ferramentas offline-first (survival/off-grid)

**Status:** Draft v1 — nome definido
**Autor:** Felipe
**Tipo de projeto:** Weekend project (MVP), com potencial de expansão
**Nome do projeto:** NOMAD:HANDHELD — sufixo categórico que posiciona o projeto como a "edição de bolso" do universo NOMAD: mesmo espírito de produto, formato mobile.
**Inspiração:** [Project N.O.M.A.D.](https://github.com/Crosstalk-Solutions/project-nomad) (Crosstalk Solutions) — **não é fork**. Nenhum código é reaproveitado; o repositório upstream é Apache 2.0 e serve apenas como referência conceitual de IA de produto (categorização, padrão de "cards" por ferramenta, filosofia de curadoria).

---

## 1. Contexto e motivação

O Project NOMAD resolve "internet cai, e agora?" no desktop/homelab: um Command Center Docker que orquestra Kiwix, Ollama, Kolibri, mapas OSM etc. em hardware dedicado (Zimaboard, mini PC, homelab).

Esse modelo **não se transporta para Android**: não há Docker nativo sem root, e a arquitetura de containers que dá coesão ao NOMAD no desktop é irrelevante no mobile — porque as ferramentas equivalentes (Kiwix, OsmAnd, LLMs locais via MLC/PocketPal) **já existem como apps nativos soltos**. O problema no mobile não é orquestração, é **fragmentação de descoberta e configuração**: ninguém centraliza "o que instalar, como configurar, onde ficam os dados, o que funciona 100% offline".

**Tese do produto:** um hub leve que aplica a mesma filosofia de curadoria do NOMAD (categorias, cards, docs de onboarding), mas para um ecossistema onde as peças já existem — o valor está em indicar, configurar e organizar, não em desenvolver runtime.

---

## 2. Objetivo

Entregar, em um fim de semana, um app/PWA mobile que funcione como **dashboard de referência e checklist** para montar um "kit de sobrevivência digital offline" no próprio celular, com QoL de UI que o diferencie de "só uma lista em markdown".

### Fora de escopo (explicitamente)
- Orquestração de containers ou runtime próprio de IA/Wikipedia/mapas.
- Reimplementar qualquer funcionalidade que já existe em apps dedicados (Kiwix, OsmAnd, MLC-Chat).
- Sincronização em nuvem, contas de usuário, backend.
- Suporte multi-idioma na v1 (PT-BR apenas).
- iOS na v1 (Android first, dado o ecossistema de sideload/F-Droid mais aberto).

---

## 3. Personas

| Persona | Necessidade |
|---|---|
| **Prepper/off-grid enthusiast** | Quer montar o celular como "backup de conhecimento" antes de uma viagem/cenário de risco. |
| **Curioso técnico (o próprio Felipe)** | Já tem NOMAD ou pretende ter; quer o equivalente mobile como complemento, não substituto. |
| **Usuário casual sem GPU/homelab** | Não tem hardware pra rodar NOMAD completo; o celular é o único "servidor" disponível. |

---

## 4. Escopo funcional (MVP — fim de semana)

### 4.1 Estrutura de categorias (herdada conceitualmente do NOMAD)
1. **Biblioteca de Informação** — Kiwix (Wikipedia, Gutenberg, guias médicos/survival)
2. **IA Local** — LLMs on-device (MLC-Chat, PocketPal, Layla)
3. **Mapas Offline** — OsmAnd, Organic Maps
4. **Educação** — Khan Academy offline (se existir app), e-books
5. **Ferramentas de Dados** — utilitários offline (calculadora de rádio, conversor de unidades, criptografia básica)

Cada categoria é uma seção com "cards" de apps.

### 4.2 Card de app (unidade central de UI)
Cada card contém:
- Nome, ícone, categoria
- Status: `Não instalado` / `Instalado, não configurado` / `Pronto`
- Botão de ação: `Baixar` (deep link pra Play Store/F-Droid) ou `Abrir` (deep link direto pro app)
- Expansível: mini-guia de onboarding (bundled, offline) — primeiro uso, onde ficam os dados, o que funciona sem internet, tamanho estimado de conteúdo a baixar
- Badge de "conteúdo recomendado" (ex: quais ZIMs do Kiwix baixar, quais regiões OSM)

### 4.3 Checklist / progresso
- Barra de progresso geral ("Kit 60% pronto")
- Persistência local do status de cada card (sem backend — `SharedPreferences`/local storage)
- Toggle manual "marquei como pronto" pros apps que o hub não consegue detectar automaticamente

### 4.4 Modo "Ficha de campo" (QoL diferencial)
- Tela resumo, otimizada pra leitura rápida em cenário de estresse/baixa luz (dark mode nativo, alto contraste)
- Atalhos fixos: os 3–5 apps mais críticos sempre visíveis no topo, independente de scroll

### 4.5 Conteúdo bundled offline
- Os guias de onboarding de cada card são markdown/JSON local, embutidos no APK — o hub em si funciona 100% offline desde a instalação, sem depender de fetch de conteúdo remoto.

---

## 5. Decisão técnica (arquitetura)

### Recomendação: **App Android nativo leve, não PWA**

| Critério | PWA | Nativo (Kotlin + Compose) |
|---|---|---|
| Deep links pra outros apps (abrir Kiwix, OsmAnd direto) | Limitado/instável | Nativo via Intents |
| Funciona 100% offline após instalado | Sim, mas depende de service worker bem feito | Sim, trivial |
| Detectar apps instalados no aparelho | Não é possível (sandbox do browser) | Possível via `PackageManager` |
| Esforço pra weekend project | Menor setup inicial | Mais familiar pro ecossistema Android real |
| Alinhado ao seu histórico (BARTLEBY, PicoFIDO) | — | Mais próximo do seu padrão de projetos com firmware/hardware — mas aqui é só app |

**Decisão:** Kotlin + Jetpack Compose. Justificativa principal: a feature mais valiosa (detectar se o Kiwix/OsmAnd já está instalado via `PackageManager.getPackageInfo`) não é viável em PWA, e é o que dá o "efeito hub real" em vez de lista estática.

### Stack sugerida
- **UI:** Jetpack Compose + Material 3 (dark mode nativo, componentes prontos economizam tempo de fim de semana)
- **Persistência local:** DataStore (Preferences) — status de cada card
- **Dados de curadoria:** JSON estático embutido em `res/raw` ou `assets` (lista de apps, categorias, guias) — fácil de editar sem rebuild de lógica
- **Sem rede, sem API, sem backend** — tudo local

---

## 6. Modelo de dados (curadoria)

```json
{
  "id": "kiwix",
  "name": "Kiwix",
  "category": "info-library",
  "package_name": "org.kiwix.kiwixmobile",
  "store_url": "https://f-droid.org/packages/org.kiwix.kiwixmobile/",
  "description_short": "Leitor offline de Wikipedia, Gutenberg e guias médicos via arquivos ZIM.",
  "onboarding_md": "onboarding/kiwix.md",
  "recommended_content": [
    "Wikipedia PT-BR (Top articles) — ~2GB",
    "Wikipedia Medicina — ~1GB",
    "Guia de Sobrevivência (Gutenberg curated) — ~200MB"
  ],
  "priority": "critical"
}
```

Esse schema é o coração do app — expandir a lista de apps depois é só editar JSON, sem tocar em código.

---

## 7. Telas (v1)

1. **Home/Dashboard** — progresso geral + categorias em accordion/grid
2. **Categoria** — lista de cards daquela categoria
3. **Detalhe do card** — onboarding guide expandido, links, conteúdo recomendado
4. **Ficha de campo** — modo resumo/emergência
5. **Configurações** — reset de progresso, sobre o projeto, link pro NOMAD desktop como "próximo passo" pra quem tem hardware

---

## 8. Critérios de sucesso do weekend project

- [ ] App instalável (APK debug ou release local) funcionando 100% offline
- [ ] Mínimo 8–10 apps curados cobrindo as 5 categorias
- [ ] Detecção real de apps instalados via PackageManager funcionando
- [ ] Onboarding guides bundled e legíveis offline
- [ ] Dark mode / modo "ficha de campo" implementado
- [ ] Zero dependência de backend/rede

---

## 9. Riscos e mitigação

| Risco | Mitigação |
|---|---|
| Escopo crescer além do fim de semana | JSON-driven content = resistir à tentação de codar features novas; qualquer coisa nova entra como dado, não como código |
| Curadoria ficar rasa (só 3-4 apps óbvios) | Reservar 1–2h só pra pesquisa de apps antes de abrir o editor |
| App virar "só uma lista", sem diferencial de UX | Priorizar a detecção via PackageManager e o modo Ficha de Campo — são os dois itens que realmente diferenciam de um post de blog |

---

## 10. Roadmap pós-MVP (não fazer agora, só registrar)

- Export/import de progresso via QR code (sem backend)
- Estimativa de espaço total necessário no aparelho
- Modo "companion" pro NOMAD desktop — o hub mobile detecta se há um NOMAD na rede local e sugere apps complementares
- Suporte a iOS (Shortcuts-based, já que iOS não permite detecção de apps instalados)

---

## 11. Nota de atribuição

O nome, estrutura de categorias e filosofia de "Supply Depot" são inspirados no Project N.O.M.A.D. (Crosstalk Solutions, licença Apache 2.0). Nenhum código é copiado. Recomenda-se creditar o projeto original no About/Configurações do app.
