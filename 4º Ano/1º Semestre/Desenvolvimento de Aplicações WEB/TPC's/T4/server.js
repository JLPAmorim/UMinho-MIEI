var http = require('http')
var fs = require('fs')

http.createServer(function (req, res){
    var arr = req.url.split("/")
    var num
    var str
    if(arr.length==3){
        num = arr[arr.length-1]
        str = arr[arr.length-2]

        fs.readFile('./arq/arq'+num+'.html' , function(err, data) {
            res.writeHead(200, {'Content-Type' : 'text/html'})
            res.write(data)
            res.end()
        })
    }else if(arr.length==2){
        num = ""
        str = arr[arr.length-1]
        if(str == 'arq' && num == ""){
            fs.readFile('./arq/arq.html' , function(err, data) {
                res.writeHead(200, {'Content-Type' : 'text/html'})
                res.write(data)
                res.end()
            })
        }
    }       
    console.log(arr)
    console.log(arr.length)
    console.log(num)
    console.log(str)  
    
}).listen(7777);
console.log('Servidor à escuta na porta 7777...')