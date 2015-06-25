package controllers.fridgeController;

import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.ControllerTestBase;
import models.fridge.Fridge;
import org.apache.http.client.methods.RequestBuilder;
import org.hamcrest.Matcher;
import play.test.Helpers;
import biz.fridge.FridgeService;
import controllers.FridgeController;
import datalayer.FridgeRepository;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import play.mvc.Http;
import static play.mvc.Http.Status.OK;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static play.test.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class FridgeControllerTest_RemoveItem extends ControllerTestBase{

    @Mock
    private FridgeService fridgeService;

    @Mock
    private FridgeRepository fridgeRepository;

    private FridgeController fridgeController;
    private Result result;
    private String testuser;
    private Long testid;
    private Long wrongtestid;
    private Fridge testfridge;

    @Before
    public void setup() throws Exception {
        testuser = "testuser";
        testid = 1L;
        testfridge = new Fridge();
        when(fridgeService.removeItem(testid,testuser)).thenReturn(testfridge);
        doThrow(new IllegalArgumentException()).when(fridgeService).removeItem(wrongtestid, testuser);
        fridgeController = new FridgeController(fridgeRepository,fridgeService);

        Http.Context mockContext = getMockContext("");
        Http.Context.current.set(mockContext);
    }


    @Test
    public void testCallsRemoveItem(){
        fridgeController.removeItem(testuser,testid);
        verify(fridgeService, times(1)).removeItem(testid,testuser);
    }

    @Test
    public void testResultEqualsOK(){
        result = fridgeController.removeItem(testuser,testid);
        assertEquals(OK, status(result));
    }

    @Test
    public void testResultEqualsNotFoundWithWrongId(){
        result = fridgeController.removeItem(testuser,wrongtestid);
        assertEquals(NOT_FOUND, status(result));
    }

    @Test
    public void testContentTypeIsTextJsonAndCharsetIsUtf8(){
        result = fridgeController.removeItem(testuser,testid);
        assertEquals("application/json", contentType(result));
        assertEquals("utf-8", charset(result));
    }


}
