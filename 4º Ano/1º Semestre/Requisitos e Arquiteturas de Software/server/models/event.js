const mongoose = require('mongoose')

var eventSchema = new mongoose.Schema(
  
    {
        sport: String,
        team1: String, 
        team2: String, 
        result_odd: { 
            home: String, 
            tie: String, 
            away: String 
        },
        date: String,
        result1: String,
        result2: String,
        winner: String, 
        state: String
  });

module.exports = mongoose.model('event', eventSchema)