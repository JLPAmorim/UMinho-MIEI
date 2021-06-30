// Controlador para o modelo Dominio

var Domain = require('../models/domain')

// Devolve a lista de Dominios
module.exports.listar = () => {
    return Domain
        .find()
        .exec()
}

module.exports.consultar = id => {
    return Domain
        .findOne({id: id})
        .exec()
}

module.exports.inserir = d => {
    var novo = new Domain(d)
    return novo.save()
}

module.exports.remover = function(id){
    return Domain.deleteOne({id: id})
}

module.exports.alterar = function(d){
    return Domain.findByIdAndUpdate({_id: d._id}, d, {new: true})
}