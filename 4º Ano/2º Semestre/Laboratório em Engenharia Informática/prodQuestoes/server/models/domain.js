const mongoose = require('mongoose')

var domainSchema = new mongoose.Schema(
  {
    id: String,
    description: String,
    study_cycle: String,
    scholarity: String,
    responsible: String,
    notes: String,
    access_type: String,
    body: [{
      subdomain: String,
      sub_description: String,
    }],
    default_user_level: String,
    high_performance_factor: String,
    low_performance_factor: String,
    high_skill_factor: String,
    low_skill_factor: String,
    min_questions_number: String,
    question_factor: String,
    inserted_by: String,
    inserted_at: String    
  });

module.exports = mongoose.model('domain', domainSchema)