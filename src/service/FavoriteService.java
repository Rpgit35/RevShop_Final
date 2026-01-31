package service;

import java.util.List;
import dao.FavoriteDAO;

public class FavoriteService {

    private FavoriteDAO dao = new FavoriteDAO();

    public boolean add(String email, int productId) {
        return dao.addFavorite(email, productId);
    }

    public boolean remove(String email, int productId) {
        return dao.removeFavorite(email, productId);
    }

    public List<Integer> getFavorites(String email) {
        return dao.getFavorites(email);
    }
}
