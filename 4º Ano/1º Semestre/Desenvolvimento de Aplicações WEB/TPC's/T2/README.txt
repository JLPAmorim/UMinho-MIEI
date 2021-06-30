TPC2 - 28/10/2020

Criação de um ficheiro DTD sobre o Project Record com um XML exemplo
que caracterize o trabalho de casa em si. A explicação do DTD é feita dentro 
do XML, mas será também introduzida aqui:

O DTD criado é caracterizado por quatro elementos principais, nomeadamente cabeçalho, equipa, abstract e deliverables.
No caso do <bold>cabeçalho</bold>, teremos o keyname, o title, o subtitle que é opcional, a begindate e a enddate, 
assim como o supervisor que é constituído por texto e um link. No caso da equipa, esta será composta por uma lista de 
um ou mais membros em que cada é composto por um <italico>id, um nome, um mail e um link opcional para esse mail</italico>. 
Para o abstract, este será composto por um ou mais paragrafos, em que cada
é constituído por um ou mais elementos de texto, podendo este mesmo ser também um link, sublinhado, italico e bold. 
Por último, o <bold>deliverables</bold> que é composto por um ou mais items, sendo cada item composto por texto e um link.