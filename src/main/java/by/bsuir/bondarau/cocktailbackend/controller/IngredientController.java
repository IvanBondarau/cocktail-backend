package by.bsuir.bondarau.cocktailbackend.controller;

import by.bsuir.bondarau.cocktailbackend.exception.NotFoundException;
import by.bsuir.bondarau.cocktailbackend.model.Ingredient;
import by.bsuir.bondarau.cocktailbackend.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ingredient")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping(value = "/{name}")
    public ResponseEntity<Ingredient> findByName(@PathVariable String name) {
        return ResponseEntity.of(ingredientRepository.findByName(name));
    }

    @PostMapping
    public Ingredient save(@RequestBody Ingredient ingredient) {
        var savedIngredient = ingredientRepository.findByName(ingredient.getName());
        if (savedIngredient.isPresent()) {
            return savedIngredient.get();
        } else {
            ingredient = ingredientRepository.save(ingredient);
            return ingredient;
        }
    }

    @DeleteMapping(value = "/{name}")
    public void delete(@PathVariable String name) {
        ingredientRepository.delete(
                ingredientRepository.findByName(name).orElseThrow(NotFoundException::new)
        );
    }

    @GetMapping
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

}
