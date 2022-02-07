// Controlador para o model event

var Event = require('../models/event')

// Devolve a lista de eventos
module.exports.listar = () => {
    return Event
        .find()
        .exec()
}

module.exports.consultar = id => {
    return Event
        .findOne({id: id})
        .exec()
}

module.exports.inserir = q => {
    var novo = new Event(q)
    return novo.save()
}

module.exports.remover = function(id){
    return Event.deleteOne({id: id})
}

module.exports.alterar = function(q){
    return Event.findByIdAndUpdate({_id: q._id}, q, {new: true})
}