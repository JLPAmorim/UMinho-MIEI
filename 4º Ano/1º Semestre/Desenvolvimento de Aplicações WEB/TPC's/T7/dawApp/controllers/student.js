// Student controller

var Student = require('../models/student')

// Returns student list
module.exports.list = () => {
    return Student
        .find()
        .sort({nome:1})
        .exec()
}

// Find student
module.exports.lookUp = id => {
    return Student
        .findOne({numero: id})
        .exec()
}

// Insert student
module.exports.insert = student => {
    var newStudent = new Student(student)
    return newStudent.save()
}

// Update student 
module.exports.update = student=> {
    return Student.updateOne({ numero: student.numero },{numero:student.numero , nome:student.nome, git:student.git, tpc:student.tpc});
};

// Delete student
module.exports.delete = (id) => {
    return Student
        .deleteOne({ numero: id })
        .exec()
}