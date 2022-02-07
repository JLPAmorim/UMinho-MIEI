// Controlador para o model bet

var Bet = require('../models/bet')

// Devolve a lista de bets
module.exports.listar = () => {
    return Bet
        .find()
        .exec()
}

module.exports.consultar = id => {
    return Bet
        .findOne({id: id})
        .exec()
}

module.exports.inserir = d => {
    var novo = new Bet(d)
    return novo.save()
}

module.exports.remover = function(id){
    return Bet.deleteOne({id: id})
}

module.exports.alterar = function(d){
    return Bet.findByIdAndUpdate({_id: d._id}, d, {new: true})
}

module.exports.updateMany = function(arr){
    return Promise.all(arr.map(d => Bet.findByIdAndUpdate(d._id, d, {new: true})))
}