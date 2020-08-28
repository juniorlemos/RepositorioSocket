# Servidor-de-Arquivos-com-Socket

Servidor de Arquivos utilizando Socket e a API JDBC para fazer o login e cadastro de novos usuarios e manupular arquivos entre servidor e cliente.

Funçoes implementadas pelo Servidor.

Upload  
Download  
Listar  
Deletar  
Mover arquivo de  diretório 


Instalação:

Baixe o arquivo PlayServidor , ServidorT e Conexão para o seu ser servidor
Baixe o arquivo mysql-connector-java-8.0.21 no seu servidor 
Extraia o mysql-connector-java-8.0.21 para um diretorio que voce deseja

Baixe o arquivo Cliente para o seu cliente

No aquivo Conexão configure a a sua conexão do banco de dados de acordo com o seu banco

compile as 4 classes com o comando javac NomeDaClasse.java

execute primeiro playServidor com o comando 
java -cp .:<com o caminho local de onde se encontra o  mysql-connector-java-8.0.21 lib> PlayServer

depois execute o cliente com o comando
java Cliente 




