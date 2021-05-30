import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    List<String> selectedItemNames;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime currentTimeMock = LocalTime.parse("20:30:00");
        restaurant = this.getRestaurantTestObject();
        Restaurant restaurantSpy = Mockito.spy(restaurant);

        Mockito.when(restaurantSpy.getCurrentTime()).thenReturn(currentTimeMock);
        Assertions.assertEquals(true, restaurantSpy.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime currentTimeMock = LocalTime.parse("22:30:00");
        restaurant = this.getRestaurantTestObject();
        Restaurant restaurantSpy = Mockito.spy(restaurant);

        Mockito.when(restaurantSpy.getCurrentTime()).thenReturn(currentTimeMock);
        Assertions.assertEquals(false, restaurantSpy.isRestaurantOpen());
    }

    private Restaurant getRestaurantTestObject(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        return new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant = this.getRestaurantTestObject();
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant = this.getRestaurantTestObject();
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant = this.getRestaurantTestObject();
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void restaurant_menu_order_value_for_multiple_items_should_match_sum_of_item_values(){
        long sumValue_Expected = 30;

        restaurant = this.getRestaurantTestObject();
        restaurant.addToMenu("Item1", 10);
        restaurant.addToMenu("Item2", 20);
        restaurant.addToMenu("Item3", 30);
        restaurant.addToMenu("Item4", 40);
        selectedItemNames = new ArrayList<String>();
        selectedItemNames.add("Item1");
        selectedItemNames.add("Item2");

        Assertions.assertEquals(sumValue_Expected, restaurant.getOrderValue(selectedItemNames));
    }

    @Test
    public void restaurant_menu_order_value_for_zero_item_should_be_zero(){
        restaurant = this.getRestaurantTestObject();
        Assertions.assertEquals(0, restaurant.getOrderValue(new ArrayList<String>()));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}