# KeePassDX — Guia de Primeiro Uso

**O que é:** gerenciador de senhas offline com criptografia AES-256 / ChaCha20. Seu banco de dados de senhas fica num arquivo `.kdbx` local — sem nuvem, sem servidor.

## Por que isso importa no kit de sobrevivência

Em cenários de emergência, você pode precisar acessar: senhas de email, acesso bancário, VPN, senhas de Wi-Fi de locais de apoio, credenciais de serviços críticos. Memorizar tudo é inviável; depender de serviço online é arriscado sem internet.

## Primeiro uso

1. Instale pela **F-Droid** (recomendado) ou Play Store.
2. Abra o app → **"Criar novo banco de dados"**.
3. Escolha uma senha mestre forte (anote em papel e guarde em local seguro).
4. Crie categorias (ex: Email, Banco, Wi-Fi, Governo) e adicione suas credenciais.
5. Salve o arquivo `.kdbx` em local redundante (celular + SD card + cópia em papel dos críticos).

## Onde ficam os dados

Você escolhe o caminho. Recomendado: `/Documents/passwords.kdbx` (e backup em SD card).

## O que funciona 100% offline

- Leitura e escrita do banco de dados
- Gerador de senhas
- Autofill (se configurado nas acessibilidades do Android)

## O que NÃO funciona offline

- Sincronização com Nextcloud/WebDAV (recursos opcionais, não necessários)

## Dica de segurança

Nunca salve a senha mestre no mesmo dispositivo que o arquivo `.kdbx`. Anote em papel e guarde separado.
