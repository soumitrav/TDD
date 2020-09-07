package com.in28minutes.business;

import com.in28minutes.data.api.TodoService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import static org.junit.Assert.*;
import java.util.Arrays;

//@RunWith(PowerMockRunner.class)
@RunWith(MockitoJUnitRunner.class)
public class ToDoTestWithMockitoInject {

    //@Rule
    //public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    TodoService todoService;

    @InjectMocks
    TodoBusinessImpl todoBusiness;

    @Captor
    ArgumentCaptor<String> captor;

    @Test
    public void testToDoService(){
        Mockito.when(todoService.retrieveTodos(Matchers.anyString())).thenReturn(Arrays.asList("Java","Spring","Kotline"));
        List<String> list = todoBusiness.retrieveTodosRelatedToSpring("Soumitra");
        Assert.assertEquals(1,list.size());
        verify(todoService).retrieveTodos("Soumitra");

    }

    @Test
    public void testToDoDeleteService(){
        Mockito.when(todoService.retrieveTodos(Matchers.anyString())).thenReturn(Arrays.asList("Java","Spring","Kotline"));
        todoBusiness.deleteTodosNotRelatedToSpring("Soumitra");
        verify(todoService).deleteTodo("Java");
        verify(todoService,times(2)).deleteTodo(captor.capture());
        assertTrue(captor.getAllValues().contains("Java"));
    }
}
