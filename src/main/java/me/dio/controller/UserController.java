    package me.dio.controller;

    import me.dio.domain.model.User;
    import me.dio.service.UserService;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

    import java.net.URI;

    @RestController
    @RequestMapping("/users")
    public class UserController {

        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping("/{id}")
        public ResponseEntity<User> findById(@PathVariable Long id) {
           var user = userService.findById(id);
           return ResponseEntity.ok(user);
        }

        @PostMapping
        public ResponseEntity<User> create(@RequestBody User userToCreate) {
            var userCreated = userService.create(userToCreate);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(userCreated.getId())
                    .toUri();
            return ResponseEntity.created(location).body(userCreated);
        }

        @PutMapping("/{id}")
        public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User updatedUser) {
            var user = userService.update(id, updatedUser);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            boolean deleted = userService.delete(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }

    }
