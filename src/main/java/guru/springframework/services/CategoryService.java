package guru.springframework.services;

import guru.springframework.domain.Category;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface CategoryService {
    Set<Category> getCategories();
    Category findById(Long l);
}
