const mongoose = require('mongoose')

var questionSchema = new mongoose.Schema(
  {
    id: String,
    language: String,
    study_cycle: String,
    scholarity: String,
    domain: String,
    subdomain: String,
    subsubdomain: String,
    difficulty_level: Number,
    author: String,
    display_mode: String,
    answering_time: Number,
    type: String,
    precedence: [String],
    repetitions: Number,
    body: [{
      answer: String,
      correction: Number,
      mandatory: Number,
      eliminative: Number,
      points: Number
    }],
    explanation: String,
    images: String,
    videos: String,
    source: String,
    notes: String,
    status: String,
    inserted_by: String,
    inserted_at: String,
    validated_by: String,
    validated_at: String
  });

module.exports = mongoose.model('question', questionSchema)