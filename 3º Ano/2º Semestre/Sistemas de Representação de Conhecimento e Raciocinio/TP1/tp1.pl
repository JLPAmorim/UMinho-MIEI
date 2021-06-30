%---------------------------------------------------------------------
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3 - Exercicio 1

% GRUPO 23
% Alexandre Martins  - A77523
% João Bernardo - A74814
% João Amorim  - A7408
% Tiago Gomes - AE4560


%---------------------------------------------------------------------
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag(discontiguous_warnings, off).
:- set_prolog_flag(single_var_warnings, off).
:- set_prolog_flag(unknown, fail).

%-----------------------------------------------------------------------------
% SICStus PROLOG: definicoes iniciais

:- op( 900, xfy,'::' ).
:- dynamic utente/4.
:- dynamic servico/4.
:- dynamic consulta/5.
:- dynamic instituicao/3.
:- dynamic medico/4.

%----------------------- Base de Conhecimento ------------------------
% Extensao do predicado utente: #IdUt, Nome, Idade, Cidade -> {V,F}

utente( 0, 'Elias',     28, 'Barcelos').
utente( 1, 'Sebastiao', 18, 'Porto').
utente( 2, 'Martim',    32, 'Braga').
utente( 3, 'Lucia',     55, 'Guimaraes').
utente( 4, 'Alexandre', 76, 'Lisboa').
utente( 5, 'Juliana',   22, 'Porto').
utente( 6, 'Joao',      8,  'Famalicao').
utente( 7, 'Antonio',   16, 'Viseu').
utente( 8, 'Isabela',   36, 'Vila do Conde').
utente( 9, 'Artur',     51, 'Setubal').
utente(10, 'Alice',     86, 'Povoa de Varzim').

% Extensao do predicado servico: #IdServ, Descricao, Instituicao, Cidade -> {V, F}	

servico( 0, 'Dermatologia', 2, 'Porto').
servico( 1, 'Psiquiatria',  1, 'Porto').
servico( 2, 'Neurologia',   0, 'Braga').
servico( 3, 'Ortopedia',    3, 'Lisboa').
servico( 4, 'Podologia',    4, 'Barcelos').
servico( 5, 'Cardiologia',  5, 'Braga').
servico( 6, 'Oftalmologia', 6, 'Faro').
servico( 7, 'Pediatria',    7, 'Famalicao').
servico( 8, 'Psicologia',   7, 'Famalicao').


% Extensao do predicado consulta: Data, #IdUt, #IdServ, IdMed, Custo -> {V, F}	

consulta(data(20, 2, 2019), 0, 4, 0, 20).
consulta(data(20, 2, 2019), 1, 0, 7, 70).
consulta(data(21, 3, 2019), 1, 7, 2, 35).
consulta(data(21, 3, 2019), 2, 7, 1, 50).
consulta(data(07, 4, 2019), 3, 5, 4, 100).
consulta(data(07, 4, 2019), 4, 3, 5, 45).
consulta(data(10, 4, 2019), 4, 6, 3, 25).
consulta(data(10, 4, 2019), 5, 1, 6, 40).
consulta(data(10, 4, 2019), 6, 2, 1, 50).
consulta(data(13, 4, 2019), 6, 1, 0, 40).
consulta(data(13, 4, 2019), 7, 6, 4, 25).
consulta(data(19, 4, 2019), 8, 7, 2, 35).
consulta(data(01, 5, 2019), 8, 0, 5, 70).
consulta(data(01, 5, 2019), 9, 3, 7, 45).
consulta(data(06, 6, 2019), 10, 2, 5, 50).
consulta(data(12, 5, 2019), 10, 0, 4, 70).
consulta(data(12, 5, 2019), 10, 4, 1, 20).
consulta(data(13, 5, 2019), 2, 8, 6, 50).

%------------------------- Predicados Auxiliares ---------------------------------
% Extensao do predicado solucoes: F, Q, S -> {V, F}	

solucoes(F, Q, S) :- findall(F, Q, S).


% Extensao do predicado comprimento: S, N -> {V, F}	

comprimento(S, N) :- length(S, N).


% Extensao do predicado sum: Lista, R -> {V, F}		
% Somatorio de uma lista

sum([], 0).
sum([H|T], R) :- sum(T, L), R is H + L.


% Extensão do predicado apagaReps: L, R -> {V, F}
% Apaga diversos elementos repetidos numa lista.

semReps([], []).
semReps([H|T], [H|L]) :- apaga(H, T, X),
                         semReps(X, L).


% Extensão do predicado apagaT: X, L, R -> {V, F}
% Apaga todas as ocorrências repetidas de um elemento numa lista.

