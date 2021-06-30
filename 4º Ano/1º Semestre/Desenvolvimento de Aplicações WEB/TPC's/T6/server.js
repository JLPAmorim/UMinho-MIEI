var http = require('http')
var axios = require('axios')
var static = require('./static')

var {parse} = require('querystring')
const { Console } = require('console')

// Aux. Functions
// Retrieves task info from request body --------------------------------
function recuperaInfo(request, callback){
    if(request.headers['content-type'] == 'application/x-www-form-urlencoded'){
        let body = ''
        request.on('data', bloco => {
            body += bloco.toString()
        })
        request.on('end', ()=>{
            console.log(body)
            callback(parse(body))
        })
    }
}

// Task Confirmation HTML Page Template -------------------------------------


// Tasks List HTML Page Template  -----------------------------------------
function geraPagTasks( tasksIncomp, tasksComp, d){
  let pagHTML = `
    <html>
        <head>
            <title>Task Manager</title>
            <meta charset="utf-8"/>
            <link rel="icon" href="favicon.png"/>
            <link rel="stylesheet" href="w3.css"/>
        </head>
        <body>
            <div class="w3-container w3-light-blue">
                <h2>Create New Task</h2>
            </div>

            <form class="w3-container" action="/tasks" method="POST">

                <label class="w3-text-teal"><b>Task ID</b></label>
                <input class="w3-input w3-border w3-light-grey" type="text" name="id">

                <label class="w3-text-teal"><b>Name</b></label>
                <input class="w3-input w3-border w3-light-grey" type="text" name="name">
        
                <label class="w3-text-teal"><b>Description</b></label>
                <input class="w3-input w3-border w3-light-grey" type="text" name="desc">

                <label class="w3-text-teal"><b>Creation Date</b></label>
                <input class="w3-input w3-border w3-light-grey" type="text" name="cdate">

                <label class="w3-text-teal"><b>Due Date</b></label>
                <input class="w3-input w3-border w3-light-grey" type="text" name="ddate">

                <label class="w3-text-teal"><b>Who's Doing It</b></label>
                <input class="w3-input w3-border w3-light-grey" type="text" name="who">

                <select name="type">
                    <option>Incomplete</option>
                    <option>Complete</option>
                </select>
        
                <input class="w3-btn w3-blue-grey" type="submit" value="Create Task"/>
                <input class="w3-btn w3-blue-grey" type="reset" value="Reset Fields"/> 
            </form>

            <div class="w3-container w3-light-blue">
                <h2>ToDo List - Incomplete Tasks</h2>
            </div>
            <table class="w3-table w3-bordered">
                <tr>
                    <th>Task ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Creation Date</th>
                    <th>Due Date</th>
                    <th>Who's Doing It</th>
                    <th>Type</th>
                </tr>         
  `

  tasksIncomp.forEach( ti => {
    pagHTML += `
        <tr>
            <td>${ti.id}</td>
            <td><a href="/tasks/${ti.id}">${ti.name}</a></td>
            <td>${ti.desc}</td>
            <td>${ti.cdate}</td>
            <td>${ti.ddate}</td>
            <td>${ti.who}</td>
            <td>${ti.type}</td>
            <td><input type="button" onclick="location.href='/tasks/${ti.id}/edit';" value="Edit" /></td>       
        </tr>
        
    `
  })

    pagHTML += ` 
    </table>
    <div class="w3-container w3-light-blue">
        <h2>ToDo List - Completed Tasks</h2>
    </div>
    <table class="w3-table w3-bordered">
        <tr>
            <th>Task ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Creation Date</th>
            <th>Due Date</th>
            <th>Who's Doing It</th>
            <th>Type</th>
        </tr>
`
    tasksComp.forEach( tc => {
    pagHTML += `
        <tr>
            <td>${tc.id}</td>
            <td><a href="/tasks/${tc.id}">${tc.name}</a></td>
            <td>${tc.desc}</td>
            <td>${tc.cdate}</td>
            <td>${tc.ddate}</td>
            <td>${tc.who}</td>
            <td>${tc.type}</td>
            <td><input type="button" onclick="location.href='/tasks/${tc.id}/delete';" value="Delete" /></td>
        </tr>
    `
    })

  pagHTML += `
        </table>
        <div class="w3-container w3-blue-grey">
            <address>Gerado por taskmaker em ${d} --------------</address>
        </div>
    </body>
    </html>
  `
  return pagHTML
}

