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
    difficulty_level: String,
    author: String,
    display_mode: String,
    answering_time: String,
    type: String,
    precedence: [String],
    repetitions: String,
    header: String,
    body: [{
      answer: String,
      correction: String,
      mandatory: String,
      eliminative: String,
      points: String
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