apaga(X, [], []).
apaga(X,[X|L1],L2) :- apaga(X,L1,L2).
apaga(X,[Y|L1],[Y|L2]) :- apaga(X,L1,L2).


% Extensão do predicado concatenar : L1,L2,R -> {V,F}

concatenar([],L,L).
concatenar([X|L1],L2,[X|L3]) :- concatenar(L1,L2,L3).


% Extensão do predicado concListList: LLs, L -> {V, F}
% Utilizando o predicado auxiliar concatenar, concatena listas dentro de uma lista.

concListList([], []).
concListList([H|T], L) :- concListList(T, L1),
                          concatenar(H, L1, L).                        

			
% Extensao do predicado insere: Termo -> {V, F}		
			
insere(Termo) :- assert(Termo).
insere(Termo) :- retract(Termo), !, fail.


% Extensao do predicado remove: Termo -> {V, F}	

remove(Termo) :- retract(Termo).
remove(Termo) :- assert(Termo), !, fail.


% Extensao do predicado teste: Lista -> {V, F}	

teste([]).
teste([H | T]) :- H, teste(T).


% Extensao do predicado evolucao: Termo -> {V, F}	

evolucao(Termo) :- solucoes(Inv, +Termo::Inv, S),
                   insere(Termo),
                   teste(S).


% Extensao do predicado involucao: Termo -> {V, F}		
			   
involucao(Termo) :- Termo,
					solucoes(Inv, -Termo::Inv, S),
                    remove(Termo),
                    teste(S).

            
%--------------- 1) 2) Registar e Remover utentes, serviços e consultas ----------------------|
%---------------------------------------------------------------------------------------------|


% Invariante Estrutural: Não permitir a insercao de utentes repetidos (com o mesmo ID)
% Id e Idade inteiros
% Idade entre 0 e 120

+utente(Id, _, Idade, _) :: (
	integer(Id), 
	integer(Idade),
	Idade >= 0, 
	Idade =< 120,
	solucoes(Id, utente(Id, _, _, _), S),
    comprimento(S, N),
    N == 1
).

% Invariante Referencial: Nao permitir a remocao de um utente enquanto existirem consultas
% associadas ao mesmo
-utente(IdUt, _, _, _) :: (
	solucoes(IdUt, consulta(_,IdUt, _, _, _), S1),
	comprimento(S1, N1),
	N1 == 0
).


% Invariante Estrutural: Nao permitir a insercao de servicos que ja existam (com o mesmo ID),
% tipos de servico que já existam, ou seja, cuja descricao + instituicao + cidade já existam,
% servicos cuja instituicao nao exista e ainda servicos cuja cidade inserido nao corresponde 
% a instituicao.
% Id deve ser inteiro

+servico(Id, Desc, IdInst, Cid) :: (
	integer(Id), integer(IdInst), 
    solucoes(Id, servico(Id, _, _, _), S1),
    solucoes((Desc,IdInst,Cid), servico(_, Desc, IdInst, Cid), S2),
	solucoes((IdInst, Cid), instituicao(IdInst, _, Cid), S3),
    comprimento(S1, N1),
	comprimento(S2, N2),
	comprimento(S3, N3),
    N1 == 1,
	N2 == 1,
	N3 == 1
).

% Invariante Referencial: Nao permitir a remocao de servico enquanto existirem consultas
% associadas ao mesmo

-servico(IdServ, _, _, _) :: (
	solucoes(IdServ, consulta(_, _, IdServ, _, _), S1),
	comprimento(S1, N1),
	N1 == 0
).


% Invariante Estrutural: Não permitir a existencia de consultas repetidas
% Nao permitir a insercao de uma consulta cujo utente, servico ou medico nao existam
% O valor do primeiro argumento deve ser uma data e o custo deve ser do tipo number 
% e ser maior ou igual a zero

+consulta(Data, IdUt, IdServ, IdMed, Custo) :: (
	integer(IdUt), integer(IdServ), integer(IdMed),
	number(Custo), Custo >= 0, 
	solucoes(IdUt, utente(IdUt, _, _, _), S1),
	solucoes(IdServ, servico(IdServ, _, _, _), S2),
	solucoes(IdMed, medico(IdMed, _, _, _), S3),
	solucoes((Data, IdUt, IdServ, IdMed, Custo),
              consulta(Data, IdUt, IdServ, IdMed, Custo), S4),
	comprimento(S1, N1),
	comprimento(S2, N2),
	comprimento(S3, N3),
	comprimento(S4, N4),
	N1 == 1, N2 == 1, N3 == 1, N4 ==1
).

