# MLC Chat — Guia de Primeiro Uso

**O que é:** roda modelos de linguagem (LLMs) 100% no dispositivo, sem internet, usando compilação ML otimizada para GPU mobile.

> ⚠️ **Atenção:** o app é instalado vazio. O modelo precisa ser baixado após a instalação. Faça isso com Wi-Fi, antes de precisar usar offline.

## Primeiro uso

1. Instale pela Play Store ou sideload do [GitHub](https://github.com/mlc-ai/binary-mlc-llm-libs/releases).
2. Abra o app → toque em **Download** na tela inicial.
3. Escolha um modelo (ver recomendações abaixo).
4. Aguarde o download — pode demorar 30–60 min dependendo da conexão.
5. Após o download, o modelo fica local. **O app não precisa mais de internet.**

## Onde ficam os dados

Os pesos do modelo ficam em `Android/data/ai.mlc.mlcchat/files/` — pasta acessível pelo gerenciador de arquivos.

## O que funciona 100% offline

- Chat completo com o modelo baixado
- Histórico de conversa (salvo localmente)

## O que NÃO funciona offline

- Download de novos modelos
- Atualizações do app

## Requisitos de hardware

| Modelo | RAM mínima | Armazenamento |
|---|---|---|
| Llama-3.2-3B Q4 | 4 GB | ~1.9 GB |
| Llama-3.1-8B Q4 | 6 GB | ~4.3 GB |
| Phi-3.5-mini Q4 | 4 GB | ~2.1 GB |

Dispositivos com menos de 4 GB de RAM podem ter instabilidade com modelos maiores.
