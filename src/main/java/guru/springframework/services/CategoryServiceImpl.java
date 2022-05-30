package guru.springframework.services;

import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Set<Category> getCategories() {
        Set<Category> categorySet = new HashSet<>();
        categoryRepository.findAll().iterator().forEachRemaining(categorySet::add);
        return categorySet;
    }

    @Override
    public Category findById(Long l) {
        Optional<Category> categoryOptional = categoryRepository.findById(l);

        if(!categoryOptional.isPresent()) {
            throw new RuntimeException("no such category");
        }
        return categoryOptional.get();
    }
}
