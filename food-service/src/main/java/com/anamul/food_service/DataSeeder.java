package com.anamul.food_service;


import com.anamul.food_service.entity.FoodEntity;
import com.anamul.food_service.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final FoodRepository foodRepository;

    @Override
    public void run(String... args) throws Exception {

        if (foodRepository.count() ==0) {

            var foods = Arrays.asList(


                    FoodEntity.builder()
                            .name("Vhuna Khichuri")
                            .description("A flavorful rice and lentil dish cooked with spices, ghee, and aromatic herbs.")
                            .category("Local Classics")
                            .price(180)
                            .imageUrl("https://rumkisgoldenspoon.com/wp-content/uploads/2021/05/Bhuna-khichuri-recipe.jpg")
                            .build(),


                    FoodEntity.builder()
                            .name("Chicken Biryani")
                            .description("Gourmet chicken biryani with steamed basmati rice and rich, fragrant spices.")
                            .category("Local Classics")
                            .price(300)
                            .imageUrl("https://static.vecteezy.com/system/resources/thumbnails/024/650/050/small/gourmet-chicken-biryani-with-steamed-basmati-rice-generated-by-ai-free-photo.jpg")
                            .build(),

                    FoodEntity.builder()
                            .name("Beef Burger")
                            .description("Juicy beef patty with fresh lettuce, tomato, cheese, and special sauce in a toasted bun.")
                            .category("Fast Food")
                            .price(260)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNc-scqyC3Bh32rDGE2voDOyxrkRkc1q99Qw&s")
                            .build(),

                    FoodEntity.builder()
                            .name("Chicken Fry")
                            .description("Tender chicken pieces fried to golden perfection, juicy and flavorful.")
                            .category("Fast Food")
                            .price(260)
                            .imageUrl("https://st.depositphotos.com/1005891/2309/i/450/depositphotos_23093506-stock-photo-fried-chicken-on-square-white.jpg")
                            .build(),
                    FoodEntity.builder()
                            .name("Ramen Noodles")
                            .description("Japanese-style ramen noodles in a rich broth with vegetables.")
                            .category("International Dishes")
                            .price(280)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9XUOR0-LuW-rJeMzWrxfXh0mt0PH7Y0Skww&s")
                            .build(),

                    FoodEntity.builder()
                            .name("Pizza")
                            .description("Cheesy pizza topped with rich tomato sauce and a crispy baked crust.")
                            .category("Fast Food")
                            .price(300)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTDA7AQFitZO1kx8L4ZlyYWaXFB5hdCyNpbYA&s")
                            .build(),
                    FoodEntity.builder()
                            .name("Mutton Biryani")
                            .description("Tender mutton cooked with fragrant basmati rice and traditional spices for a rich taste.")
                            .category("Local Classics")
                            .price(450)
                            .imageUrl("https://i.ytimg.com/vi/wlklb1hNnyM/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLCX1DbtQgVYBVSMlc4GQyJH9C3mqw")
                            .build(),

                    FoodEntity.builder()
                            .name("Normal Biryani")
                            .description("Classic chicken biryani with tender meat, aromatic spices, and perfectly cooked rice.")
                            .category("Local Classics")
                            .price(280)
                            .imageUrl("https://t4.ftcdn.net/jpg/07/17/99/31/360_F_717993139_kxPYtqfBHeEfZECdD4FtFSA5abyAfXFw.jpg")
                            .build(),
                    FoodEntity.builder()
                            .name("Nachos")
                            .description("Crunchy tortilla chips topped with melted cheese and savory seasonings.")
                            .category("Fast Food")
                            .price(180)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYA5_ImAi_-glIPwxlau3cJl_gHu864FUb1Q&s")
                            .build(),
                    FoodEntity.builder()
                            .name("Sushi")
                            .description("Fresh sushi rolls made with seasoned rice, seafood, and vegetables.")
                            .category("International Dishes")
                            .price(280)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSe0XkAAr8Zx9mNYjMzlahuvs0DRSQVxaHc0A&s")
                            .build(),

                    FoodEntity.builder()
                            .name("Berry Delight Dessert")
                            .description("Creamy dessert layered with fresh berries and sweet syrup.")
                            .category("Desserts")
                            .price(200)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZGABg98pQYNi7_gmDlGzX7FNhwsimPzDKTg&s")
                            .build(),

                    FoodEntity.builder()
                            .name("Roshmalai")
                            .description("Soft cottage cheese dumplings soaked in rich saffron milk.")
                            .category("Desserts")
                            .price(420)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSNiDe0-9nZNFWp5LmlH1kCh97kD-VB4cs8mQ&s")
                            .build(),


                    FoodEntity.builder()
                            .name("Thai Vegetable Soup")
                            .description("Light and flavorful Thai soup with fresh vegetables and aromatic herbs.")
                            .category("International Dishes")
                            .price(100)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNqMVophWQE8P8NywSe90e7AgtSrSh0l1LlA&s")
                            .build(),


                    //
                    FoodEntity.builder()
                            .name("Masala Chai (Milk Tea)")
                            .description("Authentic spiced Indian/Bangladeshi tea with milk, cardamom, ginger, cinnamon, and sugar.")
                            .category("Coffee and Tea")
                            .price(30)
                            .imageUrl("https://images.unsplash.com/photo-1571934811356-5cc061b6821f?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80")
                            .build(),
                    FoodEntity.builder()
                            .name("Payesh")
                            .description("Traditional Bengali rice pudding cooked with milk and aromatic sweetness.")
                            .category("Desserts")
                            .price(180)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvCdydajNqaGVQypTLp9xMjuvrxe9fZu7MMw&s")
                            .build(),

                    FoodEntity.builder()
                            .name("French Fries")
                            .description("Golden and crispy French fries, perfectly salted and served hot.")
                            .category("Fast Food")
                            .price(120)
                            .imageUrl("https://c4.wallpaperflare.com/wallpaper/666/302/422/food-potato-french-fries-hd-wallpaper-preview.jpg")
                            .build(),

                    FoodEntity.builder()
                            .name("Balanced Diet Package")
                            .description("A nutritious meal package with balanced protein, carbs, and fresh vegetables.")
                            .category("Specialty Diets")
                            .price(400)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS2dNQ8JRxdSM1hV6VckX-jFpjpXykECGUAvA&s")
                            .build(),

                    FoodEntity.builder()
                            .name("Hamburger")
                            .description("Classic hamburger with a tender beef patty, fresh veggies, and a soft bun.")
                            .category("Fast Food")
                            .price(230)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5-ew5WuP9VcHflAaHfkY3n6Btm6sgAzM8Fg&s")
                            .build(),

                    FoodEntity.builder()
                            .name("Chicken Burger")
                            .description("Crispy chicken fillet with lettuce, tomato, and creamy sauce in a soft bun.")
                            .category("Fast Food")
                            .price(220)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSFa5QQl6rcN9-CKpk6qLx2qk9Kn91_UE2l-g&s")
                            .build(),

                    FoodEntity.builder()
                            .name("Fresh Salad Bowl")
                            .description("Freshly prepared salad with crisp greens, vegetables, and light dressing.")
                            .category("Specialty Diets")
                            .price(150)
                            .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJVsChoIF68YNCJulZaHopAwudQFqHfHObvQ&s")
                            .build(),

                    FoodEntity.builder()
                            .name("Cappuccino")
                            .description("Rich espresso, steamed milk, and thick foam dusted with cocoa.")
                            .category("Coffee and Tea")
                            .price(120)
                            .imageUrl("https://images.unsplash.com/photo-1517256064527-09c73fc73e38?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80")
                            .build()

            );

            foodRepository.saveAll(foods);

            System.out.println("Seeded " + foods.size() + " realistic food items with image URLs into MongoDB");
        } else {
            System.out.println("Database already has data – skipping initial seed");
        }
    }
}