// Task HTML Page Template -------------------------------------------------------
function geraPagTask( task, d ){
    return `
    <html>
    <head>
        <title>Task: ${task.id}</title>
        <meta charset="utf-8"/>
        <link rel="icon" href="favicon.png"/>
        <link rel="stylesheet" href="w3.css"/>
    </head>
    <body>
        <div class="w3-card-4">
            <header class="w3-container w3-light-blue">
                <h1>Task ${task.id} - ${task.name}</h1>
            </header>

            <div class="w3-container">  
                <ul class="w3-ul w3-card-4" style="width:50%">
                    <li><b>ID: </b> ${task.id}</li>
                    <li><b>Name: </b> ${task.name}</li>
                    <li><b>Description: </b> ${task.desc}</li>
                    <li><b>Creation Date: </b> ${task.cdate}</li>
                    <li><b>Due Date: </b> ${task.ddate}</li>
                    <li><b>Who's Doing It: </b> ${task.who}</li>
                    <li><b>Type: </b> ${task.type}</li> 
                </ul>
            </div>

            <footer class="w3-container w3-blue-grey">
                <address>Gerado por taskmaker em ${d} - [<a href="/">Voltar</a>]</address>
            </footer>
        </div>
    </body>
    </html>
    `
}

// Edit Task Form HTML Page Template ------------------------------------------
function geraEditTask(t, d ){
    return `
    <html>
        <head>
            <title>Edit Task</title>
            <meta charset="utf-8"/>
            <link rel="icon" href="favicon.png"/>
            <link rel="stylesheet" href="w3.css"/>
        </head>
        <body>
        
        </body>
            <div class="w3-container w3-light-blue">
                <h2>Edit Task ${t.id} - ${t.name}</h2>
            </div>

            <form class="w3-container" action="/tasks/edit" method="POST">

                <label class="w3-text-teal"><b>Task ID</b></label>
                <input readonly class="w3-input w3-border w3-light-grey" type="text" name="id" value="${t.id}">

                <label class="w3-text-teal"><b>Name</b></label>
                <input class="w3-input w3-border w3-light-grey" type="text" name="name" value="${t.name}">
          
                <label class="w3-text-teal"><b>Description</b></label>
                <input class="w3-input w3-border w3-light-grey" type="text" name="desc" value="${t.desc}">

                <label class="w3-text-teal"><b>Creation Date</b></label>
                <input class="w3-input w3-border w3-light-grey" type="text" name="cdate" value="${t.cdate}">

                <label class="w3-text-teal"><b>Due Date</b></label>
                <input class="w3-input w3-border w3-light-grey" type="text" name="ddate" value="${t.ddate}">

                <label class="w3-text-teal"><b>Who's Doing It</b></label>
                <input class="w3-input w3-border w3-light-grey" type="text" name="who" value="${t.who}">

                <select name="type" value="${t.type}">
                    <option>Incomplete</option>
                    <option>Complete</option>
                </select>
                
                <input class="w3-btn w3-blue-grey" type="submit" value="Update Task"/>
            </form>

            <footer class="w3-container w3-blue-grey">
                <address>Gerado por taskmanager em ${d}</address>
            </footer>
        </body>
    </html>
    `
}

function geraTaskConfirm(task, d){
    return `
    <html>
    <head>
        <title>Task Confirmation: ${task.id}</title>
        <meta charset="utf-8"/>
        <link rel="icon" href="favicon.png"/>
        <link rel="stylesheet" href="w3.css"/>
    </head>
    <body>
        <div class="w3-card-4">
            <header class="w3-container w3-light-blue">
                <h1>Task ${task.id} Inserted </h1>
            </header>

            <div class="w3-container">
                <p><a href="/tasks/${task.id}">Aceda aqui à sua página.</a></p>
            </div>

            <footer class="w3-container w3-blue-grey">
                <address>Gerado por taskmanager em ${d} - [<a href="/">Voltar</a>]</address>
            </footer>
        </div>
    </body>
    </html>
    `
}

// Edit Task Form HTML Page Template ------------------------------------------
function geraDelTask(t, d ){
    return `
    <html>
        <head>
            <title>Edit Task</title>
            <meta charset="utf-8"/>
            <link rel="icon" href="favicon.png"/>
            <link rel="stylesheet" href="w3.css"/>
        </head>
        <body>
        
        </body>
            <div class="w3-container w3-light-blue">
                <h2>Delete Task ${t.id} - ${t.name}</h2>
            </div>

            <form class="w3-container" action="/tasks/delete" method="POST">

                <label class="w3-text-teal"><b>Task ID</b></label>
                <input readonly class="w3-input w3-border w3-light-grey" type="text" name="id" value="${t.id}">

                <label class="w3-text-teal"><b>Name</b></label>
                <input readonly class="w3-input w3-border w3-light-grey" type="text" name="name" value="${t.name}">
          
                <label class="w3-text-teal"><b>Description</b></label>
                <input readonly class="w3-input w3-border w3-light-grey" type="text" name="desc" value="${t.desc}">

                <h3> To Delete this Task, please press Delete. </h3> 
                
                <input class="w3-btn w3-blue-grey" type="submit" value="Delete Task"/>
            </form>

            <footer class="w3-container w3-blue-grey">
                <address>Gerado por taskmanager em ${d}</address>
            </footer>
        </body>
    </html>
    `
}

