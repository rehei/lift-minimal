package model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue

@Entity
class Person {

  @Id
  @GeneratedValue
  var id: Long = 0
  
  var name: String = ""
  
  var age: Int = 0
  
}