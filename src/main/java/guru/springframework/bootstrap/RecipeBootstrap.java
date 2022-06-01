package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        //get UOMs
        //UnitOfMeasure uom = new UnitOfMeasure();
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Each Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if(!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Tablespoon Not Found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if(!teaSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Teaspoon Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Dash Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Pint Not Found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cups");

        if(!cupsUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Cups Not Found");
        }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaspoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();

        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category American Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Mexican Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        //Yummy Guac
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n");
        guacNotes.setRecipe(guacRecipe);
        guacRecipe.setNotes(guacNotes);

        //very redundent - could add helper method, and make this simpler
        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom, guacRecipe));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoonUom, guacRecipe));
        guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom, guacRecipe));
        guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom, guacRecipe));
        guacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom, guacRecipe));
        guacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom, guacRecipe));
        guacRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom, guacRecipe));
        guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom, guacRecipe));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        //add to return list
        recipes.add(guacRecipe);

        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n");
        tacoNotes.setRecipe(tacosRecipe);
        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom, tacosRecipe));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        recipes.add(tacosRecipe);
        return recipes;
    }

}