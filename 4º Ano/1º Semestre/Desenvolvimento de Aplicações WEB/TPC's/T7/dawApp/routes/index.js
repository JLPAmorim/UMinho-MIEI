var express = require('express');
var router = express.Router();

var Student = require('../controllers/student')

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/students', function(req, res) {
  
  Student.list()
    .then(data => res.render('students', { list: data }))
    .catch(err => res.render('error', {error: err}))
  ;
});

// GET /students/register
router.get('/students/register', function(req, res) {
  res.render('regStudent')
});

// GET /students/:id
router.get('/students/:id', function(req, res,next) {
  
  Student.lookUp(req.params.id)
    .then(data => res.render('studentInfo', { aluno : data }))
    .catch(err => res.render('error', {error: err}))
  ;
});

// GET /students/edit/:id
router.get('/students/edit/:id', function(req, res,next) {
  
  Student.lookUp(req.params.id)
    .then(data => res.render('editStudent', { aluno : data }))
    .catch(err => res.render('error', {error: err}))
  ;
});

// GET /students/delete/:id
router.get('/students/delete/:id', function(req, res,next) {
  
  console.log(req.params.id)
  Student.delete(req.params.id)
    .then(() => res.render('index'))
    .catch(err => res.render('error', {error: err}))
  
});

// POST /students/register Registo
router.post('/students', function(req, res) {
   
  let num = req.body.numero;
  let nome = req.body.nome;
  let git = req.body.git;

  var aluno = new Object();
  aluno.numero = num
  aluno.nome = nome
  aluno.git = git

  let tpc1 = req.body.tpc1, tpc2 = req.body.tpc2,
      tpc3 = req.body.tpc3, tpc4 = req.body.tpc4,
      tpc5 = req.body.tpc5, tpc6 = req.body.tpc6,
      tpc7 = req.body.tpc7, tpc8 = req.body.tpc8

  let tpcs = new Array(8).fill(0)  
  let t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0, t6 = 0, t7 = 0, t8 = 0

  if (tpc1 == 'on'){
     t1 = 1
  }
  if (tpc2 == 'on'){
    t2 = 1
   }
  if (tpc3 == 'on'){
    t3 = 1
  }
  if (tpc4 == 'on'){
    t4 = 1
  }
  if (tpc5 == 'on'){
    t5 = 1
  }
  if (tpc6 == 'on'){
    t6 = 1
  }
  if (tpc7 == 'on'){
    t7 = 1
  }
  if (tpc8 == 'on'){
    t8 = 1
  }

  tpcs = [t1,t2,t3,t4,t5,t6,t7,t8]
 
  aluno.tpc = tpcs

  Student.insert(aluno)
  .then(data => res.render('index'))
  .catch(err => res.render('error', {error: err}))
;
  })

 // POST students/:id Edição

 router.post('/students/:id', function(req, res) {
  
  let num = req.body.numero;
  let nome = req.body.nome;
  let git = req.body.git;

  var aluno = new Object();
  aluno.numero = num
  aluno.nome = nome
  aluno.git = git

  let tpc1 = req.body.tpc1, tpc2 = req.body.tpc2,
      tpc3 = req.body.tpc3, tpc4 = req.body.tpc4,
      tpc5 = req.body.tpc5, tpc6 = req.body.tpc6,
      tpc7 = req.body.tpc7, tpc8 = req.body.tpc8

  let tpcs = new Array(8).fill(0)  
  let t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0, t6 = 0, t7 = 0, t8 = 0

  if (tpc1 == 'on'){
     t1 = 1
  }
  if (tpc2 == 'on'){
    t2 = 1
   }
  if (tpc3 == 'on'){
    t3 = 1
  }
  if (tpc4 == 'on'){
    t4 = 1
  }
  if (tpc5 == 'on'){
    t5 = 1
  }
  if (tpc6 == 'on'){
    t6 = 1
  }
  if (tpc7 == 'on'){
    t7 = 1
  }
  if (tpc8 == 'on'){
    t8 = 1
  }

  tpcs = [t1,t2,t3,t4,t5,t6,t7,t8]
 
  aluno.tpc = tpcs

  Student.update(aluno)
    .then(data => res.render('index'))
    .catch(err => res.render('error', {error: err}))
  ;
})

module.exports = router;
