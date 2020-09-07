package com.in28minutes.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;


import org.hamcrest.CoreMatchers;
import org.junit.Test;

import com.in28minutes.data.api.TodoService;
import static org.mockito.BDDMockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class TodoBusinessImplMockitoTest {

	@Test
	public void usingMockito() {
		TodoService todoService = mock(TodoService.class);
		List<String> allTodos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");
		when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
		List<String> todos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Ranga");
		assertEquals(2, todos.size());
	}

	@Test
	public void usingMockito_UsingBDD() {
		TodoService todoService = mock(TodoService.class);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
		List<String> allTodos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");

		//given
		BDDMockito.given(todoService.retrieveTodos("Ranga")).willReturn(allTodos);

		//when
		List<String> todos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Ranga");

		//thenReturn
		assertThat(todos.size(), CoreMatchers.is(2));
	}

	@Test
	public void testToDOBusinessImplTest_Values(){
		TodoService service = mock(TodoService.class);
		List<String> todos = Arrays.asList("Spring core", "Spring", "java");
		when(service.retrieveTodos("Dummy")).thenReturn(todos);
		TodoBusinessImpl impl = new TodoBusinessImpl(service);
		assertEquals(2,impl.retrieveTodosRelatedToSpring("Dummy").size() );
	}

	@Test
	public void letsTestDeleteNow() {

		TodoService todoService = mock(TodoService.class);

		List<String> allTodos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance","Learn to SAP");

		when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);

		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);

		todoBusinessImpl.deleteTodosNotRelatedToSpring("Ranga");

		verify(todoService).deleteTodo("Learn to Dance");
		verify(todoService).deleteTodo("Learn to SAP");

		verify(todoService, Mockito.never()).deleteTodo("Learn Spring MVC");

		verify(todoService, Mockito.never()).deleteTodo("Learn Spring");

		verify(todoService, Mockito.times(1)).deleteTodo("Learn to Dance");
		// atLeastOnce, atLeast

	}

	@Test
	public void testArgumentCapture(){
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor
				.forClass(String.class);
		List<String> listToDo = Arrays.asList("Java","Spring","Spring MVC");

		TodoService service = mock(TodoService.class);
		when(service.retrieveTodos("Soumitra")).thenReturn(listToDo);

		TodoBusinessImpl impl = new TodoBusinessImpl(service);
		impl.deleteTodosNotRelatedToSpring("Soumitra");
		verify(service).deleteTodo("Java");
		verify(service).deleteTodo(argumentCaptor.capture());
		assertEquals("Java",argumentCaptor.getValue());

	}

	@Test
	public void argumentCaptorSizeTest(){
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		List<String> todos = Arrays.asList("Java", "Kotlin", "Spring");
		TodoService service = mock(TodoService.class);
		when(service.retrieveTodos("Soumitra")).thenReturn(todos);

		TodoBusinessImpl impl = new TodoBusinessImpl(service);
		impl.deleteTodosNotRelatedToSpring("Soumitra");
		then(service).should(times(2)).deleteTodo(captor.capture());

		assertEquals(2,captor.getAllValues().size());
	}

}