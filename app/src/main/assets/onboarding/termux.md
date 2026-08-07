# Termux — Guia de Primeiro Uso

**O que é:** terminal Linux funcional no Android. Com pacotes pré-instalados, vira uma caixa de ferramentas offline: Python, SSH, nmap, vim, git e centenas de utilitários.

> ⚠️ **Instale pela F-Droid**, não pela Play Store. A versão da Play Store foi descontinuada e não recebe mais atualizações de pacotes.

## Primeiro uso

1. Instale o **F-Droid** primeiro (se não tiver): [f-droid.org](https://f-droid.org)
2. No F-Droid, instale o **Termux**.
3. Abra o Termux e execute:
   ```bash
   pkg update && pkg upgrade
   ```
4. Instale os pacotes essenciais (com internet):
   ```bash
   pkg install python git openssh nmap nano vim curl wget
   ```
5. Após isso, os pacotes instalados funcionam **100% offline**.

## Onde ficam os dados

- Pacotes: `$PREFIX/` (interno ao Termux)
- Seus scripts e arquivos: `~/` (`/data/data/com.termux/files/home/`)

## O que funciona 100% offline (após pacotes instalados)

- Python (scripts, calculadoras, processamento de dados)
- SSH cliente (para acessar outros dispositivos na rede local)
- Nmap (escaneamento de rede local)
- Vim/Nano (edição de arquivos)
- Git (versionamento local)
- Qualquer script ou ferramenta que você tiver pré-instalado

## O que NÃO funciona offline

- `pkg install` (precisa de internet)
- Atualização de pacotes
- Clone de repositórios remotos

## Scripts úteis para pré-instalar

Considere criar scripts locais para: cálculo de sub-redes, geração de QR codes (instale `qrencode`), criptografia básica (`openssl`).

## Estimativa de espaço

Instalação base + pacotes essenciais: ~500 MB. Varia conforme pacotes adicionados.
