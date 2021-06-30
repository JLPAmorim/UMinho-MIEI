#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#define MAX_FILTERS 5
#define MAX_WAIT_TIME_FOR_FILTER 15
#define MAX_RUNNING_FILTERS 200
#define FIFO_FILE_1  "../tmp/fifo"

typedef struct running_filter{
    char name[7];
    pid_t pid;
} RunningFilter;

typedef struct filter{
    char name[7];
    int max_running;
    int running;
} Filter;


Filter f1, f2, f3, f4, f5;

Filter allFilters[MAX_FILTERS];

RunningFilter runningFilters[MAX_RUNNING_FILTERS];

/* Faz o parsing de um string, dividindo 
* a mesma em strings de cada vez que encontra 
* um espaço. Devolve o array de strings obtido
*/
char ** parseString(char* command){
    
    char ** exec_args = (char**)malloc(sizeof(char*)*40);

    int i=0;
    
    char *string = strtok(command," ");   
    while(string != NULL){    
        exec_args[i] = strdup(string); 
        string = strtok(NULL," ");
        i++;            
    }

    exec_args[i] = NULL;

    return exec_args;
}

/* Devolve uma token correspondente aos primeiros caracteres
* até encontrar um espaço 
*/
char * first_arg(char * command){
    
    char * token = strtok(command, " ");

    return token;
}

/* Função auxiliar para o readln */
int readch(int fd, char *buf){
    return read(fd, buf, 1);
}

/* Função para ler uma linha
* Devolve o número de caracteres lidos
*/
ssize_t readln(int fd, char *line, size_t size){
    int next_pos = 0;

    while(next_pos < size && readch(fd, line + next_pos)>0){
        if(line[next_pos++] == '\n')
            break;
    }

    return next_pos;
}

/* Função que faz a leitura do ficheiro de configuração
* Recebe como argumento o nome do ficheiro de configuração
* e atribui os valores necessários para cada filtro, adicionando
* esse filtro ao array allFilters.
 */
void readConfig(char * arg){
    int fd = open(arg, O_RDONLY);
    char bufConfig[1000];
    int line_bytes = 0; 

    while((line_bytes = readln(fd,bufConfig, 50))>0){
        char ** line_fields = parseString(bufConfig);

        if(strcmp("alto",line_fields[0])==0){
            strcpy(f1.name, line_fields[0]);
            f1.max_running = atoi(line_fields[2]);
            f1.running = 0;
            allFilters[0] = f1;
        }
        if(strcmp("baixo",line_fields[0])==0){
            strcpy(f2.name, line_fields[0]);
            f2.max_running = atoi(line_fields[2]);
            f2.running = 0;
            allFilters[1] = f2;
        }
        if(strcmp("eco",line_fields[0])==0){
            strcpy(f3.name, line_fields[0]);
            f3.max_running = atoi(line_fields[2]);
            f3.running = 0;
            allFilters[2] = f3;
        }
        if(strcmp("rapido",line_fields[0])==0){
            strcpy(f4.name, line_fields[0]);
            f4.max_running = atoi(line_fields[2]);
            f4.running = 0;
            allFilters[3] = f4;
        }
        if(strcmp("lento",line_fields[0])==0){
            strcpy(f5.name, line_fields[0]);
            f5.max_running = atoi(line_fields[2]);
            f5.running = 0;
            allFilters[4] = f5;
        }  
    }
}


/* Procura a posição de determinado filtro 
* no array allFilters.
* Devolve a posição obtida ou -1 caso não encontre o filtro
*/
int findFilter(char * filter){
    for(int k=0;k<MAX_FILTERS; k++)
        if (strcmp(filter, allFilters[k].name)==0)
            return k;
    return -1;
}

/* Procura uma poisção no array runningFilters 
* que contenha uma posição livre ou seja, que contenha o valor -1.
* Devolve a posição obtida ou -1 caso não encontre nenhuma posição
*/
int findSlotInRunningFilters(){
    for(int i=0;i<MAX_RUNNING_FILTERS;i++){
        if (runningFilters[i].pid==-1)
            return i;
    }
    return -1;
}

