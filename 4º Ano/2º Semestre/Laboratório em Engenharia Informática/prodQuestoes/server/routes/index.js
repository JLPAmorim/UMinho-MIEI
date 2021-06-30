// Roteador do servidor API para o problema da gestão de tarefas
var express = require('express');
var router = express.Router();
const Question = require('../controllers/questController')
const Domain = require('../controllers/domainController')

// Listar todas as questões
router.get('/question', function(req, res) {
  Question.listar()
    .then(dados => res.status(200).jsonp(dados) )
    .catch(e => res.status(500).jsonp({error: e}))
});

// Listar todas os dominios
router.get('/domain', function(req, res) {
  Domain.listar()
    .then(dados => res.status(200).jsonp(dados) )
    .catch(e => res.status(500).jsonp({error: e}))
});

// Consultar uma questao por id
router.get('/question/:id', function(req, res) {
  Question.consultar(req.params.id)
    .then(dados => res.status(200).jsonp(dados))
    .catch(e => res.status(500).jsonp({error: e}))
});

// Consultar um dominio por id
router.get('/domain/:id', function(req, res) {
  Domain.consultar(req.params.id)
    .then(dados => res.status(200).jsonp(dados))
    .catch(e => res.status(500).jsonp({error: e}))
});

// Inserir uma questao
router.post('/question', function(req, res){
  Question.inserir(req.body)
    .then(dados => res.status(201).jsonp({dados: dados}))
    .catch(e => res.status(500).jsonp({error: e}))
})

// Inserir um dominio
router.post('/domain', function(req, res){
  Domain.inserir(req.body)
    .then(dados => res.status(201).jsonp({dados: dados}))
    .catch(e => res.status(500).jsonp({error: e}))
})

// Alterar uma questao
router.put('/question', function(req, res){
  Question.alterar(req.body)
    .then(dados => res.status(201).jsonp({dados: dados}))
    .catch(e => res.status(500).jsonp({error: e}))
})

// Alterar um dominio
router.put('/domain', function(req, res){
  Domain.alterar(req.body)
    .then(dados => res.status(201).jsonp({dados: dados}))
    .catch(e => res.status(500).jsonp({error: e}))
})

// Remover uma questao
router.delete('/question/:id', function(req, res) {
  Question.remover(req.params.id)
    .then(dados => res.status(200).jsonp(dados))
    .catch(e => res.status(500).jsonp({error: e}))
});

// Remover um dominio
router.delete('/domain/:id', function(req, res) {
  Domain.remover(req.params.id)
    .then(dados => res.status(200).jsonp(dados))
    .catch(e => res.status(500).jsonp({error: e}))
});

module.exports = router;