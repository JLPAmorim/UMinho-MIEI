var http = require('http')
var axios = require('axios')

http.createServer(function (req, res) {
    console.log(req.method + ' ' + req.url)
    if(req.method == 'GET'){
        if(req.url == '/'){
            res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
            res.write('<h2>Escola de Musica</h2>')
            res.write('<ul>')
            res.write('<li><a href="/alunos">Lista de Alunos</a></li>')
            res.write('<li><a href="/cursos">Lista de Cursos</a></li>')
            res.write('<li><a href="/instrumentos">Lista de Instrumentos</a></li>')
            res.write('</ul>')
            res.end()
        }else if(req.url == '/alunos'){
            axios.get('http://localhost:3001/alunos')
            .then(function (resp) {
                alunos = resp.data;
                res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                res.write('<h2>Escola de Musica: Lista de Alunos</h2>')
                res.write('<ul>')

                alunos.forEach(a => {
                    res.write('<li> <a href=\"/alunos/' + a.id + '\">' + a.id + ' - ' + a.nome + '</a></li>')      
                });

                             
                res.write('</ul>')
                res.write('<address>[<a href="/">Voltar</a>]</address>')
                res.end()
            })
            .catch(function (error)  {
                console.log('Erro na obtencao da lista de alunos: ' + error);
            }); 

        }else if(req.url.match(/alunos\/A[1-9a-zA-Z\_\-]+/)){
            var arr = req.url.split("/")
            idAluno = arr[2]
            console.log(idAluno)
            axios.get('http://localhost:3001/alunos/'+idAluno)
            .then(function (resp) {
                aluno = resp.data;
                res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                res.write('<h2>Escola de Musica: Aluno nº ' + aluno.id +'</h2>')
                res.write('<ul>')
                res.write('<li> Id: ' + aluno.id + '</li>')
                res.write('<li> Nome: ' + aluno.nome + '</li>')
                res.write('<li> Data de Nascimento: ' + aluno.dataNasc + '</li>')
                res.write('<li> Curso: ' + aluno.curso + '</li>')
                res.write('<li> Ano do Curso: ' + aluno.anoCurso + '</li>')
                res.write('<li> Instrumento: ' + aluno.instrumento + '</li>')
                res.write('</ul>')
                res.write('<address>[<a href="/alunos">Voltar</a>]</address>')
                res.end()
            })
            .catch(function (error)  {
                console.log('Erro na obtencao do aluno: ' + error);
            }); 

        } else if(req.url == '/cursos'){
            axios.get('http://localhost:3001/cursos?_sort=id')
            .then(function (resp) {
                cursos = resp.data;
                res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                res.write('<h2>Escola de Musica: Lista de Cursos</h2>')
                res.write('<ul>')
            
                cursos.forEach(c => {
                    res.write('<li> <a href=\"/cursos/' + c.id + '\">' + c.id + ' - ' + c.designacao + '</li>')
                });

                res.write('</ul>')
                res.write('<address>[<a href="/">Voltar</a>]</address>')
                res.end()
            })
            .catch(function (error)  {
                console.log('Erro na obtencao da lista de cursos: ' + error);
            }); 
        
        }else if(req.url.match(/cursos\/C[S,B]\d+/)){
            var arr = req.url.split("/")
            idCurso = arr[2]
            console.log(idCurso)
            axios.get('http://localhost:3001/cursos/'+idCurso)
            .then(function (resp) {
                curso = resp.data;
                res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                res.write('<h2>Escola de Musica: Curso nº ' + curso.id +'</h2>')
                res.write('<ul>')
                res.write('<li> Id: ' + curso.id + '</li>')
                res.write('<li> Designacao: ' + curso.designacao + '</li>')
                res.write('<li> Duracao: ' + curso.duracao + '</li>')
                res.write('<li> Instrumento: ' + curso.instrumento['#text'] + '</li>')
                res.write('</ul>')
                res.write('<address>[<a href="/cursos">Voltar</a>]</address>')
                res.end()
            })
            .catch(function (error)  {
                console.log('Erro na obtencao do curso: ' + error);
            }); 

        }else if(req.url == '/instrumentos'){
            axios.get('http://localhost:3001/instrumentos')
            .then(function (resp) {
                inst = resp.data;
                res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                res.write('<h2>Escola de Musica: Lista de Instrumentos</h2>')
                res.write('<ul>')
            
                inst.forEach(i => {
                    
                    res.write('<li> <a href=\"/instrumentos/' + i.id + '\">'+ i.id + ' - ' + i['#text'] + '</li>')
                });

                res.write('</ul>')
                res.write('<address>[<a href="/">Voltar</a>]</address>')
                res.end()
            })
            .catch(function (error)  {
                console.log('Erro na obtencao da lista de cursos: ' + error);
            }); 

        }else if(req.url.match(/instrumentos\/[I,X]\d+/)){
            var arr = req.url.split("/")
            idInstrum = arr[2]
            console.log(idInstrum)
            axios.get('http://localhost:3001/instrumentos/'+idInstrum)
            .then(function (resp) {
                instrumento = resp.data;
                res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                res.write('<h2>Escola de Musica: Instrumento nº ' + instrumento.id +'</h2>')
                res.write('<ul>')
                res.write('<li> Id: ' + instrumento.id + '</li>')
                res.write('<li> Instrumento: ' + instrumento['#text'] + '</li>')
                res.write('</ul>')
                res.write('<address>[<a href="/instrumentos">Voltar</a>]</address>')
                res.end()
            })
            .catch(function (error)  {
                console.log('Erro na obtencao do instrumento: ' + error);
            }); 

        }else{
            res.writeHead(200, {'Content-Type': 'text/html'})
            res.write("<p>Pedido nao suportado: " + req.method + " " + req.url + "</p>")
            res.end() 
        }
    }
    else{
        res.writeHead(200, {'Content-Type': 'text/html'})
        res.write("<p>Pedido nao suportado: " + req.method + " " + req.url + "</p>")
        res.end()
    }
    
}).listen(4000)

console.log('Servidor a escuta na porta 4000...')