/* Função que faz o reap dos processos terminados.
* Se o processo tiver terminado, decrementa o número de filtros
* a correr desse tipo de filtro e volta a atribuir o valor de -1
* à posição que continha o pid do processo que terminou.
*/
void reapRunningTasks(){
    for(int i=0;i<MAX_RUNNING_FILTERS;i++){
        if (runningFilters[i].pid!=-1){

            pid_t pid = waitpid(runningFilters[i].pid, NULL, WNOHANG);

            if (pid>0){
                int f = findFilter(runningFilters[i].name);
                printf("Reap: %d %s\n", i, runningFilters[i].name);
                allFilters[f].running--;
                runningFilters[i].pid=-1;
            }
        }
    }
}
/* Trata do processamento de um pedido de um Ciente*/
void processTask(char **exec_args){

    int fd1 = open(exec_args[1],O_RDONLY, 0644);
    int fd2 = open(exec_args[2],O_CREAT|O_TRUNC|O_WRONLY, 0644);

    char paths[20][37];
    char executables[20][21];
    char filters[20][7];
    int p = 0;
    

    dup2(fd1,0);
    close(fd1);

    int k = 3;
    int numFilters = 0;
    int numPipes = -1;

    while(exec_args[k]!=NULL){ 
        
        strcpy(filters[p],exec_args[k]);

        if(strcmp(exec_args[k],"alto")==0){
            strcpy(paths[p],"aurrasd-filters/aurrasd-gain-double");
            strcpy(executables[p],"aurrasd-gain-double");
        }
        if(strcmp(exec_args[k],"baixo")==0){
            strcpy(paths[p],"aurrasd-filters/aurrasd-gain-half");
            strcpy(executables[p],"aurrasd-gain-half");
        }
        if(strcmp(exec_args[k],"eco")==0){
            strcpy(paths[p],"aurrasd-filters/aurrasd-echo");
            strcpy(executables[p],"aurrasd-echo");
        }
        if(strcmp(exec_args[k],"rapido")==0){
            strcpy(paths[p],"aurrasd-filters/aurrasd-tempo-double");
            strcpy(executables[p] ,"aurrasd-tempo-double");
        }
        if(strcmp(exec_args[k],"lento")==0){
            strcpy(paths[p], "aurrasd-filters/aurrasd-tempo-half");
            strcpy(executables[p], "aurrasd-tempo-half");
        }
        p++;
        k++;
        numFilters++;
        numPipes++;

    }


    for(int i=0;i<numFilters;i++){//percorrer todos os filtros necessarios
        int f = findFilter(filters[i]);
        int maxWait=MAX_WAIT_TIME_FOR_FILTER;
        while(maxWait>0 && allFilters[f].running==allFilters[f].max_running){
            printf("WAITING for %s %d\n",filters[i],maxWait);
            reapRunningTasks();
            sleep(1);   
            maxWait--;     
        }
        if (maxWait==0){

            for(int j=0;j<i;j++){
                allFilters[findFilter(filters[j])].running--;
            }
            break;
        }

        allFilters[f].running++;
    }


    int pipe_fd[numPipes][2];

    //Criar pipe[0]
    if(pipe(pipe_fd[0])<0){
        perror("pipe");
        exit(1);
    }

    pid_t pid;
    //Primeiro Filtro
    if((pid=fork())==0){
        
        if(numPipes>0){
            close(pipe_fd[0][0]);

            dup2(pipe_fd[0][1],1);
            close(pipe_fd[0][1]);

            execl(paths[0],executables[0],NULL);
            _exit(1);
        }else{
            
            dup2(fd2,1);
            close(fd2);

            execl(paths[0],executables[0],NULL);
            _exit(1);
        }
    }

    //guardar o pid deste filtro
    int slot = findSlotInRunningFilters();
    strcpy(runningFilters[slot].name, filters[0]);
    runningFilters[slot].pid=pid;

    if(numPipes>0){
        close(pipe_fd[0][1]);
    }
    
    //Filtros Intermédios
    for(int i = 1; i<numPipes; i++){
        //Criar Pipe
        if(pipe(pipe_fd[i])<0){
            perror("pipe");
            exit(1);
        }

        //Executar Filtro
        if((pid=fork())==0){

            close(pipe_fd[i][0]);

            dup2(pipe_fd[i-1][0],0);
            close(pipe_fd[i-1][0]);

            dup2(pipe_fd[i][1],1);
            close(pipe_fd[i][1]);

            execl(paths[i],executables[i],NULL);
            _exit(1);
        }
        close(pipe_fd[i-1][0]);
        close(pipe_fd[i][1]);

        slot = findSlotInRunningFilters();
        strcpy(runningFilters[slot].name, filters[i]);
        runningFilters[slot].pid=pid;

    }

    //Último Filtro
    if(numPipes>0){
        if((pid=fork())==0){
            dup2(pipe_fd[numPipes-1][0],0);
            close(pipe_fd[numPipes-1][0]);

            dup2(fd2,1);
            close(fd2);

            execl(paths[numPipes],executables[numPipes],NULL);
            _exit(1);
        }

        slot = findSlotInRunningFilters();
        strcpy(runningFilters[slot].name, filters[numFilters-1]);
        runningFilters[slot].pid=pid;
    }
    
}

