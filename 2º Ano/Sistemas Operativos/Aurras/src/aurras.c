#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>

#define FIFO_FILE_1  "../tmp/fifo"

int main(int argc, char *argv[]){

	int client_to_server; 
	if((client_to_server = open(FIFO_FILE_1, O_WRONLY))<0){
		perror("open fifo");
		exit(1);
	}
	
	int strsize = 0;
	
	if(argc < 2){
		write(1, "./aurras status\n", 16);
		write(1, "./aurras transform input-filename output-filename filter-id-1 filter-id-2 ...\n", 79);
		return 1;
	}else if(argc >= 2 && strcmp(argv[1],"status")==0){

		pid_t pid = getpid();
		char message[14];

		sprintf(message,"status %d\n",pid);

		write(client_to_server,message,strlen(message));
		
	}else if(argc >= 2 && strcmp(argv[1],"transform")==0){
		for (int i=1; i<argc; i++) {
        	strsize += strlen(argv[i]);
        	if (argc > i+1)
            	strsize++;
    	}
		char *cmdstring;
    	cmdstring = malloc(strsize+1);
    	cmdstring[0] = '\0';

    	for (int i=1; i<argc; i++) {
        	strcat(cmdstring, argv[i]);
        	if (argc > i+1)
            	strcat(cmdstring, " ");
    	}

		cmdstring[strsize]='\n';

		write(client_to_server,cmdstring,strsize+1);
	}

	pid_t pid = getpid();
	char pipe_name[17];
	sprintf(pipe_name,"../tmp/fifo%d",pid);

	if(mkfifo(pipe_name, 0666) < 0){
        perror("mkfifo");
    }

    int server_to_client;
	if((server_to_client = open(pipe_name, O_RDONLY))<0){
		perror("pipe");
		exit(1);
	}

	int bytes_read = 0;
	char buf[1024]; 

	while(1){
		while((bytes_read = read(server_to_client, buf, 1024)) > 0){
			write(1,buf,bytes_read);
			
		}
		exit(0);
	}

	close(client_to_server);
    close(server_to_client);
    unlink(FIFO_FILE_1);
    unlink(pipe_name);

	return 0;
}