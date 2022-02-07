const mongoose = require('mongoose')
mongoose.set('useCreateIndex', true);

var userSchema = new mongoose.Schema(  
  
    {
        id: String,
        name: String,
        email: {
          unique: true,
          type: String
        },
        password: String,
        user_type: String,
        balance: {
          EUR: String,
          USD: String,
          GBP: String,
          ADA: String
        },
        currentCoin: String,
        balance_history: [{
          tipo: String,
          amountInicial: String,
          amountFinal: String,
          data: String,
          saldo_final: String
        }],
        bet_history :[{
          betID: String
        }],
        notificacoes: String
        
  });

module.exports = mongoose.model('user', userSchema)