# Guia de Compilação — NOMAD:HANDHELD

Este guia detalha o passo a passo para você compilar o aplicativo NOMAD:HANDHELD na sua própria máquina, gerando o arquivo `.apk` final para ser instalado no seu celular Android. Como este projeto foi desenhado sem a necessidade de contas, backend ou chaves de API, o processo é extremamente simples.

---

## 🛠️ 1. Pré-requisitos

Antes de começar, certifique-se de que você tem instalado no seu computador:
1. **Android Studio**: A IDE oficial do Android. Baixe a versão mais recente (atualmente *Koala* ou superior) em [developer.android.com/studio](https://developer.android.com/studio).
2. **Git**: Para controle de versão (opcional, caso você prefira apenas baixar o `.zip` do repositório).

---

## 🚀 2. Abrindo o projeto no Android Studio

1. Abra o **Android Studio**.
2. Na tela de boas-vindas, clique em **"Open"** (ou vá em `File` > `Open` se já houver algum projeto aberto).
3. Navegue até a pasta onde este repositório está salvo no seu computador (por exemplo: `C:\Users\...\project-nomad_HANDHELD`) e selecione a pasta raiz.
4. Clique em **OK**.
5. Aguarde o **Gradle Sync**. O Android Studio vai baixar todas as dependências automaticamente (Compose, Kotlin, bibliotecas do AndroidX, etc.). 
   - *Nota: Na primeira vez, este processo exige conexão com a internet e pode demorar alguns minutos. Acompanhe a barra de progresso no canto inferior direito da tela.*

---

## 📱 3. Compilando o APK (Debug / Uso Pessoal)

Se você quer apenas instalar o app no seu próprio celular e não pretende distribuí-lo publicamente, um **Debug APK** é a forma mais rápida e fácil.

### Método 1: Direto via Cabo USB / Wi-Fi
1. Conecte seu celular Android ao computador via cabo USB (ou conecte via depuração Wi-Fi).
2. No seu celular, as **Opções de Desenvolvedor** e a **Depuração USB** devem estar ativadas.
3. No topo do Android Studio, seu dispositivo deve aparecer no menu suspenso de "Available Devices".
4. Pressione o botão ▶️ **(Run 'app')** ou use o atalho `Shift + F10`.
5. O Android Studio irá compilar e instalar automaticamente o aplicativo direto no seu aparelho.

### Método 2: Gerando o arquivo `.apk` para transferir manualmente
1. No menu superior do Android Studio, vá em **Build** > **Build Bundle(s) / APK(s)** > **Build APK(s)**.
2. Uma barra de progresso aparecerá na parte inferior.
3. Quando terminar, uma notificação flutuante vai aparecer no canto inferior direito dizendo "Build APK(s)".
4. Clique em **"locate"** nessa notificação (ou abra manualmente no explorador de arquivos: `project-nomad_HANDHELD\app\build\outputs\apk\debug\`).
5. Copie o arquivo `app-debug.apk` resultante, envie para o seu celular (por cabo, Google Drive, e-mail) e instale-o.

---

## 🔒 4. Compilando um APK de Release (Produção)

Se você planeja enviar o app para seus familiares e amigos e deseja o máximo de performance (tamanho otimizado), você deve gerar um APK assinado.

1. No menu superior, vá em **Build** > **Generate Signed Bundle / APK**.
2. Escolha **APK** e clique em Next.
3. No campo *Key store path*, clique em **Create new...**
   - Escolha uma pasta no seu PC para salvar a chave (ex: `keystore.jks`).
   - Crie uma senha para a Key store.
   - Preencha o *Alias* (ex: `key0`), crie uma senha para a Key, e preencha pelo menos o seu *First and Last Name*.
   - Clique em **OK**.
4. Certifique-se de que todos os dados de senha e path estão preenchidos na janela e clique em **Next**.
5. Selecione a variante de build **release**.
6. Clique em **Create**.
7. O Android Studio irá compilar o app minificado. Quando terminar, clique em **"locate"** na notificação pop-up.
8. O arquivo final estará na pasta `project-nomad_HANDHELD\app\release\app-release.apk`.
9. Distribua e instale esse APK no seu celular ou de seus conhecidos.

---

## 🎨 (Opcional) Trocando o Ícone do App

Se você quiser substituir o ícone padrão do Android (o robô verde) por um ícone próprio antes de gerar o APK:

1. No Android Studio, expanda a árvore lateral esquerda e navegue até: `app` > `res`.
2. Clique com o botão direito na pasta `res` > **New** > **Image Asset**.
3. Na janela do "Asset Studio", na aba "Foreground layer", selecione o tipo **"Image"** e aponte o "Path" para o arquivo de logo da sua preferência no seu computador.
4. Ajuste a barra de zoom para que a logo se encaixe corretamente dentro do círculo da pré-visualização.
5. Vá para a aba "Background layer" e escolha a cor de fundo desejada.
6. Clique em **Next**, observe os arquivos que serão sobrescritos em vermelho, e clique em **Finish**.
7. Crie seu APK novamente (Passo 3 ou 4) e ele terá o novo ícone!
