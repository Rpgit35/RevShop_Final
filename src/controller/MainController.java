package controller;

import java.util.List;
import java.util.Scanner;

import model.Order;
import model.Product;
import model.User;

import service.CartService;
import service.FavoriteService;
import service.NotificationService;
import service.OrderService;
import service.ProductService;
import service.ReviewService;
import service.UserService;

public class MainController {

    private static UserService userService = new UserService();
    private static ProductService productService = new ProductService();
    private static CartService cartService = new CartService();
    private static OrderService orderService = new OrderService();
    private static ReviewService reviewService = new ReviewService();
    private static FavoriteService favoriteService = new FavoriteService();
    private static NotificationService notifier = new NotificationService();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== RevShop =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                register(sc);
            } else if (choice == 2) {
                login(sc);
            } else if (choice == 3) {
                forgotPassword(sc);
            } else {
                System.out.println("Goodbye!");
                break;
            }
        }
        sc.close();
    }

    // ================= REGISTER =================
    private static void register(Scanner sc) {
        User u = new User();

        System.out.print("Name: ");
        u.setName(sc.nextLine());

        System.out.print("Email: ");
        u.setEmail(sc.nextLine());

        System.out.print("Password: ");
        u.setPassword(sc.nextLine());

        System.out.print("Role (BUYER/SELLER): ");
        u.setRole(sc.nextLine().toUpperCase());

        System.out.print("Security Question: ");
        u.setSecurityQuestion(sc.nextLine());

        System.out.print("Security Answer: ");
        u.setSecurityAnswer(sc.nextLine());

        boolean result = userService.registerUser(u);
        System.out.println(result ? "Registration successful!" : "Registration failed!");
    }

    // ================= LOGIN =================
    private static void login(Scanner sc) {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = userService.login(email, password);

        if (user == null) {
            System.out.println("Invalid login!");
            return;
        }

        System.out.println("Welcome " + user.getName());

        if ("SELLER".equalsIgnoreCase(user.getRole())) {
            sellerMenu(sc, user);
        } else {
            buyerMenu(sc, user);
        }
    }

    // ================= FORGOT PASSWORD =================
    private static void forgotPassword(Scanner sc) {
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        String question = userService.getSecurityQuestion(email);
        if (question == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Security Question: " + question);
        System.out.print("Answer: ");
        String answer = sc.nextLine();

        System.out.print("New Password: ");
        String newPass = sc.nextLine();

        boolean result = userService.resetPassword(email, answer, newPass);
        System.out.println(result ? "Password reset successful!" : "Password reset failed!");
    }

    // ================= BUYER MENU =================
    private static void buyerMenu(Scanner sc, User user) {
        while (true) {
            System.out.println("\n=== BUYER MENU ===");
            System.out.println("1. View Products");
            System.out.println("2. Search Products");
            System.out.println("3. Add to Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. My Orders");
            System.out.println("7. Add Review");
            System.out.println("8. Add Favorite");
            System.out.println("9. My Notifications");
            System.out.println("10. Logout");
            System.out.print("Choose: ");

            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1) {
                List<Product> list = productService.getAllProducts();
                for (Product p : list) {
                    System.out.println("--------------------");
                    System.out.println("ID: " + p.getProductId());
                    System.out.println("Name: " + p.getName());
                    System.out.println("Price: Rs." + p.getPrice());
                    System.out.println("Category: " + p.getCategory());
                    System.out.println("Stock: " + p.getStock());
                }
            }

            else if (ch == 2) {
                System.out.print("Enter keyword: ");
                String key = sc.nextLine();

                List<Product> list = productService.search(key);
                for (Product p : list) {
                    System.out.println(p.getProductId() + " | " + p.getName() + " | Rs." + p.getPrice());
                }
            }

            else if (ch == 3) {
                System.out.print("Product ID: ");
                int id = Integer.parseInt(sc.nextLine());

                System.out.print("Quantity: ");
                int qty = Integer.parseInt(sc.nextLine());

                cartService.addToCart(id, qty);
                System.out.println("Added to cart!");
            }

            else if (ch == 4) {
                cartService.viewCart();
            }

            else if (ch == 5) {
                orderService.checkout(cartService, user.getEmail(), sc);
                notifier.notify(user.getEmail(), "Your order has been placed!");
            }

            else if (ch == 6) {
                List<Order> list = orderService.myOrders(user.getEmail());
                for (Order o : list) {
                    System.out.println("Order#" + o.getOrderId()
                            + " | Total: Rs." + o.getTotalAmount()
                            + " | Date: " + o.getOrderDate());
                }
            }

            else if (ch == 7) {
                System.out.print("Product ID: ");
                int pid = Integer.parseInt(sc.nextLine());

                System.out.print("Rating (1-5): ");
                int rating = Integer.parseInt(sc.nextLine());

                System.out.print("Comment: ");
                String comment = sc.nextLine();

                boolean result = reviewService.addReview(pid, user.getEmail(), rating, comment);
                System.out.println(result ? "Review added!" : "Failed to add review");
            }

            else if (ch == 8) {
                System.out.print("Product ID: ");
                int pid = Integer.parseInt(sc.nextLine());

                boolean result = favoriteService.add(user.getEmail(), pid);
                System.out.println(result ? "Added to favorites!" : "Failed to add favorite");
            }

            else if (ch == 9) {
                List<String> notes = notifier.myNotifications(user.getEmail());
                for (String s : notes) {
                    System.out.println(s);
                }
            }

            else {
                return;
            }
        }
    }

    // ================= SELLER MENU =================
    private static void sellerMenu(Scanner sc, User user) {
        while (true) {
            System.out.println("\n=== SELLER MENU ===");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View My Orders");
            System.out.println("5. Low Stock Alerts");
            System.out.println("6. Logout");
            System.out.print("Choose: ");

            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1) {
                Product p = new Product();

                System.out.print("Name: ");
                p.setName(sc.nextLine());

                System.out.print("Description: ");
                p.setDescription(sc.nextLine());

                System.out.print("Price: ");
                p.setPrice(Double.parseDouble(sc.nextLine()));

                System.out.print("MRP: ");
                p.setMrp(Double.parseDouble(sc.nextLine()));

                if (p.getPrice() > p.getMrp()) {
                    System.out.println("ERROR: Price cannot be greater than MRP!");
                    continue;
                }

                System.out.print("Category: ");
                p.setCategory(sc.nextLine());

                System.out.print("Stock: ");
                p.setStock(Integer.parseInt(sc.nextLine()));

                System.out.print("Stock Threshold: ");
                p.setStockThreshold(Integer.parseInt(sc.nextLine()));

                p.setSellerEmail(user.getEmail());

                boolean result = productService.addProduct(p);
                System.out.println(result ? "Product added!" : "Failed to add product");
            }

            else if (ch == 2) {
                System.out.print("Product ID: ");
                int id = Integer.parseInt(sc.nextLine());

                System.out.print("New Price: ");
                double price = Double.parseDouble(sc.nextLine());

                System.out.print("New Stock: ");
                int stock = Integer.parseInt(sc.nextLine());

                boolean result = productService.updateProduct(id, price, stock);
                System.out.println(result ? "Product updated!" : "Update failed");
            }

            else if (ch == 3) {
                System.out.print("Product ID: ");
                int id = Integer.parseInt(sc.nextLine());

                boolean result = productService.deleteProduct(id);
                System.out.println(result ? "Product deleted!" : "Delete failed");
            }

            else if (ch == 4) {
                List<Order> list = orderService.sellerOrders(user.getEmail());
                for (Order o : list) {
                    System.out.println("Order#" + o.getOrderId()
                            + " | Buyer: " + o.getBuyerEmail()
                            + " | Total: Rs." + o.getTotalAmount());
                }
            }

            else if (ch == 5) {
                System.out.println("\n--- LOW STOCK PRODUCTS ---");
                for (Product p : productService.getAllProducts()) {
                    if (p.getSellerEmail().equals(user.getEmail()) &&
                        p.getStock() <= p.getStockThreshold()) {

                        System.out.println(p.getName()
                                + " | Stock: " + p.getStock()
                                + " | Threshold: " + p.getStockThreshold());
                    }
                }
            }

            else {
                return;
            }
        }
    }
}
