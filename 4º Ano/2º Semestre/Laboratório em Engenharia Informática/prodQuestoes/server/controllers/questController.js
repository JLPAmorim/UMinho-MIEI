// Controlador para o modelo Tarefa

var Question = require('../models/question')

// Devolve a lista de Tarefas
module.exports.listar = () => {
    return Question
        .find()
        .exec()
}

module.exports.consultar = id => {
    return Question
        .findOne({id: id})
        .exec()
}

module.exports.inserir = q => {
    var novo = new Question(q)
    return novo.save()
}

module.exports.remover = function(id){
    return Question.deleteOne({id: id})
}

module.exports.alterar = function(q){
    return Question.findByIdAndUpdate({_id: q._id}, q, {new: true})
}