int readCommand(int fd, char * buffer){

    int i=0;
    while(read(fd, &buffer[i], 1)){
        if (buffer[i]=='\n'){
            break;
        }
        i++;
    }
    buffer[i]='\0';
    return i;
}

void sigchld_handler(int signum){
    reapRunningTasks();
}


int main(int argc, char *argv[]){
    
    readConfig(argv[1]);
    
    write(1, "[Server] Server waiting for requests...\n",41);

    for(int i=0;i<MAX_RUNNING_FILTERS;i++)
        runningFilters[i].pid=-1;

    // Create named pipe
    if(mkfifo(FIFO_FILE_1, 0666) < 0){
        perror("mkfifo");
    }

    int client_to_server; 
    
    if((client_to_server = open(FIFO_FILE_1, O_RDONLY))<0){
        perror("open fifo");
        exit(1);
    }
    
    signal(SIGCHLD,sigchld_handler); // Register signal h

    // Ler ficheiro de configuração

    
	char buf[1024];  
    char bufaux[1024]; 

    while(1){
        int n = readCommand(client_to_server, buf);

        if (n<=0)
            continue;

        printf("Cmd: %s\n",buf);
        
        reapRunningTasks();

        strcpy(bufaux,buf);

        char * token = first_arg(bufaux);
        //Verificar se comando é transform ou status
        if(strcmp(token,"transform")==0){
            char ** exec_args = parseString(buf);

            processTask(exec_args);
            write(1,"Inicio do Processamento do Pedido:\n",36);
            
            
        }else if (strcmp(token,"status")==0){
            char ** exec_args = parseString(buf);

            char pipe_name[30];

            sprintf(pipe_name,"../tmp/fifo%s",exec_args[1]);

            int server_to_client;
            usleep(100);
            if((server_to_client = open(pipe_name, O_WRONLY))<0){
                perror("Erro no pipe server->cliente");
                exit(1);
            }

            //Enviar informações dos filtros
            for(int i = 0; i<5; i++){
                char sendFilter[100];
                sprintf(sendFilter,"filter %s: %d/%d (running/max)\n",allFilters[i].name, allFilters[i].running,allFilters[i].max_running);
                write(server_to_client,sendFilter,strlen(sendFilter));
            }
            //Enviar PID do Cliente
            char lastMessage[22];
            sprintf(lastMessage,"Pid do Cliente: %s\n",exec_args[1]);
            write(server_to_client,lastMessage,strlen(lastMessage));
            
            close(server_to_client);
            unlink(pipe_name);
        }
    } 

    close(client_to_server);
    
    unlink(FIFO_FILE_1);
    
	
	return 0;
}