% Invariante Referencial: Remover uma consulta se esta existir.

-consulta(Data, IdUt, IdServ, IdMed, Custo) :: (
	solucoes((Data, IdUt, IdServ, IdMed, Custo), 
	consulta(Data, IdServ, IdServ, IdMed, Custo), S1),
	comprimento(S1, N1),
	N1 = 0
).



%--------------- 3) Identificar instituiçoes prestadoras de serviços -------------------------|
%---------------------------------------------------------------------------------------------|
% Extensão do Predicado identifica_instituiçoes: Lista -> {V,F}
identifica_instituicoes(L) :-
    solucoes((Id, Nome, Cidade), instituicao(Id, Nome, Cidade), L).



%----------- 4) Identificar utentes/serviços/consultas por criterios de seleçao --------------|
%---------------------------------------------------------------------------------------------|


%------- Identificar Utentes -------
% Identificar utentes por critérios de seleção;
% ID
% Nome
% Idade
% Cidade

% Extensão do Predicado idUtenteID: ID, Lista -> {V,F}
idUtenteID(ID, L) :- solucoes((ID, Nome, Idade, Cidade), utente(ID, Nome, Idade, Cidade), L).

% Extensão do Predicado idUtenteNome: Nome, Lista -> {V,F}
idUtenteNome(Nome, L) :- solucoes((ID, Nome, Idade, Cidade), utente(ID, Nome, Idade, Cidade), L).

% Extensão do Predicado idUtenteIdade: Idade, Lista -> {V,F}
idUtenteIdade(Idade, L) :- solucoes((ID, Nome, Idade, Cidade), utente(ID, Nome, Idade, Cidade), L).

% Extensão do Predicado idUtenteCidade: Cidade, Lista -> {V,F}
idUtenteCidade(Cidade, L) :- solucoes((ID, Nome, Idade, Cidade), utente(ID, Nome, Idade, Cidade), L).


%------- Identificar Serviços -------
% Identificar servicos por critérios de seleção;
% ID
% Descricao
% Instituicao
% Cidade

% Extensão do Predicado idUServicoID: ID, Lista -> {V,F}
idServicoID(ID, L) :- solucoes((ID, Descricao, Instituicao, Cidade), 
								servico(ID, Descricao, Instituicao, Cidade), L).

% Extensão do Predicado idServicoDesc: Descricao, Lista -> {V,F}
idServicoDesc(Descricao, L) :- solucoes((ID, Descricao, Instituicao, Cidade), 
										 servico(ID, Descricao, Instituicao, Cidade), L).

% Extensão do Predicado idServicoInst: Instituicao, Lista -> {V,F}
idServicoInst(Instituicao, L) :- solucoes((ID, Descricao, Instituicao, Cidade), 
										   servico(ID, Descricao, Instituicao, Cidade), L).

% Extensão do Predicado idServicoCid: Cidade, Lista -> {V,F}
idServicoCid(Cidade, L) :- solucoes((ID, Descricao, Instituicao, Cidade), 
									 servico(ID, Descricao, Instituicao, Cidade), L).

%------- Identificar Consultas -------
% Identificar consultas por critérios de seleção;
% Data
% idUtente
% IdServico
% IdMedico
% Custo

% Extensão do Predicado idConsultaData: Data, Lista -> {V,F}
idConsultaData(Data, L) :- solucoes((Data, IdUt, IdServ, IdMed, Custo), consulta(Data, IdUt, IdServ, IdMed, Custo), L).

% Extensão do Predicado idConsultaIDUt: IdUt, Lista -> {V,F}
idConsultaIDUt(IdUt, L) :- solucoes((Data, IdUt, IdServ, IdMed, Custo), consulta(Data, IdUt, IdServ, IdMed, Custo), L).

% Extensão do Predicado idConsultaIDServ: IdServ, Lista -> {V,F}
idConsultaIDServ(IdServ, L) :- solucoes((Data, IdUt, IdServ, IdMed, Custo), consulta(Data, IdUt, IdServ, IdMed, Custo), L).

% Extensão do Predicado idConsultaIDMed: IdMed, Lista -> {V,F}
idConsultaIDMed(IdMed, L) :- solucoes((Data, IdUt, IdServ, IdMed, Custo), consulta(Data, IdUt, IdServ, IdMed, Custo), L).

% Extensão do Predicado idConsultaCusto: Custo, Lista -> {V,F}
idConsultaCusto(Custo, L) :- solucoes((Data, IdUt, IdServ, IdMed, Custo), consulta(Data, IdUt, IdServ, IdMed, Custo), L).