function geraDeleteConfirm(task, d){
    return `
    <html>
    <head>
        <title>Task Deletion Confirmation: ${task.id}</title>
        <meta charset="utf-8"/>
        <link rel="icon" href="favicon.png"/>
        <link rel="stylesheet" href="w3.css"/>
    </head>
    <body>
        <div class="w3-card-4">
            <header class="w3-container w3-light-blue">
                <h1>Task Deleted </h1>
            </header>

            <div class="w3-container">
                <p>Task deleted with success.</p>
            </div>

            <footer class="w3-container w3-blue-grey">
                <address>Gerado por taskmanager em ${d} - [<a href="/">Voltar</a>]</address>
            </footer>
        </div>
    </body>
    </html>
    `
}

// Server setup

var toDoServer = http.createServer(function (req, res) {
    // Logger: que pedido chegou e quando
    var d = new Date().toISOString().substr(0, 16)
    console.log(req.method + " " + req.url + " " + d)

    // Request processing
    // Tests if a static resource is requested
    if(static.recursoEstatico(req)){
        static.sirvoRecursoEstatico(req, res)
    }
    else{
    // Normal request
    switch(req.method){
        case "GET": 
            // GET /tasks --------------------------------------------------------------------
            if((req.url == "/") || (req.url == "/tasks")){
                axios.get("http://localhost:3000/tasks?_sort=id")
                    .then(response => {
                        var tasks = response.data
                        var tasksComp = []
                        var tasksIncomp = []
                        var j = 0
                        var k = 0

                        tasks.forEach(t => {
                            if(t.type == "Incomplete"){
                                tasksIncomp[j] = t  
                                j += 1
                            }else{
                                tasksComp[k] = t 
                                k += 1
                            }
                        })
                        
                        res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                        res.write(geraPagTasks(tasksIncomp, tasksComp, d))
                        res.end()
                    })
                    .catch(function(erro){
                        res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                        res.write("<p>Task List not available...")
                        res.end()
                    })
            }
            // GET /tasks/:id --------------------------------------------------------------------
            else if(/\/tasks\/[1-9][0-9]*$/.test(req.url)){
                var idTask = req.url.split("/")[2]
                axios.get("http://localhost:3000/tasks/" + idTask)
                    .then( response => {
                        let t = response.data
                        
                        res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                        res.write(geraPagTask(t, d))
                        res.end()
                    })
            }
            // GET /tasks/:id/edit --------------------------------------------------------------------
            else if(/\/tasks\/[1-9][0-9]*\/edit$/.test(req.url)){
                var idTask = req.url.split("/")[2]
                axios.get("http://localhost:3000/tasks/" + idTask)
                    .then( response => {
                        let t = response.data
                        res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                        res.write(geraEditTask(t, d))
                        res.end()
                    })
            }else if(/\/tasks\/[1-9][0-9]*\/delete$/.test(req.url)){
                var idTask = req.url.split("/")[2]
                axios.get("http://localhost:3000/tasks/" + idTask)
                    .then( response => {
                        let t = response.data
                        res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                        res.write(geraDelTask(t, d))
                        res.end()
                    })
            }
            else{
                res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                res.write("<p>" + req.method + " " + req.url + " não suportado neste serviço.</p>")
                res.end()
            }
            break
        case "POST":
            if(req.url == '/tasks'){
                recuperaInfo(req, resultado => {
                    console.log('POST task:' + JSON.stringify(resultado))
                    axios.post('http://localhost:3000/tasks', resultado)
                        .then(resp => {
                            res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                            res.write(geraTaskConfirm( resp.data, d))
                            res.end()
                        })
                        .catch(erro => {
                            res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                            res.write('<p>Erro no POST: ' + erro + '</p>')
                            res.write('<p><a href="/">Voltar</a></p>')
                            res.end()
                        })
                })
            } else if(req.url == '/tasks/edit'){
                recuperaInfo(req, resultado => {
                    console.log('PUT task:' + JSON.stringify(resultado))
                    axios.put('http://localhost:3000/tasks/' + resultado.id, resultado)
                        .then(resp => {
                            res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                            res.write(geraTaskConfirm( resp.data, d))
                            res.end()
                        })
                        .catch(erro => {
                            res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                            res.write('<p>Erro no PUT: ' + erro + '</p>')
                            res.write('<p><a href="/">Voltar</a></p>')
                            res.end()
                        })
                })
            } else if(req.url == '/tasks/delete'){
                recuperaInfo(req, resultado => {
                    console.log('DELETE task:' + JSON.stringify(resultado))
                    axios.delete('http://localhost:3000/tasks/' + resultado.id, resultado)
                        .then(resp => {
                            res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                            res.write(geraDeleteConfirm( resp.data, d))
                            res.end()
                        })
                        .catch(erro => {
                            res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                            res.write('<p>Erro no PUT: ' + erro + '</p>')
                            res.write('<p><a href="/">Voltar</a></p>')
                            res.end()
                        })
                })
            }else{
                res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
                res.write('<p>Recebi um POST não suportado.</p>')
                res.write('<p><a href="/">Voltar</a></p>')
                res.end()
            }
            break
        default: 
            res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'})
            res.write("<p>" + req.method + " não suportado neste serviço.</p>")
            res.end()
    }
    }
})

toDoServer.listen(7777)
console.log('Servidor à escuta na porta 7777...')