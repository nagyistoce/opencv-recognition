# Introdução #

<p>
Git é um programa de versionamento de código. Além de produzir vários 'backups' das alterações feitas, ele contém ferramentas que facilitam o versionamento do código produzido.<br>
</p>
<p>
O Git foi desenvolvido por Linus Torvalds, pai do Linux, para versionar e manter o código do Kernel Linux. Portanto, podem imaginar o quanto esse versionador já foi posto à prova, pois manteve e mantém o código do Kernel Linux, que é alterado por centanas de milhares de pessoas ao mesmo tempo.<br>
</p>

# Instalando o GIT #

<p>
Para os que têm qualquer distro Debian poderão apenas aplicar no shell o comando: <b>sudo apt-get install git</b>. Os que não têm poderão procurar um tutorial adequado para instalação em sua distro xD (desculpa, sem tempo para tutorias extensos).<br>
</p>


# Recuperando o repositório #

<ol>
<blockquote><li>Abra um bash.<br>
</li>
<li>Crie uma pasta no diretório /home/${seu_usuário}/ com nome sugestivo, tais quais: gitRepo, gitProjects, etc.<br>
</li>
<li>Vá no sítio do google code do projeto Face Recogintion.<br>
</li>
<li>Na aba (link) Source (Código).<br>
</li>
<li>Clique em <i>'googlecode.com password'</i>
</li>
<li>Ainda no diretório /home/${seu_usuário}/ crie um arquivo .netrc com as seguintes informações: <b>machine code.google.com login ${usuario_google-code}@gmail.com password ${pass_gerado}</b>, onde ${pass_gerado} é equivalente ao resultado do link clicado anteriormente. Salve o arquivo.<br>
</li>
<li>Entre na pasta criada anteiormente.<br>
</li>
<li>Acione o comando: <b>git clone <a href='https://code.google.com/p/opencv-recognition/'>https://code.google.com/p/opencv-recognition/</a></b>
</li>
<li>Pronto, o código versionado no repositório remoto agora está versionado em um repositório local.<br>
</li>
</ol></blockquote>

# Entendendo o Git #

<p>
O Git trabalha com 2 repositórios: um remoto e outro local. Tanto o repositório remoto quanto o local detêm as mesmas funcionalidade, embora objetivos diferentes.<br>
</p>
<p>
O repositório local é seu repositório de trabalho. É nele que você efetuará <b>commits</b> (cômitis ou comítis). Esses commits mantêm seu código versionado localmente, embora o repositório compartilhado seja o remoto.<br>
</p>
<p>
<blockquote><b>Só vai para repositório remoto o trabalho concluído e pronto para <i>review</i>.</b> O código fonte no repositório remoto NUNCA deve estar incompleto. O código presente no repositório remoto é entregue mediante <b>pushes</b>. Esses pushes atualizam o versionamento remoto com o versionamento presente no local de trabalho.<br>
</p>
<p>A parte mais linda de um versionador como o Git é a capacidade de organização. Ele se organiza como uma estrutura de uma árvore, onde o início se dá em uma parte central, o tronco (<b>trunk</b>), e as continuações que provêm dele: os ramos (ou <b>branches</b>).<br>
</p>
<p>
Os branches nascem do trunk, ou seja, é algo adicional ao que já existe no trunk.<br>
</p></blockquote>

# Forma de trabalho #
<p>
Após pegarem o código via <i>git clone</i>, vocês devem criar um novo branch para vocês (ensinarei isso em outro tutorial).<br>
</p>
<p>
Após criarem um novo branch farão sucessíveis commits no repositório local, para não perderem o trabalho produzido e, consequentemente, infligir em retrabalho.<br>
</p>