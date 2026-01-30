package service;

import java.util.List;
import dao.FavoriteDAO;
import model.Product;

public class FavoriteService {

    private FavoriteDAO dao = new FavoriteDAO();

    // ✅ MUST RETURN BOOLEAN
    public boolean add(String email, int productId) {
        return dao.addFavorite(email, productId);
    }

    public boolean remove(String email, int productId) {
        return dao.removeFavorite(email, productId);
    }

    public List<Product> getFavorites(String email) {
        return dao.getFavorites(email);
    }
}
