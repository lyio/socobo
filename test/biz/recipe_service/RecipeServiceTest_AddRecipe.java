package biz.recipe_service;

import biz.recipe.RecipeService;
import datalayer.RecipeRepository;
import datalayer.UserRepository;
import models.recipes.Recipe;
import models.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static biz.LambdaMatcher.argThat;


public class RecipeServiceTest_AddRecipe {
    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UserRepository userRepository;

    private User testUser;

    private Recipe expectedRecipe;

    private RecipeService serviceUnderTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        testUser = new User();
        testUser.userName = "test user name";
        expectedRecipe = new Recipe();
        expectedRecipe.name = "SpongeBobs Squarepants";

        when(recipeRepository.save(any(Recipe.class))).thenReturn(expectedRecipe);
        when(userRepository.findByUserName(eq(testUser.userName))).thenReturn(testUser);

        serviceUnderTest = new RecipeService(recipeRepository, userRepository);
    }

    @Test
    public void testAddRecipe_Calls_RecipeRepository_Once() {
        serviceUnderTest.addRecipe(testUser.userName, expectedRecipe);
        verify(recipeRepository, times(1)).save(eq(expectedRecipe));
    }

    @Test
    public void testAddRecipe_Sets_Owner_Of_Recipe() throws Exception {
        serviceUnderTest.addRecipe(testUser.userName, expectedRecipe);
        verify(recipeRepository, times(1)).save(argThat(
                (Recipe r) -> testUser.userName.equals(r.owner.userName),
                "Owner of recipe should be added and should be same as user name"
        ));
    }

    @Test
    public void testAddRecipe_Calls_UserRepository_Once() throws Exception {
        serviceUnderTest.addRecipe(testUser.userName, expectedRecipe);
        verify(userRepository, times(1)).findByUserName(eq(testUser.userName));
    }

    @Test
    public void testAddRecipe_Sets_CreationDate() throws Exception {
        serviceUnderTest.addRecipe(testUser.userName, expectedRecipe);
        verify(recipeRepository, times(1)).save(argThat((Recipe r) -> r.createdAt != null, "createdAt should not be null"));
    }
}

