# biblioteca
SSC0103 - Programacão Orientada a Objetos - Prof. Adenilso da Silva Simão - Trabalho 3



      		...:::	LORDedalus  :::...



Membros do Grupo:				nº USP
				
	- Laércio de Oliveira Júnior		  8922198		
	
	- Luis Ricardo Guabiraba da Silva	8937402
					
	- Otávio Augusto de Oliveira		 8936801

Professor:

	- Adenilso Simao   -  Programação Orientada a Objetos (P.O.O).	
			

	
Como executar:

	No Linux (Terminal):
		
		1 - Descompacte o zip para a pasta desejada.

		2 - Abra o terminal e vá para a pasta "ProjetoBiblioteca".		

		3 - Digite a seguinte linha de comando no terminal: java -jar ProjetoBiblioteca.jar
		
		4 - Ver (início do programa).
		
		


	No Windows (Netbeans):

		1 - Descompacte o zip para a pasta desejada.

		2 - No Netbeans, abra o projeto "ProjetoBiblioteca".	
		
		3 - Aperte Shift-F11 para limpar e construir o projeto e em seguida F6 para executar o projeto.
		
		4 - Ver (início do programa).
		


		Início do programa:

			1 - Primeiramente será solicitado para o usuário qual data será utilizada para o sistema.
			    Caso a opção escolhida seja (1) Data atual do sistema, o mesmo utilizará como data atual
			    a maior data visitada. Caso a opção escolhida seja (2) A data desejada pelo usuário, será 
			    solicitado o dia/mês/ano e caso essa data seja inválida o sistema utilizará a maior data visitada
			    até então. 

			2 - O usuário deverá escolher entre criar um novo usuário e entrar como um usuário já existente. Caso
 			    a opção escolhida seja (1) Novo usuário, ele deverá preencher um formulário de cadastro. Se a opção
			    escolhida seja a (2) Usuário existente, ele deverá entrar com o seu login e senha para ter acesso ao sistema.


			3 - A partir desse ponto as opções serão listadas e o usuário poderá interagir com o programa conforme a opção desejada.


			4 - Caso o usuário escolha uma data anterior comparando com a maior data já visitada,i.e, "viajou para o passado" as opções (4) Realizar empréstimo,
			    (5) Devolver livro e (9) Adicionar novo livro na biblioteca estarão desabilitadas, pois o sistema estará no modo ReadOnly
			    (Somente leitura). 			
			
			Obs: A penalidade é acumulativa, ou seja, caso o usuário ultrapasse a máxima data de entrega sem efetuar a devolução dos livros, a penalidade será acumulada
			     até que a devolução seja efetuada.


			Obs2: O programa DEVE ser finalizado utilizando a opção (10) Sair para que as atualizações feitas em tempo de execução sejam armazenadas.	
	
	
##########################################################################################################

Como serão organizado os livros no arquivo:
code,title,author,issue,type,pages,available,quantity



Como serão organizado os usuários no arquivo:
code,name,cpf,rg,bookLimit,daysLimit,type



Como serão organizado os empréstimos no arquivo:
code,codeUser,codeBook,returned,dateBorrow,dateReturn,dateMax

Obs: dateBorrow e dateReturn são no formato: "DD/MM/YYYY"


Como serão organizado os logins no arquivo:
code,username,password,codeUser


##########################################################################################################
