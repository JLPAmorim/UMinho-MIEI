// Roteador do servidor API
var express = require('express');
var router = express.Router();
const bcrypt = require('bcrypt')
const Bet = require('../controllers/betController')
const Event = require('../controllers/eventController')
const User = require('../controllers/userController')
const UserModel = require('../models/user')
const EventModel = require('../models/event')
const BetModel = require('../models/bet')
const jwt = require('jsonwebtoken')

// -------------------------- User ------------------------------

// GET em Utilizador
router.get('/user', (req, res, next) => {
  let token = req.headers.token;
  jwt.verify(token, 'secretkey', (error, decoded) =>{
    if (error) return res.status(401).json({
      title: 'Unauthorized'
    })
    UserModel.findOne({ _id: decoded.userId }, (error, user) => {
      if (error) return console.log(error)
      return res.status(200).json({
        title: 'User Grabbed',
        user:{
          _id: user._id,
          name: user.name,
          email: user.email,
          balance: user.balance,
          currentCoin: user.currentCoin,
          balance_history: user.balance_history,
          user_type: user.user_type,
          notificacoes: user.notificacoes
        }
      })
    })
  })
});

// GET em user para obter todos os utilizadores
router.get('/allUsers', (req, res, next) => {
    let user_type = req.headers.user_type;
    UserModel.find({ user_type: user_type }, (error, user) => {
      if (error) return console.log(error)
      return res.status(200).json({
        title: 'Users Grabbed',
        users: user
      })
    })
});



// Registar um user
router.post('/user', function(req, res){
  const newUser = {
    name: req.body.name,
    email: req.body.email,
    password: bcrypt.hashSync(req.body.password, 10),
    user_type: 'User',
    balance: {
      EUR: '0.00',
      USD: '0.00',
      GBP: '0.00',
      ADA: '0.00'
    },
    currentCoin: 'EUR',
    balance_history: [],
    notificacoes: ''
  }
  User.inserir(newUser)
    .then(dados => res.status(201).jsonp({dados: dados}))
    .catch(e => res.status(500).jsonp({error: 'Email in use'}))
})

// Efetuar Login de um user
router.post('/login', (req,res, next) =>{
  UserModel.findOne({ email: req.body.email }, (err, user) =>{
    if(err) return res.status(500).json({
      title: 'Server error',
      error: err
    })
    if(!user) {
      return res.status(401).json({
        title: 'User not found',
        error: 'Invalid Credentials'
      })
    }
    if(!bcrypt.compareSync(req.body.password, user.password)){
      return res.status(401).json({
        title: 'Login failed',
        error: 'Invalid Credentials'
      })
    }
    let token = jwt.sign({userEmail: user.email}, 'secretkey');
    return res.status(201).json({
      title: 'Login successful',
      token: token
    })
  })
})

//PUT para User
router.put('/user', function(req, res){
  UserModel.findOne({ _id: req.body._id }, (err, user) =>{
    if(req.body.password!=null){
      console.log('A mudar password')
      if(bcrypt.compareSync(req.body.oldPassword, user.password)){
        console.log('Password igual à antiga')
        const editUser={
          _id: req.body._id,
          password: bcrypt.hashSync(req.body.password, 10)
        }
        console.log(editUser)
        User.alterar(editUser)
        .then(dados => res.status(201).jsonp({dados: dados}))
        .catch(e => res.status(500).jsonp({error: e}))
      }
    }else{
      console.log(req.body)
      User.alterar(req.body)
        .then(dados => res.status(201).jsonp({dados: dados}))
        .catch(e => res.status(500).jsonp({error: e}))
    }
  })
})



router.put('/usersEditados', function(req, res){
  User.updateMany(req.body)
    .then(promiseArray => res.status(201).jsonp({dados: promiseArray}))
    .catch(e => res.status(500).jsonp({error: e}))
})

// -------------------------- Evento ------------------------------
//GET para Eventos
router.get('/evento', function(req, res) {
  let sportType = req.headers.sporttype
  EventModel.find({ sport: sportType}, (error, event) => {
    if (error) return console.log(error)
    return res.status(200).json({
      title: 'Events by Sport Grabbed',
      events: event
    })
  })
});

//POST para Eventos
router.post('/evento', function(req, res){
  var today = new Date();
  var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
  var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
  var dateTime = date+' '+time; 
  const newEvento = {
    sport: req.body.sport,
    team1: req.body.team1,
    team2: req.body.team2,
    result_odd: {
      home: req.body.home,
      tie: req.body.tie || '',
      away: req.body.away
    },
    date: dateTime,
    result1: '0',
    result2: '0',
    winner:'',
    state: req.body.state
  }
Event.inserir(newEvento)
  .then(dados => res.status(201).jsonp({dados: dados}))
  .catch(e => res.status(500).jsonp({error: 'erro'}))
})

//PUT para Eventos
router.put('/evento', function(req, res){
  console.log(req.body)
  Event.alterar(req.body)
    .then(dados => res.status(201).jsonp({dados: dados}))
    .catch(e => res.status(500).jsonp({error: e}))
})

// -------------------------- Bet ------------------------------

// GET em Bet
router.get('/bet', (req, res, next) => {
  let token = req.headers.token;
  jwt.verify(token, 'secretkey', (error, decoded) =>{
    if (error) return res.status(401).json({
      title: 'Unauthorized'
    })
    BetModel.find({ user_id: decoded.userId }, (error, bet) => {
      if (error) return console.log(error)
      return res.status(200).json({
        title: 'Bets Grabbed',
        bets: bet
      })
    })
  })
});

// GET em allBets para devolver apostas em aberto
router.get('/allBets', (req, res, next) => {
  let state = req.headers.state;
  
  BetModel.find({ state: state }, (error, bet) => {
    if (error) return console.log(error)
    return res.status(200).json({
      title: 'Bets Grabbed',
      bets: bet
    })
  })
});


router.post('/bet', function(req, res){
  Bet.inserir(req.body)
    .then(dados => res.status(201).jsonp({dados: dados}))
    .catch(e => res.status(500).jsonp({error: 'erro'}))
})

/*router.put('/betsEditadas', function(req, res){
  req.body.forEach((obj) =>{
    Bet.alterar(obj)
    .then(dados => res.status(201).jsonp({dados: dados}))
    .catch(e => res.status(500).jsonp({error: e}))
  })
})*/

router.put('/betsEditadas', function(req, res){
  console.log(res.body)
  Bet.updateMany(req.body)
    .then(promiseArray => res.status(201).jsonp({dados: promiseArray}))
    .catch(e => res.status(500).jsonp({error: e}))
})


module.exports = router;