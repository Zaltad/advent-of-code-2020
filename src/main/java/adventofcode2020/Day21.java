package adventofcode2020;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class Day21 {

  public String solve(InputStream inputStream) throws FileNotFoundException {
    List<String> lines = Utils.readLines(inputStream);
    Map<String, Set<String>> allergensToIngredients = new HashMap<>();
    lines.forEach(line -> {
      String[] parts = line.split(" \\(contains ");
      Set<String> ingredients = Arrays.stream(parts[0].split(" "))
          .collect(Collectors.toSet());
      Set<String> allergens = Arrays.stream(parts[1].replace(")", "").split(", "))
          .collect(Collectors.toSet());
      allergens.forEach(allergen -> {
        if (!allergensToIngredients.containsKey(allergen)) {
          allergensToIngredients.put(allergen, new HashSet<>(ingredients));
        } else {
          allergensToIngredients.get(allergen).retainAll(ingredients);
        }
      });
    });
    Map<String, String> ingredientsToAllergens = getIngredientsToAllergens(allergensToIngredients);
    return ingredientsToAllergens.entrySet().stream()
        .sorted(Entry.comparingByValue())
        .map(Entry::getKey)
        .collect(Collectors.joining(","));
  }

  private Map<String, String> getIngredientsToAllergens(
      Map<String, Set<String>> allergensToIngredients) {
    Map<String, String> ingredientsToAllergens = new HashMap<>();
    while (true) {
      String ingredient = null;
      for (var entry : allergensToIngredients.entrySet()) {
        if (entry.getValue().size() == 1) {
          ingredient = entry.getValue().iterator().next();
          ingredientsToAllergens.put(ingredient, entry.getKey());
          break;
        }
      }
      if (ingredient == null) {
        break;
      }
      for (var entry : allergensToIngredients.entrySet()) {
        entry.getValue().remove(ingredient);
      }
    }
    return ingredientsToAllergens;
  }
}