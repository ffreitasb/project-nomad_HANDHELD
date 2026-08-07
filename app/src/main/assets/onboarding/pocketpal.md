# PocketPal AI — Guia de Primeiro Uso

**O que é:** app de LLM local baseado em llama.cpp. Suporta qualquer modelo no formato GGUF, disponíveis no HuggingFace. Interface mais amigável que o MLC Chat.

> ⚠️ **Atenção:** assim como o MLC Chat, o modelo precisa ser baixado antes do uso offline. Faça com Wi-Fi.

## Primeiro uso

1. Instale pela Play Store.
2. Abra o app → toque em **"+ Add model"**.
3. Escolha entre os modelos pré-listados **ou** cole o link de um arquivo GGUF do HuggingFace.
4. Aguarde o download do modelo.
5. Toque no modelo baixado → **"Load"** → comece a conversar.

## Diferencial em relação ao MLC Chat

- Suporta **qualquer modelo GGUF** (não só os pré-compilados pelo MLC)
- Interface de configuração mais exposta (temperatura, context size, etc.)
- Mais adequado para usuários que querem experimentar modelos diferentes

## Onde ficam os dados

Modelos baixados: `Android/data/com.pocketpalai/files/models/`

## O que funciona 100% offline

- Chat com modelos já baixados
- Troca entre modelos locais

## O que NÃO funciona offline

- Download de novos modelos do HuggingFace
- Busca de modelos no catálogo

## Estimativa de espaço por modelo

| Modelo | Tamanho |
|---|---|
| Gemma-2-2B Q4_K_M | ~1.6 GB |
| Llama-3.2-3B Q4_K_M | ~1.8 GB |
| Phi-3-mini Q4_K_M | ~2.2 GB |