%------------ 5) Identificar serviços prestados por instituiçao/cidade/data/custo -------------|
%---------------------------------------------------------------------------------------------|

%------- Servicos por Instituicao -------
% Extensão do Predicado servicosPorInst: IdInstituicao, Lista -> {V,F}

servicosPorInst(IdInst, L) :- solucoes((IdServ, Descricao, Instituicao) , 
								       (servico(IdServ, Descricao, IdInst, Cidade),
										instituicao(IdInst, Instituicao, _)), S).
										

	

%------- Servicos por Cidade -------
% Extensão do Predicado servicosPorCid: Cidade, Lista -> {V,F}

servicosPorCid(Cidade, L) :- solucoes((IdServ, Descricao, Cidade) , 
								       servico(IdServ, Descricao, _, Cidade), S).
									   


%------- Servicos por Data -------
% Extensão do Predicado servicosPorData: Data, Lista -> {V,F}

servicosPorData(Data, L) :- solucoes((IdServ, Descricao, Data) , 
								     (servico(IdServ, Descricao, _, _),
									  consulta(Data, _, IdServ,_, _)), S),
									  semReps(S,L).


%------- Servicos por Custo -------
% Extensão do Predicado servicosPorCusto: Custo, Lista -> {V,F}

servicosPorCusto(Custo, L) :- solucoes((IdServ, Descricao, Custo) , 
								       (servico(IdServ, Descricao, _, _),
										consulta(_, _, IdServ,_, Custo)), S),
										semReps(S,L).



%--------------- 6) Identificar utentes de um serviço/insituiçao -----------------------------|
%---------------------------------------------------------------------------------------------|


%--- Predicado Auxiliar ---
% Devolve a lista dos utentes com as suas informaçoes, dada uma lista com varios IdUtente
listaInfoUtentes([], []).
listaInfoUtentes([IdUt|T], [L|Ls]) :- idUtenteID(IdUt, L),
                                      listaInfoUtentes(T, Ls).

%------- Utentes de um Servico -------

% Extensao do predicado utentesPorInst: IdServico -> Lista {V, F}
% Identifica os utentes de um servico

utentesPorServ(IdServ, L) :- solucoes(IdU, (servico(IdServ, _, _, _),
                                            consulta(Data, IdU, IdServ, IdMed, Custo)), X),
                                            listaInfoUtentes(X,S),
	                                        semReps(S,D),
                                            concListList(D, L).

                                         

%------- Utentes de uma Instituicao -------   

% Extensao do predicado utentesPorInst: IdInstituicao -> Lista {V, F}
% Identifica os utentes de uma instituicao

utentesPorInst(IdInst, L) :- solucoes(IdU, (servico(IdServ, _, IdInst, _),
			                                    instituicao(IdInst, _, _),
                                                consulta(Data, IdU, IdServ, IdMed, Custo)), X),
                                                listaInfoUtentes(X,S),
	                                            semReps(S,D),
                                                concListList(D, L).
                                        


%----------- 7) Identificar serviços realizados por utente/instituicao/cidade/medico ---------|
%---------------------------------------------------------------------------------------------|

%------- Servicos por Instituicao -------
% Extensão do Predicado servicosRealizUt: IdUtente, Lista -> {V,F}

servicosRealizUt(IdUt, L) :- solucoes((Data, IdServ, Descricao, Nome) , 
								       (servico(IdServ, Descricao, _, _),
								       	utente(IdUt, Nome, _, _),
										consulta(Data, IdUt, IdServ, _, _)), L).


% Extensão do Predicado servicosRealizInst: IdInst, Lista -> {V,F}

servicosRealizInst(IdInst, L) :- solucoes((Data, IdServ, Descricao, Instituicao) , 
								         (servico(IdServ, Descricao, IdInst, _),
								          instituicao(IdInst, Instituicao, _),
										  consulta(Data, IdUt, IdServ, _, _)), L).

% Extensão do Predicado servicosRealizCid: Cidade, Lista -> {V,F}

servicosRealizCid(Cidade, L) :- solucoes((Data, IdServ, Descricao, Cidade) , 
								          (servico(IdServ, Descricao, _, Cidade),
										   consulta(Data, IdUt, IdServ, _, _)), L).

% Extensão do Predicado servicosRealizMed: IdMed, Lista -> {V,F}

servicosRealizMed(IdMed, L) :- solucoes((Data, IdServ, Descricao, Nome) , 
								        (servico(IdServ, Descricao, _, Cidade),
								         medico(IdMed, Nome, _, _),	
										 consulta(Data, IdUt, IdServ, IdMed, _)), L).


