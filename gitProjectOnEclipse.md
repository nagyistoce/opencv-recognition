# Introdução #
<p>Esse tutorial é para ser lido após <i>retrieveGitRepo</i>.<br>
</p>

# Instruções #
<p>Esse tutorial vai cobrir detalhes para import do projeto no Eclipse e algumas configurações para o OpenCV funcionar legal com o projeto.<br>
</p>

# Importando o Porjeto #
<ol>
<blockquote><li>Clique com o botão direito do mouse sobre o explorador de projetos do Eclipse, que fica à esquerda da janela, ou sobre o explorador de pacotes, que também fica à esquerda da janela.<br>
</li>
<li>Selecione a opção <b>Import...</b> (as reticências significam que uma nova janela será aberta).<br>
</li>
<li>Na caixa de texto <b>Select an import source</b> digite <b>git</b>.<br>
</li>
<li>Na opção <b>Projects from Git</b> que será filtrada dê duplo clique.<br>
</li>
<li>Escolha a opção <b>Local</b>.<br>
</li>
<li>Apertem em <b>Add...</b>.<br>
</li>
<li>Selecionem A PASTA CRIADA no Tutorial anterior e não a pasta do projeto.<br>
</li>
<li>A opção de repositório Git, apontando para o repositório escolhido será apresentada logo abaixo.<br>
</li>
<li>Escolha a opção <b>Use the new Project wizard</b>.<br>
</li>
<li>Na caixa de texto <b>Select an import source</b> digite <b>android</b>.<br>
</li>
<li>Selecionem a opção <b>Android Project from existing code</b>
</li>
<li>Apertem no botão <b>Browse...</b> e selecionem a pasta do repositório, a mesma selecionada antes. Clique em <b>Finsh</b>.<br>
</li>
</ol></blockquote>

# Configurando o Eclipse #
<ol>
<blockquote><li>Vá no menu <b>Window>>Preferences>>Build>>Build Variables</b>.<br>
</li>
<li>Clique em <b>Add...</b>
</li>
<li>Utilizando <b>Add...</b> vocês criarão duas variáveis: <b>NDKROOT</b>, apontando para o android ndk e <b>OPENCV_HOME</b>, apontando para a pasta onde está instalado o opencv-android no computador de vocês.<br>
</li>
<li>Dêm <b>Apply</b> e depois <b>OK</b>.<br>
</li>
</ol></blockquote>

# Configurando o Projeto #
<ol>
<blockquote><li>Cliquem com o botão direito do mouse sobre o explorador de projetos ou de pacotes.<br>
</li>
<li>Selecionem a opção <b>Import...</b>.<br>
</li>
<li>Depois selecione a opção <b>Existing Android Code into workspace</b>
</li>
<li>Clique em <b>Browse...</b> e selecione o diretório do Android SDK.<br>
</li>
<li>Na lista que aparecerá após a seleção, selecione apenas <b>OpenCV Library</b>
</li>
<li>Clique com o direito sobre o projeto do Git, <b>face-recognition</b>.<br>
</li>
<li>Selecione a opção <b>Properties</b>.<br>
</li>
<li>Selecione <b>Android</b>. No painel direito remova a <b>Library</b> que contém um <font color='red'><b>X</b></font> vermelho.<br>
</li>
<li>Agora na mesma <b>Library</b> clique em <b>Add...</b> e selecione o projeto <b>Library</b> do OpenCV.<br>
</li>
<li>Aperte <b>Apply</b> e depois <b>Ok</b>.<br>
</li>
</ol>