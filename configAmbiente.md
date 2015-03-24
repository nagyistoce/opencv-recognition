# Orientações #
<ul>
<blockquote><li>Ler toda uma sessão antes de executar qualquer ação.</li>
<li>Certifiquem-se de que ao menos compreendem o propósito de cada ação, não sendo necessário, agora, entender todos os procedimentos.</li>
<li>Para as orientações nebulosas, utilizem os sites oficiais das ferramentas, não tem como eu me estender nesse tutorial, os passos são muito simples e tudo aqui detém um extenso conteúdo na internet, podendo vocês se utilizarem de consultas na web.</li>
<li>Como projeto acadêmico é interessante tirarem o maior proveito e todo tipo de experiência o possível desse trabalho.</li>
</ul></blockquote>

# Prerequisitos do Ambiente #

<ul>
<blockquote><li><a href='http://sourceforge.net/projects/opencvlibrary/files/opencv-android/2.4.5/OpenCV-2.4.5-android-sdk.zip/download'>OpenCV para android: <a href='http://sourceforge.net/projects/opencvlibrary/files/opencv-android/2.4.5/OpenCV-2.4.5-android-sdk.zip/download'>http://sourceforge.net/projects/opencvlibrary/files/opencv-android/2.4.5/OpenCV-2.4.5-android-sdk.zip/download</a></a></li>
<li>
eclipse juno com ADT do android e o CDT, que já vem com o C/C++ do Eclipse. Link é instalado no Eclipse via Help>>Install new Software>>add>>url>>OK. ADT: <a href='#'><a href='https://dl-ssl.google.com/android/eclipse/'>https://dl-ssl.google.com/android/eclipse/</a></a>.<br>
</li>
<li>
Android SDK e o NDK instalados. Android sdk: <a href='http://developer.android.com/sdk/index.html'><a href='http://developer.android.com/sdk/index.html'>http://developer.android.com/sdk/index.html</a></a>; Android ndk: <a href='http://developer.android.com/tools/sdk/ndk/index.html'><a href='http://developer.android.com/tools/sdk/ndk/index.html'>http://developer.android.com/tools/sdk/ndk/index.html</a></a>
</li>
<li>
ter o java instalado.<br>
</li>
<li>Instalar o pacote build-essential do linux. Não vou citar os passos, pois já existem tutoriais demasiados na internet para instrução da instalação. Mas se for uma distro Debian, utilizar o programa apt para instalação dos pacotes.</li>
</ul></blockquote>


# Configurando o Ambiente #
<p>
Inicialmente é interessante que tenhamos todos os plugins com fácil acesso via shell. Para isso é necessário editar o .bashrc na home do seu usuário no linux. Na ultima linha devem colocar o seguinte:<br>
</p>
<p>
<blockquote><b>export PATH=$PATH:<caminho para a pasta do android ndk></b>
</p>
<p>
Após a edição do arquivo, salvar as alterações.<br>
</p>
<p>
<b>OBS.: É importante que para todas as alterações em arquivos efetuadas, tenhamos as versões de backup. Salve antes de qualquer alteração uma versão .bashrc.old para sabermos que essa é uma versão anterior à atualização.</b>
</p>
<p>
É bom termos disponível também as ferramentas do sdk para depuração em dispositivos android. Para fazer isso é o mesmo procedimento de alteração do bash para o ndk, mas apontando para a pasta platform-tools do diretório raiz da SDK do android. Iremos utilizar o comando bash 'abd' para depuração de dispositivos.<br>
</p>
<h1>Configurando os Projetos do Eclipse</h1>
<p>Como já existe um tutorial mais que suficiente para os procedimento de configuração dos projetos no Eclipse, segue o link <a href='http://docs.opencv.org/doc/tutorials/introduction/android_binary_package/O4A_SDK.html'>http://docs.opencv.org/doc/tutorials/introduction/android_binary_package/O4A_SDK.html</a> com todas as instruções necessárias, inclusive podem utilizar a sessão de dependências para validar as dependências necessárias para compilar os projetos baseados em bibliotecas do OpenCV.</p></blockquote>

<p>
Dentro desse tutorial vocês vão encontrar instruções de configuração dos samples presentes no sdk do OpenCV Android. Juntamente aos samples, tem o projeto que contém as bibliotecas utilizadas por esses projetos.<br>
</p>

<p>
Quando forem executar os samples, vão requisitar que vocês instalem o Manager do OpenCV para android, acho que é ele que contém os serviços utilizado para interceptar a câmera e outros periféricos do dispositivo.<br>
</p>

<p>
<blockquote><b>SEGUIR O TUTORIAL DE CIMA A BAIXO. NÃO PULAR UMA SÓ SESSÃO.</b> Ao final vocês serão capazes de executar os samples. Testem no emulador (AVDs do Android). Certifiquem-se de utilizarem AVDs que tenham como microcontrolador armv7a. Na sessão 'Import OpenCV library and samples to the Eclipse', bem ao final, contém observações importantes sobre o dispositivo emulado, o que vai servir para dispositivos físicos também.<br>
</p></blockquote>

# OBSERVAÇÕES #
<ul>
<blockquote><li>Os caminhos utilizados no bashrc devem ser completos, não são relativos.</li>
</ul>