%---------- 8) Calcular custo total das consultas por utente/serviço/insituiçao/medico/data ---------|
%---------------------------------------------------------------------------------------------|

% Extensão do Predicado custoToal: IdUtente, IdServico, Instituicao, IdMed, Data, Lista -> {V,F}
custoTotal(IdU, IdServ, IdInst, IdMed, Data, C) :-
    solucoes(Custo,
             (servico(IdServ, _, IdInst, _),
              consulta(Data, IdU, IdServ, IdMed, Custo)), 
             R),
    sum(R, C).


%--------------------------- Conhecimento Adicional -------------------------------

% Extensao do predicado instituicao: #Id, Nome, Cidade -> {V, F}	

instituicao(0, 'Hospital de Braga',          'Braga').
instituicao(1, 'Hospital Sao Joao',          'Porto').
instituicao(2, 'Hospital de Santa Maria',    'Porto').
instituicao(3, 'Hospital de Santa Maria',    'Lisboa').
instituicao(4, 'Hospital Santa Maria Maior', 'Barcelos').
instituicao(5, 'Hospital Escala',            'Braga').
instituicao(6, 'Hospital de Faro',           'Faro').
instituicao(7, 'Hospital de Famalicao',      'Famalicao').

% Extensao do predicado medico: #Id, Nome, Idade, #IdInstituicao -> {V, F}	

medico(0, 'Alberto',   35, 3).
medico(1, 'Ana',       28, 2).
medico(2, 'Roberto',   58, 7).
medico(3, 'Josefina',  42, 5).
medico(4, 'Helena',    27, 0).
medico(5, 'Nuno',      37, 1).
medico(6, 'Margarida', 48, 6).


% Invariante Estrutural: Não permitir a insercao de instituicoes repetidas (com o mesmo ID)
% Id inteiros

+instituicao(IdInst, _, _) :: (
	integer(IdInst), 
	solucoes(IdInst, instituicao(IdInst, _, _), S),
    comprimento(S, N),
    N == 1
).


% Invariante Referencial: Nao permitir a remocao de uma instituicao se existirem 
% servicos ou medicos associadas a mesma

-instituicao(IdInst, _, _) :: (
	solucoes(IdInst, servico(_, _, IdInst, _), S1),
	solucoes(IdInst, medico(_, _, _, IdInst), S2),
	comprimento(S1, N1),
	comprimento(S2, N1),
	N1 == 0
).


% Invariante Estrutural: Não permitir a insercao de medicos repetidos (com o mesmo ID)
% ou cuja insituicao nao exista
% Id e Idade devem ser inteiros e idade entre os 0 e os 120

+medico(Id, _, Idade, IdInst) :: (
	integer(Id), integer(Idade),
	Idade >= 0, Idade =< 120,
	solucoes(Id, medico(Id, _, _, _), S1),
	solucoes(IdInst, instituicao(IdInst, _, _), S2),
	comprimento(S1, N1),
	comprimento(S2, N2),
	N1 == 1, N2 == 1
).

% Invariante Referencial: Remover um medico se este existir

-medico(Id, Nome, Idade, IdInst) :: (
	solucoes((Id, Nome, Idade, IdInst), 
			  medico(Id, Nome, Idade, IdInst), S1),
	comprimento(S1, N1),
	N1 = 0
).

%------------------ Extra) Identificar medicos por criterios de seleçao ----------------------|
%Vários predicados extra relacionados com os medicos misturados com os predicados do enunciado|
%---------------------------------------------------------------------------------------------|


%------- Identificar Medicos -------
% Identificar utentes por critérios de seleção;
% ID
% Nome
% Idade
% Instituicao

% Extensão do Predicado idUtenteID: ID, Lista -> {V,F}
idMedicoID(ID, L) :- solucoes((ID, Nome, Idade, IdInst), medico(ID, Nome, Idade, IdInst), L).

% Extensão do Predicado idUtenteNome: Nome, Lista -> {V,F}
idMedicoNome(Nome, L) :- solucoes((ID, Nome, Idade, IdInst), medico(ID, Nome, Idade, IdInst), L).

% Extensão do Predicado idUtenteIdade: Idade, Lista -> {V,F}
idMedicoIdade(Idade, L) :- solucoes((ID, Nome, Idade, IdInst), medico(ID, Nome, Idade, IdInst), L).

% Extensão do Predicado idUtenteCidade: Cidade, Lista -> {V,F}
idMedicoInstituicao(Instituicao, L) :- solucoes((ID, Nome, Idade, IdInst), medico(ID, Nome, Idade, IdInst), L).













