package com.example.crud.controller;

import com.example.crud.exception.StudentException;
import com.example.crud.model.Student;
import com.example.crud.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
/*@Log4j2
@Controller*/
@RequestMapping("api/v1")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    @GetMapping("/users")
    public List<Student> getAllStudent() throws Exception
    {return studentRepository.findAll();
    }
//    @GetMapping("/test")
//    public String test(){
//        return "hello hari";
//    }
    @GetMapping("{id}")
    public ResponseEntity<Optional<Student>> getEmployeeById(@PathVariable long id) {
        Optional<Student> usr = studentRepository.findById(id);
        return ResponseEntity.ok(usr);
    }
    @PostMapping("/users")
    public ResponseEntity<Response> createUser(@RequestBody Student user) {
        Response response = null;
        String test=user.getGender();
        if (!test.equals("male") && !test.equals("female") && !test.equals("transgend")) {
            System.out.println(user.getGender());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else{
            Student stud = studentRepository.save(user);
    }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<Student> updateUser(@PathVariable long id,@RequestBody Student studentDetails) throws StudentException {
        Student updateStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(id + " does not exists"));

        System.out.println("updating employees details");


        updateStudent.setName(studentDetails.getName());
        studentRepository.save(updateStudent);

        return ResponseEntity.ok(updateStudent);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id){
        if(studentRepository.existsById(id))
            studentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
