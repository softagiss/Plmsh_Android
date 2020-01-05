package softagi.saqah.Ui.Categories;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import softagi.saqah.Models.CartModel;
import softagi.saqah.Models.CategoryModel;

public class CategoriesViewModel extends ViewModel
{
    private CategoriesRepository categoriesRepository;

    public CategoriesViewModel()
    {
        categoriesRepository = new CategoriesRepository();
    }

    MutableLiveData<List<CategoryModel>> getCategories()
    {
        return categoriesRepository.getCategories();
    }
}
