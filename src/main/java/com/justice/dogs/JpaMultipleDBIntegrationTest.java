// package com.justice.dogs;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.transaction.annotation.EnableTransactionManagement;

// import com.justice.dogs.dogsHolder.Dog;
// import com.justice.dogs.dogsHolder.DogsRepo;
// import com.justice.dogs.users.UserObj;
// import com.justice.dogs.users.UserRepo;

// import org.springframework.transaction.annotation.Transactional;

// @EnableTransactionManagement
// public class JpaMultipleDBIntegrationTest {

//     @Autowired
//     private DogsRepo dogsRepo;

//     @Autowired
//     private UserRepo userRepo;

//     @Transactional("dogsTransactionManager")
//     public void whenCreatingDog_thenCreated() {
//         Dog newDog = new Dog();
//         newDog.setBreed("French Bulldog");
//         newDog.setColor("Tan");
//         newDog.setName("Pibble");
//         newDog = dogsRepo.save(newDog);
//     }

//     @Transactional("usersTransactionManager")
//     public void whenCreatingUser_thenCreated() {
//         UserObj user = new UserObj();
//         user.setUsername("user1");
//         user.setEmail("email@email.com");
//         user.setPassword("password123");
//         user = userRepo.save(user);
//     }
// }
